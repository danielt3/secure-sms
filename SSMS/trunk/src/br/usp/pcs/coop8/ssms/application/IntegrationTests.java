/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.application;

import br.usp.larc.pbarreto.smspairing.SMSCurve;
import br.usp.larc.pbarreto.smspairing.SMSCurve2;
import br.usp.larc.pbarreto.smspairing.SMSField4;
import br.usp.larc.pbarreto.smspairing.SMSPairing;
import br.usp.larc.pbarreto.smspairing.SMSParams;
import br.usp.larc.pbarreto.smspairing.SMSPoint;
import br.usp.larc.pbarreto.smspairing.SMSPoint2;
import br.usp.larc.pbarreto.smspairing.SMSTests;
import br.usp.pcs.coop8.ssms.protocol.BDCPS;
import br.usp.pcs.coop8.ssms.protocol.BDCPSAuthority;
import br.usp.pcs.coop8.ssms.protocol.BDCPSClient;
import br.usp.pcs.coop8.ssms.protocol.BDCPSParameters;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import br.usp.pcs.coop8.ssms.protocol.exception.InvalidMessageException;
import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.util.Util;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import pseudojava.BigInteger;
import pseudojava.SecureRandom;

/**
 *
 * @author rodrigo
 */
public class IntegrationTests {
    
    protected static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _2 = BigInteger.valueOf(2L),
        _3 = BigInteger.valueOf(3L),
        _4 = BigInteger.valueOf(4L),
        _5 = BigInteger.valueOf(5L),
        _6 = BigInteger.valueOf(6L);

    protected static void mockUpBenchmark() {
        int i = 176, BM = 1;
        byte[] randSeed = new byte[20];
        (new SecureRandom()).nextBytes(randSeed);
        SecureRandom rnd = new SecureRandom(randSeed);
        long elapsed;
        Output.println("\n======== bits: " + i);
        SMSParams sms = new SMSParams(i);
        SMSCurve E = new SMSCurve(sms); //Output.println(E);
        SMSCurve2 E2 = new SMSCurve2(E); //Output.println(E2);
        SMSPoint P = E.getG();
        SMSPoint2 Q = E2.getGt();
        BigInteger s = new BigInteger(i, rnd).mod(sms.getN()); // master key
        SMSPoint Ppub = P.multiply(s);
        BigInteger h1ID_A = new BigInteger(i, rnd).mod(sms.getN()); // h_1(ID_A)
        BigInteger h1ID_B = new BigInteger(i, rnd).mod(sms.getN()); // h_1(ID_B)
        SMSPoint2 Q_A = Q.multiply(h1ID_A.add(s).modInverse(sms.getN()));
        SMSPoint2 Q_B = Q.multiply(h1ID_B.add(s).modInverse(sms.getN()));
        BigInteger x_A = new BigInteger(i, rnd).mod(sms.getN());
        BigInteger x_B = new BigInteger(i, rnd).mod(sms.getN());
        SMSPairing pair = new SMSPairing(E2);
        SMSField4 g = pair.ate(Q, P);
        SMSField4 y_A = g.exp(x_A);
        SMSField4 y_B = g.exp(x_B);
        BigInteger h_A = new BigInteger(i, rnd).mod(sms.getN()); // h_0(r_A, ID_A, y_A)
        BigInteger h_B = new BigInteger(i, rnd).mod(sms.getN()); // h_0(r_B, ID_B, y_B)
        SMSPoint2 T_A = Q_A;//.multiply(u_A.subtract(x_A.multiply(h_A)).mod(sms.n));
        SMSPoint2 T_B = Q_A;//.multiply(u_B.subtract(x_B.multiply(h_B)).mod(sms.n));
    	BigInteger h = new BigInteger(i, rnd).mod(sms.getN());
    	BigInteger z = new BigInteger(i, rnd).mod(sms.getN());

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking Eta Pairing:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            SMSField4 f = pair.eta(P, Q);
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking Ate Pairing:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            SMSField4 f = pair.ate(Q, P);
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking BDCPS Private-Key-Extract:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            Q_A = Q.multiply(h1ID_A.add(s).modInverse(sms.getN()));
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking BDCPS Check-Private-Key:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            SMSField4 f = pair.ate(Q_A, P.multiply(h1ID_A).add(Ppub));
            boolean ok = f.equals(g);
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking BDCPS Set-Public-Value:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            y_A = g.exp(x_A);
        	byte[] y_A_comp = y_A.toByteArray(); // key compression
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking BDCPS Set-Public-Key:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
        	BigInteger u_A = new BigInteger(i, rnd).mod(sms.getN());
            SMSField4 r_A = g.exp(u_A);
            T_A = Q_A.multiply(u_A.subtract(x_A.multiply(h_A)).mod(sms.getN()));
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking BDCPS Public-Key-Validate:");
        byte[] y_A_comp = y_A.toByteArray(); // key compression
        elapsed = -System.currentTimeMillis();
        //Output.println("y_A = " + y_A);
        for (int t = 0; t < BM; t++) {
        	SMSField4 y_A_exp = new SMSField4(sms, y_A_comp, 0); // key expansion
            SMSField4 r_A = pair.ate(T_A, P.multiply(h1ID_A).add(Ppub)).multiply(y_A.exp(h_A));
            //SMSField4 one = y_A_exp.exp(sms.n);
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking BDCPS Signcrypt:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
        	BigInteger u = new BigInteger(i, rnd).mod(sms.getN());
            //SMSField4 o = g.exp(u);
            SMSField4 r = y_B.exp(u);
            z = u.subtract(x_A.multiply(h)).mod(sms.getN());
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking BDCPS Unsigncrypt:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
	        SMSField4 r = y_A.fastSimultaneous(h.multiply(x_B).mod(sms.getN()), z, y_B);
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking private RSA-" + 4*sms.getP().bitLength());
        BigInteger p = BigInteger.probablePrime(2*sms.getP().bitLength(), rnd);
        BigInteger q = BigInteger.probablePrime(2*sms.getP().bitLength(), rnd);
        BigInteger u = q.modInverse(p);
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(_1).multiply(q.subtract(_1));
        BigInteger e = BigInteger.valueOf(65537L);
        BigInteger d = e.modInverse(phi);
        BigInteger m = new BigInteger(4*sms.getP().bitLength(), rnd).mod(n);
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            //m = m.modPow(d, n);
            //* // chinese remainder theorem:
            BigInteger mp = m.modPow(d, p);
            BigInteger mq = m.modPow(d, q);
            m = mp.subtract(mq).multiply(u).mod(p).multiply(q).add(mq);
            //*/
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        Output.println("Benchmarking public RSA-" + 4*sms.getP().bitLength());
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            m = m.modPow(e, n);
        }
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float)elapsed/BM + " ms.");
        Output.println("");
    }

    protected static void implBenchmark() {

        // Declaring
        Digest sha = null;
        sha = new SHA1Digest();
        int bits = 176;
        long elapsed;
        
        byte[] s;
        byte[] xa_alice = new byte[20];
        byte[] xb_bob = new byte[20];
        
        Output.println("\n======== bits: 176");
        
        // Initializing
        BDCPSParameters bdcpsPar = BDCPSParameters.getInstance(bits);

        String aliceKey = "hi i am alice";
        String bobKey = "hi i am bob marley";

        String id_a = "551199991234";
        String id_b = "551188884321";
        String id_auth = "thegodfather";

        BigInteger s500 = new BigInteger("2811324208781249769788073818190026244636491661539281873387241581414870671338535758736366135621660583188369946048623028749823899797612403393315005280995");
        s = s500.mod(BDCPSParameters.getInstance(Configuration.K).N).toByteArray();

        // Hashing keys
        try {

            sha.reset();

            sha.update(aliceKey.getBytes(), 0, aliceKey.getBytes().length);
            sha.doFinal(xa_alice, 0);

            sha.reset();

            sha.update(bobKey.getBytes(), 0, bobKey.getBytes().length);
            sha.doFinal(xb_bob, 0);
            
        } catch (/*Digest*/Exception ex) {
            Output.println("BDCPS: Digest exception.");
            ex.printStackTrace();
            throw new RuntimeException("BDCPS: Digest exception.");
        }

        // Starting protocol instances
       
        BDCPS auth = new BDCPSAuthority(bits, s, id_auth.getBytes());
        BDCPS alice = new BDCPSClient(bits, bdcpsPar.PPubBytes /*auth.getPublicPoint()*/, id_a.getBytes());
        BDCPS bob = new BDCPSClient(bits, bdcpsPar.PPubBytes /*auth.getPublicPoint()*/, id_b.getBytes());

        // Set-Secret-Value
       
        alice.setSecretValue(xa_alice);
        bob.setSecretValue(xb_bob);
        
        // Set-Public-Value
       
        Output.println("Benchmarking Set-Public-Value");
        elapsed = -System.currentTimeMillis();
        alice.setPublicValue();
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float) elapsed + " ms.");
        
        bob.setPublicValue();

        // Private-Key-Extract
        
        Output.println("Benchmarking Private-Key-Extract");
        elapsed = -System.currentTimeMillis();
        byte[] Q_A = auth.privateKeyExtract(id_a.getBytes(), alice.getPublicValue());
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float) elapsed + " ms.");

        byte[] Q_B = auth.privateKeyExtract(id_b.getBytes(), bob.getPublicValue());
        
        // Set-Private-Key
        
        alice.setPrivateKey(Q_A);
        bob.setPrivateKey(Q_B);
        
        //Check-Private-Key
        
        Output.println("Benchmarking Check-Private-Key");
        elapsed = -System.currentTimeMillis();
        alice.checkPrivateKey(Q_A, alice.getPublicValue(), id_a.getBytes());
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float) elapsed + " ms.");
        
        bob.checkPrivateKey(Q_B, bob.getPublicValue(), id_b.getBytes());
        
        //Set-Public-Key
        Output.println("Benchmarking Set-Public-Key");
        elapsed = -System.currentTimeMillis();
        alice.setPublicKey();
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float) elapsed + " ms.");
        
        bob.setPublicKey();

        // Public-Key-Validate
        
        Output.println("Benchmarking Public-Key-Validate");
        elapsed = -System.currentTimeMillis();
        boolean isAliceValid = bob.publicKeyValidate(alice.getPublicKey(), id_a.getBytes());
        elapsed += System.currentTimeMillis();
        Output.println("Elapsed time: " + (float) elapsed + " ms.");        
        
        boolean isBobValid = alice.publicKeyValidate(bob.getPublicKey(), id_b.getBytes());
        
        /*
        if (isBobValid) {
            Output.println("Bob is valid!");
        } else {
            Output.println("Bob is false!");
        }
        if (isAliceValid) {
            Output.println("Alice is valid!");
        } else {
            Output.println("Alice is false!");
        }
        */

        String plaintext = "Hora da Pizza!";
        
        // Signcrypt
       
        byte[] m = plaintext.getBytes();
        byte[][] c = null;
        try {
            
            Output.println("Benchmarking Signcryption");
            elapsed = -System.currentTimeMillis();
            c = alice.signcrypt(m, id_b.getBytes(), bob.getPublicValue());
            elapsed += System.currentTimeMillis();
            Output.println("Elapsed time: " + (float) elapsed + " ms.");
        
            //Output.println("Criptograma[0]: " + Util.byteArrayToDebugableString(c[0]) + "\n");
            //Output.println("Criptograma[1]: " + Util.byteArrayToDebugableString(c[1]) + "\n");
            //Output.println("Criptograma[2]: " + Util.byteArrayToDebugableString(c[2]) + "\n");
        } catch (CipherException e) {
            e.printStackTrace();
        }

        // Unsigncrypt
       
        byte[] m_dec = null;
        try {
            Output.println("Benchmarking Unsigncryption");
            elapsed = -System.currentTimeMillis();
            m_dec = bob.unsigncrypt(c, id_a.getBytes(), alice.getPublicValue());
            elapsed += System.currentTimeMillis();
            Output.println("Elapsed time: " + (float) elapsed + " ms.");
            
            Output.println("Unsigncrypted message: " + Util.byteArrayToDebugableString(m_dec));
            
        } catch (InvalidMessageException e) {
            Output.println("BDCPS: Invalid Exception");
            Output.println("Unsigncrypt: Invalid message!");
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        if (m_dec != null) {
            Output.println("Plaintext: " + new String(m_dec));
        }
    }
}

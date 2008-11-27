package br.usp.larc.pbarreto.smspairing;
/**
 * SMSTests.java
 *
 * Copyright (C) Paulo S. L. M. Barreto.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import pseudojava.BigInteger;
import pseudojava.SecureRandom;
import java.io.IOException;

public class SMSTests {

    protected static final BigInteger
        _0 = BigInteger.valueOf(0L),
        _1 = BigInteger.valueOf(1L),
        _2 = BigInteger.valueOf(2L),
        _3 = BigInteger.valueOf(3L),
        _4 = BigInteger.valueOf(4L),
        _5 = BigInteger.valueOf(5L),
        _6 = BigInteger.valueOf(6L);

    /**
     * Generic prototypes used in the SMSPoint and SMSPoint2 tests.
     */
    SMSPoint prototype;
    SMSPoint2 prototype2;

    /**
     * Create an instance of SMSTests by providing prototypes
     * for SMSPoint and GF variables.
     *
     * This is a direct application of the "Prototype" design pattern
     * as described by E. Gamma, R. Helm, R. Johnson and J. Vlissides in
     * "Design Patterns - Elements of Reusable Object-Oriented Software",
     * Addison-Wesley (1995), pp. 117-126.
     *
     * @param   prototype   the prototype for SMSPoint instantiation
     */
    public SMSTests(SMSPoint prototype, SMSPoint2 prototype2) {
        this.prototype = prototype;
        this.prototype2 = prototype2;
    }

    /**
     * Perform a complete test suite on the SMSCurve implementation
     *
     * @param   iterations  the desired number of iterations of the test suite
     * @param   random      the source of randomness for the various tests
     */
    public void doTest(int iterations, SecureRandom rand, boolean verbose) {
        SMSPoint w, x, y, z, ecZero;
        BigInteger m, n;
        int numBits = 256; // caveat: maybe using larger values is better
        long totalElapsed = -System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            if (verbose) {
                System.out.print("test #" + i);
            }
            long elapsed = -System.currentTimeMillis();
            // create random values from the prototype:
            x = prototype.randomize(rand);
            y = prototype.randomize(rand);
            z = prototype.randomize(rand);
            ecZero = prototype.E.O;
            m = new BigInteger(numBits, rand);
            n = new BigInteger(numBits, rand);

            // check cloning/comparison/pertinence:
            if (iterations == 1) {
                System.out.print("\nchecking cloning/comparison/pertinence");
            }
            if (!x.equals(x)) {
                throw new RuntimeException("Comparison failure");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.isOnSameCurve(x)) {
                throw new RuntimeException("Inconsistent pertinence self-comparison");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.E.contains(x)) {
                throw new RuntimeException("Inconsistent curve pertinence");
            }
            if (verbose) {
                System.out.print(".");
            }

            // check addition properties:
            if (iterations == 1) {
                System.out.print(" done.\nchecking addition properties");
            }
            if (!x.add(y).equals(y.add(x))) {
                throw new RuntimeException("x + y != y + x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(ecZero).equals(x)) {
                throw new RuntimeException("x + 0 != x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(x.negate()).isZero()) {
                throw new RuntimeException("x + (-x) != 0");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(y).add(z).equals(x.add(y.add(z)))) {
                throw new RuntimeException("(x + y) + z != x + (y + z)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.negate().negate().equals(x)) {
                throw new RuntimeException("-(-x) != x");
            }

            // check scalar multiplication properties:
            if (iterations == 1) {
                System.out.print(" done.\nchecking scalar multiplication properties");
            }
            if (!x.multiply(BigInteger.valueOf(0L)).equals(ecZero)) {
                throw new RuntimeException("0*x != 0");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(1L)).equals(x)) {
                throw new RuntimeException("1*x != x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(2L)).equals(x.twice(1))) {
                throw new RuntimeException("2*x != twice x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(2L)).equals(x.add(x))) {
                throw new RuntimeException("2*x != x + x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(-1L)).equals(x.negate())) {
                throw new RuntimeException("(-1)*x != -x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.negate()).equals(x.negate().multiply(m))) {
                throw new RuntimeException("(-m)*x != m*(-x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.negate()).equals(x.multiply(m).negate())) {
                throw new RuntimeException("(-m)*x != -(m*x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.add(n)).equals(x.multiply(m).add(x.multiply(n)))) {
                throw new RuntimeException("(m + n)*x != m*x + n*x");
            }
            if (verbose) {
                System.out.print(".");
            }
            w = x.multiply(n).multiply(m);
            if (!w.equals(x.multiply(m).multiply(n))) {
                throw new RuntimeException("m*(n*x) != n*(m*x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!w.equals(x.multiply(m.multiply(n)))) {
                throw new RuntimeException("m*(n*x) != (m*n)*x");
            }
            // TODO: test point compression/expansion/conversion
            elapsed += System.currentTimeMillis();
            if (verbose) {
                System.out.println(" done; elapsed =  " + (float)elapsed/1000 + " s.");
            }
        }
        totalElapsed += System.currentTimeMillis();
        //if (verbose) {
            System.out.println(" OK; all " + iterations + " tests done in " + (float)totalElapsed/1000 + " s.");
        //}
    }

    /**
     * Perform a complete test suite on the SMSCurve2 implementation
     *
     * @param   iterations  the desired number of iterations of the test suite
     * @param   random      the source of randomness for the various tests
     */
    public void doTest2(int iterations, SecureRandom rand, boolean verbose) {
        SMSPoint2 w, x, y, z, ecZero;
        BigInteger m, n;
        int numBits = prototype2.E.sms.getP().bitLength();
        long totalElapsed = -System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            if (verbose) {
                System.out.print("test #" + i);
            }
            long elapsed = -System.currentTimeMillis();
            // create random values from the prototype:
            x = prototype2.randomize(rand);
            y = prototype2.randomize(rand);
            z = prototype2.randomize(rand);
            ecZero = prototype2.E.Ot;
            m = new BigInteger(numBits, rand);
            n = new BigInteger(numBits, rand);

            // check cloning/comparison/pertinence:
            if (iterations == 1) {
                System.out.print("\nchecking cloning/comparison/pertinence");
            }
            if (!x.equals(x)) {
                throw new RuntimeException("Comparison failure");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.isOnSameCurve(x)) {
                throw new RuntimeException("Inconsistent pertinence self-comparison");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.E.contains(x)) {
                throw new RuntimeException("Inconsistent curve pertinence");
            }
            if (verbose) {
                System.out.print(".");
            }

            // check addition properties:
            if (iterations == 1) {
                System.out.print(" done.\nchecking addition properties");
            }
            if (!x.add(y).equals(y.add(x))) {
                throw new RuntimeException("x + y != y + x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(ecZero).equals(x)) {
                throw new RuntimeException("x + 0 != x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(x.negate()).isZero()) {
                throw new RuntimeException("x + (-x) != 0");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.add(y).add(z).equals(x.add(y.add(z)))) {
                throw new RuntimeException("(x + y) + z != x + (y + z)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.negate().negate().equals(x)) {
                throw new RuntimeException("-(-x) != x");
            }

            // check scalar multiplication properties:
            if (iterations == 1) {
                System.out.print(" done.\nchecking scalar multiplication properties");
            }
            if (!x.multiply(BigInteger.valueOf(0L)).equals(ecZero)) {
                throw new RuntimeException("0*x != 0");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(1L)).equals(x)) {
                throw new RuntimeException("1*x != x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(2L)).equals(x.twice(1))) {
                throw new RuntimeException("2*x != twice x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(2L)).equals(x.add(x))) {
                throw new RuntimeException("2*x != x + x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(BigInteger.valueOf(-1L)).equals(x.negate())) {
                throw new RuntimeException("(-1)*x != -x");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.negate()).equals(x.negate().multiply(m))) {
                throw new RuntimeException("(-m)*x != m*(-x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.negate()).equals(x.multiply(m).negate())) {
                throw new RuntimeException("(-m)*x != -(m*x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!x.multiply(m.add(n)).equals(x.multiply(m).add(x.multiply(n)))) {
                throw new RuntimeException("(m + n)*x != m*x + n*x");
            }
            if (verbose) {
                System.out.print(".");
            }
            w = x.multiply(n).multiply(m);
            if (!w.equals(x.multiply(m).multiply(n))) {
                throw new RuntimeException("m*(n*x) != n*(m*x)");
            }
            if (verbose) {
                System.out.print(".");
            }
            if (!w.equals(x.multiply(m.multiply(n)))) {
                throw new RuntimeException("m*(n*x) != (m*n)*x");
            }
            // TODO: test point compression/expansion/conversion
            elapsed += System.currentTimeMillis();
            if (verbose) {
                System.out.println(" done; elapsed =  " + (float)elapsed/1000 + " s.");
            }
        }
        totalElapsed += System.currentTimeMillis();
        //if (verbose) {
            System.out.println(" OK; all " + iterations + " tests done in " + (float)totalElapsed/1000 + " s.");
        //}
    }

    public static void benchmarks(int BM, int fieldBits) {
        byte[] randSeed = new byte[20];
        (new SecureRandom()).nextBytes(randSeed);
        /*
        for (int i = 0; i < randSeed.length; i++) {
            randSeed[i] = (byte)i;
        }
        */
        SecureRandom rnd = new SecureRandom(randSeed);
        long elapsed;
        int lowerBound = (fieldBits != 0) ? fieldBits : SMSParams.nextValid(0);
        int upperBound = (fieldBits != 0) ? fieldBits : SMSParams.nextValid(-1);
        for (int i = lowerBound; i <= upperBound; i = SMSParams.nextValid(i)) {
            System.out.println("======== bits: " + i);
            SMSParams sms = new SMSParams(i);
            SMSCurve E = new SMSCurve(sms); //System.out.println(E);
            SMSCurve2 E2 = new SMSCurve2(E); //System.out.println(E2);
            SMSTests T = new SMSTests(E.G, E2.Gt);
            //T.doTest(10, rnd, true);
            //T.doTest2(10, rnd, true);
            SMSPoint P = E.G;
            SMSPoint2 Q = E2.Gt;
            System.out.println("-----------------");
            BigInteger k = new BigInteger(i, rnd);
            BigInteger kk = new BigInteger(i, rnd);
            /*
            System.out.println("Benchmarking SMSPoint:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            System.out.println("P = " + P);
            //*/
            /*
            System.out.println("Benchmarking SMSPoint2:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                Q = Q.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            System.out.println("Q = " + Q);
            //*/
            if (P.isZero()) {
                throw new RuntimeException("LOGIC ERROR!");
            }
            if (Q.isZero()) {
                throw new RuntimeException("LOGIC ERROR!");
            }
            SMSField4 f = sms.Fp4_0, g, a, b, c;
            SMSPairing pair = new SMSPairing(E2);

            //*
            System.out.println("\nTesting Eta Pairing:");
            g = pair.eta(E.G, E2.Gt);
            System.out.println("P   = " + E.G);
            System.out.println("n*P = " + E.G.multiply(sms.getN()));
            System.out.println("Q   = " + E2.Gt);
            System.out.println("n*Q = " + E2.Gt.multiply(sms.getN()));
            System.out.println("g   = " + g);
            System.out.println("g^n = " + g.exp(sms.getN()));
            a = pair.eta(E.G.twice(1), E2.Gt);
            b = pair.eta(E.G, E2.Gt.twice(1));
            c = g.square();
            System.out.println("eq? " + (a.equals(b) && b.equals(c)));
            for (int j = 0; j < 10; j++) {
                BigInteger m = new BigInteger(i, rnd);
                a = pair.eta(E.G.multiply(m), E2.Gt);
                b = pair.eta(E.G, E2.Gt.multiply(m));
                c = g.exp(m);
                System.out.println("eq? " + (a.equals(b) && b.equals(c)));
                if (!(a.equals(b) && b.equals(c)) || a.isOne()) {
                    throw new RuntimeException("LOGIC ERROR!");
                }
            }
            //*/

            //*
            System.out.println("\nTesting Ate Pairing:");
            g = pair.ate(E2.Gt, E.G);
            System.out.println("P   = " + E.G);
            System.out.println("n*P = " + E.G.multiply(sms.getN()));
            System.out.println("Q   = " + E2.Gt);
            System.out.println("n*Q = " + E2.Gt.multiply(sms.getN()));
            System.out.println("g   = " + g);
            System.out.println("g^n = " + g.exp(sms.getN()));
            a = pair.ate(E2.Gt.twice(1), E.G);
            b = pair.ate(E2.Gt, E.G.twice(1));
            c = g.square();
            System.out.println("eq? " + (a.equals(b) && b.equals(c)));
            for (int j = 0; j < 10; j++) {
                BigInteger m = new BigInteger(i, rnd);
                a = pair.ate(E2.Gt.multiply(m), E.G);
                b = pair.ate(E2.Gt, E.G.multiply(m));
                c = g.exp(m);
                System.out.println("eq? " + (a.equals(b) && b.equals(c)));
                if (!(a.equals(b) && b.equals(c)) || a.isOne()) {
                	System.out.println("a = " + a);
                	System.out.println("b = " + b);
                	System.out.println("c = " + c);
                    throw new RuntimeException("LOGIC ERROR!");
                }
            }
            //*/

			/*
			if (g != null) {
				continue;
			}
			//*/

            /*
            System.out.println("Benchmarking Eta Pairing:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.eta(P, Q);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            System.out.println("f = " + f);
            //*/
            /*
            System.out.println("Benchmarking Ate Pairing:");
            f = sms.Fp4_0;
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.ate(Q, P);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            System.out.println("f = " + f);
            //*/
            /*
            System.out.println("Benchmarking SMSField4 exponentiation:");
            f = pair.eta(P, Q);
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.exp(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            //*/
            //*
	        System.out.println("Benchmarking private RSA-" + 4*sms.getP().bitLength());
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
	        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            //*/

            //*
            System.out.println("Benchmarking Barbosa-Farshim key validation:");
            f = pair.ate(Q, P);
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.ate(Q, P);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BLMQ preprocessing:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking LXH preprocessing:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.ate(Q, P);
                f = pair.ate(Q, P);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking CLPKE-G_T preprocessing:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.exp(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BDCPS-G_T key validation:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.ate(Q, P);
                f = f.exp(k); f = f.exp(k);
                P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking CLPKE-G_1 preprocessing:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BDCPS-G_1 key validation:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                P = P.multiply(k);
                f = pair.ate(Q, P);
                P = P.simultaneous(k, kk, E.G);
                f = pair.ate(Q, P);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking Barbosa-Farshim signcryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.exp(k);
                P = P.multiply(k); P = P.multiply(k); Q = Q.simultaneous(k, k, Q);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BLMQ signcryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.exp(k);
                P = P.multiply(k); Q = Q.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking LXH signcryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.exp(k);
                f = f.exp(k);
                P = P.multiply(k);
                P = P.multiply(k);
                P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking CLPKE-G_T signcryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.exp(k); f = f.exp(k); f = f.exp(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BDCPS-G_T signcryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.exp(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking CLPKE-G_1 signcryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                P = P.multiply(k); P = P.multiply(k); P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BDCPS-G_1 signcryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

			/*
            System.out.println("Benchmarking BDCPS* signcryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
            	BigInteger qq = k.modInverse(sms.n); // pure Zheng
                f = f.exp(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            //*/

            System.out.println("Benchmarking Barbosa-Farshim unsigncryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.ate(Q, P); f = pair.ate(Q, P); f = pair.ate(Q, P); f = pair.ate(Q, P);
                P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BLMQ unsigncryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.ate(Q, P); f = pair.ate(Q, P);
                f = f.exp(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking LXH unsigncryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = pair.ate(Q, P); f = pair.ate(Q, P);
                f = pair.ate(Q, P); f = pair.ate(Q, P);
                f = f.exp(k);
                f = f.exp(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking CLPKE-G_T unsigncryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.exp(k); f = f.exp(k); f = f.exp(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BDCPS-G_T unsigncryption:");
            g = pair.ate(E2.Gt, E.G);
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                f = f.fastSimultaneous(k, kk, g);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking CLPKE-G_1 unsigncryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                P = P.multiply(k); P = P.multiply(k); P = P.multiply(k);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

            System.out.println("Benchmarking BDCPS-G_1 unsigncryption:");
            elapsed = -System.currentTimeMillis();
            for (int t = 0; t < BM; t++) {
                P = P.simultaneous(k, kk, E.G);
            }
            elapsed += System.currentTimeMillis();
            System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
            //*/
        }
    }

	public static void BDCPS(int i, int BM) {
        byte[] randSeed = new byte[20];
        (new SecureRandom()).nextBytes(randSeed);
        SecureRandom rnd = new SecureRandom(randSeed);
        long elapsed;
        System.out.println("\n======== bits: " + i);
        SMSParams sms = new SMSParams(i);
        SMSCurve E = new SMSCurve(sms); //System.out.println(E);
        SMSCurve2 E2 = new SMSCurve2(E); //System.out.println(E2);
        SMSPoint P = E.G;
        SMSPoint2 Q = E2.Gt;
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
        System.out.println("Benchmarking Eta Pairing:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            SMSField4 f = pair.eta(P, Q);
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking Ate Pairing:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            SMSField4 f = pair.ate(Q, P);
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking BDCPS Private-Key-Extract:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            Q_A = Q.multiply(h1ID_A.add(s).modInverse(sms.getN()));
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking BDCPS Check-Private-Key:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            SMSField4 f = pair.ate(Q_A, P.multiply(h1ID_A).add(Ppub));
            boolean ok = f.equals(g);
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking BDCPS Set-Public-Value:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            y_A = g.exp(x_A);
        	byte[] y_A_comp = y_A.toByteArray(); // key compression
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking BDCPS Set-Public-Key:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
        	BigInteger u_A = new BigInteger(i, rnd).mod(sms.getN());
            SMSField4 r_A = g.exp(u_A);
            T_A = Q_A.multiply(u_A.subtract(x_A.multiply(h_A)).mod(sms.getN()));
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking BDCPS Public-Key-Validate:");
        byte[] y_A_comp = y_A.toByteArray(); // key compression
        elapsed = -System.currentTimeMillis();
        //System.out.println("y_A = " + y_A);
        for (int t = 0; t < BM; t++) {
        	SMSField4 y_A_exp = new SMSField4(sms, y_A_comp, 0); // key expansion
            SMSField4 r_A = pair.ate(T_A, P.multiply(h1ID_A).add(Ppub)).multiply(y_A.exp(h_A));
            //SMSField4 one = y_A_exp.exp(sms.n);
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking BDCPS Signcrypt:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
        	BigInteger u = new BigInteger(i, rnd).mod(sms.getN());
            //SMSField4 o = g.exp(u);
            SMSField4 r = y_B.exp(u);
            z = u.subtract(x_A.multiply(h)).mod(sms.getN());
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking BDCPS Unsigncrypt:");
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
	        SMSField4 r = y_A.fastSimultaneous(h.multiply(x_B).mod(sms.getN()), z, y_B);
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking private RSA-" + 4*sms.getP().bitLength());
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
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");

		////////////////////////////////////////////////////////////////////
        System.out.println("Benchmarking public RSA-" + 4*sms.getP().bitLength());
        elapsed = -System.currentTimeMillis();
        for (int t = 0; t < BM; t++) {
            m = m.modPow(e, n);
        }
        elapsed += System.currentTimeMillis();
        System.out.println("Elapsed time: " + (float)elapsed/BM + " ms.");
	}

	public static void BDCPSTest(int bits) {
        byte[] randSeed = new byte[20];
        (new SecureRandom()).nextBytes(randSeed);
        SecureRandom rnd = new SecureRandom(randSeed);
        long elapsed;
        System.out.println("\n======== bits: " + bits);
        SMSParams sms = new SMSParams(bits);
        SMSCurve E = new SMSCurve(sms); //System.out.println(E);
        SMSCurve2 E2 = new SMSCurve2(E); //System.out.println(E2);
        SMSPoint P = E.G;
        SMSPoint2 Q = E2.Gt;
        SMSPairing pair = new SMSPairing(E2);
        SMSField4 g = pair.ate(Q, P);
        String hex = "0123456789abcdef";

		////////////////////////////////////////////////////////////////////
        System.out.println("BDCPS Setup:");
        BigInteger s = new BigInteger(bits, rnd).mod(sms.getN()); // master key
        SMSPoint Ppub = P.multiply(s);
        System.out.println("s = " + s);
        System.out.println("P_pub = " + Ppub);

        BigInteger h1ID_A = new BigInteger(bits, rnd).mod(sms.getN()); // simulated h_1(ID_A)
        BigInteger h1ID_B = new BigInteger(bits, rnd).mod(sms.getN()); // simulated h_1(ID_B)

		////////////////////////////////////////////////////////////////////
        System.out.println("BDCPS Set-Secret-Value:");
        BigInteger x_A = new BigInteger(bits, rnd).mod(sms.getN());
        BigInteger x_B = new BigInteger(bits, rnd).mod(sms.getN());
        System.out.println("x_A = " + x_A);
        System.out.println("x_B = " + x_B);

		////////////////////////////////////////////////////////////////////
        System.out.println("BDCPS Set-Public-Value:");
        SMSField4 y_A = g.exp(x_A);
        SMSField4 y_B = g.exp(x_B);
        System.out.println("y_A     = " + y_A);
        byte[] y_A_comp = y_A.toByteArray(); // key compression
        System.out.print("comp(y_A) [" +  y_A_comp.length + " bytes] = "); for (int i = 0; i < y_A_comp.length; i++) { System.out.print(hex.charAt((y_A_comp[i] & 0xff) >>> 4)); System.out.print(hex.charAt(y_A_comp[i] & 15)); } System.out.println();
        System.out.println("y_B     = " + y_B);
        byte[] y_B_comp = y_B.toByteArray(); // key compression
        System.out.print("comp(y_B) [" +  y_B_comp.length + " bytes] = "); for (int i = 0; i < y_B_comp.length; i++) { System.out.print(hex.charAt((y_B_comp[i] & 0xff) >>> 4)); System.out.print(hex.charAt(y_B_comp[i] & 15)); } System.out.println();

		////////////////////////////////////////////////////////////////////
        System.out.println("BDCPS Private-Key-Extract:");

        SMSPoint2 Q_A = Q.multiply(h1ID_A.add(s).modInverse(sms.getN())).normalize();
        System.out.println("Q_A = " + Q_A);
        byte[] Q_A_comp = Q_A.toByteArray(SMSPoint2.COMPRESSED); // key compression
        System.out.print("comp(Q_A) [" +  Q_A_comp.length + " bytes] = "); for (int i = 0; i < Q_A_comp.length; i++) { System.out.print(hex.charAt((Q_A_comp[i] & 0xff) >>> 4)); System.out.print(hex.charAt(Q_A_comp[i] & 15)); } System.out.println();
        SMSField4 f_A = pair.ate(Q_A, P.multiply(h1ID_A).add(Ppub));
        SMSPoint2 Q_A_exp = new SMSPoint2(Q.E, Q_A_comp);
        if (!f_A.equals(g) || !Q_A_exp.equals(Q_A)) {
        	throw new RuntimeException("Failure @Check-Private-Key");
        }
        // TODO: send Q_A_comp to A signcrypted under y_A and ID_A (use the scheme itself -- the KGB has an implicit key y_pub = e(P_pub, Q) for signature verification)

        SMSPoint2 Q_B = Q.multiply(h1ID_B.add(s).modInverse(sms.getN())).normalize();
        System.out.println("Q_B = " + Q_B);
        byte[] Q_B_comp = Q_B.toByteArray(SMSPoint2.COMPRESSED); // key compression
        System.out.print("comp(Q_B) [" +  Q_B_comp.length + " bytes] = "); for (int i = 0; i < Q_B_comp.length; i++) { System.out.print(hex.charAt((Q_B_comp[i] & 0xff) >>> 4)); System.out.print(hex.charAt(Q_B_comp[i] & 15)); } System.out.println();
        SMSField4 f_B = pair.ate(Q_B, P.multiply(h1ID_B).add(Ppub));
        SMSPoint2 Q_B_exp = new SMSPoint2(Q.E, Q_B_comp);
        if (!f_B.equals(g) || !Q_B_exp.equals(Q_B)) {
        	throw new RuntimeException("Failure @Check-Private-Key");
        }
        // TODO: send Q_B_comp to B signcrypted under y_B and ID_B (use the scheme itself -- the KGB has an implicit key y_pub = e(P_pub, Q) for signature verification)

		////////////////////////////////////////////////////////////////////
        System.out.println("BDCPS Set-Public-Key:");

        // TODO: unsigncrypt received Q_A with x_A!
    	BigInteger u_A = new BigInteger(bits, rnd).mod(sms.getN());
        SMSField4  r_A = g.exp(u_A);
        BigInteger h_A = new BigInteger(bits, rnd).mod(sms.getN()); // simulated h_0(r_A, y_A, ID_A)
        SMSPoint2  T_A = Q_A.multiply(u_A.subtract(x_A.multiply(h_A)).mod(sms.getN()));
        byte[] T_A_comp = T_A.toByteArray(SMSPoint2.COMPRESSED); // key compression
        System.out.print("comp(T_A) [" +  T_A_comp.length + " bytes] = "); for (int i = 0; i < T_A_comp.length; i++) { System.out.print(hex.charAt((T_A_comp[i] & 0xff) >>> 4)); System.out.print(hex.charAt(T_A_comp[i] & 15)); } System.out.println();
        byte[] h_A_comp = h_A.toByteArray(); // <<<<<<<< CAVEAT: ambiguous (variable length)!
        byte[] pub_A = new byte[y_A_comp.length + h_A_comp.length + T_A_comp.length];
        System.arraycopy(y_A_comp, 0, pub_A, 0,                y_A_comp.length);
        System.arraycopy(h_A_comp, 0, pub_A, y_A_comp.length,  h_A_comp.length);
        System.arraycopy(T_A_comp, 0, pub_A, y_A_comp.length + h_A_comp.length, T_A_comp.length);
        System.out.print("Pub(A) [" +  pub_A.length + " bytes] = "); for (int i = 0; i < pub_A.length; i++) { System.out.print(hex.charAt((pub_A[i] & 0xff) >>> 4)); System.out.print(hex.charAt(pub_A[i] & 15)); } System.out.println();
        // TODO: map the sequence [y_A_comp, h_A, T_A_comp] to a byte array unambiguously!

        // TODO: unsigncrypt received Q_B with x_B!
    	BigInteger u_B = new BigInteger(bits, rnd).mod(sms.getN());
        SMSField4  r_B = g.exp(u_B);
        BigInteger h_B = new BigInteger(bits, rnd).mod(sms.getN()); // simulated h_0(r_B, y_B, ID_B)
        SMSPoint2  T_B = Q_B.multiply(u_B.subtract(x_B.multiply(h_B)).mod(sms.getN()));
        byte[] T_B_comp = T_B.toByteArray(SMSPoint2.COMPRESSED); // key compression
        System.out.print("comp(T_B) [" +  T_B_comp.length + " bytes] = "); for (int i = 0; i < T_B_comp.length; i++) { System.out.print(hex.charAt((T_B_comp[i] & 0xff) >>> 4)); System.out.print(hex.charAt(T_B_comp[i] & 15)); } System.out.println();
        byte[] h_B_comp = h_B.toByteArray(); // <<<<<<<< CAVEAT: ambiguous (variable length)!
        byte[] pub_B = new byte[y_B_comp.length + h_B_comp.length + T_B_comp.length];
        System.arraycopy(y_B_comp, 0, pub_B, 0,                y_B_comp.length);
        System.arraycopy(h_B_comp, 0, pub_B, y_B_comp.length,  h_B_comp.length);
        System.arraycopy(T_B_comp, 0, pub_B, y_B_comp.length + h_B_comp.length, T_B_comp.length);
        System.out.print("Pub(B) [" +  pub_B.length + " bytes] = "); for (int i = 0; i < pub_B.length; i++) { System.out.print(hex.charAt((pub_B[i] & 0xff) >>> 4)); System.out.print(hex.charAt(pub_B[i] & 15)); } System.out.println();
        // TODO: map the sequence [y_B_comp, h_B, T_B_comp] to a byte array unambiguously!

		////////////////////////////////////////////////////////////////////
        System.out.println("BDCPS Public-Key-Validate:");

        // TODO: parse the received byte array representing the sequence [y_A_comp, h_A, T_A_comp]!
        SMSField4 y_A_exp = new SMSField4(sms, y_A_comp, 0);
        System.out.println("y_A_exp = " + y_A_exp);
        System.out.println("y_A     = " + y_A);
        if (!y_A.equals(y_A_exp)) {
        	throw new RuntimeException("Failure @Public-Key-Validate");
        }
        if (y_A_exp.isOne() || !y_A.exp(sms.getN()).isOne()) {
        	throw new RuntimeException("Failure @Public-Key-Validate");
        }
    	SMSPoint2 T_A_exp = new SMSPoint2(Q.E, T_A_comp); // point expansion
        if (!T_A_exp.equals(T_A)) {
        	throw new RuntimeException("Failure @Public-Key-Validate");
        }
        SMSField4 r_A2 = pair.ate(T_A_exp, P.multiply(h1ID_A).add(Ppub)).multiply(y_A_exp.exp(h_A));
        if(r_A.equals(r_A2)) System.out.println("r_A é igual a r_A2!!!!!");
        BigInteger v_A = h_A; // simulated h_0(r_A, y_A, ID_A)
        if (v_A.compareTo(h_A) != 0) {
        	throw new RuntimeException("Failure @Public-Key-Validate");
        }

        // TODO: parse the received byte array representing the sequence [y_B_comp, h_B, T_B_comp]!
        SMSField4 y_B_exp = new SMSField4(sms, y_B_comp, 0);
        System.out.println("y_B_exp = " + y_B_exp);
        System.out.println("y_B     = " + y_B);
        if (!y_B.equals(y_B_exp)) {
        	throw new RuntimeException("Failure @Public-Key-Validate");
        }
        if (y_B_exp.isOne() || !y_B_exp.exp(sms.getN()).isOne()) {
        	throw new RuntimeException("Failure @Public-Key-Validate");
        }
    	SMSPoint2 T_B_exp = new SMSPoint2(Q.E, T_B_comp); // point expansion
        if (!T_B_exp.equals(T_B)) {
        	throw new RuntimeException("Failure @Public-Key-Validate");
        }
        r_B = pair.ate(T_B_exp, P.multiply(h1ID_B).add(Ppub)).multiply(y_B_exp.exp(h_B));
        BigInteger v_B = h_B; // simulated h_0(r_A, y_A, ID_A)
        if (v_B.compareTo(h_B) != 0) {
        	throw new RuntimeException("Failure @Public-Key-Validate");
        }

		////////////////////////////////////////////////////////////////////
        System.out.println("BDCPS Signcrypt:");
    	BigInteger u = new BigInteger(bits, rnd).mod(sms.getN());
        SMSField4  r = y_B.exp(u);
        //byte[] c = new byte[m.length]; // simulated symmetric encryption under key r (TODO: map r to an AES-128 key and encrypt m in pure CTR mode)
    	BigInteger h = new BigInteger(bits, rnd).mod(sms.getN()); // simulated h_3(r, m, y_A, ID_A, y_B, ID_B)
    	BigInteger z = u.subtract(x_A.multiply(h)).mod(sms.getN());
        //System.out.println("(h,z) = " + h + ":" + z);
        byte[] h_array = h.toByteArray();
        System.out.print("h [" + h_array.length + " bytes] = "); for (int i = 0; i < h_array.length; i++) { System.out.print(hex.charAt((h_array[i] & 0xff) >>> 4)); System.out.print(hex.charAt(h_array[i] & 15)); } System.out.println();
        byte[] z_array = z.toByteArray();
        System.out.print("z [" + z_array.length + " bytes] = "); for (int i = 0; i < z_array.length; i++) { System.out.print(hex.charAt((z_array[i] & 0xff) >>> 4)); System.out.print(hex.charAt(z_array[i] & 15)); } System.out.println();
        // TODO: map [c,h,z] unambiguously to a byte array (e.g. by prepending h and z with fixed length)

		////////////////////////////////////////////////////////////////////
        System.out.println("BDCPS Unsigncrypt:");
        // TODO: parse the received [c,h,z]
        SMSField4 rr = y_A.fastSimultaneous(h.multiply(x_B).mod(sms.getN()), z, y_B);
        BigInteger v = h; // simulated h_3(r, m, y_A, ID_A, y_B, ID_B)
        if (v.compareTo(h) != 0 || !rr.equals(r)) {
        	throw new RuntimeException("Failure @Unsigncrypt");
        }
        System.out.println("r  = " + r);
        System.out.println("r' = " + rr);
        System.out.println("h  = " + h);
        System.out.println("v  = " + v);

	}
	
    public static void main(String[] args) throws IOException {
    	//benchmarks(100, 176);
    	/*
    	int iterations = 1;// 100;
    	BDCPS(127, iterations);
        */
    	BDCPS(176, 1);
    	
    	
    	//BDCPSTest(127);
    	//BDCPSTest(176);
    	//*/
    }

}

/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.spec.AlgorithmParameterSpec;



import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import br.usp.larc.smspairing.SMSField4;
import br.usp.larc.pbarreto.jaes.*;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import java.security.DigestException;
import javax.crypto.ShortBufferException;
import pseudojava.BigInteger;
import pseudojava.SecureRandom;

/**
 * @author rodrigo
 *
 */
public class BDCPSUtil {

    private static final String HASH_ALGORITHM = "SHA-1";
    private static final MessageDigest sha;
    private static final SecureRandom rnd;
    private static final String hex = "0123456789abcdef";

    static {
        try {
            sha = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        //Pseudo Random Number Generator
        byte[] randSeed = new byte[20];
        (new SecureRandom()).nextBytes(randSeed);
        rnd = new SecureRandom(randSeed);
    }

    protected static final BigInteger h0(SMSField4 r, SMSField4 y, byte[] id, BigInteger n) {
        sha.reset();
        sha.update(r.toByteArray(), 0, r.toByteArray().length);
        sha.update(y.toByteArray(), 0, y.toByteArray().length);
        sha.update(id, 0, id.length);

        byte[] hash0 = new byte[20];
        try {
            sha.digest(hash0, 0, 20);
        } catch (DigestException ex) {
            System.out.println("BDCPS: Digest exception.");
            ex.printStackTrace();
            //TODO: arrumar
            throw new RuntimeException("BDCPS: Digest exception.");

        }
        return new BigInteger(hash0).mod(n);
    }

    protected static final BigInteger h1(SMSField4 y, byte[] id, BigInteger n) {

        sha.reset();
        sha.update(y.toByteArray(), 0, y.toByteArray().length);
        sha.update(id, 0, id.length);

        byte[] hash1 = new byte[20];
        try {
            sha.digest(hash1, 0, 20);
        } catch (DigestException ex) {
            System.out.println("BDCPS: Digest exception.");
            ex.printStackTrace();
            //TODO: arrumar
            throw new RuntimeException("BDCPS: Digest exception.");

        }
        return (new BigInteger(hash1)).mod(n);

    }

    protected static final BigInteger h3(SMSField4 r, byte[] m, SMSField4 y_A, byte[] id_A, SMSField4 y_B, byte[] id_B, BigInteger n) {
        sha.reset();
        sha.update(r.toByteArray(), 0, r.toByteArray().length);
        sha.update(y_A.toByteArray(), 0, y_A.toByteArray().length);
        sha.update(id_A, 0, id_A.length);
        sha.update(y_B.toByteArray(), 0, y_B.toByteArray().length);
        sha.update(id_B, 0, id_B.length);
        sha.update(m, 0, m.length);

        byte[] hash3 = new byte[20];
        try {
            sha.digest(hash3, 0, 20);
        } catch (DigestException ex) {
            System.out.println("BDCPS: Digest exception.");
            ex.printStackTrace();
            //TODO: arrumar
            throw new RuntimeException("BDCPS: Digest exception.");

        }
        return (new BigInteger(hash3)).mod(n);
    }

    protected static final byte[] h2(SMSField4 y, byte[] message, String mode) throws CipherException {
        //TODO: fix this. I am emulating a null cipher to check the signature.
        //return message;
        return CTR_AES(y.toByteArray(), message, mode, rnd);
    }

    /**
     * This method calls the CMAC authentication code from pbarreto libs. Given a data chunk it returns a fixed size hash
     * 
     * @param data	the data to hash
     * @param bits	the block size in bits
     * @return		the fixed size hash
     * @author 		rodrigo
     */
    private static final byte[] CMAC(byte[] data, int bits) {
        byte[] tag = new byte[16];
        byte[] key = new byte[16];
        for (int i = 0; i < key.length; i++) {
            key[i] = 0;
        }

        BlockCipher cipher = new AES();
        cipher.makeKey(key, bits, BlockCipher.DIR_ENCRYPT);
        CMAC cmac = new CMAC(cipher);
        cmac.init();
        cmac.update(data);
        cmac.finish(false);
        cmac.getTag(tag, cipher.blockSize());
        return tag;
    }

    private static final byte[] CTR_AES(byte[] r, byte[] data, String mode, SecureRandom rnd) throws CipherException {

        byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) 0;
        }

        byte key[] = new byte[16];
        byte[] ret;// = new byte[data.lenght];
        int _mode;



        //Here we map r to a fixed size hash that will be used as a 128-bit key to AES
        key = CMAC(r, 128);

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CTR/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("BDCPS: Invalid algorithm.");
            e.printStackTrace();
            throw new CipherException("BDCPS: Invalid algorithm.");
        } catch (NoSuchPaddingException e) {
            System.out.println("BDCPS: Invalid padding.");
            e.printStackTrace();
            throw new CipherException("BDCPS: Invalid padding.");
        }

        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv, 0, iv.length);

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, 0, key.length, "AES");

        if (mode.equals("ENC")) {
            _mode = Cipher.ENCRYPT_MODE;
        } else if (mode.equals("DEC")) {
            _mode = Cipher.DECRYPT_MODE;
        } else {
            throw new IllegalArgumentException("BDCPS: Unknown mode: " + mode);
        }

        try {
            cipher.init(_mode, secretKeySpec, paramSpec);
        } catch (InvalidKeyException e) {
            System.out.println("BDCPS: Invalid key.");
            e.printStackTrace();
            throw new CipherException("BDCPS: Invalid key.");
        } catch (InvalidAlgorithmParameterException e) {
            System.out.println("BDCPS: Invalid algorithm parameters.");
            e.printStackTrace();
            throw new CipherException("BDCPS: Invalid algorithm parameters.");
        }

        try {
            ret = new byte[data.length];
            cipher.doFinal(data, 0, data.length, ret, 0);
        } catch (IllegalBlockSizeException e) {
            System.out.println("BDCPS: Bad block size.");
            e.printStackTrace();
            throw new CipherException("BDCPS: Bad block size.");
        } catch (BadPaddingException e) {
            System.out.println("BDCPS: Bad padding.");
            e.printStackTrace();
            throw new CipherException("BDCPS: Bad padding.");
        } catch (ShortBufferException e) {
            System.out.println("BDCPS: Short buffer.");
            e.printStackTrace();
            throw new CipherException("BDCPS: Short buffer.");
        }
        return ret;
    }

    public static BigInteger randomBigInteger(int k) {
        return new BigInteger(k, rnd);
    }

    public static String printByteArray(byte[] array) {
        String ret = new String() + "[ ";
        for (int i = 0; i < array.length; i++) {
            ret += hex.charAt((array[i] & 0xff) >>> 4) + hex.charAt(array[i] & 15) + " ";
        }
        return ret + " ]";
    }
}

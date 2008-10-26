/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import br.usp.larc.smspairing.SMSField4;

/**
 * @author rodrigo
 *
 */
public class BDCPSUtil {
	
	private static final String HASH_ALGORITHM = "SHA-1"; 
	private static final MessageDigest sha;
	private static final SecureRandom rnd;
	
	static{
		try {
			sha = MessageDigest.getInstance(HASH_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException (e.getMessage());
		}
		//Pseudo Random Number Generator
		byte[] randSeed = new byte[20];
		(new Random()).nextBytes(randSeed);
		rnd = new SecureRandom(randSeed);
	}
	
	@SuppressWarnings("unused")
	protected static final BigInteger h0(SMSField4 r, SMSField4 y, byte[] id) {		
		sha.reset();
		sha.update(r.toByteArray());
		sha.update(y.toByteArray());
		sha.update(id);
		return new BigInteger(sha.digest());
	}

	@SuppressWarnings("unused")
	protected static final BigInteger h1(SMSField4 y, byte[] id) {
		sha.reset();
		sha.update(y.toByteArray());
		sha.update(id);
		return new BigInteger (sha.digest());
	}

	@SuppressWarnings("unused")
	protected static final BigInteger h3(SMSField4 r, byte[] m, SMSField4 y_A, byte[] id_A, SMSField4 y_B, byte[] id_B) {
		sha.reset();
		sha.update(r.toByteArray());
		sha.update(y_A.toByteArray());
		sha.update(id_A);
		sha.update(y_B.toByteArray());
		sha.update(id_B);
		sha.update(m);
		return new BigInteger(sha.digest());
	}

	@SuppressWarnings("unused")
	protected static final byte[] h2(SMSField4 y, byte[] message, String mode) throws CipherException {
		return CTR_AES(y.toByteArray(), message, mode, rnd);
	}

	private static final byte[] CTR_AES(byte[] key, byte[] data, String mode, SecureRandom rnd) throws CipherException {
		byte[] iv = new byte[20];
		rnd.nextBytes(iv);
		byte[] ret;
		int _mode;
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

		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

		if (mode == "ENC") _mode = Cipher.ENCRYPT_MODE;
		else if (mode == "DEC") _mode = Cipher.DECRYPT_MODE;
		else throw new IllegalArgumentException("BDCPS: Unknown mode: " + mode);

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
			ret = cipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			System.out.println("BDCPS: Bad block size.");
			e.printStackTrace();
			throw new CipherException("BDCPS: Bad block size.");
		} catch (BadPaddingException e) {
			System.out.println("BDCPS: Bad padding.");
			e.printStackTrace();
			throw new CipherException("BDCPS: Bad padding.");
		}
		return ret;
	}

	public static BigInteger randomBigInteger(int k) {
		return new BigInteger(k, rnd);
	}
}

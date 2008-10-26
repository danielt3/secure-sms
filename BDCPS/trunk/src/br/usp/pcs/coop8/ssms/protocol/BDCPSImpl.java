/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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

import br.usp.larc.smspairing.*;
import br.usp.pcs.coop8.ssms.protocol.BDCPSUtil;
//import pseudojava.BigInteger;

/**
 * @author rodrigo
 *
 */
public class BDCPSImpl {

	/*   #### Constructors #### */

	public BDCPSImpl() {}	

	/*   #### Private Class Members #### */

	private int k;

	private byte[] id;

	private BigInteger x_A, s;

	private SMSPoint2 Q_A;

	private byte[][] privateKey, publicKey;

	private SMSParams sms;

	private SMSCurve E;

	private SMSCurve2 E2;

	private SMSPoint P, Ppub;

	private SMSPoint2 Q;

	private SMSPairing pair;

	private SMSField4 y_A, g;

	private SecureRandom rnd;

	private static final String HEX = "0123456789abcdef";




	/*   #### Protocol Methods #### */

	/**
	 * Trust authority setup
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param masterKey A byte array containing the trust authority master key
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */
	public void setupAuthority(int bits, byte[] masterKey) {

		setupAuthority(bits, new BigInteger(masterKey));
	}

	/**
	 * Trust authority setup
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param masterKey The trust authority master key (a BigInteger)
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */
	protected void setupAuthority(int bits, BigInteger masterKey) {
		k = bits;
		initParams();
		s = masterKey.mod(sms.getN());
		Ppub = P.multiply(s);		
	}

	/**
	 * Client setup. Must be run before any method.
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param masterKey The trust authority's public point byte representation
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */	
	public void setup(int bits, byte[] publicPoint) {		
		k = bits;
		initParams();		
		this.Ppub = new SMSPoint(E, publicPoint);	
	}

	/**
	 * This method sets secretValue as entity's secret value 
	 * 
	 * @param secretValue A byte representation of the entity's _hashed_ password
	 */
	public void setSecretValue(byte[] secretValue) {
		setSecretValue(new BigInteger(secretValue).mod(sms.getN()));
	}

	protected void setSecretValue(BigInteger secretValue) {
		this.x_A = secretValue;
	}

	/**
	 * This method computes an entity's public value given its secret value
	 * 
	 * @author rodrigo
	 */
	public void setPublicValue() {
		if (x_A == null) throw new RuntimeException ("BDCPS: Secret value not set!");
		this.y_A = g.exp(x_A);
	}

	/**
	 * This method computes an entity's identity-based private key
	 * 
	 * @param id The entity's identifier
	 * @return A byte representation (compressed) of the entity's private key
	 * @author rodrigo
	 */
	public byte[] privateKeyExtract(byte[] id) {
		if (id == null) throw new IllegalArgumentException ("BDCPS: id cannot be null!");
		this.id = id;

		if (s == null) throw new RuntimeException ("BDCPS: Trust Authority not set!");
		if (y_A == null) throw new RuntimeException ("BDCPS: Public value not set!");
		if (sms == null) throw new RuntimeException ("BDCPS: SMSParams not set!");
		//TODO: check k
		this.Q_A = Q.multiply(BDCPSUtil.h1(y_A, id).add(s).modInverse(sms.getN())).normalize();
		if (!checkPrivateKey()) throw new RuntimeException ("BDCPS: Failure at Check-Private-Key");
		return Q_A.toByteArray(SMSPoint2.COMPRESSED);
	}

	/**
	 * This method sets the privateKey pair to represent the entity's complete private key
	 * 
	 * @author rodrigo
	 */
	public void setPrivateKey() {
		//TODO think about a way to serialize the objects!
		this.privateKey[0] = this.x_A.toByteArray();
		this.privateKey[1] = this.Q_A.toByteArray(SMSPoint2.COMPRESSED);    	
	}

	/**
	 * This method computes an entity's complete public key
	 * 
	 * @author rodrigo
	 */
	public void setPublicKey() {
		//TODO must get a random number??
		BigInteger u_A = randomBigInteger();
		SMSField4 r = g.exp(u_A);
		BigInteger h_A = BDCPSUtil.h0(r, y_A, id);
		SMSPoint2 T_A = Q_A.multiply(u_A.subtract(x_A.multiply(h_A)));

		publicKey[0] = y_A.toByteArray();
		publicKey[1] = h_A.toByteArray();
		publicKey[2] = T_A.toByteArray(SMSPoint2.COMPRESSED);
	}

	/**
	 * This method validates an entity's public key 
	 * 
	 * @param publicKey a byte representation of the entity's public key
	 * @param id the entity's identifier
	 * @return true if the key is valid and false if the key is not valid
	 */
	public boolean publicKeyValidate(byte[][] publicKey, byte id[]) {
		SMSField4 y_A = new SMSField4(sms, publicKey[0], 0);
		//TODO verify if offset = 0 is right
		BigInteger h_A = new BigInteger(publicKey[1]);
		SMSPoint2 T_A = new SMSPoint2(E2, publicKey[2]);

		return publicKeyValidate(y_A, h_A, T_A, id);
	}

	protected boolean publicKeyValidate(SMSField4 y_A, BigInteger h_A, SMSPoint2 T_A, byte[] id) {

		//first check that y_A has order n
		if (!(!y_A.isOne()) && (y_A.exp(BigInteger.valueOf((long)k)).isOne())) return false;

		SMSField4 r_A = pair.ate(T_A, P.multiply(BDCPSUtil.h1(y_A, id)).add(Ppub)).multiply(y_A.exp(h_A));
		BigInteger v_A = BDCPSUtil.h0(r_A, y_A, id);
		return v_A.equals(h_A);

	}

	protected byte[][] signcrypt(byte[] message, byte[] receiverId, byte[] receiverPublicValue) 
	throws CipherException {
		if (y_A == null) throw new RuntimeException ("BDCPS: Public value not set!");
		if (sms == null) throw new RuntimeException ("BDCPS: SMSParams not set!");
		SMSField4 y_B = new SMSField4(sms, receiverPublicValue, 0);
		return signcrypt(message, id, receiverId, y_A, y_B);
	}

	protected byte[][] signcrypt(byte[] message, byte[] senderId, byte[] receiverId, SMSField4 y_A, SMSField4 y_B) 
	throws CipherException {
		BigInteger u = randomBigInteger();
		SMSField4  r = y_B.exp(u);
		//byte[] c = new byte[m.length]; // simulated symmetric encryption under key r (TODO: map r to an AES-128 key and encrypt m in pure CTR mode)
		byte[] c = BDCPSUtil.h2_enc(r, message, rnd);
		BigInteger h = BDCPSUtil.h3(r, message, y_A, id, y_B, receiverId);
		BigInteger z = u.subtract(x_A.multiply(h)).mod(sms.getN());

		byte[][] cryptogram = new byte[3][];

		cryptogram[0] = c;
		cryptogram[1] = h.toByteArray();
		cryptogram[2] = z.toByteArray();
		// TODO: map [c,h,z] unambiguously to a byte array (e.g. by prepending h and z with fixed length)
		return cryptogram;
	}

	public byte[] unsigncrypt(byte[][] cryptogram, byte[] senderId, byte[] senderPublicValue) 
	throws InvalidMessageException, CipherException {
		byte[] c = cryptogram[0];
		BigInteger h = new BigInteger(cryptogram[1]);
		BigInteger z = new BigInteger(cryptogram[2]);
		SMSField4 y_B = new SMSField4(sms, senderPublicValue, 0);
		return unsigncrypt(c, z, h, id, senderId, y_A, y_B, x_A );

	}

	protected byte[] unsigncrypt(byte[] c, BigInteger z, BigInteger h, byte[] ID_A, byte[] ID_B, SMSField4 y_A, SMSField4 y_B, BigInteger x_A ) 
	throws InvalidMessageException, CipherException {
		SMSField4 r = y_A.fastSimultaneous(h.multiply(x_A).mod(sms.getN()), z, y_B);
		BigInteger v = BDCPSUtil.h3(r, c, y_A, ID_A, y_B, ID_B);
		if (v.compareTo(h) != 0) {
			throw new InvalidMessageException("BDCPS: Invalid message!");
		}
		byte[] m = BDCPSUtil.h2_dec(r, c, rnd);
		return m;

	}


	/*   #### Auxliliary Methods #### */

	private void initParams() {
		sms = new SMSParams(k);
		E = new SMSCurve(sms);
		E2 = new SMSCurve2(E);
		P = E.getG();
		Q = E2.getGt();
		pair = new SMSPairing(E2);
		g = pair.ate(Q, P);

		//Pseudo Random Number Generator
		byte[] randSeed = new byte[20];
		(new Random()).nextBytes(randSeed);
		rnd = new SecureRandom(randSeed);
	}

	@SuppressWarnings("unused")
	private byte[] getRandomBytes() {
		byte[] buf = new byte[(int)Math.ceil(1.0*k/8.0)];
		rnd.nextBytes(buf);
		return buf;
	}

	private BigInteger randomBigInteger() {
		return new BigInteger(k, rnd).mod(sms.getN());
	}

	/*   #### Accessor Methods #### */

	public byte[] getPublicValue() {
		return this.y_A.toByteArray();
	}

	public boolean checkPrivateKey() {
		return pair.ate(Q_A, P.multiply(BDCPSUtil.h1(y_A, id)).add(Ppub)).equals(g);
	}




}

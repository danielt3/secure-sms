/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import br.usp.larc.smspairing.*;
import br.usp.pcs.coop8.ssms.protocol.BDCPSUtil;
//import pseudojava.BigInteger;

/**
 * @author rodrigo
 *
 */
public abstract class BDCPSImpl implements BDCPS{

	/*   #### Constructors #### */
	

	/*   #### Private Class Members #### */

	
	
	protected int k;

	protected byte[] id;

	protected BigInteger x_A;

	protected SMSPoint2 Q_A;

	protected byte[][] privateKey, publicKey;

	protected SMSParams sms;

	protected SMSCurve E;

	protected SMSCurve2 E2;

	protected SMSPoint P;

	protected SMSPoint Ppub;

	protected SMSPoint2 Q;

	protected SMSPairing pair;

	protected SMSField4 y_A, g;

	private SecureRandom rnd;
	
	protected Logger logger;
	
	private static final int LOG_MODE = 5;


	/*   #### Protocol Methods #### */
	
	public abstract void setup(int bits, byte[] param, byte[] id);
	
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
		logger.debug("BDCPS: SetSecretValue "+x_A.toString());
	}

	/**
	 * This method computes an entity's public value given its secret value
	 * 
	 * @author rodrigo
	 */
	public void setPublicValue() {
		if (x_A == null) throw new RuntimeException ("BDCPS: Secret value not set!");
		this.y_A = g.exp(x_A);
		logger.debug("BDCPS: SetPublicValue "+y_A.toString());
		
	}

	/**
	 * This method computes an entity's identity-based private key
	 * 
	 * @param id The entity's identifier
	 * @return A byte representation (compressed) of the entity's private key
	 * @author rodrigo
	 */
	public abstract byte[] privateKeyExtract(byte[] id, byte[] publicValue);

	/**
	 * This method sets the privateKey pair to represent the entity's complete private key
	 * 
	 * @author rodrigo
	 */
	public void setPrivateKey(byte[] secretPoint) {
		//TODO think about a way to serialize the objects!
		this.Q_A = new SMSPoint2(E2, secretPoint);
		this.privateKey[0] = this.x_A.toByteArray();
		this.privateKey[1] = secretPoint;    	
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
		logger.debug("r from random u_A: " + r);
		BigInteger h_A = BDCPSUtil.h0(r, y_A, id, sms.getN());
		SMSPoint2 T_A = Q_A.multiply((u_A.subtract(x_A.multiply(h_A))).mod(sms.getN()));
		logger.debug("setPublicKey:");
		logger.debug("y_A before: "+ y_A.toString());
		logger.debug("h_A before: "+ h_A.toString());
		logger.debug("id: " + new String(id));
		logger.debug("Ppub: " + Ppub);
		logger.debug("T_A before: "+ T_A.toString());
	

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
		BigInteger h_A = new BigInteger(publicKey[1]);
		SMSPoint2 T_A = new SMSPoint2(E2, publicKey[2]);
		//logger.debug("y_A after: "+y_A.toString());
		//logger.debug("h_A after: "+h_A.toString());
		//logger.debug("T_A after: "+T_A.toString());
		return publicKeyValidate(y_A, h_A, T_A, id);
	}

	protected boolean publicKeyValidate(SMSField4 y_A, BigInteger h_A, SMSPoint2 T_A, byte[] id) {
		//logger.debug("pk-validate: chegou");
		//first check that y_A has order n
		if (!(!y_A.isOne()) && (y_A.exp(BigInteger.valueOf((long)k)).isOne())) return false;
		//logger.debug("pk-validate: passei do isOne()");
		
		logger.debug("before pairing:");
		logger.debug("y_A: " + y_A);
		logger.debug("id: " + new String(id));
		logger.debug("Ppub: " + Ppub);
		logger.debug("T_A: " + T_A);
		//SMSField4 r_A = pair.ate(T_A, P.multiply(BDCPSUtil.h1(y_A, id, sms.getN())).add(Ppub)).multiply(y_A.exp(h_A));
		
		SMSField4 r_A = pair.ate(T_A, P.multiply(BDCPSUtil.h1(y_A, id, sms.getN())).add(Ppub)).multiply(y_A.exp(h_A));
		
		logger.debug("r_A from pairing: " + r_A);
		BigInteger v_A = BDCPSUtil.h0(r_A, y_A, id, sms.getN());
		logger.debug("v_A: " + v_A + " h_A: " + h_A);
		return v_A.equals(h_A);

	}

	public byte[][] signcrypt(byte[] message, byte[] receiverId, byte[] receiverPublicValue) 
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
		logger.debug("Message before signcrypt: " + new String(message));
		logger.debug("Message before signcrypt: " + BDCPSUtil.printByteArray(message));
		byte[] c = new byte[message.length];
		c = BDCPSUtil.h2(r, message, "ENC");
		logger.debug("Message after signcrypt: " + BDCPSUtil.printByteArray(c));
		BigInteger h = BDCPSUtil.h3(r, message, y_A, id, y_B, receiverId, sms.getN());
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
		logger.debug("Message before unsigncrypt: " + BDCPSUtil.printByteArray(c));
		BigInteger h = new BigInteger(cryptogram[1]);
		BigInteger z = new BigInteger(cryptogram[2]);
		SMSField4 y_B = new SMSField4(sms, senderPublicValue, 0);
		return unsigncrypt(c, z, h, id, senderId, y_A, y_B, x_A );

	}

	protected byte[] unsigncrypt(byte[] c, BigInteger z, BigInteger h, byte[] ID_A, byte[] ID_B, SMSField4 y_A, SMSField4 y_B, BigInteger x_A ) 
	throws InvalidMessageException, CipherException {
		SMSField4 r = y_A.fastSimultaneous(h.multiply(x_A).mod(sms.getN()), z, y_B);
		BigInteger v = BDCPSUtil.h3(r, c, y_A, ID_A, y_B, ID_B, sms.getN());
		byte[] m = BDCPSUtil.h2(r, c, "DEC");
		if (v.compareTo(h) != 0) {
			logger.debug("The failed message is: " + new String(m));
			logger.debug("The failed message is: " + BDCPSUtil.printByteArray(m));
			throw new InvalidMessageException("BDCPS: Invalid message!");
		}
		return m;

	}


	/*   #### Auxliliary Methods #### */

	protected void initParams() {
		sms = new SMSParams(k);
		E = new SMSCurve(sms);
		E2 = new SMSCurve2(E);
		P = E.getG();
		Q = E2.getGt();
		pair = new SMSPairing(E2);
		g = pair.ate(Q, P);
		logger = new Logger(LOG_MODE);
		privateKey = new byte[3][];
		publicKey = new byte[3][];
	}

	@SuppressWarnings("unused")
	private byte[] getRandomBytes() {
		byte[] buf = new byte[(int)Math.ceil(1.0*k/8.0)];
		rnd.nextBytes(buf);
		return buf;
	}

	private BigInteger randomBigInteger() {
		return BDCPSUtil.randomBigInteger(k).mod(sms.getN());
		//return BigInteger.valueOf((long) 10).mod(sms.getN())
		
	}

	/*   #### Accessor Methods #### */

	public byte[] getPublicValue() {
		return this.y_A.toByteArray();
	}

	public boolean checkPrivateKey(SMSPoint2 Q_A, SMSField4 y_A, byte[] id) {
		return pair.ate(Q_A, P.multiply(BDCPSUtil.h1(y_A, id, sms.getN())).add(Ppub)).equals(g);
	}
	
	public byte[] getPublicPoint() {
		return Ppub.toByteArray(SMSPoint.COMPRESSED);
	}
	
	public byte[][] getPublicKey() {
		return this.publicKey;
	}




}

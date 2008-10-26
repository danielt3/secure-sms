/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.math.BigInteger;

import br.usp.larc.smspairing.SMSField4;
import br.usp.larc.smspairing.SMSPoint;
import br.usp.larc.smspairing.SMSPoint2;

/**
 * @author rodrigo
 *
 */
public class BDCPSAuthority extends BDCPSImpl {
	
	public BDCPSAuthority(int bits, byte[] masterKey, byte[] id) {
		setup(bits, masterKey, id);
	}
	
	@SuppressWarnings("unused")
	private BDCPSAuthority(){}
	
	private BigInteger s;
	
	/**
	 * Trust authority setup
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param masterKey A byte array containing the trust authority master key
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */
	public void setup(int bits, byte[] param, byte[] id) {

		setup(bits, new BigInteger(param), id);
	}

	/**
	 * Trust authority setup
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param masterKey The trust authority master key (a BigInteger)
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */
	protected void setup(int bits, BigInteger masterKey, byte[] id) {
		k = bits;
		this.id = id;
		initParams();
		s = masterKey.mod(sms.getN());
		Ppub = P.multiply(s);		
		byte[] Ppub_array = Ppub.toByteArray(SMSPoint.COMPRESSED);
		SMSPoint Ppub2 = new SMSPoint(E, Ppub_array);
		logger.debug("Ppubs are " + (Ppub.equals(Ppub2)? "equal" : "different"));
		logger.debug("Auth's Ppub: " + Ppub.normalize());
		logger.debug("Auth's generated Ppub: " + Ppub2.normalize());
	}
	
	public byte[] privateKeyExtract(byte[] id, byte[] publicValue) {
		if (id == null) throw new IllegalArgumentException ("BDCPS: id cannot be null!");
		if (sms == null) throw new RuntimeException ("BDCPS: SMSParams not set!");
		SMSField4 y_A = new SMSField4(sms, publicValue, 0); 

		if (s == null) throw new RuntimeException ("BDCPS: Trust Authority not set!");
		if (y_A == null) throw new RuntimeException ("BDCPS: Public value not set!");
		
		//TODO: check k
		SMSPoint2 Q_A = Q.multiply( (BDCPSUtil.h1(y_A, id, sms.getN()).add(s)).modInverse(sms.getN())).normalize();
		if (!checkPrivateKey(Q_A, y_A, id)) throw new RuntimeException ("BDCPS: Failure at Check-Private-Key");
		return Q_A.toByteArray(SMSPoint2.COMPRESSED);
	}



}

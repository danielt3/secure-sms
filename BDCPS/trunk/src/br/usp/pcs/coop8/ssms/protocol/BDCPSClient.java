/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.math.BigInteger;

import br.usp.larc.smspairing.SMSPoint;

/**
 * @author rodrigo
 *
 */
public class BDCPSClient extends BDCPSImpl {
	
	public BDCPSClient(int bits, byte[] publicPoint, byte[] id) {
		setup(bits, publicPoint, id);
	}
	
	public BDCPSClient(int bits, SMSPoint publicPoint, byte[] id) {
		setup(bits, publicPoint, id);
	}
	
	@SuppressWarnings("unused")
	private BDCPSClient(){}
	
	/**
	 * Client setup. Must be run before any method.
	 * 
	 * @param bits 		Security parameter (key size)
	 * @param masterKey The trust authority's public point byte representation
	 * @author 			rodrigo
	 * @since 			2008-10-25
	 */	
	public void setup(int bits, byte[] param, byte[] id) {		
		k = bits;
		this.id = id;
		initParams();
		this.Ppub = new SMSPoint(E, param).normalize();
		logger.debug("Client's Ppub: " + Ppub.normalize());
	}
	
	public void setup(int bits, SMSPoint Ppub, byte[] id) {
				
		k = bits;
		this.id = id;
		initParams();
		this.Ppub = Ppub.normalize();
	}	

	@Override
	public byte[] privateKeyExtract(byte[] id, byte[] publicValue) {
		throw new RuntimeException("BDCPS: A client cannot run privateKeyExtract!");
	}

}

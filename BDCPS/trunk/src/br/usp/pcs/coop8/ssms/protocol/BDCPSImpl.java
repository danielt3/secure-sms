/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.math.BigInteger;

import br.usp.larc.smspairing.*;

/**
 * @author rodrigo
 *
 */
public class BDCPSImpl {
	
	/*   #### Constructors #### */
	
	public BDCPSImpl() {}	
	
	/*   #### Private Class Members #### */
	
	private int k;
	
	private BigInteger secretValue, s;
	
	private SMSParams sms;
	
	private SMSCurve E;
	
	private SMSCurve2 E2;
	
	private SMSPoint P, Ppub;
	
	private SMSPoint2 Q;
	
	private SMSPairing pair;
	
	private SMSField4 publicValue, g; //Belong to group Gt
	
	private static final String HEX = "0123456789abcdef";
	
	
	
	
	/*   #### Protocol Methods #### */
	
	public void setup(int bits, BigInteger masterKey) {
		k = bits;
		initParams();
		s = masterKey.mod(sms.getN());
        Ppub = P.multiply(s);		
	}
	
	public void setup(int bits, byte[] publicPoint) {
		
		k = bits;
		initParams();		
		this.Ppub = new SMSPoint(E, publicPoint);	
	}
    
    public void setSecretValue(BigInteger secretValue) {
    	this.secretValue = secretValue;
    }
    
    public void setPublicValue() {
    	this.publicValue = g.exp(secretValue);
    }
    
    public byte[] privateKeyExtract(byte[] id) {
    	SMSPoint2 Q_A = Q.multiply(h1(publicValue, id).add(s).modInverse(sms.getN())).normalize();
    	return Q_A.toByteArray(k);
    }
    
    public void setPrivateKey() {
    	
    	
    }
    
    public void setPublicKey(){}
    
    public void publicKeyValidate(){}
    
    public void signcrypt(){}
    
    public void unsigncrypt(){}
    
    
    /*   #### Auxliliary Methods #### */
    
    private void initParams() {
    	sms = new SMSParams(k);
        E = new SMSCurve(sms);
        E2 = new SMSCurve2(E);
        P = E.getG();
        Q = E2.getGt();
        pair = new SMSPairing(E2);
        g = pair.ate(Q, P);
    }
    
    @SuppressWarnings("unused")
	private BigInteger h0(SMSField4 r, SMSField4 y, byte[] id) {
    	//TODO implement h0 function
    	
    	return null;
    }
    
    @SuppressWarnings("unused")
	private BigInteger h1(SMSField4 y, byte[] id) {
    	//TODO implement h1 function
    	
    	return null;
    }
    
    @SuppressWarnings("unused")
	private byte[] h2(SMSField4 y) {
    	//TODO implement h2 function
    	
    	return null;
    }
    
    private BigInteger h3() {
    	
    	return null;
    }
    
    
    
    
    
    /*   #### Accessor Methods #### */
    
    public byte[] getPublicValue() {
    	return this.publicValue.toByteArray();
    }

    
    
    
}

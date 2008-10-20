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
	
	private SMSField4 y_A, g; //Belong to group Gt
	
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
    	this.x_A = secretValue;
    }
    
    public void setPublicValue() {
    	this.y_A = g.exp(x_A);
    }
    
    public byte[] privateKeyExtract(byte[] id) {
    	this.id = id;
    	this.Q_A = Q.multiply(h1(y_A, id).add(s).modInverse(sms.getN())).normalize();
    	return Q_A.toByteArray(k);
    }
    
    public void setPrivateKey() {
    	this.privateKey[0] = this.x_A.toByteArray();
    	this.privateKey[1] = this.Q_A.toByteArray(k);    	
    }
    
    public void setPublicKey() {
    	//TODO must get a random number??
    	BigInteger u_A = BigInteger.valueOf(10);
    	SMSField4 r = g.exp(u_A);
    	BigInteger h_A = h0(r, y_A, id);
    	SMSPoint2 T_A = Q_A.multiply(u_A.subtract(x_A.multiply(h_A)));
    	
    	publicKey[0] = y_A.toByteArray();
    	publicKey[1] = h_A.toByteArray();
    	publicKey[2] = T_A.toByteArray(k);
    }
    
    public boolean publicKeyValidate(byte[][] publicKey, byte id[]) {
    	SMSField4 y_A = new SMSField4(sms, publicKey[0], 0);
    	//TODO verify if offset = 0 is right
    	BigInteger h_A = new BigInteger(publicKey[1]);
    	SMSPoint2 T_A = new SMSPoint2(E2, publicKey[2]);
    	
    	return publicKeyValidate(y_A, h_A, T_A, id);
    }
    
    private boolean publicKeyValidate(SMSField4 y_A, BigInteger h_A, SMSPoint2 T_A, byte[] id) {
    	
    	//first check that y_A has order n
    	if (!(!y_A.isOne()) && (y_A.exp(BigInteger.valueOf((long)k)).isOne())) return false;
    	
    	SMSField4 r_A = pair.ate(T_A, P.multiply(h1(y_A, id)).add(Ppub)).multiply(y_A.exp(h_A));
    	BigInteger v_A = h0(r_A, y_A, id);
    	
    	//TODO check if this method really compares values
    	return v_A.equals(h_A);

    }
    
    public void signcrypt() {
    	
    }
    
    public void unsigncrypt() {
    	
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
    	return this.y_A.toByteArray();
    }
    
    public boolean checkPrivateKey() {
    	return pair.ate(Q_A, P.multiply(h1(y_A, id)).add(Ppub)).equals(g);
    }

    
    
    
}

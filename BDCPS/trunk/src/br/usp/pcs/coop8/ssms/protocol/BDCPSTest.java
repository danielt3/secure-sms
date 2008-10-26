/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.usp.larc.smspairing.SMSPoint;

/**
 * @author rodrigo
 *
 */
public class BDCPSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MessageDigest sha = null;
		
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("BDCPS: Hash Algorithm not found.");
			e.printStackTrace();
		}
		
		
		int bits = 176;
		
		String masterKey = "honnisoitquimalypense";
		String aliceKey = "hi i am alice";
		String bobKey = "hi i am bob marley";
		
		String id_a = "551199991234";
		String id_b = "551188884321";
		String id_auth = "thegodfather";
		
		byte[] s = sha.digest(masterKey.getBytes());
		sha.reset();
		byte[] xa_alice = sha.digest(aliceKey.getBytes());
		sha.reset();
		byte[] xb_bob = sha.digest(bobKey.getBytes());
		
		BDCPS auth = new BDCPSAuthority(bits, s, id_auth.getBytes());
		BDCPS alice = new BDCPSClient(bits, auth.getPublicPoint(), id_a.getBytes());
		BDCPS bob = new BDCPSClient(bits, auth.getPublicPoint(), id_b.getBytes());
		
		
		
		alice.setSecretValue(xa_alice);
		bob.setSecretValue(xb_bob);
		alice.setPublicValue();
		bob.setPublicValue();
		
		
		byte[] Q_A = auth.privateKeyExtract(id_a.getBytes(), alice.getPublicValue());
		byte[] Q_B = auth.privateKeyExtract(id_b.getBytes(), bob.getPublicValue());
		
		alice.setPrivateKey(Q_A);
		bob.setPrivateKey(Q_B);
		System.out.println("\nSetPublicKey-Alice: ");
		alice.setPublicKey();
		System.out.println("SetPublicKey-Bob: ");
		bob.setPublicKey();
		
		System.out.println("\nValidate-Alice: ");
		boolean isAliceValid = bob.publicKeyValidate(alice.getPublicKey(), id_a.getBytes());
		System.out.println("Validate-Bob: ");
		boolean isBobValid = alice.publicKeyValidate(bob.getPublicKey(), id_b.getBytes());
		
		if (isBobValid) System.out.println("Bob is valid!");
		else System.out.println("Bob is false!");
		if (isAliceValid) System.out.println("Alice is valid!");
		else System.out.println("Alice is false!");
		
		System.out.println("\nBegin sign- and unsigncryption:");
		
		byte[] m = "Hello, world!".getBytes();
		byte[][] c = null;
		try {
			c = alice.signcrypt(m, id_b.getBytes(), bob.getPublicValue());
		} catch (CipherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] m_dec = null;
		try {
			m_dec = bob.unsigncrypt(c, id_a.getBytes(), alice.getPublicValue());
		} catch (InvalidMessageException e) {
			System.out.println("Unsigncrypt: Invalid message!");
			e.printStackTrace();
		} catch (CipherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(m_dec!=null)
		System.out.println("Decrypted message: "+ new String(m_dec));
		
		
		
		

	}

}

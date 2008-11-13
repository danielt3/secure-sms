/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import br.usp.pcs.coop8.ssms.protocol.exception.InvalidMessageException;

/**
 * @author rodrigo
 *
 */
public interface BDCPS {

	/*   #### Protocol Methods #### */
	
	public abstract void setup(int bits, byte[] param, byte[] id);
	
	/**
	 * This method sets secretValue as entity's secret value 
	 * 
	 * @param secretValue A byte representation of the entity's _hashed_ password
	 */
	public abstract void setSecretValue(byte[] secretValue);

	/**
	 * This method computes an entity's public value given its secret value
	 * 
	 * @author rodrigo
	 */
	public abstract void setPublicValue();
        
        /**
         * Sets y_A, if it is already known there is no need to calculate it again.
         * @param y_A
         */
        public void setPublicValue(byte[] y_A);

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
	public abstract void setPrivateKey(byte[] secretPoint);

	/**
	 * This method computes an entity's complete public key
	 * 
	 * @author rodrigo
	 */
	public abstract void setPublicKey();

	/**
	 * This method validates an entity's public key 
	 * 
	 * @param publicKey a byte representation of the entity's public key
	 * @param id the entity's identifier
	 * @return true if the key is valid and false if the key is not valid
	 */
	public abstract boolean publicKeyValidate(byte[][] publicKey, byte id[]);
        
        public boolean checkPrivateKey(byte[] Q_A, byte[] y_A, byte[] id);

	public abstract byte[][] signcrypt(byte[] message, byte[] receiverId, byte[] receiverPublicValue)
	throws CipherException;

	public abstract byte[] unsigncrypt(byte[][] cryptogram, byte[] senderId, byte[] senderPublicValue) 
	throws InvalidMessageException, CipherException;

	public abstract byte[] getPublicValue();
	
	public abstract byte[] getPublicPoint();
	
	public abstract byte[][] getPublicKey();
}

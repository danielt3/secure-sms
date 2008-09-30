package br.usp.pcs.coop8.ssms.protocol;

/**
 *
 * @author rodrigo
 */
public interface BDCPS {
    
    public abstract void setup(int k);
    
    public abstract void setSecretValue();
    
    public abstract void setPublicValue();
    
    public abstract void privateKeyExtract();
    
    public abstract void setPrivateKey();
    
    public abstract void setPublicKey();
    
    public abstract void publicKeyValidate();
    
    public abstract void signcrypt();
    
    public abstract void unsigncrypt();

}

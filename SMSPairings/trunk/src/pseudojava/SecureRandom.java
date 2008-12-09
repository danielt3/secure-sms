package pseudojava;

import java.util.Random;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.Provider;

public class SecureRandom extends Random {

    public SecureRandom() {
        super();
    }

    public SecureRandom(byte[] seed) {
        super();
        for (int i = 0; i < seed.length; i++) {
            setSeed((long)seed[i]);
        }
    }

    public static SecureRandom getInstance(String algorithm) /*throws NoSuchAlgorithmException*/ {
        return new SecureRandom();
    }

    public static SecureRandom getInstance(String algorithm, String provider) /*throws NoSuchAlgorithmException, NoSuchProviderException*/ {
        return new SecureRandom();
    }

    /*
    public static SecureRandom getInstance(String algorithm, Provider provider) //throws NoSuchAlgorithmException
    {
        return new SecureRandom();
    }
    //*/

    /*
    public Provider getProvider() {
        return null;
    }
    //*/

    static byte[] getSeed(int numBytes) {
        SecureRandom rand = new SecureRandom();
        byte[] seed = new byte[numBytes];
        for (int i = 0; i < numBytes; i++) {
            seed[i] = (byte)rand.nextInt();
        }
        return seed;
    }

    void setSeed(byte[] seed) {
        for (int i = 0; i < seed.length; i++) {
            setSeed((long)seed[i]);
        }
    }
    
    public void nextBytes(byte[] bytes) {
        //FIXME: faz nada =P
    }
            
            

}
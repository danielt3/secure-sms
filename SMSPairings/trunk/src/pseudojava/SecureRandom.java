package pseudojava;

import br.usp.larc.pbarreto.jaes.AES;
import br.usp.larc.pbarreto.jaes.BlockCipher;
import br.usp.larc.pbarreto.jaes.CMAC;
import br.usp.larc.pbarreto.jaes.CTR;
import br.usp.larc.pbarreto.jaes.MAC;
import java.util.Random;

public class SecureRandom extends Random {

    static private AES aes;
    static private CTR ctr;
    static private MAC mac;
    static private byte[] key;
    static private long[] teb;
    static private int tei;
    private byte[] buf = new byte[8];

    static {
        aes = new AES();
        ctr = new CTR(aes);
        mac = new CMAC(aes);
        key = new byte[AES.BLOCK_SIZE];
        teb = new long[AES.BLOCK_BITS]; // pending samples of entropy
        tei = teb.length - 1;			// flag to initialize the entropy collector
        aes.makeKey(key, AES.BLOCK_SIZE, BlockCipher.DIR_ENCRYPT);
        collectEntropy(null);
    }

    static private synchronized void collectEntropy(byte[] seed) {
        // collect a small entropy sample from the system:
        teb[tei++] = System.currentTimeMillis();
        if (tei == teb.length || seed != null) {
            // mix in the sampled entropy:
            mac.init();
            for (int t = 0; t < tei; t++) {
                long sample = teb[t];
                for (int i = 0; i < AES.BLOCK_SIZE; i++) {
                    key[i] = (byte) (sample >> ((i & 7) << 3));
                }
                mac.update(key, AES.BLOCK_SIZE, false);
            }
            // mix in the supplied external seed, if any:
            if (seed != null) {
                mac.update(seed, seed.length, false);
            }
            // refresh the key with the sampled entropy:
            mac.getTag(key, AES.BLOCK_SIZE);
            aes.makeKey(key, AES.BLOCK_SIZE, BlockCipher.DIR_ENCRYPT);
            tei = 0;
        }
    }

    static private synchronized void fillEntropy(byte[] bytes, int numBytes) {
        ctr.update(bytes, numBytes, bytes, null);
    }

    public SecureRandom() {
        collectEntropy(null);
    }

    public SecureRandom(byte[] seed) {
        collectEntropy(seed);
    }

    public void nextBytes(byte[] bytes) {
        collectEntropy(null);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) 0;
        }
        fillEntropy(bytes, bytes.length);
    }

    public int nextInt() {
        collectEntropy(null);
        for (int i = 0; i < 4; i++) {
            buf[i] = (byte) 0;
        }
        fillEntropy(buf, 4);
        return (((int) buf[3]) << 24) |
                (((int) buf[2] & 0xff) << 16) |
                (((int) buf[1] & 0xff) << 8) |
                (((int) buf[0] & 0xff));
    }

    public long nextLong() {
        collectEntropy(null);
        for (int i = 0; i < 8; i++) {
            buf[i] = (byte) 0;
        }
        fillEntropy(buf, 8);
        return (((long) buf[7]) << 56) |
                (((long) buf[6] & 0xff) << 48) |
                (((long) buf[5] & 0xff) << 40) |
                (((long) buf[4] & 0xff) << 32) |
                (((long) buf[3] & 0xff) << 24) |
                (((long) buf[2] & 0xff) << 16) |
                (((long) buf[1] & 0xff) << 8) |
                (((long) buf[0] & 0xff));
    }

    static byte[] getSeed(int numBytes) {
        collectEntropy(null);
        byte[] seed = new byte[numBytes];
        for (int i = 0; i < numBytes; i++) {
            seed[i] = (byte) 0;
        }
        fillEntropy(seed, numBytes);
        return seed;
    }

    public byte[] generateSeed(int numBytes) {
        collectEntropy(null);
        byte[] seed = new byte[numBytes];
        for (int i = 0; i < numBytes; i++) {
            seed[i] = (byte) 0;
        }
        fillEntropy(seed, numBytes);
        return seed;
    }

    void setSeed(byte[] seed) {
        collectEntropy(seed);
    }

    public String getAlgorithm() {
        return "Secure SMS Pseudo-Random Number Generator";
    }
}

/**
 *
 * Copyright (C) 2008 Rodrigo Rodrigues da Silva, Eduardo de Souza Cruz and 
 *                    Geovandro Carlos C. F. Pereira
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package br.usp.pcs.coop8.ssms.protocol;


//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.spec.IvParameterSpec;
import br.usp.larc.pbarreto.smspairing.SMSField4;
import br.usp.larc.pbarreto.jaes.*;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.spec.AlgorithmParameterSpec;
//import javax.crypto.ShortBufferException;
import pseudojava.BigInteger;
import pseudojava.SecureRandom;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/**
 * @author rodrigo
 *
 */
public class BDCPSUtil {

    private static final String HASH_ALGORITHM = "SHA-1";
    private static final Digest sha;
    private static final SecureRandom rnd;
    private static final String hex = "0123456789abcdef";

    static {

        sha = new SHA1Digest();

        //Pseudo Random Number Generator
        byte[] randSeed = new byte[20];
        (new SecureRandom()).nextBytes(randSeed);
        rnd = new SecureRandom(randSeed);
    }

    protected static final BigInteger h0(SMSField4 r, SMSField4 y, byte[] id, BigInteger n) {
        sha.reset();
        sha.update(r.toByteArray(), 0, r.toByteArray().length);
        sha.update(y.toByteArray(), 0, y.toByteArray().length);
        sha.update(id, 0, id.length);

        byte[] hash0 = new byte[20];

        sha.doFinal(hash0, 0);

        return new BigInteger(hash0).mod(n);
    }

    protected static final BigInteger h1(SMSField4 y, byte[] id, BigInteger n) {

        sha.reset();
        sha.update(y.toByteArray(), 0, y.toByteArray().length);
        sha.update(id, 0, id.length);

        byte[] hash1 = new byte[20];
        sha.doFinal(hash1, 0);

        return (new BigInteger(hash1)).mod(n);

    }

    protected static final BigInteger h3(SMSField4 r, byte[] m, SMSField4 y_A, byte[] id_A, SMSField4 y_B, byte[] id_B, BigInteger n) {
        sha.reset();
        sha.update(r.toByteArray(), 0, r.toByteArray().length);
        sha.update(y_A.toByteArray(), 0, y_A.toByteArray().length);
        sha.update(id_A, 0, id_A.length);
        sha.update(y_B.toByteArray(), 0, y_B.toByteArray().length);
        sha.update(id_B, 0, id_B.length);
        sha.update(m, 0, m.length);

        byte[] hash3 = new byte[20];
        sha.doFinal(hash3, 0);

        return (new BigInteger(hash3)).mod(n);
    }

    protected static final byte[] h2(SMSField4 y, byte[] message, String mode) throws CipherException {
        //TODO: fix this. I am emulating a null cipher to check the signature.
        //return message;
        return CTR_AES(y.toByteArray(), message, mode, rnd);
    }

    /**
     * This method calls the CMAC authentication code from pbarreto libs. Given a data chunk it returns a fixed size hash
     * 
     * @param data	the data to hash
     * @param bits	the block size in bits
     * @return		the fixed size hash
     * @author 		rodrigo
     */
    private static final byte[] CMAC(byte[] data, int bits) {
        byte[] tag = new byte[16];
        byte[] key = new byte[16];
        for (int i = 0; i < key.length; i++) {
            key[i] = 0;
        }

        AES cipher = new AES();
        cipher.makeKey(key, bits, BlockCipher.DIR_ENCRYPT);
        CMAC cmac = new CMAC(cipher);
        cmac.init();
        cmac.update(data);
        cmac.finish(false);
        cmac.getTag(tag, cipher.blockSize());
        return tag;
    }

    private static final byte[] CTR_AES(byte[] r, byte[] data, String mode, SecureRandom rnd) throws CipherException {

        PBEParametersGenerator generator = new PKCS5S2ParametersGenerator();

        byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            //Acho que o IV não pode ser nulo! Pergunte-me por quê... Ass: Eduardo
            //http://en.wikipedia.org/wiki/Cipher_block_chaining#Counter_.28CTR.29
            iv[i] = (byte) 0;
        }

        byte encryptionkey[] = new byte[16];
        byte[] ret;// = new byte[data.lenght];
        //int _mode;



        //Here we map r to a fixed size hash that will be used as a 128-bit key to AES
        encryptionkey = CMAC(r, 128);


        generator.init(encryptionkey, iv, 1000);



        //Cipher cipher = null;
        SICBlockCipher cipher2 = null;

        try {
            //cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher2 = new SICBlockCipher(new AESEngine());

        } catch (/*NoSuchAlgorithm*/Exception e) {
            System.out.println("BDCPS: Invalid algorithm.");
            e.printStackTrace();
            throw new CipherException("BDCPS: Invalid algorithm.");
        }

        //lgorithmParameterSpec paramSpec = new IvParameterSpec(iv, 0, iv.length);
        //SecretKeySpec secretKeySpec = new  SecretKeySpec(key, 0, key.length, "AES");
        KeyParameter keyparam = new KeyParameter(encryptionkey);
        ParametersWithIV paramwithiv = new ParametersWithIV(keyparam, iv);

        /*
        if (mode.equals("ENC")) {
        _mode = Cipher.ENCRYPT_MODE;
        } else if (mode.equals("DEC")) {
        _mode = Cipher.DECRYPT_MODE;
        } else {
        throw new IllegalArgumentException("BDCPS: Unknown mode: " + mode);
        }
         */

        //cipher.init(_mode, secretKeySpec, paramSpec);
        cipher2.init(true, paramwithiv);



        ret = new byte[data.length];
        System.out.println("!!! Meu ret tem: " + ret.length + " bytes =)");
        //cipher.doFinal(data, 0, data.length, ret, 0);

        int numberOfBlocks = data.length / cipher2.getBlockSize();
        if ((data.length % cipher2.getBlockSize()) > 0) {
            numberOfBlocks++;
        }


        for (int blockNumber = 0;
                blockNumber < numberOfBlocks;
                blockNumber++) {
            byte[] blockIn = new byte[cipher2.getBlockSize()];
            byte[] blockOut = new byte[cipher2.getBlockSize()];


            int effectiveCurrentBlockSize;
            //Verifica se é o último bloco e é incompleto
            if (blockNumber == numberOfBlocks - 1) {
                // É o último...
                effectiveCurrentBlockSize = data.length % cipher2.getBlockSize();

                //Se o resto da divisão deu 0, o último bloco é completo =)
                if (effectiveCurrentBlockSize == 0) {
                    effectiveCurrentBlockSize = cipher2.getBlockSize();
                }

            } else {
                //Não é o último, logo o blockIn terá todos os bytes efetivos
                effectiveCurrentBlockSize = cipher2.getBlockSize();
            }

            System.arraycopy(data, blockNumber * cipher2.getBlockSize(), blockIn, 0, effectiveCurrentBlockSize);
            cipher2.processBlock(blockIn, 0, blockOut, 0);
            System.arraycopy(blockOut, 0, ret, blockNumber * cipher2.getBlockSize(), effectiveCurrentBlockSize);

        }

        return ret;
    }

    public static BigInteger randomBigInteger(int k) {
        return new BigInteger(k, rnd);
    }

    public static String printByteArray(byte[] array) {
        String ret = new String() + "[ ";
        for (int i = 0; i < array.length; i++) {
            ret += hex.charAt((array[i] & 0xff) >>> 4) + hex.charAt(array[i] & 15) + " ";
        }
        return ret + " ]";
    }
}

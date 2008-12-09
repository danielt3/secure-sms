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

/**
 * @author rodrigo
 *
 */
public class BDCPSUtil {

    private static final CMAC cmac;
    private static final SecureRandom rnd;
    private static final String hex = "0123456789abcdef";

    static {
        AES aes = new AES();
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) 0x00;
        }
        aes.makeKey(
                key, 16, AES.DIR_ENCRYPT);
        cmac = new CMAC(aes);


        //Pseudo Random Number Generator
        byte[] randSeed = new byte[16];
        (new SecureRandom()).nextBytes(randSeed);
        rnd = new SecureRandom(randSeed);
    }

    protected static final BigInteger h0(SMSField4 r, SMSField4 y, byte[] id, BigInteger n) {
        cmac.init();
        cmac.update(r.toByteArray());
        cmac.update(y.toByteArray());
        cmac.update(id);

        byte[] hash0 = new byte[16];

        cmac.getTag(hash0);

        return new BigInteger(hash0).mod(n);
    }

    protected static final BigInteger h1(SMSField4 y, byte[] id, BigInteger n) {

        cmac.init();
        cmac.update(y.toByteArray());
        cmac.update(id);

        byte[] hash1 = new byte[16];
        cmac.getTag(hash1);

        return (new BigInteger(hash1)).mod(n);

    }

    protected static final BigInteger h3(SMSField4 r, byte[] m, SMSField4 y_A, byte[] id_A, SMSField4 y_B, byte[] id_B, BigInteger n) {
        cmac.init();

        System.out.println("r = " + byteArrayToDebugableString(r.toByteArray()));
        cmac.update(r.toByteArray());

        System.out.println("y_A = " + byteArrayToDebugableString(y_A.toByteArray()));
        cmac.update(y_A.toByteArray());

        System.out.println("id_A = " + byteArrayToDebugableString(id_A));
        cmac.update(id_A);

        System.out.println("y_B = " + byteArrayToDebugableString(y_B.toByteArray()));
        cmac.update(y_B.toByteArray());

        System.out.println("id_B = " + byteArrayToDebugableString(id_B));
        cmac.update(id_B);

        System.out.println("m = " + byteArrayToDebugableString(m));
        cmac.update(m);

        byte[] hash3 = new byte[16];
        cmac.getTag(hash3);

        System.out.println("hash3 = " + byteArrayToDebugableString(hash3));

        return (new BigInteger(hash3)).mod(n);
    }

    protected static final byte[] h2(SMSField4 y, byte[] message, String mode) throws CipherException {
        //TODO: fix this. I am emulating a null cipher to check the signature.
        //return message;
        return CTR_AES(y.toByteArray(), message);
    }


    private static final byte[] CTR_AES(byte[] r, byte[] data) throws CipherException {

        AES aes = new AES();

        byte[] r_normalizado = new byte[16];

        cmac.init();
        cmac.update(r);
        cmac.getTag(r_normalizado);

        aes.makeKey(r_normalizado, r_normalizado.length, AES.DIR_ENCRYPT);

        CTR ctr = new CTR(aes);
        //Só 4 ints.. PB justificou
        int[] iv = new int[4];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = (byte) 0;
        }

        ctr.init(iv);

        byte[] ret = new byte[data.length];
        ctr.update(data, data.length, ret, null);

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

    /**
     * Retorna uma string representando o byte array para ser impressa, para
     * fins de debug.
     */
    public static String byteArrayToDebugableString(byte[] ba) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ba.length; i++) {
            int byteAsInt = (int) ba[i];
            if (byteAsInt < 0) {
                byteAsInt = 256 + byteAsInt;
            }
            String strByte = Integer.toHexString(byteAsInt);
            if (strByte.length() == 1) {
                strByte = "0" + strByte;
            }
            sb.append(strByte);
        }
        return sb.toString();
    }
}

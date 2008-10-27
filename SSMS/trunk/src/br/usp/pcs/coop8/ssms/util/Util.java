/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.util;

import java.io.ByteArrayOutputStream;
import pseudojava.BigInteger;

/**
 *
 * @author Administrador
 */
public class Util {

    //01010000 (os 4 bits menos signifiativos são reservados para o nibble de controle da primitiva
    public static byte BYTE_BASE_VERSAO = 0x5;

    public static boolean isLong(String arg) {
        try {
            Long.parseLong(arg);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Converte um array para um BigInteger essencialmente não negativo.
     */
    public static BigInteger byteArrayToBigInteger(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }

        if (ba[0] >> 7 == 1) {
            //Se for assim ele vai pensar que é negativo.. 
            //temos que colocar um byte a mais 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                baos.write(0); // Byte 00
                baos.write(ba); // E depois os outros
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            ba = baos.toByteArray();
        }

        return new BigInteger(ba);

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

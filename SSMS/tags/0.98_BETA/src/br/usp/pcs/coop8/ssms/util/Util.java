/**
 *
 * Copyright (C) 2008 Eduardo de Souza Cruz, Geovandro Carlos C. F. Pereira
 *                    and Rodrigo Rodrigues da Silva
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

package br.usp.pcs.coop8.ssms.util;

import java.io.ByteArrayOutputStream;
import pseudojava.BigInteger;

/**
 *
 * @author Administrador
 */
public class Util {

    //01010000 (os 4 bits menos signifiativos são reservados para o nibble de controle da primitiva
    public static byte BYTE_BASE_VERSAO = 0x50;

    public static boolean isLong(String arg) {
        try {
            Long.parseLong(arg);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Converte um byte (unsigned) para um inteiro entre 0 e 255
     */
    public static int byteToInt(byte b) {
        int i = (int) b;
        if (i < 0) {
            i = 256 + i;
        }
        return i;
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

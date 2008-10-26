/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.tests;

/**
 *
 * @author Administrador
 */
public class Util {

    //01010000 (os 4 bits menos signifiativos são reservados para o nibble de controle da primitiva
    public static byte BYTE_BASE_VERSAO = 0x5;
    /**
     * Número de bits do primo
     */
    public static int K;

    public static boolean isLong(String arg) {
        try {
            Long.parseLong(arg);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    
}

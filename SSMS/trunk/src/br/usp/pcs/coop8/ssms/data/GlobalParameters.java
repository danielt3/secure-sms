/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.data;

import br.usp.larc.smspairing.SMSField2;
import pseudojava.BigInteger;

/**
 *
 * @author Administrador
 */
public abstract class GlobalParameters {

    public GlobalParameters() {
    }
    //params = (k; n;G1;G2;GT ; e; P; Q; g; Ppub; h0; h1; h2; h3).
    public static final int K;
    public static final String KGB_TEL;
    public static final byte[] KGB_ID_HASH;
    public static final byte[] PUBLIC_POINT;
    public static final BigInteger N;
    public static final SMSField2 G1;
    public static final SMSField2 G2;
    public static final SMSField2 GT;
    
    
    public static final int SMS_PORT = 2102;

    static {        
        K = 176;
        PUBLIC_POINT = null; // TODO; definir
        KGB_TEL = "1174749679";
        KGB_ID_HASH = null; //TODO: definir
        N = new BigInteger("81308659748347271006407969275494383070287122113917637");
        G1 = null; //TODO: definir o G1 padrão, decidir, ver se é SMSField 2 ou 4
        G2 = null; //TODO: definir o GT padrão, decidir, ver se é SMSField 2 ou 4
        GT = null; //TODO: definir o GT padrão, decidir, ver se é SMSField 2 ou 4
    }
    //TODO: continuar...
    /**
     * o Ppub da autoridade certificadora - certeza
     * 
     * o public key da autoridade para ele mesmo validar?
     * o y_A da autoridade certificadora
     * o id da autoridade
     * 
     * o k - certeza
     */
}

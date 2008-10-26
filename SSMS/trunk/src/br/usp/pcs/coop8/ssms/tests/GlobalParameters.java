/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.pcs.coop8.ssms.tests;

import br.usp.larc.smspairing.SMSField2;
import pseudojava.BigInteger;

/**
 *
 * @author Administrador
 */
public abstract class GlobalParameters {
    
    private GlobalParameters() {}
    
    //params = (k; n;G1;G2;GT ; e; P; Q; g; Ppub; h0; h1; h2; h3).
    
    public static final int K = 0; //TODO: definir o K padrão
    public static final BigInteger N = null; //TODO: definir o N padrão
    public static final SMSField2 G1 = null; //TODO: definir o G1 padrão, decidir, ver se é SMSField 2 ou 4
    public static final SMSField2 G2 = null; //TODO: definir o G2 padrão, decidir, ver se é SMSField 2 ou 4
    public static final SMSField2 GT = null; //TODO: definir o GT padrão, decidir, ver se é SMSField 2 ou 4
    
    //TODO: continuar...
    

}

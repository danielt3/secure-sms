/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.protocol.BDCPSParameters;

/**
 * Define parâmetros de aplicação configuráveis
 * 
 * @author Administrador
 */
public final class Configuration {
    
    //TODO: Para v2.0, implementar algum arquivo de configuaração alterável,
    //de forma que seja possível alterar estes parâmetros sem precisar
    //recompilar o código.

    /**
     * Não instanciável
     */
    private Configuration() {
    }
    /**
     * Número de bits do primo N
     */
    public static final int K = 176;    
    /**
     * O telefone da KGB
     */
    public static final String KGB_TEL = "1174749679";// "1174749679";
    /**
     * Porta a ser usada para envio de SMS
     */
    public static final int SMS_PORT = 50001;
    
    /**
     * Os parâmetros BDCPS para o K dado.
     */
    private static final BDCPSParameters BDCPS_PARAMS = BDCPSParameters.getInstance(K);
}

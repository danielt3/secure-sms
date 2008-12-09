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
package br.usp.pcs.coop8.ssms.application;

import br.usp.larc.pbarreto.jaes.AES;
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
    ///**
    // * O telefone da KGB
    // */
    //public static final String KGB_TEL = "1185841768";// "1174749679";
    /**
     * Porta a ser usada para envio de SMS
     */
    public static final int SMS_PORT = 50001;
    /**
     * Os parâmetros BDCPS para o K dado.
     */
    private static final BDCPSParameters BDCPS_PARAMS = BDCPSParameters.getInstance(K);

    /**
     * Retorna uma instância do AES com chave nula.
     */
    public static final AES getAes() {
        AES aes = new AES();
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) 0x00;
        }
        aes.makeKey(
                key, 16, AES.DIR_ENCRYPT);
        return aes;
    }
}

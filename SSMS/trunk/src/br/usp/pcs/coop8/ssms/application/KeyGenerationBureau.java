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
import br.usp.larc.pbarreto.jaes.CMAC;
import br.usp.pcs.coop8.ssms.data.PrivateData;
import br.usp.pcs.coop8.ssms.messaging.SignupResponse;
import br.usp.pcs.coop8.ssms.messaging.SecureMessage;
import br.usp.pcs.coop8.ssms.messaging.SignupMessage;
import br.usp.pcs.coop8.ssms.messaging.SmsSender;
import br.usp.pcs.coop8.ssms.protocol.BDCPSAuthority;
import br.usp.pcs.coop8.ssms.protocol.BDCPSParameters;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.util.Util;
import pseudojava.BigInteger;

/**
 * Classe que representa o KGB. Não deve estar presente no aplicativo mobile cliente.
 * Está aqui apenas para fins de desenvolvimento/testes/demonstração.
 * 
 * @author Administrador
 */
public class KeyGenerationBureau {

    /**
     * Um número de 500 bits ultra-secreto.
     * O S efetivo será _s500.mod(N), reduzido para k bits
     */
    private static BigInteger _s500 = new BigInteger("2811324208781249769788073818190026244636491661539281873387241581414870671338535758736366135621660583188369946048623028749823899797612403393315005280995");

    /**
     * Retorna o valor de S efetivo para o campo sendo usado, ou seja, _s500.mod(N)
     */
    private static byte[] getS() {
        return _s500.mod(BDCPSParameters.getInstance(Configuration.K).N).toByteArray();
    }

    public static void returnQaMessage(SignupMessage rmqam, String id) {


        byte[] hashIdA = new byte[16];
        byte[] hashKgbId = new byte[16];
        
        {
            CMAC cmac = new CMAC(Configuration.getAes());
            cmac.init();
            cmac.update(id.getBytes());
            Output.println("id_A no sign:" + Util.byteArrayToDebugableString(id.getBytes()));
            cmac.getTag(hashIdA);
            
            cmac.init();
            cmac.update(PrivateData.getInstance().getIdA().getBytes());
            cmac.getTag(hashKgbId);
        }

        byte[] yA = rmqam.getYA();

        //byte[][] cryptogram = new byte[3][];
        //cryptogram[0] = rmqam.getYA(); //yA encriptado
        //cryptogram[1] = rmqam.getH();
        //cryptogram[2] = rmqam.getZ();
        BDCPSAuthority bdcpsA = new BDCPSAuthority(Configuration.K, getS(), hashKgbId);



        byte[] qA = bdcpsA.privateKeyExtract(hashIdA, yA);

        bdcpsA.setPublicValue();
        byte[][] cryptogram;
        try {
            cryptogram = bdcpsA.signcrypt(qA, hashIdA, yA);
        } catch (CipherException ex) {
            ex.printStackTrace();
            Output.println("Exception excriptando qA");
            return;
        }


        SecureMessage msg = new SignupResponse(cryptogram[0], cryptogram[1], cryptogram[2]);

        SmsSender.sendSingleThread(id, msg.getMessageBytes());


    }
}

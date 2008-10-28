/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.messaging.HereIsYourQaMessage;
import br.usp.pcs.coop8.ssms.messaging.MessageSsms;
import br.usp.pcs.coop8.ssms.messaging.RequestMyQaMessage;
import br.usp.pcs.coop8.ssms.protocol.BDCPSAuthority;
import br.usp.pcs.coop8.ssms.protocol.BDCPSParameters;
import pseudojava.BigInteger;

/**
 * Classe que representa o KGB. Não deve estar presente no aplicativo mobile cliente.
 * Está aqui apenas para fins de desenvolvimento/testes/demonstração.
 * 
 * @author Administrador
 */
public class KgbSsms {

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

    public static void returnQaMessage(RequestMyQaMessage rmqam, byte[] id) {
        byte[] yA = rmqam.getYA();

        BDCPSAuthority bdcpsA = new BDCPSAuthority(Configuration.K, getS(), "1175758877".getBytes());

        byte[] qA = bdcpsA.privateKeyExtract(id, yA);

        //TODO: encriptar o qA com alg convencional


        MessageSsms msg = new HereIsYourQaMessage(qA);

        Controller.enviarSmsBinario(new String(id), msg.getMessageBytes());

    }
}

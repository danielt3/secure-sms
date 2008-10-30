/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.util.Util;

/**
 *
 * @author Administrador
 */
public class HereIsYourQaMessage extends MessageSsms {

    private byte[] qA;

    protected HereIsYourQaMessage() {
    }

    /**
     * 
     */
    public HereIsYourQaMessage(byte[] qA) {
        this.qA = qA;
        this.messageBytes = serialize(qA);
    }

    public byte[] getQA() {
        return this.qA;
    }

    private static byte[] serialize(byte[] qA) {

        byte[] msgBytes = new byte[4 + 1 + qA.length];

        //4 bytes iniciais do protocolo
        msgBytes[0] = Util.BYTE_BASE_VERSAO;
        msgBytes[1] = MessageSsms.HERE_IS_YOUR_QA;
        msgBytes[2] = (byte) Configuration.K;
        msgBytes[3] = (byte) 0x00; //Reservado para segmentação

        //Bytes indicando o tamanho dos campos:
        msgBytes[4] = (byte) qA.length;

        //Campos:        
        System.arraycopy(qA, 0, msgBytes, 5, qA.length);

        return msgBytes;

    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(messageBytes);
        qA = new byte[Util.byteToInt(messageBytes[4])];
        System.arraycopy(msgBytes, 5, qA, 0, qA.length);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.util.Util;

/**
 * Representa a mensagem de primeiro uso. a
 */
public class RequestMyQaMessage extends MessageSsms {

    private byte[] yA;

    protected RequestMyQaMessage() {
    }

    public RequestMyQaMessage(byte[] yA) {
        this.yA = yA;
        this.messageBytes = serialize(yA);
    }

    public byte[] getYA() {
        return this.yA;
    }

    private static byte[] serialize(byte[] yA) {

        byte[] msgBytes = new byte[4 + 1 + yA.length];

        //4 bytes iniciais do protocolo
        msgBytes[0] = Util.BYTE_BASE_VERSAO;
        msgBytes[1] = MessageSsms.GIMME_QA;
        msgBytes[2] = (byte) Configuration.K;
        msgBytes[3] = (byte) 0x00; //Reservado para segmentação

        //Bytes indicando o tamanho dos campos:
        msgBytes[4] = (byte) yA.length;

        //Campos:        
        System.arraycopy(yA, 0, msgBytes, 5, yA.length);

        return msgBytes;

    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(messageBytes);
        yA = new byte[Util.byteToInt(messageBytes[4])];
        System.arraycopy(msgBytes, 5, yA, 0, yA.length);
    }
}

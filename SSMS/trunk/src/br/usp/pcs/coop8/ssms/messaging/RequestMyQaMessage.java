/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.protocol.BDCPS;
import br.usp.pcs.coop8.ssms.util.Util;

/**
 * Representa a mensagem de primeiro uso. a
 */
public class RequestMyQaMessage extends MessageSsms {

    private int maxCharacters = 140;
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


        byte[] msgBytes = new byte[140];
        //22 bytes cada..         
        msgBytes[0] = (byte) ((int) Util.BYTE_BASE_VERSAO ^ (int) MessageSsms.GIMME_QA);
        //msgBytes[1] = (byte) BDCPS.getInstance().getK();

        System.arraycopy(yA, 0, msgBytes, 2, 22);

        return msgBytes;

    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(messageBytes);
        yA = new byte[22];
        System.arraycopy(msgBytes, 2, yA, 0, 22);
    }
}

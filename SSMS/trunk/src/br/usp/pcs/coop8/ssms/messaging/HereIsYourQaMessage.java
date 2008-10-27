/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.protocol.BDCPS;
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

        byte[] msgBytes = new byte[140];
        //22 bytes cada..         
        msgBytes[0] = (byte) ((int) Util.BYTE_BASE_VERSAO ^ (int) MessageSsms.HERE_IS_YOUR_QA);
        //msgBytes[1] = (byte) BDCPS.getInstance().getK();

        System.arraycopy(qA, 0, msgBytes, 2, 22);

        return msgBytes;

    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(messageBytes);
        qA = new byte[22];
        System.arraycopy(msgBytes, 2, qA, 0, 22);
    }
}

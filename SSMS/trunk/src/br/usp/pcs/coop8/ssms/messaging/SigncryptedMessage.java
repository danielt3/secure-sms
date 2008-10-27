/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.util.Util;

/**
 *
 * @author Administrador
 */
public class SigncryptedMessage extends MessageSsms {

    private byte[] message;

    protected SigncryptedMessage() {
    }

    /**
     * 
     */
    public SigncryptedMessage(byte[] message) {
        this.message = message;
        this.messageBytes = serialize(message);
    }

    private static byte[] serialize(byte[] message) {

        byte[] msgBytes = new byte[140];
        //22 bytes cada..         
        msgBytes[0] = (byte) ((int) Util.BYTE_BASE_VERSAO ^
                (int) MessageSsms.SIGNCRYPTED_MESSAGE);
        //msgBytes[1] = (byte) BDCPS.getInstance().getK();

        System.arraycopy(message, 0, msgBytes, 2, message.length);

        return msgBytes;

    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(messageBytes);
        message = new byte[140 - 2];
        System.arraycopy(msgBytes, 2, message, 0, msgBytes.length - 2);
    }
}

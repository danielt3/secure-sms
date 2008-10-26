/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.message;

import br.usp.pcs.coop8.ssms.tests.*;

/**
 *
 * @author Administrador
 */
public class AuthenticationMessage extends MessageSsms {


    private byte[] yA;
    private byte[] hA;
    private byte[] tA;

    private AuthenticationMessage() {
    }
    
    public AuthenticationMessage(byte[] yA, byte[] hA, byte[] tA) {
        this.yA = yA;
        this.hA = hA;
        this.tA = tA;
        this.messageBytes = serialize(yA, hA, tA);
    }

    private static byte[] serialize(byte[] yA, byte[] hA, byte[] tA) {

        byte[] msgBytes = new byte[140];
        //22 bytes cada..         
        msgBytes[0] = (byte) ((int) Util.BYTE_BASE_VERSAO ^ (int) MessageSsms.AUTHENTICATE_ME);
        msgBytes[1] = (byte) BDCPS.getInstance().getK();

        System.arraycopy(yA, 0, msgBytes, 2, 22);
        System.arraycopy(hA, 0, msgBytes, 24, 22);
        System.arraycopy(tA, 0, msgBytes, 46, 22);

        return msgBytes;

    }
    
    protected void deserialize(byte[] rawMessage) {
        this.messageBytes = rawMessage;
        
        System.arraycopy(messageBytes, 2, yA, 0, 22);
        System.arraycopy(messageBytes, 24, hA, 0, 22);
        System.arraycopy(messageBytes, 46, tA, 0, 22);

    }
}

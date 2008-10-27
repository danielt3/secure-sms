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

    public RequestMyQaMessage(byte[] yA) {
        this.yA = yA;
        this.messageBytes = serialize(yA);
    }

    private static byte[] serialize(byte[] yA) {


        byte[] msgBytes = new byte[140];
        //22 bytes cada..         
        msgBytes[0] = (byte) ((int) Util.BYTE_BASE_VERSAO ^ (int) MessageSsms.AUTHENTICATE_ME);
        //msgBytes[1] = (byte) BDCPS.getInstance().getK();
        
        System.arraycopy(yA, 0, msgBytes, 2, 22);

        return msgBytes;

    }

    protected void deserialize(byte[] msgBytes) {
        this.messageBytes = msgBytes;
        
        System.arraycopy(msgBytes, 2, yA, 0, 22);
    }

    

    
}

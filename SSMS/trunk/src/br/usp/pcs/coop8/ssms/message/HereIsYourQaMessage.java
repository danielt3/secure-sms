/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.pcs.coop8.ssms.message;

import br.usp.pcs.coop8.ssms.tests.BDCPS;
import br.usp.pcs.coop8.ssms.tests.Util;

/**
 *
 * @author Administrador
 */
public class HereIsYourQaMessage extends MessageSsms {
    
    private int maxCharacters = 140;
    private byte[] qA;

    /**
     * 
     */
    public HereIsYourQaMessage(byte[] qA) {
        this.qA = qA;
        this.messageBytes = serialize(qA);
    }

    private static byte[] serialize(byte[] qA) {

        byte[] msgBytes = new byte[140];
        //22 bytes cada..         
        msgBytes[0] = (byte) ((int) Util.BYTE_BASE_VERSAO ^ (int) MessageSsms.AUTHENTICATE_ME);
        msgBytes[1] = (byte) BDCPS.getInstance().getK();
        
        System.arraycopy(qA, 0, msgBytes, 2, 22);
        
        return msgBytes;

    }

    protected void deserialize(byte[] msgBytes) {
        this.messageBytes = msgBytes;
        
        System.arraycopy(msgBytes, 2, qA, 0, 22);
    }

    

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.util.Util;

/**
 * Representa a mensagem de primeiro uso. a
 */
public class RequestMyQaMessage extends MessageSsms {

    private byte[] yA;
    //private byte[] h;
    //private byte[] z;
    protected RequestMyQaMessage() {
    }

    public RequestMyQaMessage(byte[] yA) {
        this.yA = yA;
        //this.h = h;
        //this.z = z;
        this.messageBytes = serialize(MessageSsms.GIMME_QA, new byte[][]{yA //        , h, z
        });
    }

    public byte[] getYA() {
        return this.yA;
    }

    //public byte[] getH() {
    //    return this.h;
    //}

    //public byte[] getZ() {
    //    return this.z;
    //}
    protected void deserialize(byte[] msgBytes) {
        super.deserialize(msgBytes);
        yA = new byte[Util.byteToInt(messageBytes[4])];
        System.arraycopy(msgBytes, 5, yA, 0, yA.length);
        
        //yA = new byte[Util.byteToInt(messageBytes[4])];
        //h = new byte[Util.byteToInt(messageBytes[5])];
        //z = new byte[Util.byteToInt(messageBytes[6])];
        
        //System.arraycopy(msgBytes, 7, yA, 0, yA.length);
        //System.arraycopy(msgBytes, 7 + yA.length, h, 0, h.length);
        //System.arraycopy(msgBytes, 7 + yA.length + h.length, z, 0, z.length);
        
    }
}

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
public class HereIsYourQaMessage extends MessageSsms {

    private byte[] qA;
    private byte[] h;
    private byte[] z;

    protected HereIsYourQaMessage() {
    }

    /**
     * 
     */
    public HereIsYourQaMessage(byte[] qA, byte[] h, byte[] z) {
        this.qA = qA;
        this.h = h;
        this.z = z;
        this.messageBytes = serialize(MessageSsms.HERE_IS_YOUR_QA, new byte[][]{qA, h, z});
    }

    public byte[] getQA() {
        return this.qA;
    }

    public byte[] getH() {
        return h;
    }

    public byte[] getZ() {
        return z;
    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(msgBytes);
        qA = new byte[Util.byteToInt(messageBytes[4])];
        h = new byte[Util.byteToInt(messageBytes[5])];
        z = new byte[Util.byteToInt(messageBytes[6])];

        System.arraycopy(msgBytes, 7, qA, 0, qA.length);
        System.arraycopy(msgBytes, 7 + qA.length, h, 0, h.length);
        System.arraycopy(msgBytes, 7 + qA.length + h.length, z, 0, z.length);
    }
}

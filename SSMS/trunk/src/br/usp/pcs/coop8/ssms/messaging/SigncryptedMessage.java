/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.util.Util;
import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author Administrador
 */
public class SigncryptedMessage extends MessageSsms implements Persistable {

    private byte[] c;
    private byte[] h;
    private byte[] z;

    public byte[] getC() {
        return c;
    }

    public void setC(byte[] c) {
        this.c = c;
    }

    public byte[] getH() {
        return h;
    }

    public void setH(byte[] h) {
        this.h = h;
    }

    public byte[] getZ() {
        return z;
    }

    public void setZ(byte[] z) {
        this.z = z;
    }

    public SigncryptedMessage() {
    }

    /**
     * 
     */
    public SigncryptedMessage(byte[] c, byte[] h, byte[] z) {
        this.c = c;
        this.h = h;
        this.z = z;
        this.messageBytes = serialize(c, h, z);
    }

    private static byte[] serialize(byte[] c, byte[] h, byte[] z) {

        byte[] msgBytes = new byte[4 + 3 + c.length + h.length + z.length];

        //4 bytes iniciais do protocolo
        msgBytes[0] = Util.BYTE_BASE_VERSAO;
        msgBytes[1] = MessageSsms.SIGNCRYPTED_MESSAGE;
        msgBytes[2] = (byte) Configuration.K;
        msgBytes[3] = (byte) 0x00; //Reservado para segmentação

        //Bytes indicando o tamanho dos campos:
        msgBytes[4] = (byte) c.length;
        msgBytes[5] = (byte) h.length;
        msgBytes[6] = (byte) z.length;

        //Campos:        
        System.arraycopy(c, 0, msgBytes, 7, c.length);
        System.arraycopy(h, 0, msgBytes, 7 + c.length, h.length);
        System.arraycopy(z, 0, msgBytes, 7 + c.length + h.length, z.length);

        return msgBytes;

    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(messageBytes);
        c = new byte[Util.byteToInt(messageBytes[4])];
        h = new byte[Util.byteToInt(messageBytes[5])];
        z = new byte[Util.byteToInt(messageBytes[6])];

        System.arraycopy(msgBytes, 7, c, 0, c.length);
        System.arraycopy(msgBytes, 7 + c.length, h, 0, h.length);
        System.arraycopy(msgBytes, 7 + c.length + h.length, z, 0, z.length);
    }
}

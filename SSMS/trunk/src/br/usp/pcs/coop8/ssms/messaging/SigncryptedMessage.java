/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.util.Util;
import java.util.Date;
import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author Administrador
 */
public class SigncryptedMessage extends MessageSsms implements Persistable {

    private byte[] c;
    private byte[] h;
    private byte[] z;
    //Estes campos tem que ser redundantes mesmo.. senão o floggy não os persite    
    private String sender;
    private String destinatary;
    private Date date;

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
        this.messageBytes = serialize(MessageSsms.SIGNCRYPTED_MESSAGE, new byte[][]{c, h, z});
    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(msgBytes);
        c = new byte[Util.byteToInt(messageBytes[4])];
        h = new byte[Util.byteToInt(messageBytes[5])];
        z = new byte[Util.byteToInt(messageBytes[6])];

        System.arraycopy(msgBytes, 7, c, 0, c.length);
        System.arraycopy(msgBytes, 7 + c.length, h, 0, h.length);
        System.arraycopy(msgBytes, 7 + c.length + h.length, z, 0, z.length);
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setDestinatary(String destinatary) {
        this.destinatary = destinatary;
    }

    public String getDestinatary() {
        return this.destinatary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static Class getThisClass() {
        try {
            return Class.forName("br.usp.pcs.coop8.ssms.messaging.SigncryptedMessage");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

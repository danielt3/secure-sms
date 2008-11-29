/**
 *
 * Copyright (C) 2008 Eduardo de Souza Cruz, Geovandro Carlos C. F. Pereira
 *                    and Rodrigo Rodrigues da Silva
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.util.Util;
import java.util.Date;
import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author Administrador
 */
public class SigncryptedMessage extends SecureMessage implements Persistable {

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
        this.messageBytes = serialize(SecureMessage.SIGNCRYPTED_MESSAGE, new byte[][]{c, h, z});
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

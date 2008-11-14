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

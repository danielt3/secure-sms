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
 *
 * @author Administrador
 */
public class AuthenticationMessage extends MessageSsms {

    private byte[] yA;
    private byte[] hA;
    private byte[] tA;

    protected AuthenticationMessage() {
    }

    public AuthenticationMessage(byte[] yA, byte[] hA, byte[] tA) {
        this.yA = yA;
        this.hA = hA;
        this.tA = tA;
        this.messageBytes = serialize(MessageSsms.AUTHENTICATE_ME, new byte[][]{yA, hA, tA});
    }

    public byte[] getYA() {
        return yA;
    }

    public byte[] getHA() {
        return hA;
    }

    public byte[] getTA() {
        return tA;
    }

    protected void deserialize(byte[] msgBytes) {
        super.deserialize(msgBytes);
        yA = new byte[Util.byteToInt(messageBytes[4])];
        hA = new byte[Util.byteToInt(messageBytes[5])];
        tA = new byte[Util.byteToInt(messageBytes[6])];

        System.arraycopy(msgBytes, 7, yA, 0, yA.length);
        System.arraycopy(msgBytes, 7 + yA.length, hA, 0, hA.length);
        System.arraycopy(msgBytes, 7 + yA.length + hA.length, tA, 0, tA.length);
    }
}

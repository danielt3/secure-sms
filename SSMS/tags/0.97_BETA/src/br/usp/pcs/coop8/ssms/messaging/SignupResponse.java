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
public class SignupResponse extends SecureMessage {

    private byte[] qA;
    private byte[] h;
    private byte[] z;

    protected SignupResponse() {
    }

    /**
     * 
     */
    public SignupResponse(byte[] qA, byte[] h, byte[] z) {
        this.qA = qA;
        this.h = h;
        this.z = z;
        this.messageBytes = serialize(SecureMessage.HERE_IS_YOUR_QA, new byte[][]{qA, h, z});
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

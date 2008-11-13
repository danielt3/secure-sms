/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.Configuration;
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

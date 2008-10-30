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
        this.messageBytes = serialize(yA, hA, tA);
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

    private static byte[] serialize(byte[] yA, byte[] hA, byte[] tA) {

        byte[] msgBytes = new byte[4 + 3 + yA.length + hA.length + tA.length];

        //4 bytes iniciais do protocolo
        msgBytes[0] = Util.BYTE_BASE_VERSAO;
        msgBytes[1] = MessageSsms.AUTHENTICATE_ME;
        msgBytes[2] = (byte) Configuration.K;
        msgBytes[3] = (byte) 0x00; //Reservado para segmentação

        //Bytes indicando o tamanho dos campos:
        msgBytes[4] = (byte) yA.length;
        msgBytes[5] = (byte) hA.length;
        msgBytes[6] = (byte) tA.length;

        //Campos:        
        System.arraycopy(yA, 0, msgBytes, 7, yA.length);
        System.arraycopy(hA, 0, msgBytes, 7 + yA.length, hA.length);
        System.arraycopy(tA, 0, msgBytes, 7 + yA.length + hA.length, tA.length);

        return msgBytes;

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

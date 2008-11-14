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

import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.util.Util;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public abstract class MessageSsms {

    //Primitivas:
    public static final byte GIMME_QA = 0x00;
    public static final byte HERE_IS_YOUR_QA = 0x01;
    public static final byte AUTHENTICATE_ME = 0x02;
    public static final byte SIGNCRYPTED_MESSAGE = 0x03;
    protected byte[] messageBytes;
    protected String sender;
    protected String destinatary;
    protected Date date;

    /**
     * Método de fábrica, identifica que tipo de mensagem é baseado na primitiva
     * e retorna a instância adequada da mensagem
     */
    public static MessageSsms getMessage(byte[] msgBytes) {

        MessageSsms ret;
        switch (msgBytes[1]) {
            case GIMME_QA:
                ret = new RequestMyQaMessage();
                ret.deserialize(msgBytes);
                break;
            case HERE_IS_YOUR_QA:
                ret = new HereIsYourQaMessage();
                ret.deserialize(msgBytes);
                break;
            case AUTHENTICATE_ME:
                ret = new AuthenticationMessage();
                ret.deserialize(msgBytes);
                break;
            case SIGNCRYPTED_MESSAGE:
                ret = new SigncryptedMessage();
                ret.deserialize(msgBytes);
                break;
            default:
                ret = null;

        }
        return ret;
    }

    protected static byte[] serialize(byte primitive, byte[][] args) {

        int tamanhoArgs = 0;
        for (int i = 0; i < args.length; i++) {
            tamanhoArgs += args[i].length;
        }

        //4 bytes de cabeçalho
        //args.length bytes reservados para o tamanho dos argumentos, 1 byte para cada argumento
        byte[] msgBytes = new byte[4 + args.length + tamanhoArgs];

        //4 bytes iniciais do protocolo
        msgBytes[0] = Util.BYTE_BASE_VERSAO;
        msgBytes[1] = primitive;
        msgBytes[2] = (byte) Configuration.K;
        msgBytes[3] = (byte) 0x00; //Reservado para segmentação

        //Bytes indicando o tamanho dos campos:
        int bytesWritten = 0;
        for (int i = 0; i < args.length; i++) {
            msgBytes[4 + i] = (byte) args[i].length;
            System.arraycopy(args[i], 0, msgBytes, 4 + args.length + bytesWritten, args[i].length);
            bytesWritten += args[i].length;

        }
        return msgBytes;

    }

    /**
     * Retorna os bytes binários da mensagem (8 bits)
     */
    public byte[] getMessageBytes() {
        return this.messageBytes;
    }

    protected void deserialize(byte[] rawMessage) {
        this.messageBytes = rawMessage;
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
}

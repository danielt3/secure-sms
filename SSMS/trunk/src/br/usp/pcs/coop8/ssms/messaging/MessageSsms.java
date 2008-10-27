/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

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

    /**
     * Método de fábrica, identifica que tipo de mensagem é baseado na primitiva
     * e retorna a instância adequada da mensagem
     */
    public static MessageSsms getMessage(byte[] msgBytes) {
        //TODO: implementar
        return null;
    }

    /**
     * Retorna os bytes binários da mensagem (8 bits)
     */
    public byte[] getBytes() {
        return this.messageBytes;
    }
    
    //protected abstract void deserialize(byte[] rawMessage);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.tests;

import pseudojava.BigInteger;

/**
 *
 * @author Administrador
 */
public class AuthenticationMessage extends MessageSsms {

    private static String messageText;
    private int maxCharacters = 140;
    private byte[] msgBytes;
    private byte[] xA;
    private byte[] hA;
    private byte[] qA;
    
    public AuthenticationMessage() {
        
    }

    public static byte[] serialize(byte[] xA, byte[] hA, byte[] qA) {

        /*
        Public-Key-Validate: dada a chave p´ublica completa da entidade A, (yA; hA; TA),
        este algoritmo verifica que yA tem ordem n (i.e. que yA , 1 mas yn
        A = 1) e calcula
        1. rA   e(h1(yA; IDA)P + Ppub; TA)yhA
        A
        2. vA   h0(rA; yA; IDA)
        O verificador aceita a mensagem se, e somente se vA = hA. O processo de
        validac¸ ˜ao combina a verificac¸ ˜ao da assinatura Schnorr com a verificac¸ ˜ao da assinatura
        BLMQ.
         */
        byte[] msgBytes = new byte[140];
        //22 bytes cada..         
        msgBytes[0] = (byte) ((int) Util.BYTE_BASE_VERSAO ^ (int) MessageSsms.AUTHENTICATE_ME);
        msgBytes[1] = (byte) Util.K;
        for (int i = 0; i < 22; i++) {
            msgBytes[i + 2] = xA[i];
            msgBytes[i + 2 + 22] = hA[i];
            msgBytes[i + 2 + 22 + 22] = qA[i];
        }
        
        return msgBytes;
        
    }
    
    public AuthenticationMessage(byte[] msgBytes) {
        this.msgBytes = msgBytes;
        
        //TODO: Desserializar, extrair 
        
    }

    public String getString() {
        return this.messageText;
    }
}

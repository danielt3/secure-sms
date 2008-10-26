/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.tests;

/**
 *
 * @author Administrador
 */
public abstract class MessageSsms {

    //Primitivas:
    public static final byte AUTHENTICATE_ME = 0x01;
    public static final byte SENDMESSAGE = 0x02;

    public abstract String getString();
}

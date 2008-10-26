/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.tests;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 * Executa funcionalidades a partir do menu
 * @author Administrador
 */
public abstract class Controlador {

    private static SmsListener smsListener = null;

    private Controlador() {
    }
    
    public static void enviarSmsAutenticacao(String telefone) {
               
    }

    public static void receberSms() {
        if (smsListener == null) {
            smsListener = new SmsListener();
            smsListener.startListening();
        }
    }

    public static void enviarSms(String phone, String texto, int porta) {

        try {
            String addr = "sms://" + phone + ":" + porta;
            MessageConnection conn = (MessageConnection) Connector.open(addr);
            TextMessage msg =
                    (TextMessage) conn.newMessage(MessageConnection.TEXT_MESSAGE);
            msg.setPayloadText("GEO GEO GEO GEOVEMDETERRAAAA");

            conn.send(msg);
        } catch (IllegalArgumentException iae) {
        //do something

        } catch (Exception e) {
        //do something
        }
    }
}

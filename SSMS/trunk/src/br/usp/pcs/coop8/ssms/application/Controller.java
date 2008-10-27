/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.protocol.BDCPS;
import br.usp.pcs.coop8.ssms.messaging.SmsListener;
import br.usp.pcs.coop8.ssms.messaging.MessageSsms;
import br.usp.pcs.coop8.ssms.messaging.RequestMyQaMessage;
import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 * Executa funcionalidades a partir do menu
 * @author Administrador
 */
public abstract class Controller {

    private static SmsListener smsListener = null;

    private Controller() {
    }

    public static void enviarSmsAutenticacao(String telefone) {

    }

    /**
     * Pede o Qa para a operadora.
     */
    public static void requestMyQa(byte[] xA) {
/**
        BDCPS.getInstance().setPrivateKey(xA);
        BDCPS.getInstance().setPublicValue();
        byte[] ya = BDCPS.getInstance().getPublicValue();
                
        MessageSsms msg = new RequestMyQaMessage(ya);
 */
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


            msg.setPayloadText(texto);

            conn.send(msg);
        } catch (IllegalArgumentException iae) {
        //do something

        } catch (Exception e) {
        //do something
        }
    }

    public static void enviarSmsBinario(String phone, byte[] data, int porta) {
        try {
            String addr = "sms://" + phone + ":" + porta;
            MessageConnection conn = (MessageConnection) Connector.open(addr);
            BinaryMessage msg = (BinaryMessage) conn.newMessage(MessageConnection.BINARY_MESSAGE);

            msg.setPayloadData(data);

            conn.send(msg);
        } catch (IllegalArgumentException iae) {
        //do something

        } catch (Exception e) {
        //do something
        }
    }
}

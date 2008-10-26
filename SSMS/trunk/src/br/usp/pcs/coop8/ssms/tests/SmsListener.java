/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.tests;

import javax.microedition.io.Connector;
import javax.microedition.media.control.MetaDataControl;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;

/**
 *
 * @author Administrador
 */
public class SmsListener
        implements MessageListener {

    private int port;
    private MessageConnection messageConnection;
    private Thread threadListener;

    /**
     * Escuta a porta 2102 aguardando o recebimento de um SMS.
     * 
     * @param port A porta a ser escutada.
     */
    public SmsListener() {
        this(2102);
    }

    /**
     * Escuta uma porta, aguardando o recebimento de um SMS.
     * 
     * @param port A porta a ser escutada.
     */
    public SmsListener(int port) {
        this.port = port;
    }

    public void startListening() {
        try {
            this.messageConnection = (MessageConnection) Connector.open("sms://:" + port);
            this.messageConnection.setMessageListener(this);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void stopListening() {
        if (this.messageConnection != null) {
            try {
                this.messageConnection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void notifyIncomingMessage(MessageConnection messageConnection) {
        Output.println("Recieved SMS from " + messageConnection);

        this.threadListener =
                new Thread() {

                    MessageConnection conn;

                    Thread set(MessageConnection conn) {
                        this.conn = conn;
                        return (this);
                    }

                    public void run() {
                        try {
                            TextMessage msg = (TextMessage) conn.receive();
                            Output.println("Recieved SMS: " + msg.getPayloadText());
                            Output.println("Veio de: " + msg.getAddress());

                        //Adicionar código aqui!  Como mostrar na telinha??? =(
                            


                        } catch (Exception e) {
                            Output.println("SMS recieve Exception " + e);
                            e.printStackTrace();
                        }
                    }
                }.set(messageConnection);

        threadListener.start();
    }
}


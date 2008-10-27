/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.KgbSsms;
import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.util.Util;
import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;

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

        final SmsListener _this = this;

        new Thread() {

            public void run() {
                try {
                    messageConnection = (MessageConnection) Connector.open("sms://:" + port);
                    messageConnection.setMessageListener(_this);
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                }
            }
        }.start();


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
        Output.println("Receieved SMS from " + messageConnection);

        this.threadListener =
                new Thread() {

                    MessageConnection conn;

                    Thread set(
                            MessageConnection conn) {
                        this.conn = conn;
                        return (this);
                    }

                    public void run() {
                        try {
                            BinaryMessage binMsg = (BinaryMessage) conn.receive();
                            Output.println("Recieved SMS: " +
                                    Util.byteArrayToDebugableString(binMsg.getPayloadData()));
                            Output.println("Came from: " + binMsg.getAddress());

                            // A string é assim: sms://01185841768

                            //Desse jeito vem 10 dígitos
                            String telRemetente = binMsg.getAddress().substring(7, 17);

                            MessageSsms msg = MessageSsms.getMessage(binMsg.getPayloadData());

                            if (msg instanceof AuthenticationMessage) {

                            } else if (msg instanceof HereIsYourQaMessage) {
                                byte myQa[] = ((HereIsYourQaMessage) msg).getQA();
                            //TODO: persistir meu QA !

                            } else if (msg instanceof RequestMyQaMessage) {
                                //Aqui vou me passar por KGB
                                KgbSsms.returnQaMessage((RequestMyQaMessage) msg, telRemetente.getBytes());
                            }

                        } catch (Exception e) {
                            Output.println("SMS recieve Exception " + e);
                            e.printStackTrace();
                        }

                    }
                }.set(messageConnection);

        threadListener.start();
    }
}


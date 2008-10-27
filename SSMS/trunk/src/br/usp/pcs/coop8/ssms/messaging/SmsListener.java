/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.util.Util;
import br.usp.pcs.coop8.ssms.messaging.AuthenticationMessage;
import br.usp.pcs.coop8.ssms.messaging.HereIsYourQaMessage;
import br.usp.pcs.coop8.ssms.messaging.MessageSsms;
import br.usp.pcs.coop8.ssms.messaging.RequestMyQaMessage;
import javax.microedition.io.Connector;
import javax.microedition.media.control.MetaDataControl;
import javax.wireless.messaging.BinaryMessage;
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
        Output.println("Receieved SMS from " + messageConnection);

        this.threadListener =
                new Thread() {

                    MessageConnection conn;

                    Thread set(MessageConnection conn) {
                        this.conn = conn;
                        return (this);
                    }

                    public void run() {
                        try {
                            BinaryMessage binMsg = (BinaryMessage) conn.receive();
                            Output.println("Recieved SMS: " +
                                    Util.byteArrayToDebugableString(binMsg.getPayloadData()));
                            Output.println("Came from: " + binMsg.getAddress());

                            MessageSsms msg = MessageSsms.getMessage(binMsg.getPayloadData());
                            
                            if (msg instanceof AuthenticationMessage) {                                
                                
                            } else if (msg instanceof HereIsYourQaMessage)  {
                                
                            } else if (msg instanceof RequestMyQaMessage)  {
                                
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


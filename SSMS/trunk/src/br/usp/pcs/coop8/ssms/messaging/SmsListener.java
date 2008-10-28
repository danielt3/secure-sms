/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.application.KgbSsms;
import br.usp.pcs.coop8.ssms.data.MyPrivateData;
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
     * Escuta a porta padrão aguardando o recebimento de um SMS.
     * 
     * @param port A porta a ser escutada.
     */
    public SmsListener() {
        this(Configuration.SMS_PORT);
    }

    /**
     * Escuta uma porta qualquer, aguardando o recebimento de um SMS.
     * 
     * @param port A porta a ser escutada.
     */
    public SmsListener(int port) {
        this.port = port;
    }

    /**
     * Inicia a escuta
     */
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
                                handleAuthenticationMessageReceived((AuthenticationMessage) msg, telRemetente);
                            } else if (msg instanceof HereIsYourQaMessage) {
                                handleHereIsYourQaMessageReceived((HereIsYourQaMessage) msg, telRemetente);
                            } else if (msg instanceof RequestMyQaMessage) {
                                handleRequestMyQaMessageReceived((RequestMyQaMessage) msg, telRemetente);
                            } else if (msg instanceof SigncryptedMessage) {
                                handleSignCryptedMessageReceived((SigncryptedMessage) msg, telRemetente);
                            }

                        } catch (Exception e) {
                            Output.println("SMS recieve Exception " + e);
                            e.printStackTrace();
                        }

                    }
                }.set(messageConnection);

        threadListener.start();
    }

    private void handleAuthenticationMessageReceived(AuthenticationMessage msg, String telRemetente) {
    //TODO: Verificar se a chave que chegou é válida, e 
    //adicionar o novo contado na lista com a nova chave        

    }

    private void handleHereIsYourQaMessageReceived(HereIsYourQaMessage msg, String telRemetente) {
        byte myQa[] = msg.getQA();
        //TODO: persistir meu QA !        
        MyPrivateData myPrivData = null; //TODO: ler do banco        
        myPrivData.setQA(myQa);
        Output.println("Recebido meu QA: " + Util.byteArrayToDebugableString(myQa));
    //TODO: persistir de novo no banco.
    }

    private void handleRequestMyQaMessageReceived(RequestMyQaMessage msg, String telRemetente) {
        //Aqui vou me passar por KGB
        Output.println("Sou KGB, recebido yA: " + Util.byteArrayToDebugableString(msg.getYA()));
        KgbSsms.returnQaMessage((RequestMyQaMessage) msg, telRemetente.getBytes());
    }

    private void handleSignCryptedMessageReceived(SigncryptedMessage msg, String telRemetente) {
    //TODO: fazer tudo        
    }
}


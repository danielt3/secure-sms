/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.larc.smspairing.SMSPoint;
import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.application.KgbSsms;
import br.usp.pcs.coop8.ssms.data.Contact;
import br.usp.pcs.coop8.ssms.data.MyPrivateData;
import br.usp.pcs.coop8.ssms.protocol.BDCPSClient;
import br.usp.pcs.coop8.ssms.protocol.BDCPSParameters;
import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.util.Util;
import java.util.Date;
import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import net.sourceforge.floggy.persistence.Filter;
import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;
import org.bouncycastle.crypto.digests.SHA1Digest;
import pseudojava.FilterInputStream;

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
                            msg.setSender(telRemetente);
                            msg.setDate(new Date());

                            if (msg instanceof AuthenticationMessage) {
                                handleAuthenticationMessageReceived((AuthenticationMessage) msg);
                            } else if (msg instanceof HereIsYourQaMessage) {
                                handleHereIsYourQaMessageReceived((HereIsYourQaMessage) msg);
                            } else if (msg instanceof RequestMyQaMessage) {
                                handleRequestMyQaMessageReceived((RequestMyQaMessage) msg);
                            } else if (msg instanceof SigncryptedMessage) {
                                handleSignCryptedMessageReceived((SigncryptedMessage) msg);
                            }

                        } catch (Exception e) {
                            Output.println("SMS recieve Exception " + e);
                            e.printStackTrace();
                        }

                    }
                }.set(messageConnection);

        threadListener.start();
    }

    private void handleAuthenticationMessageReceived(AuthenticationMessage msg) {
        try {

            final String senderPhone = msg.getSender();


            PersistableManager perMan = PersistableManager.getInstance();

            ObjectSet results = perMan.find(Contact.class, new Filter() {

                public boolean matches(Persistable arg0) {
                    return ((Contact) arg0).getPhone().equals(senderPhone);
                }
            }, null);

            Contact contact;
            if (results.size() > 0) {
                contact = (Contact) results.get(0);
            } else {
                // Ainda não conheço o contato... Vamos criar um novo com o nome definido
                // como o telefone. O usuário poderá trocar depois.
                contact = new Contact(senderPhone, senderPhone);
            }

            if (contact.getYA() != null) {
                //Já temos os parâmetros públicos para este contato.
                //O que fazer? Acho que devemos ignorar os novos parâmetros
                //e manter os antigos...
                return;
            } else {
                //Ainda não conheciamos os parâmetros públicos.. vamos validá-los.

                MyPrivateData myData = MyPrivateData.getInstance();
                byte[] hashMyId = new byte[20];
                byte[] hashIdB = new byte[20];
                SHA1Digest sha = new SHA1Digest();

                sha.reset();
                sha.update(myData.getIdA().getBytes(), 0, myData.getIdA().getBytes().length);
                sha.doFinal(hashMyId, 0);

                sha.reset();
                sha.update(senderPhone.getBytes(), 0, senderPhone.getBytes().length);
                sha.doFinal(hashIdB, 0);

                BDCPSClient bdcps = new BDCPSClient(Configuration.K, BDCPSParameters.getInstance(Configuration.K).PPub.toByteArray(SMSPoint.COMPRESSED), hashMyId);

                byte[][] publicKeyB = new byte[3][];
                publicKeyB[0] = msg.getYA();
                publicKeyB[1] = msg.getHA();
                publicKeyB[2] = msg.getTA();

                if (bdcps.publicKeyValidate(publicKeyB, hashIdB)) {

                    //Parâmetros válidos ! Vamos salvá-los...
                    contact.setYA(msg.getYA());
                    contact.setHA(msg.getHA());
                    contact.setTA(msg.getTA());

                    perMan.save(contact);
                    return;
                } else {
                    //Não é válido, é algum mentiroso!                    
                    //Mantém tudo nulo, ignora a mensagem que chegou
                    return;
                }
            }


        } catch (FloggyException ex) {
            ex.printStackTrace();
        }


    }

    private void handleHereIsYourQaMessageReceived(HereIsYourQaMessage msg) {

        byte[] myQa = msg.getQA();

        //Chegou o QA

        MyPrivateData myPrivData = MyPrivateData.getInstance();


        myPrivData.setQA(myQa);

        // Esses aqui o usuário vai precisar fornecer o xA para calcular...
        // Deixamos o QA guardado e vamos pedir o XA quando conveniente
        myPrivData.setHA(null);
        myPrivData.setTA(null);

        PersistableManager perMan = PersistableManager.getInstance();
        try {
            perMan.save(myPrivData);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
        Output.println("Recebido meu QA: " + Util.byteArrayToDebugableString(myQa));

    }

    private void handleRequestMyQaMessageReceived(RequestMyQaMessage msg) {

        final String senderPhone = msg.getSender();

        //Aqui vou me passar por KGB
        Output.println("Sou KGB, recebido yA: " + Util.byteArrayToDebugableString(msg.getYA()));
        KgbSsms.returnQaMessage((RequestMyQaMessage) msg, senderPhone.getBytes());
    }

    private void handleSignCryptedMessageReceived(SigncryptedMessage msg) {

        //Simplesmente persiste a mensagem... Ficará no inbox
        PersistableManager perMan = PersistableManager.getInstance();
        try {
            perMan.save(msg);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }
}


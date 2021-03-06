/**
 *
 * Copyright (C) 2008 Eduardo de Souza Cruz, Geovandro Carlos C. F. Pereira
 *                    and Rodrigo Rodrigues da Silva
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */
package br.usp.pcs.coop8.ssms.messaging;

import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.application.Controller;
import br.usp.pcs.coop8.ssms.application.KeyGenerationBureau;
import br.usp.pcs.coop8.ssms.data.Contact;
import br.usp.pcs.coop8.ssms.data.PrivateData;
import br.usp.pcs.coop8.ssms.protocol.BDCPSClient;
import br.usp.pcs.coop8.ssms.protocol.BDCPSParameters;
import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.util.Util;
import java.util.Date;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.AlertType;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import net.sourceforge.floggy.persistence.Filter;
import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;
import org.bouncycastle.crypto.digests.SHA1Digest;

/**
 *
 * @author Administrador
 */
public class SmsListener
        implements MessageListener {

    private int port;
    private MessageConnection messageConnection;
    private Thread threadListener;
    private boolean killAppAfterReceived = false;

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

    public void receiveOneMessageAndExit() {

        this.killAppAfterReceived = true;
        startListening();
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
                            Output.println("Receieved SMS: " +
                                    Util.byteArrayToDebugableString(binMsg.getPayloadData()));
                            Output.println("Came from: " + binMsg.getAddress());

                            // A string é assim: sms://01185841768
                            //Mas também vem assim as vezes: sms://+5511etctc

                            //Desse jeito vem 10 dígitos
                            String telRemetente;
                            if (binMsg.getAddress().startsWith("sms://+55")) {

                                telRemetente = binMsg.getAddress().substring(9, 19);
                            } else {
                                telRemetente = binMsg.getAddress().substring(7, 17);
                            }

                            SecureMessage msg = SecureMessage.getMessage(binMsg.getPayloadData());

                            if (msg != null) {
                                msg.setSender(telRemetente);
                                msg.setDate(new Date());
                            }

                            if (msg == null) {
                                //Mensagem desconhecida recebida
                                Output.println("Mensagem desconhecida ignorada.");
                            } else if (msg instanceof ValidationMessage) {
                                handleAuthenticationMessageReceived((ValidationMessage) msg);
                            } else if (msg instanceof SignupResponse) {
                                handleHereIsYourQaMessageReceived((SignupResponse) msg);
                            } else if (msg instanceof SignupMessage) {
                                handleRequestMyQaMessageReceived((SignupMessage) msg);
                            } else if (msg instanceof SigncryptedMessage) {
                                handleSignCryptedMessageReceived((SigncryptedMessage) msg);
                            }

                        } catch (Exception e) {
                            Output.println("SMS receieve Exception " + e);
                            e.printStackTrace();
                        }

                    }
                }.set(messageConnection);

        threadListener.start();
    }

    private void handleAuthenticationMessageReceived(ValidationMessage msg) {
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
                Output.println("Parâmetros públicos recebidos para um contato já autenticado. Ingorado.");
                return;
            } else {
                //Ainda não conheciamos os parâmetros públicos.. vamos validá-los.

                PrivateData myData = PrivateData.getInstance();
                byte[] hashMyId = new byte[20];
                byte[] hashIdB = new byte[20];
                SHA1Digest sha = new SHA1Digest();

                sha.reset();
                sha.update(myData.getIdA().getBytes(), 0, myData.getIdA().getBytes().length);
                sha.doFinal(hashMyId, 0);

                sha.reset();
                sha.update(senderPhone.getBytes(), 0, senderPhone.getBytes().length);
                sha.doFinal(hashIdB, 0);

                BDCPSClient bdcps = new BDCPSClient(Configuration.K, BDCPSParameters.getInstance(Configuration.K).PPubBytes, hashMyId);

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
                    Output.println("Chave pública recebida e validada. " + contact.getPhone() + " está autenticado.");
                    AlertType.INFO.playSound(Controller.getDisplay());
                    return;
                } else {
                    Output.println("Chave pública recebida não é válida. Ignora o mentiroso.");
                    AlertType.ERROR.playSound(Controller.getDisplay());
                    //Não é válido, é algum mentiroso!                    
                    //Mantém tudo nulo, ignora a mensagem que chegou
                    return;
                }
            }


        } catch (FloggyException ex) {
            ex.printStackTrace();
        }


    }

    private void handleHereIsYourQaMessageReceived(SignupResponse msg) {

        PrivateData myPrivData = PrivateData.getInstance();


        if (!msg.getSender().equals(myPrivData.getKgbPhone())) {
            Output.println("QA recebido de alguém diferente da KGB. Ignordado. Recebido de: " + msg.getSender());
            AlertType.ERROR.playSound(Controller.getDisplay());
            return;
        } else if (myPrivData.getQA() != null || myPrivData.getEncryptedQA_c() != null) {
            Output.println("QA recebido da KGB, porém já havia sido recebido anterioriormente. Ignorado.");
            return;
        } else {
            //QA chegou em boa hora

            myPrivData.setEncryptedQA_c(msg.getQA());
            myPrivData.setEncryptedQA_h(msg.getH());
            myPrivData.setEncryptedQA_z(msg.getZ());

            PersistableManager perMan = PersistableManager.getInstance();
            try {
                perMan.save(myPrivData);
            } catch (FloggyException ex) {
                ex.printStackTrace();
            }
            Output.println("Recebido meu QA cifrado.");

            AlertType.INFO.playSound(Controller.getDisplay());
            return;
        }

    //Chegou o QA, precisamos decriptá-lo


    }

    private void handleRequestMyQaMessageReceived(SignupMessage msg) {

        final String senderPhone = msg.getSender();

        //Aqui vou me passar por KGB
        Output.println("Sou KGB, recebido yA: " + Util.byteArrayToDebugableString(msg.getYA()));
        KeyGenerationBureau.returnQaMessage((SignupMessage) msg, senderPhone);
    }

    private void handleSignCryptedMessageReceived(SigncryptedMessage msg) {

        //Simplesmente persiste a mensagem... Ficará no inbox
        PersistableManager perMan = PersistableManager.getInstance();
        Output.println("Mensagem crifrassinada recebida de " + msg.getSender());
        try {
            perMan.save(msg);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }

        AlertType.INFO.playSound(Controller.getDisplay());
    }
}


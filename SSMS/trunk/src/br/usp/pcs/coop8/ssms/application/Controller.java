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

package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.data.Contact;
import br.usp.pcs.coop8.ssms.data.MyPrivateData;
import br.usp.pcs.coop8.ssms.messaging.AuthenticationMessage;
import br.usp.pcs.coop8.ssms.messaging.RequestMyQaMessage;
import br.usp.pcs.coop8.ssms.messaging.SigncryptedMessage;
import br.usp.pcs.coop8.ssms.messaging.SmsListener;
import br.usp.pcs.coop8.ssms.protocol.BDCPS;
import br.usp.pcs.coop8.ssms.protocol.BDCPSAuthority;
import br.usp.pcs.coop8.ssms.protocol.BDCPSClient;
import br.usp.pcs.coop8.ssms.protocol.BDCPSParameters;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import br.usp.pcs.coop8.ssms.protocol.exception.InvalidMessageException;
import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.util.Util;
import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import net.sourceforge.floggy.persistence.Filter;
import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import pseudojava.BigInteger;

/**
 * Executa funcionalidades a partir do menu
 * @author Administrador
 */
public abstract class Controller {

    private static SmsListener smsListener = null;
    private static SSMSMain ssmsApp;
    private static Contact selectedContact;
    private static SigncryptedMessage selectedMessage;

    private Controller() {
    }

    public static void resetData() {
        PersistableManager perMan = PersistableManager.getInstance();
        try {
            perMan.deleteAll();
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
        MyPrivateData.clearInstance();
    }

    /**
     * Rotina de inicialização da aplicação
     */
    public static void startApplication(SSMSMain ssmsApp) {

        Controller.ssmsApp = ssmsApp;

        Controller.receberSms();
        MyPrivateData.getInstance();
    }

    /**
     * Recebe o xA como texto, e envia o SMS de autenticação para a operadora
     * @param xA
     */
    public static void firstTimeUse(String xA, String id) {


        byte[] hashDoXa = new byte[20];
        byte[] hashDoId = new byte[20];
        byte[] hashDoTelKgb = new byte[20];

        MyPrivateData myData = MyPrivateData.getInstance();

        {

            SHA1Digest sha = new SHA1Digest();
            sha.reset();
            sha.update(xA.getBytes(), 0, xA.getBytes().length);
            sha.doFinal(hashDoXa, 0);

            sha.reset();
            sha.update(id.getBytes(), 0, id.getBytes().length);
            sha.doFinal(hashDoId, 0);

            sha.reset();
            sha.update(myData.getKgbPhone().getBytes(), 0, myData.getKgbPhone().getBytes().length);
            sha.doFinal(hashDoTelKgb, 0);
        }


        BDCPSClient bdcps = new BDCPSClient(Configuration.K, BDCPSParameters.getInstance(Configuration.K).PPubBytes, hashDoId);
        bdcps.setSecretValue(hashDoXa);
        bdcps.setPublicValue();

        byte[] yA = bdcps.getPublicValue();





        myData.setIdA(id);
        myData.setYA(yA);


        //Estes só serão preenchidos quando chegar o QA
        myData.setHA(null);
        myData.setTA(null);

        myData.setQA(null);

        PersistableManager perMan = PersistableManager.getInstance();
        try {
            perMan.save(myData);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }

        // Encriptar o yA:
        //byte[][] cryptogram;
        //try {
        //    cryptogram = bdcps.signcrypt(yA, hashDoTelKgb, BDCPSParameters.getInstance(Configuration.K).yKgbBytes);
        //} catch (CipherException ex) {
        //    ex.printStackTrace();
        //    Output.println("Exception encriptando yA: " + ex.getMessage());
        //    return;
        //}

        RequestMyQaMessage reqMessage = new RequestMyQaMessage(yA);//cryptogram[0], cryptogram[1], cryptogram[2]);

        enviarSmsBinario(myData.getKgbPhone(), reqMessage.getMessageBytes());


    }

    public static void finalizeFirstConfig(String xA) {

        MyPrivateData myPrivData = MyPrivateData.getInstance();

        byte[] hashDoXa = new byte[20];
        byte[] hashDoId = new byte[20];
        byte[] hashIdKgb = new byte[20];

        {

            SHA1Digest sha = new SHA1Digest();
            sha.reset();
            sha.update(xA.getBytes(), 0, xA.getBytes().length);
            sha.doFinal(hashDoXa, 0);

            sha.reset();
            sha.update(myPrivData.getIdA().getBytes(), 0, myPrivData.getIdA().getBytes().length);
            sha.doFinal(hashDoId, 0);
            
            sha.reset();
            sha.update(myPrivData.getKgbPhone().getBytes(), 0, myPrivData.getKgbPhone().getBytes().length);
            sha.doFinal(hashIdKgb, 0);
        }


        BDCPSClient bdcps = new BDCPSClient(Configuration.K, BDCPSParameters.getInstance(Configuration.K).PPubBytes, hashDoId);
        bdcps.setSecretValue(hashDoXa);
        bdcps.setPublicValue(myPrivData.getYA());
        byte[] myQa;
        try {
        myQa =  bdcps.unsigncrypt(new byte[][]{myPrivData.getEncryptedQA_c(),myPrivData.getEncryptedQA_h(),myPrivData.getEncryptedQA_z()}, hashIdKgb, BDCPSParameters.getInstance(Configuration.K).yKgbBytes);
        } catch (InvalidMessageException ex) {
            ex.printStackTrace();
            Output.println("Erro, assinatura da KGB é inválida, QA é invalido.");
            //TODO: avisar usuário
            return;
        }
        catch (CipherException ex) {
            ex.printStackTrace();
            Output.println("Erro, assinatura da KGB é inválida, QA é invalido.");
            //TODO: avisar 
            return;
        }
        
        if (!bdcps.checkPrivateKey(myQa, myPrivData.getYA(), hashDoId)) {
            Output.println("Erro, recebido QA inválido, não passou no checkPrivateKey. Ignorado.");
            //validar o QA com a fórmula, e avisar usuário caso esteja tudo errado.
            return;
        }
        
        //TUDO OK
        myPrivData.setQA(myQa);
        myPrivData.setEncryptedQA_c(null);
        myPrivData.setEncryptedQA_h(null);
        myPrivData.setEncryptedQA_z(null);
        
        bdcps.setPrivateKey(myPrivData.getQA());
        bdcps.setPublicKey();
        byte[][] pubKey = bdcps.getPublicKey();


        myPrivData.setHA(pubKey[1]);
        myPrivData.setTA(pubKey[2]);

        PersistableManager perMan = PersistableManager.getInstance();
        try {
            perMan.save(myPrivData);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }

    }

    public static void addNewContact(final String name, final String phone) {
        try {

            //Busca no banco por um contacto com este phone
            PersistableManager perMan = PersistableManager.getInstance();


            ObjectSet results = perMan.find(Contact.getThisClass(), new Filter() {

                public boolean matches(Persistable arg0) {
                    return ((Contact) arg0).getPhone().equals(phone);
                }
            }, null);

            Contact contact;
            if (results.size() > 0) {
                //O contato já estava cadastrado.. Atualiza o nome dele
                contact = (Contact) results.get(0);
                contact.setName(name);
            } else {
                contact = new Contact(name, phone);
            }

            //Envia agora a mensagem para o contato
            MyPrivateData myPrivData = MyPrivateData.getInstance();
            AuthenticationMessage msg = new AuthenticationMessage(myPrivData.getYA(), myPrivData.getHA(), myPrivData.getTA());
            enviarSmsBinario(phone, msg.getMessageBytes());

            //Salva o contato
            perMan.save(contact);

        } catch (FloggyException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Cria e ativa um novo listener
     */
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

    public static void sendSigncryptedMessage(String message, String xA) {

        MyPrivateData myPrivData = MyPrivateData.getInstance();

        byte[] hashDoXa = new byte[20];
        byte[] hashDoIdA = new byte[20];
        byte[] hashDoIdB = new byte[20];

        {

            SHA1Digest sha = new SHA1Digest();
            sha.reset();
            sha.update(xA.getBytes(), 0, xA.getBytes().length);
            sha.doFinal(hashDoXa, 0);

            sha.reset();
            sha.update(myPrivData.getIdA().getBytes(), 0, myPrivData.getIdA().getBytes().length);
            sha.doFinal(hashDoIdA, 0);

            sha.reset();
            sha.update(selectedContact.getPhone().getBytes(), 0, selectedContact.getPhone().getBytes().length);
            sha.doFinal(hashDoIdB, 0);
        }


        BDCPS bdcps = new BDCPSClient(Configuration.K, BDCPSParameters.getInstance(Configuration.K).PPubBytes, hashDoIdA);
        bdcps.setSecretValue(hashDoXa);
        bdcps.setPublicValue(myPrivData.getYA());

        //bdcps.setPrivateKey(myPrivData.getQA());
        //bdcps.setPublicKey();

        try {
            byte[][] cryptogram = bdcps.signcrypt(message.getBytes(), hashDoIdB, selectedContact.getYA());
            SigncryptedMessage msg = new SigncryptedMessage(cryptogram[0], cryptogram[1], cryptogram[2]);

            enviarSmsBinario(selectedContact.getPhone(), msg.getMessageBytes());

        } catch (CipherException ex) {
            ex.printStackTrace();
            return;
        }
    }

    public static String getUnsigncryptedText(String xA) throws CipherException {

        MyPrivateData myPrivData = MyPrivateData.getInstance();

        byte[] hashDoXa = new byte[20];
        byte[] hashDoIdA = new byte[20];
        byte[] hashDoIdB = new byte[20];

        {

            SHA1Digest sha = new SHA1Digest();
            sha.reset();
            sha.update(xA.getBytes(), 0, xA.getBytes().length);
            sha.doFinal(hashDoXa, 0);

            sha.reset();
            sha.update(myPrivData.getIdA().getBytes(), 0, myPrivData.getIdA().getBytes().length);
            sha.doFinal(hashDoIdA, 0);

            sha.reset();
            sha.update(selectedMessage.getSender().getBytes(), 0, selectedMessage.getSender().getBytes().length);
            sha.doFinal(hashDoIdB, 0);
        }


        BDCPS bdcps = new BDCPSClient(Configuration.K, BDCPSParameters.getInstance(Configuration.K).PPubBytes, hashDoIdA);
        bdcps.setSecretValue(hashDoXa);
        bdcps.setPublicValue(myPrivData.getYA());

        //bdcps.setPrivateKey(myPrivData.getQA());
        //bdcps.setPublicKey();


        byte[][] cryptogram = new byte[3][];
        cryptogram[0] = ((SigncryptedMessage) selectedMessage).getC();
        cryptogram[1] = ((SigncryptedMessage) selectedMessage).getH();
        cryptogram[2] = ((SigncryptedMessage) selectedMessage).getZ();

        PersistableManager perMan = PersistableManager.getInstance();
        ObjectSet result;
        try {

            result =
                    perMan.find(Contact.getThisClass(), new Filter() {

                public boolean matches(Persistable arg0) {
                    return ((Contact) arg0).getPhone().equals(selectedMessage.getSender());
                }
            }, null);

        } catch (FloggyException ex) {
            ex.printStackTrace();
            return null;
        }

        Contact contact;
        try {
            contact = (Contact) result.get(0);
        } catch (FloggyException ex) {
            ex.printStackTrace();
            return null;
        }


        try {
            byte[] unsigncryptedMsg = bdcps.unsigncrypt(cryptogram, hashDoIdB, contact.getYA());
            return new String(unsigncryptedMsg);

        } catch (InvalidMessageException ex) {
            ex.printStackTrace();
            return "Erro, identidade do remetente não pode ser confirmada";
        } catch (CipherException ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    public static void enviarSmsBinarioMesmaThread(String phone, byte[] data) {
        enviarSmsBinario(phone, data, Configuration.SMS_PORT);
    }

    public static void enviarSmsBinario(final String phone, final byte[] data) {
        new Thread() {

            public void run() {
                enviarSmsBinario(phone, data, Configuration.SMS_PORT);
            }
        }.start();

    }

    private static void enviarSmsBinario(String phone, byte[] data, int port) {

        try {
            String addr = "sms://" + phone + ":" + port;
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

    public static void setSelectedContact(Contact contact) {
        Controller.selectedContact = contact;
    }

    public static Contact getSelectedContact() {
        return Controller.selectedContact;
    }

    public static void setSelectedMessage(SigncryptedMessage selectedMessage) {
        Controller.selectedMessage = selectedMessage;
    }

    public static SigncryptedMessage getSelectedMessage() {
        return Controller.selectedMessage;
    }

    public static void testinho() {
        
        Output.println("#Begin benchmarks");
        
        Output.println("==Mockup==");
        IntegrationTests.mockUpBenchmark();
        
        Output.println("==Implementation==");
        IntegrationTests.implBenchmark();
        
        Output.println("#End benchmarks");


        



    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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


        Digest sha = null;


        sha = new SHA1Digest();


        int bits = 176;



        BDCPSParameters bdcpsPar = BDCPSParameters.getInstance(bits);

        //String masterKey = "honnisoitquimalypense";
        String aliceKey = "hi i am alice";
        String bobKey = "hi i am bob marley";

        String id_a = "551199991234";
        String id_b = "551188884321";
        String id_auth = "thegodfather";

        byte[] s;// = new byte[20];
        byte[] xa_alice = new byte[20];
        byte[] xb_bob = new byte[20];

        try {
            // sha.update(masterKey.getBytes(), 0, masterKey.getBytes().length);
            //sha.doFinal(s, 0);

            BigInteger _s500 = new BigInteger("2811324208781249769788073818190026244636491661539281873387241581414870671338535758736366135621660583188369946048623028749823899797612403393315005280995");

            s = _s500.mod(BDCPSParameters.getInstance(Configuration.K).N).toByteArray();



            sha.reset();

            sha.update(aliceKey.getBytes(), 0, aliceKey.getBytes().length);
            sha.doFinal(xa_alice, 0);

            sha.reset();

            sha.update(bobKey.getBytes(), 0, bobKey.getBytes().length);
            sha.doFinal(xb_bob, 0);
        } catch (/*Digest*/Exception ex) {
            System.out.println("BDCPS: Digest exception.");
            ex.printStackTrace();
            throw new RuntimeException("BDCPS: Digest exception.");
        }
        BDCPS auth = new BDCPSAuthority(bits, s, id_auth.getBytes());
        BDCPS alice = new BDCPSClient(bits, bdcpsPar.PPubBytes /*auth.getPublicPoint()*/, id_a.getBytes());
        BDCPS bob = new BDCPSClient(bits, bdcpsPar.PPubBytes /*auth.getPublicPoint()*/, id_b.getBytes());



        alice.setSecretValue(xa_alice);
        bob.setSecretValue(xb_bob);
        alice.setPublicValue();
        bob.setPublicValue();


        byte[] Q_A = auth.privateKeyExtract(id_a.getBytes(), alice.getPublicValue());
        byte[] Q_B = auth.privateKeyExtract(id_b.getBytes(), bob.getPublicValue());

        alice.setPrivateKey(Q_A);
        bob.setPrivateKey(Q_B);
        System.out.println("\nSetPublicKey-Alice: ");
        alice.setPublicKey();
        System.out.println("SetPublicKey-Bob: ");
        bob.setPublicKey();

        System.out.println("\nValidate-Alice: ");
        boolean isAliceValid = bob.publicKeyValidate(alice.getPublicKey(), id_a.getBytes());
        System.out.println("Validate-Bob: ");
        boolean isBobValid = alice.publicKeyValidate(bob.getPublicKey(), id_b.getBytes());

        if (isBobValid) {
            System.out.println("Bob is valid!");
        } else {
            System.out.println("Bob is false!");
        }
        if (isAliceValid) {
            System.out.println("Alice is valid!");
        } else {
            System.out.println("Alice is false!");
        }


        System.out.println("\nBegin sign- and unsigncryption:");

        //String plaintext = "Como possíveis aplicações de nossa solução, podemos citar a realização de transações bancárias usando mensagens SMS, sistemas de comunicação que requeiram confidencialidade e integridade (órgãos militares e governamentais, executivos de grandes empresas) ou apenas usuários comuns em busca de maiores níveis de privacidade.Este artigo compreende a descrição do cenário e desafios encontrados no levantamento dos requisitos de um sistema de troca de mensagens SMS seguro e na escolha e aplicação de um esquema de criptografia que garantisse esses requisitos. Desse modo, são discutidos os conceitos dos algoritmos BLMQ (baseado em identidades) e BDCPS (CL-PKC), criado devido ao insucesso do primeiro em atender aos requisitos do projeto.Os algoritmos citados anteriormente utilizam os conceitos de cifrassinatura e vericifração de mensagens. A cifrassinatura consiste em um método de criptografia de chave pública que garante infalsificabilidade e confidencialidade simultaneamente com um overhead menor do que o requerido pela assinatura digital seguida de encriptação de chave pública. Isto é alcançado assinando e encriptando uma mensagem em um único passo. A vericifração consiste da operação inversa, ou seja, a verificação da validade do autor da mensagem e sua decriptação de chave pública, simultaneamente.";
        String plaintext = "Hora da Pizza!";

        Output.println("Testo Claro: " + plaintext + " \n");
        byte[] m = plaintext.getBytes();
        byte[][] c = null;
        try {
            c = alice.signcrypt(m, id_b.getBytes(), bob.getPublicValue());
            Output.println("Criptograma[0]: " + Util.byteArrayToDebugableString(c[0]) + "\n");
            Output.println("Criptograma[1]: " + Util.byteArrayToDebugableString(c[1]) + "\n");
            Output.println("Criptograma[2]: " + Util.byteArrayToDebugableString(c[2]) + "\n");
        } catch (CipherException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        byte[] m_dec = null;
        try {
            m_dec = bob.unsigncrypt(c, id_a.getBytes(), alice.getPublicValue());
            Output.println("Mensagem Vericifrada: " + Util.byteArrayToDebugableString(m_dec));
        } catch (InvalidMessageException e) {
            Output.println("BDCPS: Invalid Exception");
            System.out.println("Unsigncrypt: Invalid message!");
            e.printStackTrace();
        } catch (CipherException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (m_dec != null) {
            Output.println("Texto Vericifrado: " + new String(m_dec));
        }



    }
}

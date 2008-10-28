/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.messaging.SmsListener;
import br.usp.pcs.coop8.ssms.protocol.BDCPS;
import br.usp.pcs.coop8.ssms.protocol.BDCPSAuthority;
import br.usp.pcs.coop8.ssms.protocol.BDCPSClient;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import br.usp.pcs.coop8.ssms.protocol.exception.InvalidMessageException;
import br.usp.pcs.coop8.ssms.util.Output;
import br.usp.pcs.coop8.ssms.util.Util;
import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;

/**
 * Executa funcionalidades a partir do menu
 * @author Administrador
 */
public abstract class Controller {

    private static SmsListener smsListener = null;
    private static int port = 2102;

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

    public static void enviarSmsBinario(String phone, byte[] data) {
        enviarSmsBinario(phone, data, port);
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

    public static void testinho() {
        Digest sha = null;


        sha = new SHA1Digest();


        int bits = 176;

        String masterKey = "honnisoitquimalypense";
        String aliceKey = "hi i am alice";
        String bobKey = "hi i am bob marley";

        String id_a = "551199991234";
        String id_b = "551188884321";
        String id_auth = "thegodfather";

        byte[] s = new byte[20];
        byte[] xa_alice = new byte[20];
        byte[] xb_bob = new byte[20];

        try {
            sha.update(masterKey.getBytes(), 0, masterKey.getBytes().length);
            sha.doFinal(s, 0);

            sha.reset();

            sha.update(aliceKey.getBytes(), 0, aliceKey.getBytes().length);
            sha.doFinal(xa_alice, 0);

            sha.reset();

            sha.update(bobKey.getBytes(), 0, bobKey.getBytes().length);
            sha.doFinal(xb_bob, 0);
        } catch (/*Digest*/Exception ex) {
            System.out.println("BDCPS: Digest exception.");
            ex.printStackTrace();
            //TODO: arrumar
            throw new RuntimeException("BDCPS: Digest exception.");
        }
        BDCPS auth = new BDCPSAuthority(bits, s, id_auth.getBytes());
        BDCPS alice = new BDCPSClient(bits, auth.getPublicPoint(), id_a.getBytes());
        BDCPS bob = new BDCPSClient(bits, auth.getPublicPoint(), id_b.getBytes());



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

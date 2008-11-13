/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.data.MyPrivateData;
import br.usp.pcs.coop8.ssms.messaging.HereIsYourQaMessage;
import br.usp.pcs.coop8.ssms.messaging.MessageSsms;
import br.usp.pcs.coop8.ssms.messaging.RequestMyQaMessage;
import br.usp.pcs.coop8.ssms.protocol.BDCPSAuthority;
import br.usp.pcs.coop8.ssms.protocol.BDCPSParameters;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import br.usp.pcs.coop8.ssms.util.Output;
import org.bouncycastle.crypto.digests.SHA1Digest;
import pseudojava.BigInteger;

/**
 * Classe que representa o KGB. Não deve estar presente no aplicativo mobile cliente.
 * Está aqui apenas para fins de desenvolvimento/testes/demonstração.
 * 
 * @author Administrador
 */
public class KgbSsms {

    /**
     * Um número de 500 bits ultra-secreto.
     * O S efetivo será _s500.mod(N), reduzido para k bits
     */
    private static BigInteger _s500 = new BigInteger("2811324208781249769788073818190026244636491661539281873387241581414870671338535758736366135621660583188369946048623028749823899797612403393315005280995");

    /**
     * Retorna o valor de S efetivo para o campo sendo usado, ou seja, _s500.mod(N)
     */
    private static byte[] getS() {
        return _s500.mod(BDCPSParameters.getInstance(Configuration.K).N).toByteArray();
    }

    public static void returnQaMessage(RequestMyQaMessage rmqam, String id) {


        byte[] hashIdA = new byte[20];
        byte[] hashKgbId = new byte[20];

        SHA1Digest sha = new SHA1Digest();
        sha.reset();
        sha.update(id.getBytes(), 0, id.getBytes().length);
        sha.doFinal(hashIdA, 0);

        sha.reset();
        sha.update(MyPrivateData.getInstance().getIdA().getBytes(), 0, MyPrivateData.getInstance().getIdA().getBytes().length);
        sha.doFinal(hashKgbId, 0);

        byte[] yA = rmqam.getYA();

        //byte[][] cryptogram = new byte[3][];
        //cryptogram[0] = rmqam.getYA(); //yA encriptado
        //cryptogram[1] = rmqam.getH();
        //cryptogram[2] = rmqam.getZ();
        BDCPSAuthority bdcpsA = new BDCPSAuthority(Configuration.K, getS(), hashKgbId);



        byte[] qA = bdcpsA.privateKeyExtract(hashIdA, yA);

        bdcpsA.setPublicValue();
        byte[][] cryptogram;
        try {
            cryptogram = bdcpsA.signcrypt(qA, hashIdA, yA);
        } catch (CipherException ex) {
            ex.printStackTrace();
            Output.println("Exception excriptando qA");
            return;
        }


        MessageSsms msg = new HereIsYourQaMessage(cryptogram[0], cryptogram[1], cryptogram[2]);

        Controller.enviarSmsBinarioMesmaThread(id, msg.getMessageBytes());


    }
}

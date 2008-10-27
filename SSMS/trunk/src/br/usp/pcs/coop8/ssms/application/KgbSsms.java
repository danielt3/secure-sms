/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.pcs.coop8.ssms.application;

import br.usp.pcs.coop8.ssms.data.GlobalParameters;
import br.usp.pcs.coop8.ssms.messaging.HereIsYourQaMessage;
import br.usp.pcs.coop8.ssms.messaging.MessageSsms;
import br.usp.pcs.coop8.ssms.messaging.RequestMyQaMessage;
import br.usp.pcs.coop8.ssms.protocol.BDCPSAuthority;

/**
 *
 * @author Administrador
 */
public class KgbSsms {

    /**
     * Este será o S pra sempre. Ele tem 22 bytes.
     */
    private static final byte[] s = new byte[]{
        (byte) 0x20,
        (byte) 0x4a,
        (byte) 0x31,
        (byte) 0x7a,
        (byte) 0x56,
        (byte) 0x75,
        (byte) 0x17,
        (byte) 0x6d,
        (byte) 0xf5,
        (byte) 0x5a,
        (byte) 0x4b,
        (byte) 0x05,
        (byte) 0xbd,
        (byte) 0x94,
        (byte) 0x13,
        (byte) 0xd1,
        (byte) 0xe2,
        (byte) 0x9a,
        (byte) 0x0f,
        (byte) 0x5d,
        (byte) 0xab,
        (byte) 0xac
    };

    public static void returnQaMessage(RequestMyQaMessage rmqam, byte[] id) {
        byte[] yA = rmqam.getYA();

        BDCPSAuthority bdcpsA = new BDCPSAuthority(GlobalParameters.K, s, "1175758877".getBytes());

        byte[] qA = bdcpsA.privateKeyExtract(id, yA);

        //TODO: encriptar o qA com alg convencional


        MessageSsms msg = new HereIsYourQaMessage(qA);

        Controller.enviarSmsBinario(new String(id), msg.getMessageBytes());

    }
}

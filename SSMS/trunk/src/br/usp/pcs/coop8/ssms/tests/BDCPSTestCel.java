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

package br.usp.pcs.coop8.ssms.tests;

import br.usp.larc.pbarreto.jaes.AES;
import br.usp.larc.pbarreto.jaes.CMAC;
import br.usp.pcs.coop8.ssms.application.Configuration;
import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import br.usp.pcs.coop8.ssms.protocol.exception.InvalidMessageException;
import br.usp.pcs.coop8.ssms.protocol.*;

/**
 *
 * @author rodrigo
 */
public class BDCPSTestCel {

    public static void test() {
        CMAC cmac = null;

        try {
            cmac = new CMAC(Configuration.getAes());
        } catch (/*NoSuchAlgorithm*/Exception e) {
            System.out.println("BDCPS: Hash Algorithm not found.");
            e.printStackTrace();
        }


        int bits = 176;

        String masterKey = "honnisoitquimalypense";
        String aliceKey = "hi i am alice";
        String bobKey = "hi i am bob marley";

        String id_a = "551199991234";
        String id_b = "551188884321";
        String id_auth = "thegodfather";

        byte[] s = new byte[16];
        byte[] xa_alice = new byte[16];
        byte[] xb_bob = new byte[16];

        cmac.init();        
        cmac.update(masterKey.getBytes());
        cmac.getTag(s);

        cmac.init();
        cmac.update(aliceKey.getBytes());
        cmac.getTag(xa_alice);

        cmac.init();
        cmac.update(bobKey.getBytes());
        cmac.getTag(xb_bob);

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

        String plaintext = "Como possíveis aplicações de nossa solução, podemos citar a realização de transações bancárias usando mensagens SMS, sistemas de comunicação que requeiram confidencialidade e integridade (órgãos militares e governamentais, executivos de grandes empresas) ou apenas usuários comuns em busca de maiores níveis de privacidade.Este artigo compreende a descrição do cenário e desafios encontrados no levantamento dos requisitos de um sistema de troca de mensagens SMS seguro e na escolha e aplicação de um esquema de criptografia que garantisse esses requisitos. Desse modo, são discutidos os conceitos dos algoritmos BLMQ (baseado em identidades) e BDCPS (CL-PKC), criado devido ao insucesso do primeiro em atender aos requisitos do projeto.Os algoritmos citados anteriormente utilizam os conceitos de cifrassinatura e vericifração de mensagens. A cifrassinatura consiste em um método de criptografia de chave pública que garante infalsificabilidade e confidencialidade simultaneamente com um overhead menor do que o requerido pela assinatura digital seguida de encriptação de chave pública. Isto é alcançado assinando e encriptando uma mensagem em um único passo. A vericifração consiste da operação inversa, ou seja, a verificação da validade do autor da mensagem e sua decriptação de chave pública, simultaneamente.";
        byte[] m = plaintext.getBytes();
        byte[][] c = null;
        try {
            c = alice.signcrypt(m, id_b.getBytes(), bob.getPublicValue());
        } catch (CipherException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        byte[] m_dec = null;
        try {
            m_dec = bob.unsigncrypt(c, id_a.getBytes(), alice.getPublicValue());
        } catch (InvalidMessageException e) {
            System.out.println("Unsigncrypt: Invalid message!");
            e.printStackTrace();
        } catch (CipherException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (m_dec != null) {
            System.out.println("Decrypted message: " + new String(m_dec));
        }

    }
}

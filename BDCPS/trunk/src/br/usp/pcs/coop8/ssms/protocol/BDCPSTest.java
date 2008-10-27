/**
 * 
 */
package br.usp.pcs.coop8.ssms.protocol;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.usp.pcs.coop8.ssms.protocol.exception.CipherException;
import br.usp.pcs.coop8.ssms.protocol.exception.InvalidMessageException;

/**
 * @author rodrigo
 *
 */
public class BDCPSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MessageDigest sha = null;
		
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
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
		
		byte[] s = sha.digest(masterKey.getBytes());
		sha.reset();
		byte[] xa_alice = sha.digest(aliceKey.getBytes());
		sha.reset();
		byte[] xb_bob = sha.digest(bobKey.getBytes());
		
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
		
		if (isBobValid) System.out.println("Bob is valid!");
		else System.out.println("Bob is false!");
		if (isAliceValid) System.out.println("Alice is valid!");
		else System.out.println("Alice is false!");
		
		
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
		if(m_dec!=null)
		System.out.println("Decrypted message: "+ new String(m_dec));
		
		
		
		

	}

}

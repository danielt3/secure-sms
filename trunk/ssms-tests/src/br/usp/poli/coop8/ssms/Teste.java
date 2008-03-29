package br.usp.poli.coop8.ssms;


import sms_package.BigInteger;
import java.util.Random;

public class Teste {
	
	public static int getUnsignedIntFromByte(byte b) {
		if (b >= 0 && b <= 127) {
			return (int)b;		
		}
		else {
			return 256 +(int)b;
		}
	}
	
	public static String bigIntegerToString(BigInteger bigInt) {
		
		byte[] byteArray = bigInt.toByteArray();
		String retorno = "";
		for (int i = 0; i < byteArray.length; i++) {
			String strByteAtual = Integer.toString(getUnsignedIntFromByte(byteArray[i]), 16).toUpperCase();
			if (strByteAtual.length() < 2) 
				strByteAtual = "0" + strByteAtual;
			retorno =  retorno + strByteAtual;
		}
		return retorno;
	}

	public static void testaBenchMark() {
		Random rnd = new Random();
		BigInteger p;
		BigInteger a;		
		int i;
		long startTime, finalTime, elapsed;
		
		int numBits = 160;
		
		a = BigInteger.valueOf(2L);
		p = new BigInteger(numBits , rnd);
		
		System.out.println("Gerou o rand�mico " + p);
		
		startTime = System.currentTimeMillis();
		while (!p.isProbablePrime(5)) {
			p = p.add(BigInteger.ONE);
		}		
		finalTime = System.currentTimeMillis();		
		
		elapsed = finalTime - startTime;		
		System.out.println("Encontrou o primo " + p + " em " + elapsed +" milisegundos.");
		
		startTime = System.currentTimeMillis();		
		for(i = 0; i < 10000; i++) {
			a = a.modPow(a, p);
			//a^a mod p		
		}		
		finalTime = System.currentTimeMillis();
		
		elapsed = finalTime - startTime;		
		System.out.println("Decorridos " + elapsed + " milisigundos para executar o loop " + i + " vezes.");
	}
	
	public static void verificaComoEhOArmazenamentoInterno() {
		
		BigInteger bigInt;
		
		System.out.println("Armazenamento de numeros positivos:\r\n");
		for (int i = 0; i <= 1024; i++) {
			bigInt = new BigInteger(Integer.toString(i));
			System.out.println("numero " + i + ": \t" + Teste.bigIntegerToString(bigInt));
		}
		
		System.out.println("\r\nArmazenamento de numeros negativos:\r\n");		
		for (int i = -1; i >= -1024; i--) {
			bigInt = new BigInteger(Integer.toString(i));
			System.out.println("numero " + i + ": \t" + Teste.bigIntegerToString(bigInt));
		}		
	}

	public static void testarSoma() {
		// Testar soma de BigInteger
		BigInteger bigInt1, bigInt2, bigInt3;

		bigInt1 = new BigInteger(
				"777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972");
		bigInt2 = new BigInteger(
				"7771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912972777171571579720975197219721927912791279129727771715715797209751972197219279127912791297277717157157972097519721972192791279127912973");
		bigInt3 = bigInt1.multiply(bigInt2);

		System.out.println(bigInt3);		
	}
	
	public static void main(String[] args) {
		//Teste.testarSoma();
	
		Teste.testaBenchMark();
	}
	
}

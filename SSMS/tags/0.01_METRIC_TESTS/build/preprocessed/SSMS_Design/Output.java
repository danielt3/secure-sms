package SSMS_Design;

/**
 * Classe para simular um console no celular.
 */
public class Output {
	
	private static String output = "";


	
	public static void println(String print) {
		System.out.println(print);
		output = output + print + "\r\n";
	}	
	public static void print(String print) {
		System.out.print(print);
		output = output + print;
	}	
	public static String getOutput() {
		return output;
	}
}
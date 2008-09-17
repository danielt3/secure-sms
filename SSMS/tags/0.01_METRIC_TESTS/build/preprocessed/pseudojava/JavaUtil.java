package pseudojava;

public class JavaUtil {

	public static int[] clone(int [] x) {
		if (x == null) return null;
		
		int[] clone = new int[x.length];
		for (int i = 0; i< x.length; i++) {
			clone[i] = x[i];
		}
		
		return clone;
	}
}

package lab.moea.util;

public class StringUtil {
	
	/**
	 * Private constructor to prevent instantiation.
	 */	
	private StringUtil() {
		super();
	}
	
	private static String SEP = " ";
	
	public static void PrintStrings(String... strings) {
		for (String string : strings) {
			System.out.print(string + SEP);
		}
		System.out.println();
	}	
}

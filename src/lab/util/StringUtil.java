package lab.util;

public class StringUtil {
	private static String SEP = " ";
	
	public static void PrintStrings(String... strings) {
		for (String string : strings) {
			System.out.print(string + SEP);
		}
		System.out.println();
	}	
}

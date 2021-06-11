package sdpsd;

public class Util {
	public static String makePfName(String problem, int nobj) {
		problem = problem.toUpperCase();

		if (problem.startsWith("DTLZ")) {
			return "pf/"+ problem + "." + nobj + "D.pf";
		} else if (problem.startsWith("WFG")){
			return "pf/"+ problem + "." + nobj + "D.pf";
		} else {
			return null;
		}
	}
	
	
	public static void print(String algorithm, String problem, int trial, int digit, long seed) {
		System.out.println(algorithm + " - " + problem + " [ " + trial + " ] " + digit + " digit, seed = " + seed);
	}
	
	public static String makeFileName(String header, String algorithm, String problem, int trial, String extention) {
		if (header == "") {
			return algorithm+"_"+problem+"_"+trial+"." + extention;
		} else {
			return header+"_"+algorithm+"_"+problem+"_"+trial+"." + extention;
		}
	}
}

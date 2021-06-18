package lab.moea.problem;

import java.util.Properties;

import org.moeaframework.core.Problem;
import org.moeaframework.problem.CEC2009.CF1;
import org.moeaframework.problem.CEC2009.CF10;
import org.moeaframework.problem.CEC2009.CF2;
import org.moeaframework.problem.CEC2009.CF3;
import org.moeaframework.problem.CEC2009.CF4;
import org.moeaframework.problem.CEC2009.CF5;
import org.moeaframework.problem.CEC2009.CF6;
import org.moeaframework.problem.CEC2009.CF7;
import org.moeaframework.problem.CEC2009.CF8;
import org.moeaframework.problem.CEC2009.CF9;
import org.moeaframework.problem.CEC2009.UF1;
import org.moeaframework.problem.CEC2009.UF10;
import org.moeaframework.problem.CEC2009.UF11;
import org.moeaframework.problem.CEC2009.UF12;
import org.moeaframework.problem.CEC2009.UF13;
import org.moeaframework.problem.CEC2009.UF2;
import org.moeaframework.problem.CEC2009.UF3;
import org.moeaframework.problem.CEC2009.UF4;
import org.moeaframework.problem.CEC2009.UF5;
import org.moeaframework.problem.CEC2009.UF6;
import org.moeaframework.problem.CEC2009.UF7;
import org.moeaframework.problem.CEC2009.UF8;
import org.moeaframework.problem.CEC2009.UF9;
import org.moeaframework.problem.DTLZ.DTLZ1;
import org.moeaframework.problem.DTLZ.DTLZ2;
import org.moeaframework.problem.DTLZ.DTLZ3;
import org.moeaframework.problem.DTLZ.DTLZ4;
import org.moeaframework.problem.DTLZ.DTLZ7;
import org.moeaframework.problem.LZ.LZ1;
import org.moeaframework.problem.LZ.LZ2;
import org.moeaframework.problem.LZ.LZ3;
import org.moeaframework.problem.LZ.LZ4;
import org.moeaframework.problem.LZ.LZ5;
import org.moeaframework.problem.LZ.LZ6;
import org.moeaframework.problem.LZ.LZ7;
import org.moeaframework.problem.LZ.LZ8;
import org.moeaframework.problem.LZ.LZ9;
import org.moeaframework.problem.WFG.WFG1;
import org.moeaframework.problem.WFG.WFG2;
import org.moeaframework.problem.WFG.WFG3;
import org.moeaframework.problem.WFG.WFG4;
import org.moeaframework.problem.WFG.WFG5;
import org.moeaframework.problem.WFG.WFG6;
import org.moeaframework.problem.WFG.WFG7;
import org.moeaframework.problem.WFG.WFG8;
import org.moeaframework.problem.WFG.WFG9;
import org.moeaframework.problem.ZDT.ZDT1;
import org.moeaframework.problem.ZDT.ZDT2;
import org.moeaframework.problem.ZDT.ZDT3;
import org.moeaframework.problem.ZDT.ZDT4;
import org.moeaframework.problem.ZDT.ZDT5;
import org.moeaframework.problem.ZDT.ZDT6;
import org.moeaframework.problem.misc.Belegundu;
import org.moeaframework.problem.misc.Binh;
import org.moeaframework.problem.misc.Binh2;
import org.moeaframework.problem.misc.Binh3;
import org.moeaframework.problem.misc.Binh4;
import org.moeaframework.problem.misc.Fonseca;
import org.moeaframework.problem.misc.Fonseca2;
import org.moeaframework.problem.misc.Jimenez;
import org.moeaframework.problem.misc.Kita;
import org.moeaframework.problem.misc.Kursawe;
import org.moeaframework.problem.misc.Laumanns;
import org.moeaframework.problem.misc.Lis;
import org.moeaframework.problem.misc.Murata;
import org.moeaframework.problem.misc.OKA1;
import org.moeaframework.problem.misc.OKA2;
import org.moeaframework.problem.misc.Obayashi;
import org.moeaframework.problem.misc.Osyczka;
import org.moeaframework.problem.misc.Osyczka2;
import org.moeaframework.problem.misc.Poloni;
import org.moeaframework.problem.misc.Quagliarella;
import org.moeaframework.problem.misc.Rendon;
import org.moeaframework.problem.misc.Rendon2;
import org.moeaframework.problem.misc.Schaffer;
import org.moeaframework.problem.misc.Schaffer2;
import org.moeaframework.problem.misc.Srinivas;
import org.moeaframework.problem.misc.Tamaki;
import org.moeaframework.problem.misc.Tanaka;
import org.moeaframework.problem.misc.Viennet;
import org.moeaframework.problem.misc.Viennet2;
import org.moeaframework.problem.misc.Viennet3;
import org.moeaframework.problem.misc.Viennet4;
import org.moeaframework.util.TypedProperties;

public class CustomProblemFactory {
	public static Problem getProblem(String name, int nobj, int nvar, int ncon, Properties properties) {
		name = name.toUpperCase();
		TypedProperties typedProperties = new TypedProperties(properties);		
		
		// for WFG. k + l = nvar
		int l = typedProperties.getInt("l", 10);
		int k = nvar - l;
		
		try {
			if (name.startsWith("DTLZ1")) {
				return new DTLZ1(nvar, nobj);
			} else if (name.startsWith("DTLZ2")) {
				return new DTLZ2(nvar, nobj);
			} else if (name.startsWith("DTLZ3")) {
				return new DTLZ3(nvar, nobj);
			} else if (name.startsWith("DTLZ4")) {
				return new DTLZ4(nvar, nobj);
			} else if (name.startsWith("DTLZ7")) {
				return new DTLZ7(nvar, nobj);
			} else if (name.startsWith("WFG1")) {
				return new WFG1(k, l, nobj);
			} else if (name.startsWith("WFG2")) {
				return new WFG2(k, l, nobj);
			} else if (name.startsWith("WFG3")) {
				return new WFG3(k, l, nobj);
			} else if (name.startsWith("WFG4")) {
				return new WFG4(k, l, nobj);
			} else if (name.startsWith("WFG5")) {
				return new WFG5(k, l, nobj);
			} else if (name.startsWith("WFG6")) {
				return new WFG6(k, l, nobj);
			} else if (name.startsWith("WFG7")) {
				return new WFG7(k, l, nobj);
			} else if (name.startsWith("WFG8")) {
				return new WFG8(k, l, nobj);
			} else if (name.startsWith("WFG9")) {
				return new WFG9(k, l, nobj);
			} else if (name.equals("ZDT1")) {
				return new ZDT1(nvar);
			} else if (name.equals("ZDT2")) {
				return new ZDT2(nvar);
			} else if (name.equals("ZDT3")) {
				return new ZDT3(nvar);
			} else if (name.equals("ZDT4")) {
				return new ZDT4(nvar);
			} else if (name.equals("ZDT5")) {
				return new ZDT5(nvar);
			} else if (name.equals("ZDT6")) {
				return new ZDT6(nvar);
			} else if (name.equals("UF1")) {
				return new UF1(nvar);
			} else if (name.equals("UF2")) {
				return new UF2(nvar);
			} else if (name.equals("UF3")) {
				return new UF3(nvar);
			} else if (name.equals("UF4")) {
				return new UF4(nvar);
			} else if (name.equals("UF5")) {
				return new UF5(nvar);
			} else if (name.equals("UF6")) {
				return new UF6(nvar);
			} else if (name.equals("UF7")) {
				return new UF7(nvar);
			} else if (name.equals("UF8")) {
				return new UF8(nvar);
			} else if (name.equals("UF9")) {
				return new UF9(nvar);
			} else if (name.equals("UF10")) {
				return new UF10(nvar);
			} else if (name.equals("UF11")) {
				return new UF11(nvar, nobj);
			} else if (name.equals("UF12")) {
				return new UF12(nvar, nobj); // DTLZ3, nvar=10 or 30
			} else if (name.equals("UF13")) {
				return new UF13(); // WFG1, nvar=30, nobj=5
			} else if (name.equals("CF1")) {
				return new CF1();
			} else if (name.equals("CF2")) {
				return new CF2();
			} else if (name.equals("CF3")) {
				return new CF3();
			} else if (name.equals("CF4")) {
				return new CF4();
			} else if (name.equals("CF5")) {
				return new CF5();
			} else if (name.equals("CF6")) {
				return new CF6();
			} else if (name.equals("CF7")) {
				return new CF7();
			} else if (name.equals("CF8")) {
				return new CF8();
			} else if (name.equals("CF9")) {
				return new CF9();
			} else if (name.equals("CF10")) {
				return new CF10();
			} else if (name.equals("LZ1")) {
				return new LZ1();
			} else if (name.equals("LZ2")) {
				return new LZ2();
			} else if (name.equals("LZ3")) {
				return new LZ3();
			} else if (name.equals("LZ4")) {
				return new LZ4();
			} else if (name.equals("LZ5")) {
				return new LZ5();
			} else if (name.equals("LZ6")) {
				return new LZ6();
			} else if (name.equals("LZ7")) {
				return new LZ7();
			} else if (name.equals("LZ8")) {
				return new LZ8();
			} else if (name.equals("LZ9")) {
				return new LZ9();
			} else if (name.equalsIgnoreCase("Belegundu")) {
				return new Belegundu();
			} else if (name.equalsIgnoreCase("Binh")) {
				return new Binh();
			} else if (name.equalsIgnoreCase("Binh2")) {
				return new Binh2();
			} else if (name.equalsIgnoreCase("Binh3")) {
				return new Binh3();
			} else if (name.equalsIgnoreCase("Binh4")) {
				return new Binh4();
			} else if (name.equalsIgnoreCase("Fonseca")) {
				return new Fonseca();
			} else if (name.equalsIgnoreCase("Fonseca2")) {
				return new Fonseca2();
			} else if (name.equalsIgnoreCase("Jimenez")) {
				return new Jimenez();
			} else if (name.equalsIgnoreCase("Kita")) {
				return new Kita();
			} else if (name.equalsIgnoreCase("Kursawe")) {
				return new Kursawe();
			} else if (name.equalsIgnoreCase("Laumanns")) {
				return new Laumanns();
			} else if (name.equalsIgnoreCase("Lis")) {
				return new Lis();
			} else if (name.equalsIgnoreCase("Murata")) {
				return new Murata();
			} else if (name.equalsIgnoreCase("Obayashi")) {
				return new Obayashi();
			} else if (name.equalsIgnoreCase("OKA1")) {
				return new OKA1();
			} else if (name.equalsIgnoreCase("OKA2")) {
				return new OKA2();
			} else if (name.equalsIgnoreCase("Osyczka")) {
				return new Osyczka();
			} else if (name.equalsIgnoreCase("Osyczka2")) {
				return new Osyczka2();
			} else if (name.equalsIgnoreCase("Poloni")) {
				return new Poloni();
			} else if (name.equalsIgnoreCase("Quagliarella")) {
				return new Quagliarella();
			} else if (name.equalsIgnoreCase("Rendon")) {
				return new Rendon();
			} else if (name.equalsIgnoreCase("Rendon2")) {
				return new Rendon2();
			} else if (name.equalsIgnoreCase("Schaffer")) {
				return new Schaffer();
			} else if (name.equalsIgnoreCase("Schaffer2")) {
				return new Schaffer2();
			} else if (name.equalsIgnoreCase("Srinivas")) {
				return new Srinivas();
			} else if (name.equalsIgnoreCase("Tamaki")) {
				return new Tamaki();
			} else if (name.equalsIgnoreCase("Tanaka")) {
				return new Tanaka();
			} else if (name.equalsIgnoreCase("Viennet")) {
				return new Viennet();
			} else if (name.equalsIgnoreCase("Viennet2")) {
				return new Viennet2();
			} else if (name.equalsIgnoreCase("Viennet3")) {
				return new Viennet3();
			} else if (name.equalsIgnoreCase("Viennet4")) {
				return new Viennet4();
			} else {
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}
}

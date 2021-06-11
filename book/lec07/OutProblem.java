package lec07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class OutProblem extends AbstractProblem {
	private final Runtime runtime = Runtime.getRuntime(); // get runtime object
	
	public OutProblem() {
		super(1,2); // 1 variable, 2 objectives
	}
	
	public void init() {
		System.out.println("Init Out Problem");
	}
	
	@Override
	public void evaluate(Solution solution) {
		double x = EncodingUtils.getReal(solution.getVariable(0));
		double f1 = 0.0;
		double f2 = 0.0;
		
		String[] cmd = {"python3.8","./practice/lec07/test.py", String.valueOf(x)};
		try {
			Process p = runtime.exec(cmd);
			p.waitFor();
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			f1 = Double.valueOf(b.readLine());
			f2 = Double.valueOf(b.readLine());
			
//			String line = "";
//			while ((line = b.readLine()) != null) {
//				System.out.println(line);
//			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} 

		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
		
//		solution.setObjective(0, Math.pow(x, 2.0));
//		solution.setObjective(1, Math.pow(x - 2.0, 2.0));
	}
	
	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 2); // 1 variable, 2 objectives
		solution.setVariable(0,  EncodingUtils.newReal(-10.0, 10.0));
		return solution;
	}
}

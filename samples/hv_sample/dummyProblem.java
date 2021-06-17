package hv_sample;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class dummyProblem extends AbstractProblem {
	public dummyProblem() {
		super(1,2); // 1 variable, 2 objectives
	}
	
	@Override
	public void evaluate(Solution solution) {
//		double x = EncodingUtils.getReal(solution.getVariable(0));
// 
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

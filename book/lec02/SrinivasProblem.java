package lec02;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class SrinivasProblem extends AbstractProblem {
	public SrinivasProblem() {
		super(2, 2, 2); // 2 variables, 2 objectives, 2 constraints
	}
	
	@Override
	public void evaluate(Solution solution) {
		double x = EncodingUtils.getReal(solution.getVariable(0));
		double y = EncodingUtils.getReal(solution.getVariable(1));
		
		double f1 = Math.pow(x-2.0, 2.0) + Math.pow(y-1.0, 2.0) + 2.0;
		double f2 = 9.0 * x - Math.pow(y-1.0,  2.0);
		double c1 = Math.pow(x, 2.0) + Math.pow(y, 2.0) - 225.0;
		double c2 = x - 3.0*y + 10.0;
		
		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
		solution.setConstraint(0, c1 <= 0.0 ? 0.0 : c1);
		solution.setConstraint(1, c2 <= 0.0 ? 0.0 : c2);		
	}
	
	@Override
	public Solution newSolution() {
		Solution solution = new Solution(2, 2, 2); // 2 variables, 2 objectives, 2 constraints
		solution.setVariable(0,  EncodingUtils.newReal(-20.0, 20.0));
		solution.setVariable(1,  EncodingUtils.newReal(-20.0, 20.0));
		return solution;
	}
}
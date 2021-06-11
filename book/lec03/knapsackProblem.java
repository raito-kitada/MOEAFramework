package lec03;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.util.Vector;


public class knapsackProblem  extends AbstractProblem {
	public static int nsacks = 2;
	public static int nitems = 5;
	
	public static int[][] profit = {
			{2,5},
			{1,4},
			{6,2},
			{5,1},
			{3,3}
	};
	
	public static int[][] weight = {
			{3,3},
			{4,2},
			{1,5},
			{5,3},
			{5,2}
	};
	
	public static int[]	capacity = {10, 8};
	
	public knapsackProblem() {
		super(1, nsacks, nsacks); // 1 variables, nsacks objectives, nsacks constraints
	}
	
	@Override
	public void evaluate(Solution solution) {
		boolean[] d = EncodingUtils.getBinary(solution.getVariable(0));
		double[] f = new double[nsacks];
		double[] g = new double[nsacks];
		
		for (int i = 0; i < nitems; i++) {
			if (d[i]) {
				for (int j = 0; j < nsacks; j++) {
					f[j] += profit[i][j];
					g[j] += weight[i][j];
				}
			}
		}
		
		for (int j = 0; j < nsacks; j++) {
			if (g[j] <= capacity[j]) {
				g[j] = 0.0;
 			} else {
 				g[j] = g[j] - capacity[j];
 			}
		}
		
		solution.setObjectives(Vector.negate(f));
		solution.setConstraints(g);
	}
	
	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, nsacks, nsacks); // 2 variables, 2 objectives, 2 constraints
		solution.setVariable(0,  EncodingUtils.newBinary(nitems));
		return solution;
	}
}


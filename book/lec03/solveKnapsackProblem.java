package lec03;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.util.Vector;
import org.moeaframework.analysis.plot.Plot;

public class solveKnapsackProblem {

	public static void main(String[] args) {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(knapsackProblem.class)
				.withMaxEvaluations(10000)
//				.withProperty("populationSize", 200)
//				.withProperty("operator", "de+pm")
				.run();
		
		for (int i = 0; i < result.size(); i++) {
			Solution solution = result.get(i);
			
			double[] objectives = solution.getObjectives();
			
			// negate objectives to return them to their maximized form
			objectives = Vector.negate(objectives);
			
			System.out.println("Solution " + (i+1) + ":");
			System.out.println("    Sack 1 Profit: " + objectives[0]);
			System.out.println("    Sack 2 Profit: " + objectives[1]);
			System.out.println("    Binary String: " + solution.getVariable(0));
		}
		
//		new Plot()
//		.add("NSGAII", result)
//		.show();
	}

}

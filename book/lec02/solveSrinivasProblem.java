package lec02;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import org.moeaframework.analysis.plot.Plot;

public class solveSrinivasProblem {

	public static void main(String[] args) {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(SrinivasProblem.class)
				.withMaxEvaluations(10000)
//				.withProperty("populationSize", 200)
//				.withProperty("operator", "de+pm")
				.run();
		
		for (Solution solution : result) {
			if (!solution.violatesConstraints()) {
				System.out.printf("%.5f -> %.5f, %.5f\n", 
						EncodingUtils.getReal(solution.getVariable(0)),
						solution.getObjective(0),
						solution.getObjective(1));
			}
		}
		
		new Plot()
		.add("NSGAII", result)
		.show();
	}

}

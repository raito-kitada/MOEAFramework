package lec01;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import org.moeaframework.analysis.plot.Plot;

public class solveSchafferProblem {

	public static void main(String[] args) {
		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
//				.withProperty("populationSize", 200)
//				.withProperty("operator", "de+pm")
				.run();
		
		for (Solution solution : result) {
			System.out.printf("%.5f -> %.5f, %.5f\n", 
					EncodingUtils.getReal(solution.getVariable(0)),
					solution.getObjective(0),
					solution.getObjective(1));
		}
		
		new Plot()
		.add("NSGAII", result)
		.show();
	}

}

package mpi_sample;

import org.moeaframework.Executor;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Settings;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import mpi_sample.mpiproblem;

public class solvempiproblem {

	public static void main(String[] args) {
		// enable evaluateAll in parallel
		Settings.PROPERTIES.setBoolean("org.moeaframework.problem.evaluateall_in_parallel", true);

		NondominatedPopulation result = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(mpiproblem.class)
				.withMaxEvaluations(10000)
				.withProperty("populationSize", 200)
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

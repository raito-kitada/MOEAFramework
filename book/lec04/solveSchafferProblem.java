package lec04;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import org.moeaframework.analysis.plot.Plot;

public class solveSchafferProblem {

	public static void main(String[] args) {
		NondominatedPopulation result1 = new Executor()
				.withAlgorithm("NSGAII")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();

		NondominatedPopulation result2 = new Executor()
				.withAlgorithm("MOEAD")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		NondominatedPopulation result3 = new Executor()
				.withAlgorithm("GDE3")
				.withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000)
				.run();
		
		new Plot()
		.add("NSGAII", result1)
		.add("MOEAD", result2)
		.add("GDE3", result3)
		.show();
		
		
	}

}

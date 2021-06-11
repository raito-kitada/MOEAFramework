package lec07;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import org.moeaframework.analysis.plot.Plot;

public class solveOutProblem {

	public static void main(String[] args) {
		Problem problem = new OutProblem(); 
		
		((OutProblem) problem).init();
		
		NondominatedPopulation result1 = new Executor()
				.withAlgorithm("NSGAII")
				.withProblem(problem)
				.withMaxEvaluations(10)
				.run();
		
		new Plot()
		.add("NSGAII", result1)
		.show();
		
		
	}

}

package lec05;

import org.moeaframework.Executor;
import org.moeaframework.Analyzer;
import org.moeaframework.analysis.plot.Plot;

public class solveProblem {

	public static void main(String[] args) {
		String problem = "UF1";
//		String[] algorithms = {"NSGAII", "GDE3", "eMOEA", "MOEAD"};
		String[] algorithms = {"NSGAIII", "NSGAII", "eMOEA", "MOEAD"};
		
		Executor executor = new Executor()
				.withProblem(problem)
				.withMaxEvaluations(10000);
		
		Analyzer analyzer = new Analyzer()
				.withSameProblemAs(executor)
				.includeHypervolume()
				.showStatisticalSignificance();
		
		for (String algorithm : algorithms) {
			analyzer.addAll(algorithm, 
					executor.withAlgorithm(algorithm).runSeeds(50));
		}
		
		analyzer.printAnalysis();
		
		new Plot()
		.add(analyzer)
		.show();
	}

}

package cpsd;

import org.moeaframework.algorithm.MOEAD;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.algorithm.ReferencePointNondominatedSortingPopulation;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.PRNG;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Selection;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;
import org.moeaframework.core.comparator.AggregateConstraintComparator;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.DominanceComparator;
import org.moeaframework.core.operator.TournamentSelection;

public class CustomAlgorithmFactory {
	public static Algorithm getAlgorithm(String name, 
			Problem problem, Selection selection, Variation variation, Initialization initialization) {
		name = name.toUpperCase();
		
		try {
			if (name.startsWith("NSGAII")) {		
				return new NSGAII(
						problem,
						new NondominatedSortingPopulation(),
						null, // no archive
						selection,
						variation,
						initialization);
			} else if(name.startsWith("NSGAIII")) {
				int divisionsOuter = 12; // for 3objs (See newNSGAIII() in StandardAlgorithms.java)
				int divisionsInner = 0;
				
				ReferencePointNondominatedSortingPopulation population = new ReferencePointNondominatedSortingPopulation(
						problem.getNumberOfObjectives(), divisionsOuter, divisionsInner);

				if (problem.getNumberOfConstraints() == 0) {
					selection = new Selection() {
			
						@Override
						public Solution[] select(int arity, Population population) {
							Solution[] result = new Solution[arity];
							
							for (int i = 0; i < arity; i++) {
								result[i] = population.get(PRNG.nextInt(population.size()));
							}
							
							return result;
						}
						
					};
				} else {
					selection = new TournamentSelection(2, new ChainedComparator(
							new AggregateConstraintComparator(),
							new DominanceComparator() {

								@Override
								public int compare(Solution solution1, Solution solution2) {
									return PRNG.nextBoolean() ? -1 : 1;
								}
								
							}));
				}				

				return new NSGAII(
						problem,
						population,
						null, // no archive
						selection,
						variation,
						initialization);				
			} else if(name.startsWith("MOEAD")) {
				int neighborhoodSize = 20;
				int eta = 2;
				double delta = 0.9;
				int updateUtility = -1;
				
				return new MOEAD(problem,
						neighborhoodSize,
						initialization,
						variation,
						delta, 
						eta,
						updateUtility);
			}else {
				return null;
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}
}

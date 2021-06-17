package lab.algorithm;

import java.util.Properties;

import org.moeaframework.algorithm.MOEAD;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.algorithm.ReferencePointNondominatedSortingPopulation;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
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
import org.moeaframework.core.spi.OperatorFactory;
import org.moeaframework.util.TypedProperties;

import lab.algorithm.NSGAIIPSD;
/**
 * A provider of standard algorithms. 
 */
public class AlgorithmFactory {
	public static Algorithm getAlgorithm(
										String name, 
										Problem problem, 
										Selection selection, 
										Variation variation, 
										Initialization initialization, 
										Properties properties
										) {
		name = name.toUpperCase();
		TypedProperties typedProperties = new TypedProperties(properties);

		// Common Parameter
		boolean usearchive = typedProperties.getBoolean("use_archive", false);		
		double eps         = typedProperties.getDouble ("archive_eps", 0.01);
		EpsilonBoxDominanceArchive archive = usearchive ? new EpsilonBoxDominanceArchive(eps) : null;

		try {
			if (name.startsWith("NSGAII")) {
				
				return new NSGAII(
						problem,
						new NondominatedSortingPopulation(),
						archive,
						selection,
						variation,
						initialization);
				
			} else if (name.startsWith("NSGAIIPSD")) { // NSGAII with Parameter Space Discretization 
				
				return new NSGAIIPSD(
						problem,
						new NondominatedSortingPopulation(),
						archive,
						selection,
						variation,
						initialization); 
				
			}else if(name.startsWith("NSGAIII")) {
				
				int divisionsOuter = typedProperties.getInt("nsga3_divisions_outer", 12); // for 3objs (See newNSGAIII() in StandardAlgorithms.java)
				int divisionsInner = typedProperties.getInt("nsga3_divisions_inner", 0);
				
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
						archive,
						selection,
						variation,
						initialization);
				
			} else if(name.startsWith("MOEAD")) {
				
				int neighborhoodSize = typedProperties.getInt   ("moead_neighborhood_size", 20);
				int eta              = typedProperties.getInt   ("moead_eta", 2);
				double delta         = typedProperties.getDouble("moead_delta", 0.9);
				int updateUtility    = typedProperties.getInt   ("moead_update_utility", -1);
				
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

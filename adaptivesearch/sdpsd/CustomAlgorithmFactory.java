package sdpsd;

import org.moeaframework.algorithm.MOEAD;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Selection;
import org.moeaframework.core.Variation;

public class CustomAlgorithmFactory {
	public static Algorithm getAlgorithm(String name, 
			Problem problem, Selection selection, Variation variation, Initialization initialization) {
		name = name.toUpperCase();
		
		try {
			if (name.startsWith("NSGAIIPSD")) {		
				return new NSGAIIPSD(
						problem,
						new NondominatedSortingPopulation(),
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

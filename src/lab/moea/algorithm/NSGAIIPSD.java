package lab.moea.algorithm;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Selection;
import org.moeaframework.core.Variation;

import lab.moea.operator.real.SDPSD;
/**
 * NSGA-II with SDPSD
 * 
 * It's almost the same as NSGA-II.
 * But before calling the iterate function of NSGA-II, 
 * the update function of SDPSD is called.
 * 
 */
public class NSGAIIPSD extends NSGAII{

	public NSGAIIPSD(Problem problem, NondominatedSortingPopulation population,
			EpsilonBoxDominanceArchive archive, Selection selection,
			Variation variation, Initialization initialization) {
		super(problem, population, archive, selection, variation, initialization);
	}

	public void iterate() {
		Population population = getPopulation();
		SDPSD.update(population);
		
		super.iterate();
	}
}

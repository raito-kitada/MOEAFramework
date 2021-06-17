package lab.algorithm;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Selection;
import org.moeaframework.core.Variation;

import lab.operator.real.SDPSD;

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

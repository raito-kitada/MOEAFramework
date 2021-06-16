/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassignment.setimprovement;

import aos.creditassigment.AbstractSetImprovement;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.moeaframework.core.FitnessEvaluator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;

/**
 * This credit definition gives credit if the specified solution improves the
 * median fitness value of a solution set
 *
 * @author Nozomi
 */
public class MedianIndicatorImprovement extends AbstractSetImprovement {

    private final Percentile medianCompute;

    /**
     * Constructor for indicator based set improvement credit assignment
     */
    public MedianIndicatorImprovement() {
        medianCompute = new Percentile(50.0);
    }

    @Override
    public double compute(Solution offspring, Population solutionSet) {
        double[] fitnessvals = new double[solutionSet.size()];
            double minFitness = Double.POSITIVE_INFINITY;
            double maxFitness = Double.NEGATIVE_INFINITY;
            //find sum of the fitness minus the offspring
            for (int i = 0; i < solutionSet.size() - 1; i++) {
                fitnessvals[i] = (double) solutionSet.get(i).getAttribute(FitnessEvaluator.FITNESS_ATTRIBUTE);
                minFitness = Math.min(minFitness, fitnessvals[i]);
                maxFitness = Math.max(maxFitness, fitnessvals[i]);
            }
            double median = medianCompute.evaluate(fitnessvals, 50.0);
            double offspringFit = (double) offspring.getAttribute(FitnessEvaluator.FITNESS_ATTRIBUTE);
            return Math.max((median - offspringFit) / (median - minFitness), 0.0);
    }

    @Override
    public Population getSet(Population population, NondominatedPopulation paretoFront, NondominatedPopulation archive) {
        return population;
    }
}
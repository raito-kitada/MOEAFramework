/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassignment.setimprovement;

import aos.creditassigment.AbstractSetImprovement;
import aos.creditassignment.fitnessindicator.IIndicator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;

/**
 * This credit definition gives credit if the specified solution improves the
 * mean fitness value of a solution set
 *
 * @author Nozomi
 */
public class IndicatorImprovement extends AbstractSetImprovement{
    
    private final IIndicator indicator;
    
    /**
     * Constructor for indicator based set improvement credit assignment
     * @param indicator
     */
    public IndicatorImprovement(IIndicator indicator) {
        this.indicator = indicator;
    }

    @Override
    public double compute(Solution offspring, Population solutionSet) {
        return indicator.computeContribution(solutionSet, offspring);
    }

    @Override
    public Population getSet(Population population, NondominatedPopulation paretoFront, NondominatedPopulation archive) {
        return population;
    }
}
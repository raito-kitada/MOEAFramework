/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassignment.setcontribution;

import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;

/**
 * This Credit definition gives credit to all solutions created by the specified
 * operator, including the solution given, that are on the Pareto front
 *
 * @author Nozomi
 */
public class ParetoFrontContribution extends PopulationContribution {
    

    /**
     * Constructor to specify the rewards to give to the operator responsible
     * for each solution on the Pareto front
     *
     * @param rewardInP reward to assign to each solution on the Pareto front
     * that the operator created
     * @param rewardNotInP reward to assign if there are no solutions on the
     * Pareto front created by the operator
     */
    public ParetoFrontContribution(double rewardInP, double rewardNotInP) {
        super(rewardInP, rewardNotInP);
    }

    @Override
    public Population getSet(Population population, NondominatedPopulation paretoFront, NondominatedPopulation archive) {
        return paretoFront;
    }

    
}
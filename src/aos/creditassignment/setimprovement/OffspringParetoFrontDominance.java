/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassignment.setimprovement;

import aos.creditassigment.AbstractSetImprovement;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;

/**
 * This credit definition gives credit if the specified solution lies on the
 * Pareto front. Credit is only assigned to the specified solution
 *
 * @author Nozomi
 */
public class OffspringParetoFrontDominance extends AbstractSetImprovement {

    /**
     * Credit received if a new solution is nondominated with respect to the
     * population
     */
    protected final double inPF;

    /**
     * Credit received if a new solution is dominated with respect to the
     * population
     */
    protected final double notInPF;
    
    /**
     * Constructor to specify the credits that are assigned when a solution is
     * nondominated or dominated with respect to the given population
     *
     * @param inPF credit to assign when solution is nondominated with respect
     * to the given population
     * @param notInPF credit to assign when solution is dominated with respect
     * to the given population
     */
    public OffspringParetoFrontDominance(double inPF, double notInPF) {
        this.notInPF = notInPF;
        this.inPF = inPF;
    }

    @Override
    public double compute(Solution offspring, Population population) {
        //assumes that the newly created offspring has already been inserted
        //(or tried to be inserted into the archive). New solutions that enter 
        //archive are put at the end of the population so check there for any changes
        for (int i = population.size() - 1; 
                i  >= population.size() - getNumberOfNewOffspring();
                i--) {
            if (population.get(i).equals(offspring)) {
                return inPF;
            }
        }
        
        return notInPF;
    }

    @Override
    public Population getSet(Population population, NondominatedPopulation paretoFront, NondominatedPopulation archive) {
        return paretoFront;
    }
}
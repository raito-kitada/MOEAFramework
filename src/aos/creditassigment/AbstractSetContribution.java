/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassigment;

import java.util.Map;
import java.util.Set;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;

/**
 * A category of credit assignment strategies that computes an operator's
 * contribution to a set of solutions.
 *
 * @author nozomihitomi
 */
public abstract class AbstractSetContribution implements ICreditAssignment {
    /**
     * Credit assigned if an operator does not contributed to the set;
     */
    private final double noContribution;
    
    /**
     * Constructs a abstract set contribution credit assignment
     *
     * @param noContribution Credit assigned if an operator does not contributed
     * to the set;
     */
    public AbstractSetContribution(double noContribution) {
        this.noContribution = noContribution;
    }

    /**
     * Computes the credit by comparing the offspring solution to a set of
     * solutions
     *
     * @param solutionSet the set of solutions
     * @param operators the operators involved in the credit assignment
     * @return the credit to assign to each operator for their contribution
     */
    public abstract Map<String, Double> compute(Population solutionSet, Set<String> operators);

    /**
     * gets the set of solutions to use in the credit assignment
     *
     * @param population the current population
     * @param paretoFront the current Pareto front
     * @param archive the current archive
     * @return set of solutions
     */
    public abstract Population getSet(Population population, NondominatedPopulation paretoFront,
            NondominatedPopulation archive);
    

    @Override
    public Map<String, Double> compute(Solution[] offspring, Solution[] parent,
            Population population, NondominatedPopulation paretoFront,
            NondominatedPopulation archive, Set<String> operators) {
        Map<String, Double> credits = compute(
                getSet(population, paretoFront, archive), operators);
        for(String op : operators){
            if(!credits.containsKey(op)){
                credits.put(op, noContribution);
            }
        }
        return credits;
    }

}
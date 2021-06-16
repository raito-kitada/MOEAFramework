/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassigment;

import aos.aos.SerializableVal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;

/**
 * A category of credit assignment strategies that compares the offspring to a
 * set of solutions to assess if the offspring improves the set
 *
 * @author nozomihitomi
 */
public abstract class AbstractSetImprovement implements ICreditAssignment {

    /**
     * The number of offspring just created.
     */
    private int nOffspring;

    /**
     * Computes the credit by comparing the offspring solution to a set of
     * solutions
     *
     * @param offspring offspring solution
     * @param solutionSet the set of solutions
     * @return the credit to assign
     */
    public abstract double compute(Solution offspring, Population solutionSet);

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

    /**
     * Gets the number of offspring solutions just created.
     *
     * @return the number of offspring solutions just created
     */
    protected int getNumberOfNewOffspring() {
        return nOffspring;
    }

    @Override
    public Map<String, Double> compute(Solution[] offspring, Solution[] parent,
            Population population, NondominatedPopulation paretoFront,
            NondominatedPopulation archive, Set<String> operators) {
        HashMap<String, Double> credits = new HashMap<>();
        this.nOffspring = offspring.length;
        for (Solution o : offspring) {
            String name = ((SerializableVal) o.getAttribute("operator")).getSval();
            if (!operators.contains(name)) {
                continue;
            }
            double c = compute(o, getSet(population, paretoFront, archive));
            if (!credits.containsKey(name)) {
                credits.put(name, c);
            } else {
                credits.put(name, credits.get(name) + c);
            }
        }
        return credits;
    }

}
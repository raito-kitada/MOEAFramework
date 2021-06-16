/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassignment.setcontribution;

import aos.aos.SerializableVal;
import aos.creditassigment.AbstractSetContribution;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;

/**
 * This Credit definition gives credit to all solutions created by the specified
 * operator, including the solution given, that are in the population
 *
 * @author Nozomi
 */
public class PopulationContribution extends AbstractSetContribution {

    /**
     * Credit value for being in the Population
     */
    private final double rewardInP;

    /**
     * Constructor to specify the rewards to give to the operator responsible
     * for each solution in the Population
     *
     * @param rewardInP reward to assign to each solution in the Population that
     * the operator created
     * @param rewardNotInP reward to assign if there are no solutions in the
     * Population created by the operator
     */
    public PopulationContribution(double rewardInP, double rewardNotInP) {
        super(rewardNotInP);
        this.rewardInP = rewardInP;
    }

    @Override
    public Map<String, Double> compute(Population solutionSet, Set<String> operators) {
        HashMap<String, Double> credits = new HashMap<>();
        for (Solution o : solutionSet) {
            String name = String.valueOf(o.getAttribute("operator"));
            if (!operators.contains(name)) {
                continue;
            }
            if (!credits.containsKey(name)) {
                credits.put(name, rewardInP);
            } else {
                credits.put(name, credits.get(name) + rewardInP);
            }
        }
        return credits;
    }

    @Override
    public Population getSet(Population population,
            NondominatedPopulation paretoFront, NondominatedPopulation archive) {
        return population;
    }

}
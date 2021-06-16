 
package aos.creditassigment;

import java.util.Map;
import java.util.Set;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;

/**
 * Use this to define what solutions are included in computing and what metrics
 * should be used to compute credit.
 *
 * @author Nozomi
 */
public interface ICreditAssignment {

    /**
     * Computes the credit for the improvement offspring solutions have on the
     * parents, the population, the current pareto front, the archive, or all or
     * some of the above. It is assumed that the offspring have already been
     * introduced into the population, paretoFront, and archive if its quality
     * was high enough.
     *
     * @param offspring solution that will receive credits
     * @param parent the parent solutions
     * @param population the population
     * @param paretoFront the current pareto front
     * @param archive the archive
     * @param operators the names of the operators involved in the credit
     * assignment
     * @return Map of the credits to reward to each operator's names for the
     * given offspring solutions, parent solutions, the population, the current
     * pareto front, and the archive.
     */
public Map<String, Double> compute(Solution[] offspring, Solution[] parent,
            Population population, NondominatedPopulation paretoFront,
            NondominatedPopulation archive, Set<String> operators);
 }
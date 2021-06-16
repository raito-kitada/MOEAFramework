package aos.creditassignment.setcontribution;

import aos.aos.SerializableVal;
import aos.creditassigment.ICreditAssignment;
import aos.creditassignment.fitnessindicator.IIndicator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;
/**
 * This credit assignment rewards an operator proportional to the sum of the
 * fitness values of the solutions it has created in the set. The credit values
 * are normalized such that the lowest credit received by an operator is 0 while
 * the highest is 1.0
 *
 * @author SEAK2
 */
public class IndicatorContribution implements ICreditAssignment {
    private final IIndicator indicator;
    /**
     *
     * @param indicator
     */
    public IndicatorContribution(IIndicator indicator) {
        this.indicator = indicator;
    }


    @Override
    public Map<String, Double> compute(Solution[] offspring, Solution[] parent,
            Population population, NondominatedPopulation paretoFront, 
            NondominatedPopulation archive, Set<String> operators) {
        indicator.computeContributions(population);

        HashMap<String, Double> credits = new HashMap<>();
        for (String name : operators) {
            credits.put(name, 0.0);
        }

        double minCredit = Double.POSITIVE_INFINITY;
        double maxCredit = Double.NEGATIVE_INFINITY;
        
        for (Solution soln : population) {
            double contribution = (double) soln.getAttribute("contribution");
            if (soln.hasAttribute("operator")) {
                String operator = ((SerializableVal) soln.getAttribute("operator")).getSval();
                credits.put(operator, credits.get(operator) + contribution);
            }
        }
        
        for (String name : operators) {
            minCredit = Math.min(minCredit, credits.get(name));
            maxCredit = Math.max(maxCredit, credits.get(name));
        }

        //normalize the reward values
        for (String name : operators) {
            double normalizedCredit = Math.max(0.0,(credits.get(name) - minCredit) / (maxCredit - minCredit));
            credits.put(name, normalizedCredit);
        }
        return credits;
    }


}
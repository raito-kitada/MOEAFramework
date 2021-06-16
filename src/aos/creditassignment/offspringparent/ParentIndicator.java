/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassignment.offspringparent;

import aos.creditassigment.AbstractOffspringParent;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.fitness.AdditiveEpsilonIndicatorFitnessEvaluator;
import org.moeaframework.core.fitness.HypervolumeFitnessEvaluator;

/**
 * This credit assignment strategies compares the offspring indicator-based
 * fitness to that of its parents
 *
 * @author nozomihitomi
 */

public class ParentIndicator extends AbstractOffspringParent {
	protected final double alpha;
    private final HVFitnessEvaluator hvFitnessEvaluator;
    private final EpsilonFitnessEvaluator epsilonEvaluator;
    private final ParetoDominanceComparator comp;

    public ParentIndicator(Problem problem, double alpha) {
        super();
        this.hvFitnessEvaluator = new HVFitnessEvaluator(problem);
        this.epsilonEvaluator = new EpsilonFitnessEvaluator(problem);
        this.alpha = alpha;
        this.comp = new ParetoDominanceComparator();
        }

    @Override
    public double compute(Solution offspring, Solution parent) {
    	switch (comp.compare(offspring, parent)) {
        case -1:
    
        //double hv1 = hvFitnessEvaluator.calculate(parent, offspring);
        //double hv2 = hvFitnessEvaluator.calculate(offspring, parent);
        //double ep1 = epsilonEvaluator.calculate(parent,offspring);
        //double ep2 = epsilonEvaluator.calculate(offspring,parent);
        
        double dif = 0;
        for(int i=0;i<offspring.getNumberOfObjectives();i++){
         dif += Math.pow((parent.getObjective(i) - offspring.getObjective(i)),2);
         }
        double sqrt_dif=0;
        sqrt_dif = Math.sqrt(dif);
        
        //double ave_dif=0;
        //ave_dif = dif/(offspring.getNumberOfObjectives());
        //double cr = alpha * ((ep1 -ep2)/ ep1) + (1 - alpha) * ((hv1 - hv2)/hv1);
        //double cr = alpha*ave_dif + (1-alpha) * ((hv1 - hv2)/hv1);
        //return Math.max((hv1 - hv2) / hv1, 0.0);
        return Math.max(sqrt_dif, 0.0);
        case 0:
        	return 0.0;
        default:
            return 0.0;
    	}
    }

    /**
     * A wrapper for the fitness evaluation that exposes the calculation of indicators on two solutions
     */
    private class HVFitnessEvaluator extends HypervolumeFitnessEvaluator{

        public HVFitnessEvaluator(Problem problem) {
            super(problem);
        }

       
        public double calculate(Solution solution1, Solution solution2) {
            return super.calculateIndicator(solution1, solution2); //To change body of generated methods, choose Tools | Templates.
        }
    }
    private class EpsilonFitnessEvaluator extends AdditiveEpsilonIndicatorFitnessEvaluator{

        public EpsilonFitnessEvaluator(Problem problem) {
            super(problem);
        }

       
        public double calculate(Solution solution1, Solution solution2) {
            return super.calculateIndicator(solution1, solution2); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
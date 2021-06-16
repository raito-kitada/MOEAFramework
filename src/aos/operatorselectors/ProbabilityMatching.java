/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aos.operatorselectors;

import aos.creditassigment.Credit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import org.moeaframework.core.Variation;


/**
 * Selects operators based on probability which is proportional to the 
 * operators credits. Each operator gets selected with a minimum probability
 * of pmin. If current credits in credit repository becomes negative, zero 
 * credit is re-assigned to that operator. For the first iteration, operators
 * are selected with uniform probability.
 * @author nozomihitomi
 */
public class ProbabilityMatching extends RouletteWheel {
    
    /**
     * Alpha is the adaptation rate
     */
    private final double alpha;

    /**
     * Constructor to initialize probability map for selection
     * @param operators from which to select from 
     * @param alpha The adaptive rate
     * @param pmin The minimum probability for a operator to be selected
     */
    public ProbabilityMatching(Collection<Variation> operators, double alpha,double pmin) {
        super(operators,pmin);
        this.alpha = alpha;
        qualities1 = new HashMap<>();
        qualities2 = new HashMap<>();
        reset();
    }
    
    @Override
    public String toString() {
        return "ProbabilityMatching";
    }
    
    /**
     * Updates the quality of the operator based on the last reward received by
     * the operator. Only those operators who received a reward will be
     * updated. The update rule is Q(t+1) = (1-alpha)Q(t)+ R. If the quality
     * becomes negative, it is reset to 0.0. Only updates those operators that
     * were just rewarded.
     *
     * @param reward given to the operator
     * @param operator to be rewarded
     */
    @Override
  /*  public void update(Credit reward, Variation operator) {
        double newQuality = (1-alpha)*qualities1.get(operator) + reward.getValue();
        qualities1.put(operator, newQuality);
        checkQuality();
        updateProbabilities();
    }
    */
    
    //credit = only one. reward1 = {operator ,credit} , map.
    public void update(Credit reward1, Variation operator) {
        double newQuality1 = (1-alpha)*qualities1.get(operator) + reward1.getValue1();
        double newQuality2 = (1-alpha)*qualities2.get(operator) + reward1.getValue2();
        qualities1.put(operator, newQuality1);
        qualities2.put(operator, newQuality2);
        checkQuality();
        updateProbabilities();
    }
        
    
}
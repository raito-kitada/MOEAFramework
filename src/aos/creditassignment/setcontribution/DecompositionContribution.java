/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aos.creditassignment.setcontribution;

/**
 *
 * @author nozomihitomi
 */
public class DecompositionContribution extends PopulationContribution{
    
    /**
     * Constructor to specify the rewards to give to the heuristic responsible 
     * for each solution in a given neighborhood
     * @param rewardInN reward to assign to each solution in the neighborhood that the heuristic created
     * @param rewardNotInN reward to assign if there are no solutions in the neighborhood created by the heuristic 
     */
    public DecompositionContribution(double rewardInN,double rewardNotInN) {
        super(rewardInN, rewardNotInN);
    }
}
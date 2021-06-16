/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassignment.setimprovement;

import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;

/**
 * This credit definition gives credit to the specified solution if it makes it
 * in the epsilon archive. Credit is only assigned to the specified solution.
 *
 * @author nozomihitomi
 */
public class OffspringArchiveDominance extends OffspringParetoFrontDominance {

    /**
     * Constructor to specify the credits that are assigned when a solution is
     * in the current archive
     *
     * @param inA credit to assign when solution is in the current archive
     * @param notInA credit to assign when solution is absent in the current
     * archive
     */
    public OffspringArchiveDominance(double inA, double notInA) {
        super(inA, notInA);
    }

    @Override
    public Population getSet(Population population, NondominatedPopulation paretoFront, NondominatedPopulation archive) {
        return archive;
    }


}
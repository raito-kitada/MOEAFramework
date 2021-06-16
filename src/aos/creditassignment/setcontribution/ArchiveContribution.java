/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aos.creditassignment.setcontribution;

import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;

/**
 * This credit definition gives credit to the specified operator for all the
 * solutions it created that are  in the epsilon archive
 * @author nozomihitomi
 */
public class ArchiveContribution extends PopulationContribution{
    
    /**
     * The constructor needs the value for credit when a solution is in the 
     * e-archive and for when a solution is not in the e-archive
     * @param inArchive credit to assign when solution is in the archive 
     * @param notInArchive credit to assign when solution is not in the archive 
     */
    public ArchiveContribution(double inArchive, double notInArchive) {
        super(inArchive,notInArchive);
    }

    @Override
    public Population getSet(Population population, 
            NondominatedPopulation paretoFront, NondominatedPopulation archive) {
        return archive;
    }

    
}
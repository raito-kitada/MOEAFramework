/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.operatorselectors.replacement;

import aos.aos.IAOS;

/**
 *
 * @author nozomihitomi
 */
public interface ReplacementTrigger {

    /**
     * Checks the replacement trigger to see if operators should be replaced
     * based on the quality of the operators and the number of evaluations so
     * far
     *
     * @param aos the adaptive operator selector
     * @return
     */
    public boolean checkTrigger(IAOS aos);
}
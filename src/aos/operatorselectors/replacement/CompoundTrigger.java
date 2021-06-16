/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.operatorselectors.replacement;

import aos.aos.IAOS;
import java.util.Collection;

/**
 * A class to hold multiple triggers . Each trigger within the compound trigger
 * is checked to see if operators should be replaced.
 *
 * @author nozomihitomi
 */
public class CompoundTrigger implements ReplacementTrigger {

    private final Collection<ReplacementTrigger> triggers;

    public CompoundTrigger(Collection<ReplacementTrigger> triggers) {
        this.triggers = triggers;
    }

    @Override
    public boolean checkTrigger(IAOS aos) {
        boolean out = false;
        for (ReplacementTrigger rt : triggers) {
            if (rt.checkTrigger(aos)) {
                out = true;
            }
        }
        return out;
    }

}
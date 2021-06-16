/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.operatorselectors.replacement;

import aos.aos.IAOS;
import java.util.Collection;
import org.moeaframework.core.Variation;

/**
 * This class will remove n operators that have the lowest quality as seen by
 * the AOS operator selector
 *
 * @author nozomihitomi
 */
public class RemoveNLowest extends AbstractOperatorRemover {

    /**
     * The number of operators to remove when selectOperators is called
     */
    private final int numToRemove;

    /**
     * number of operators selected for removal so far.
     */
    private int nSelected;

    /**
     * Constructor for the operator remover
     *
     * @param permanentOperators the operators that are prohibited from being
     * selected for removal
     * @param numToRemove the number of operators to select for removal at each
     * epoch
     */
    public RemoveNLowest(Collection<Variation> permanentOperators, int numToRemove) {
        super(permanentOperators);
        this.numToRemove = numToRemove;
    }

    /**
     * Returns true if the number of operators selected is greater than or equal
     * to the desired number of operators to removes
     *
     * @param numOperators number of operators to select from to remove
     * operators. number should not count the number of permanent operators
     * @return
     */
    @Override
    protected boolean finished(int numOperators) {
        if (numToRemove > numOperators) {
            return nSelected >= numOperators;
        } else {
            return nSelected >= numToRemove;
        }
    }

    /**
     * The performance of the operator is just the quality as computed by the
     * AOS operator selector
     *
     * @param operator the operator to compute the metric for
     * @param aos the adaptive operator selector
     * @return the operator selected for removal
     */
    @Override
    protected double computeMetric(Variation operator, IAOS aos) {
        return aos.getOperatorSelector().getconvQualities().get(operator);
    }
}
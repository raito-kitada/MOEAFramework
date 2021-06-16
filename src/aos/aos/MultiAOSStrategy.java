 //* To change this license header, choose License Headers in Project Properties.
package aos.aos;

import aos.creditassigment.ICreditAssignment;
import aos.nextoperator.IOperatorSelector;
/**
 * The adaptive operator strategy that consists of a credit assignment and
 * operator selection strategy. The credit assignment strategy defines when and
 * how an operator is rewarded. The operator selection strategy uses the credits
 * to determine which operator to select next
 *
 * @author nozomihitomi
 */
public class MultiAOSStrategy {

    /**
     * The credit assignment strategy
     */
    private final ICreditAssignment creditAssignment1;
   // private final ICreditAssignment creditAssignment2;

    /**
     * The operator selection strategy
     */
    private final IOperatorSelector operatorSelection;

    /**
     * Creates the AOS strategy with a credit assignment and operator selection
     * strategy
     *
     * @param creditAssignment credit assignment strategy
     * @param operatorSelection operator selection strategy
     */
    public MultiAOSStrategy(ICreditAssignment creditAssignment1,ICreditAssignment creditAssignment2, IOperatorSelector operatorSelection) {
        this.creditAssignment1 = creditAssignment1;
        //this.creditAssignment2 = creditAssignment2;
        this.operatorSelection = operatorSelection;
    }

    /**
     * gets the credit assignment strategy
     * @return the credit assignment strategy
     */
    public ICreditAssignment getCreditAssignment() {
        return creditAssignment1;
    }

    /**
     * gets the operator selection strategy
     * @return the operator selection strategy
     */
    public IOperatorSelector getOperatorSelection() {
        return operatorSelection;
    }

}
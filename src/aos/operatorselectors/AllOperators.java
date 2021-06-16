/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aos.operatorselectors;

import aos.nextoperator.AbstractOperatorSelector;
import aos.creditassigment.Credit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.moeaframework.core.Variation;
import org.moeaframework.core.operator.CompoundVariation;

/**
 * Uses all operators that are available in a random order
 * @author nozomihitomi
 */
public class AllOperators extends AbstractOperatorSelector{
    
    /**
     * AllOperators does not utilize the credit repository so any 
     * repository will do
     * @param operators from which to select from 
     */
    public AllOperators(Collection<Variation> operators) {
        super(operators);
    }

    @Override
    public Variation nextOperator() {
        incrementIterations();
        ArrayList<Variation> allOperators = new ArrayList(super.getOperators());
        Collections.shuffle(allOperators);
        Variation[] operatorArray = new Variation[allOperators.size()];
        return new CompoundVariation(allOperators.toArray(operatorArray));
    }

    @Override
    public String toString() {
        return "AllOperators";
    }

    @Override
    public void update(Credit reward, Variation heuristic) {
        //no need to do any updates
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.operatorselectors.replacement;

import java.util.Collection;
import org.moeaframework.core.Variation;

/**
 * This interface shall be implemented by classes that are responsible for
 * creating new operators during the optimization process
 *
 * @author nozomihitomi
 */
public interface OperatorCreator {

    /**
     * Creates a new operator that can be added to the set of operators
     * available to the AOS
     *
     * @return new operator that can be added to the set of operators
     * available to the AOS
     */
    public Variation createOperator();
    
    /**
     * Creates a new operator that can be added to the set of operators
     * available to the AOS
     *
     * @param nOperator number of operators to create
     * @return new operator that can be added to the set of operators
     * available to the AOS
     */
    public Collection<Variation> createOperator(int nOperator);
}
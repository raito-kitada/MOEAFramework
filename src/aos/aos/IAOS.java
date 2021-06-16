/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aos.aos;

import aos.history.CreditHistory;
import aos.history.OperatorQualityHistory;
import aos.history.OperatorSelectionHistory;
import aos.creditassigment.ICreditAssignment;
import org.moeaframework.core.Algorithm;
import aos.nextoperator.IOperatorSelector;

/**
 * AOS is the framework using a credit assignment and operator selection strategy 
 * @author nozomihitomi
 */
public interface IAOS extends Algorithm{
    
    /**
     * Returns the selection history stored in the AOS
     * @return 
     */
    public OperatorSelectionHistory getSelectionHistory();
    
    /**
     * gets the quality history stored for each operator in the AOS
     * @return 
     */
    public OperatorQualityHistory getQualityHistory();
    
    /**
     * gets the credit history stored for each operator in the AOS
     *
     * @return
     */
    public CreditHistory getCreditHistory();
    
    /**
     * Gets the credit definition being used.
     * @return 
     */
    public ICreditAssignment getCreditAssignment1();
    public ICreditAssignment getCreditAssignment2();
     
    /**
     * Gets the strategy that is used to generate or select the next operator 
     * @return 
     */
    public IOperatorSelector getOperatorSelector();
    
    
    /**
     * Sets the adaptive operator selector's name
     * @param name
     */
    public void setName(String name);
    
    /**
     * Gets the adaptive operator selector's name
     * @return 
     */
    public String getName();

}
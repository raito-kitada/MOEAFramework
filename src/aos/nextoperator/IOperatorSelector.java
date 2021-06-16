/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.nextoperator;

import aos.creditassigment.Credit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import org.moeaframework.core.Variation;

/**
 * Interface to control methods used to select or generate next operator(s) to
 * be used in the adaptive operator selector
 *
 * @author nozomihitomi
 */
public interface IOperatorSelector {

    /**
     * Method to select or generate the next operator based on some selection or
     * generation method
     *
     * @return the next operator to be applied
     */
    public Variation nextOperator();

    /**
     * Method to update the internals of the adaptive operator selector or
     * generator based on the given CreditRepository
     *
     * @param reward received
     * @param operator to be rewarded
     */
    public void update(Credit reward, Variation operator);
    
  //  public void update(Credit reward1, Credit reward2, Variation operator);


    /**
     * Resets all stored history, qualities and credits
     */
    public void reset();

    /**
     * Gets the current quality of each operator stored
     *
     * @return the current quality for each operator stored
     */
    public HashMap<Variation, Double> getconvQualities();
    
    public HashMap<Variation, Double> getdivQualities();

    /**
     * Returns the number of times nextOperator() has been called
     *
     * @return the number of times nextOperator() has been called
     */
    public int getNumberOfIterations();

    /**
     * Gets the operators currently available to the adaptive operator selector
     *
     * @return
     */
    public Collection<Variation> getOperators();
    
    /**
     * Gets the names of the operators currently available to the adaptive operator selector
     *
     * @return the names of the operators currently available to the adaptive operator selector
     */
    public Set<String> getOperatorNames();
    
    /**
     * Gets the operator with the corresponding name
     *
     * @return the operator with the corresponding name if it exists. Else null
     */
    public Variation getOperator(String name);


    /**
     * Removes an operator from the current set of operators
     *
     * @param operator operator to remove from the current set of operators
     * @return true of the specified operator was removed from the current set.
     * False if the operator was not removed or if the operator does not exist
     * in the current set
     */
    public boolean removeOperator(Variation operator);

    /**
     * Adds an operator to the current set of operators
     *
     * @param operator to add to the current set of operators
     * @return true if the operator was successfully added to the current set of
     * operators. False if the operator was not added, or if it already existed
     * in the current set
     */
    public boolean addOperator(Variation operator);

	}
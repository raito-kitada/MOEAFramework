/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.history;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;
import org.moeaframework.core.Variation;

/**
 * Stores the history of qualities associated with each operator. Mostly for
 * analysis purposes
 *
 * @author nozomihitomi
 */
public class OperatorQualityHistory implements Serializable {

    private static final long serialVersionUID = -2323214225020219554L;

    protected HashMap<Variation, Stack<Double>> history;

    public OperatorQualityHistory() {
        history = new HashMap();
    }

    /**
     * Gets the operators involved in the selection process
     *
     * @return a collection containing the operators involved in the selection
     * process
     */
    public Collection<Variation> getOperators() {
        return history.keySet();
    }

    /**
     * This adds the quality of a operator to the history. If the operator is
     * not currently in the history, it is added to the set of operators
     * included in this history.
     *
     * @param operator the operator to add to the history
     * @param quality the quality value to add
     */
    public void add(Variation operator, double quality) {
        if (!history.containsKey(operator)) {
            history.put(operator, new Stack<Double>());
        }
        history.get(operator).push(quality);
    }

    /**
     * Gets the quality history of a particular operator
     *
     * @param operator of interest
     * @return the quality history of the specified operator
     */
    public Collection<Double> getHistory(Variation operator) {
        return history.get(operator);
    }

    /**
     * Gets the latest quality of each operator
     *
     * @return
     */
    public HashMap<Variation, Double> getLatest() {
        HashMap<Variation, Double> out = new HashMap<>();
        for (Variation operator : getOperators()) {
            out.put(operator, this.getHistory(operator).iterator().next());
        }
        return out;
    }

    /**
     * Clears the history
     */
    public void clear() {
        for (Variation operator : getOperators()) {
            history.get(operator).clear();
        }
    }

}
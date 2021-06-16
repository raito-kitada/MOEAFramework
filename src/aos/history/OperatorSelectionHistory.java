/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.history;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.moeaframework.core.Variation;

/**
 * This class stores the history of which operator was selected at a certain
 * iteration
 *
 * @author nozomihitomi
 */
public class OperatorSelectionHistory implements Serializable {

    private static final long serialVersionUID = -2323214225020219554L;

    protected HashMap<Variation, Integer> operatorSelectionCount;
    protected ArrayList<Selection> history;
    protected int totalSelectionCount;

    public OperatorSelectionHistory() {
        operatorSelectionCount = new HashMap();
        this.totalSelectionCount = 0;
        history = new ArrayList<>();
    }

    /**
     * Returns the history of the selected operators in the ordered they
     * occurred.
     *
     * @return a stack of Variations which contains the history of the selected
     * operators in the ordered they occurred. Selections at the beginning of
     * the search are at the top of the Stack.
     */
    public ArrayList<Variation> getOrderedHistory() {
        ArrayList<Variation> out = new ArrayList<>();
        for (int i = 0; i < history.size(); i++) {
            Selection sel = history.get(i);
            out.add(sel.getOperator());
        }
        return out;
    }

    /**
     * Returns the history of the time the operator was selected
     *
     * @return a stack of Variations which contains the history of the selected
     * operators in the ordered they occurred. Selections at the beginning of
     * the search are at the top of the Stack.
     */
    public ArrayList<Integer> getOrderedSelectionTime() {
        ArrayList<Integer> out = new ArrayList<>();
        for (int i = 0; i < history.size(); i++) {
            Selection sel = history.get(i);
            out.add(sel.getTime());
        }
        return out;
    }

    /**
     * Adds the operator to the history. If the Variation is a sequence of
     * operators, the operator in the sequence will be added to the history. If
     * the operator is not currently in the history, it is added to the set of
     * operators included in this history.
     *
     * @param operator to add to the history
     * @param timeSelected
     */
    public void add(Variation operator, int timeSelected) {
        history.add(new Selection(operator, timeSelected));
        if (operatorSelectionCount.containsKey(operator)) {
            operatorSelectionCount.put(operator, operatorSelectionCount.get(operator) + 1);
        } else {
            operatorSelectionCount.put(operator, 1);
        }
        totalSelectionCount++;
    }

    /**
     * Gets the number of times the specified operator was selected using the
     * history's memory
     *
     * @param operator
     * @return
     */
    public int getSelectedTimes(Variation operator) {
        return operatorSelectionCount.get(operator);
    }

    /**
     * Clears all selection history
     */
    public void reset() {
        Iterator<Variation> iter = operatorSelectionCount.keySet().iterator();
        while (iter.hasNext()) {
            operatorSelectionCount.put(iter.next(), 0);
        }
        totalSelectionCount = 0;
    }

    /**
     * Returns the number of selections made so far. This is the sum of the
     * selection counts for each operator across all operators
     *
     * @return the total number of selections made so far
     */
    public int getTotalSelectionCount() {
        return totalSelectionCount;
    }

    /**
     * Gets the operators involved in the selection process
     *
     * @return a collection containing the operators involved in the selection
     * process
     */
    public Collection<Variation> getOperators() {
        return operatorSelectionCount.keySet();
    }

    private class Selection implements Serializable {

        private static final long serialVersionUID = -2323214221420219554L;

        private final Variation operator;
        private final int time;

        public Variation getOperator() {
            return operator;
        }

        public int getTime() {
            return time;
        }

        public Selection(Variation operator, int time) {
            this.operator = operator;
            this.time = time;
        }
    }

}
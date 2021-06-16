// To change this license header, choose License Headers in Project Properties.

package aos.creditassigment;

import java.io.Serializable;

/**
 *
 * @author nozomihitomi
 */
public class Credit implements Comparable<Credit>, Serializable{
    private static final long serialVersionUID = 6693024115485521262L;
    
    /**
     * The credit value assigned
     */
    protected final double value1;
    
    protected final double value2;
    
    /**
     * Iteration at which credit was assigned
     */
    protected final int iteration;
    
    /**
     * Constructor assigns value and the iteration t to the credit
     * @param value Value to assign to credit
     * @param t iteration when credit was assigned
     */
    public Credit(int t,double value1,double value2){
        this.value1 = value1;
        this.value2 = value2;
        this.iteration = t;
    }

    /**
     * Returns the value assigned to this credit
     * @return value of this credit
     */
    public double getValue1() {
        return value1;
    }
    public double getValue2() {
        return value2;
    }  
    /**
     * Returns what iteration this credit was assigned
     * @return iteration when the credit was assigned
     */
    public int getIteration(){
        return iteration;
    }
    
    /**
     * Fraction of original value is used more for decaying credit
     * @param iteration The current iteration
     * @return 1 since this credit class does not decay in value over time;
     */
    public double fractionOriginalVal(int iteration){
        return 1;
    }

    /**
     * Compares the value of this Credit with a specified Credit
     * @param o
     * @return 0 if this Credit equals the specified Credit.
     * returns -1 if this Credit is less than the specified Credit.
     * returns 1 if this Credit is greater than the specified Credit.
     */
    @Override
    public int compareTo(Credit o) {
        if(this.equals(o))
            return 0;
        return (this.value1 < o.value1 && this.value2 < o.value2) ? -1 : 1; 
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.value1) ^ (Double.doubleToLongBits(this.value1) >>> 32));
        hash = 13 * hash + this.iteration;
        return hash;
    }

    /**
     * Returns true if the two credits have the same value irrespective of the iteration.
     * @param obj Should be Credit
     * @return  true if the two credits have the same value irrespective of the iteration. Otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Credit other = (Credit) obj;
        if (Double.doubleToLongBits(this.value1) != Double.doubleToLongBits(other.value1)||Double.doubleToLongBits(this.value2) != Double.doubleToLongBits(other.value2)) {
            return false;
        }
        return true;
    }
    
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.operatorselectors;

import aos.nextoperator.AbstractOperatorSelector;
import aos.creditassigment.Credit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.moeaframework.core.PRNG;
import org.moeaframework.core.Variation;

/**
 * Selects operators based on probability which is proportional to the
 * operators credits. Each operator gets selected with a minimum probability
 * of pmin. If current credits in credit repository becomes negative, zero
 * credit is re-assigned to that operator. For the first iteration, operators
 * are selected with uniform probability.
 *
 * @author nozomihitomi
 */
public class RouletteWheel extends AbstractOperatorSelector {

    /**
     * Hashmap to store the selection probabilities of each operator
     */
    protected HashMap<Variation, Double> probabilities;

    /**
     * The minimum probability for a operator to be selected
     */
    protected final double pmin;

    /**
     * Constructor to initialize probability map for selection
     *
     * @param operators from which to select from
     * @param pmin The minimum probability for a operator to be selected
     */
    public RouletteWheel(Collection<Variation> operators, double pmin) {
        super(operators);
        this.pmin = pmin;
        this.probabilities = new HashMap();
        reset();
    }

    /**
     * Will return the next operator that gets selected based on probability
     * proportional to a operators credits. Each operator gets selected with a
     * minimum probability of pmin
     *
     * @return
     */
    @Override
    public Variation nextOperator() {
        double p = PRNG.nextDouble();
        Iterator<Variation> iter = probabilities.keySet().iterator();
        double sum = 0.0;
        Variation operator = null;
        while (iter.hasNext()) {
            operator = iter.next();
            sum += probabilities.get(operator);
            if (sum >= p) {
                break;
            }
        }
        incrementIterations();
        if (operator == null) {
            throw new NullPointerException("No operator was selected by Probability matching operator selector. Check probabilities");
        } else {
            return operator;
        }
    }

    /**
     * calculate the sum of all qualities across the operators
     *
     * @return the sum of the operators' qualities
     */
    protected double sumQualities1() {
        double sum = 0.0;
        Iterator<Variation> iter = qualities1.keySet().iterator();
        while (iter.hasNext()) {
            sum += qualities1.get(iter.next());
        }
        return sum;
    }
    protected double sumQualities2() {
        double sum = 0.0;
        Iterator<Variation> iter = qualities2.keySet().iterator();
        while (iter.hasNext()) {
            sum += qualities2.get(iter.next());
        }
        return sum;
    }
    /**
     * Clears the credit repository and resets the selection probabilities
     */
    @Override
    public void reset() {
        super.resetQualities();
        super.reset();
        probabilities.clear();
        Iterator<Variation> iter = operators.iterator();
        while (iter.hasNext()) {
            //all operators get uniform selection probability at beginning
            probabilities.put(iter.next(), 1.0 / (double) operators.size());
        }
    }

    @Override
    public String toString() {
        return "ProbabilityMatching";
    }

    /**
     * Updates the selection probabilities of the operators according to the
     * qualities of each operator.
     */
    protected void updateProbabilities(){
        double sum1 = sumQualities1();
        double sum2 = sumQualities2();

        // if the credits sum up to zero, apply uniform probabilty to  operators
        Iterator<Variation> iter = operators.iterator();
        int obj = 3;
        String Probname = "DTLZ2_"+ obj;
        //String Probname = "WFG1_"+ obj;
        //String Probname = "ZDT4";
        //String name1 = "output16/Popsize100/"+Probname+"/prob.log";
        //String name2 = "output16/Popsize100/"+Probname+"/qual.log";
        String name1 = "output/test/probtest.log";
        String name2 = "output/test/qualtest.log";
        double alpha = 0.5;
        if (Math.abs(sum1) < Math.pow(10.0, -14)&&Math.abs(sum2) < Math.pow(10.0, -14)) {
            while (iter.hasNext()) {
                Variation operator_i = iter.next();
                double newProb1 = 1.0 / (double) operators.size();
                double newProb2 = 1.0 / (double) operators.size();
                double newProb = alpha*newProb1 + (1-alpha)*newProb2;
                probabilities.put(operator_i, newProb);                
                try{
                	int num = 1;
                File file1 = new File(name1);
                File file2 = new File(name2);
                PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1,true)));
                pw1.print("operatorNo" + num + ",");                 
                pw1.print(newProb1+",");
                pw1.print(newProb2+",");
                pw1.println(newProb);
                
                pw1.close();
                
                PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2,true)));
                pw2.print("operatorNo" + num + ",");                 
                pw2.print(qualities1.get(operator_i) +",");
                pw2.println(qualities2.get(operator_i));
                
                pw2.close();
            
                }catch(IOException e){
                	System.out.println(e);
                }
            }
        } else if (Math.abs(sum1) < Math.pow(10.0, -14)) {
            while (iter.hasNext()) {
                Variation operator_i = iter.next();
                double newProb1 = 1.0 / (double) operators.size();
                double newProb2 = pmin + (1 - probabilities.size() * pmin)
                        * (qualities2.get(operator_i) / sum2);
                double newProb = alpha*newProb1 + (1-alpha)*newProb2;
                probabilities.put(operator_i, newProb);                
                try{
                	int num = 1;
                File file1 = new File(name1);
                File file2 = new File(name2);
                PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1,true)));
                pw1.print("operatorNo" + num + ",");                 
                pw1.print(newProb1+",");
                pw1.print(newProb2+",");
                pw1.println(newProb);
                
                pw1.close();
                
                PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2,true)));
                pw2.print("operatorNo" + num + ",");                 
                pw2.print(qualities1.get(operator_i) +",");
                pw2.println(qualities2.get(operator_i));
                
                pw2.close();
            
                }catch(IOException e){
                	System.out.println(e);
                }
            }
        } else if (Math.abs(sum2) < Math.pow(10.0, -14)) {
            while (iter.hasNext()) {
                Variation operator_i = iter.next();
                double newProb1 = pmin + (1 - probabilities.size() * pmin)
                        * (qualities1.get(operator_i) / sum1);
                double newProb2 = 1.0 / (double) operators.size();
                double newProb = alpha*newProb1 + (1-alpha)*newProb2;
                probabilities.put(operator_i, newProb);  
                try{
                	int num = 1;
                File file1 = new File(name1);
                File file2 = new File(name2);
                PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1,true)));
                pw1.print("operatorNo" + num + ",");                 
                pw1.print(newProb1+",");
                pw1.print(newProb2+",");
                pw1.println(newProb);
                
                pw1.close();
                
                PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2,true)));
                pw2.print("operatorNo" + num + ",");                 
                pw2.print(qualities1.get(operator_i) +",");
                pw2.println(qualities2.get(operator_i));
                
                pw2.close();
            
                }catch(IOException e){
                	System.out.println(e);
                }
            }
        }
            else { //else update probabilities proportional to quality
            while (iter.hasNext()) {
                Variation operator_i = iter.next();
                double newProb1 = pmin + (1 - probabilities.size() * pmin)
                        * (qualities1.get(operator_i) / sum1);
                double newProb2 = pmin + (1 - probabilities.size() * pmin)
                        * (qualities2.get(operator_i) / sum2);
                double newProb = alpha*newProb1 + (1-alpha)*newProb2;
                probabilities.put(operator_i, newProb);
                try{
                	int num = 1;
                File file1 = new File(name1);
                File file2 = new File(name2);
                PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1,true)));
                pw1.print("operatorNo" + num + ",");                 
                pw1.print(newProb1+",");
                pw1.print(newProb2+",");
                pw1.println(newProb);
                
                pw1.close();
                
                PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2,true)));
                pw2.print("operatorNo" + num + ",");                 
                pw2.print(qualities1.get(operator_i) +",");
                pw2.println(qualities2.get(operator_i));
                
                pw2.close();
                }catch(IOException e){
                	System.out.println(e);
                }
            }
        }
    }

    /**
     * Selection probabilities are updated
     * @param reward given to the operator
     * @param operator to be rewarded
     */
    @Override
  /*  public void update(Credit reward, Variation operator) {
        qualities1.put(operator, qualities1.get(operator)+reward.getValue());
        qualities2.put(operator, qualities2.get(operator)+reward.getValue());
        super.checkQuality();
        updateProbabilities();
    }*/
    public void update(Credit reward1,Variation operator) {
        qualities1.put(operator, qualities1.get(operator)+reward1.getValue1());
        qualities2.put(operator, qualities2.get(operator)+reward1.getValue2());
        super.checkQuality();
        updateProbabilities();
    }
    @Override
    public boolean removeOperator(Variation operator) {
        boolean out = super.removeOperator(operator);
        probabilities.remove(operator);
        return out;
    }
    
    
}
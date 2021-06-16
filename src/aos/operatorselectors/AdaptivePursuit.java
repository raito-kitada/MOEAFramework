/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.operatorselectors;

import aos.creditassigment.Credit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.moeaframework.core.PRNG;
import org.moeaframework.core.Variation;

/**
 * Adaptive pursuit algorithm is based on Thierens, D. (2005). An adaptive
 * pursuit strategy for allocating operator probabilities. Belgian/Netherlands
 * Artificial Intelligence Conference, 385?386. doi:10.1145/1068009.1068251
 *
 * @author nozomihitomi
 */
public class AdaptivePursuit extends ProbabilityMatching {

    /**
     * The maximum probability that the heuristic with the highest credits can
     * be selected. It is implicitly defined as 1.0 - m*pmin where m is the
     * number of operators used and pmin is the minimum selection probability
     */
    double pmax;
    private final double alpha;
    /**
     * The Learning Rate
     */
    private final double beta;

    /**
     * Constructor to initialize adaptive pursuit map for selection. The maximum
     * selection probability is implicitly defined as 1.0 - m*pmin where m is
     * the number of operators defined in the given credit repository and pmin
     * is the minimum selection probability
     *
     * @param operators from which to select from 
     * @param alpha the adaptation rate
     * @param beta the learning rate
     * @param pmin the minimum selection probability
     */
    public AdaptivePursuit(Collection<Variation> operators, double alpha, double beta, double pmin) {
        super(operators, alpha, pmin);
        this.pmax = 1 - (operators.size() - 1) * pmin;
        this.alpha = alpha;
        this.beta = beta;
        if (pmax < pmin) {
            throw new IllegalArgumentException("the implicit maxmimm selection "
                    + "probability " + pmax + " is less than the minimum selection probability " + pmin);
        }
        reset();
        //Initialize the probabilities such that a random heuristic gets the pmax
        //Random r = new Random();
        //int operator_lead = r.nextInt(probabilities.size());
        //Iterator<Variation> iter = probabilities.keySet().iterator();
        //int count = 0;
        //while (iter.hasNext()) {
          //  if (count == operator_lead) {
          //      probabilities.put(iter.next(), 1.0 / (double) operators.size());
            //} else {
              //  probabilities.put(iter.next(), 1.0 / (double) operators.size());
            //}
            //count++;
        //}
    }
    
    /**
     * Updates the probabilities stored in the selector
     */
    @Override
    public void update(Credit reward1, Variation operator) {
        double newQuality1 = (1-alpha)*qualities1.get(operator) + reward1.getValue1();
        double newQuality2 = (1-alpha)*qualities2.get(operator) + reward1.getValue2();
        qualities1.put(operator, newQuality1);
        qualities2.put(operator, newQuality2);
        checkQuality();
        updateProbabilities();
    }
    
    /**
     * Clears the credit repository and resets the selection probabilities and updates the p_max
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
     * Updates the selection probabilities of the operators according to the
     * qualities of each operator.
     */
    @Override
    protected void updateProbabilities(){
    	 double sum1 = sumQualities1();
        double sum2 = sumQualities2();
        Variation leadOperator1 = argMax1(qualities1.keySet());
        Variation leadOperator2 = argMax2(qualities2.keySet());
        Iterator<Variation> iter = operators.iterator();
        int obj = 3;
        //String Probname = "DTLZ2_"+ obj;
        String Probname = "WFG1_"+ obj;
        //String Probname = "ZDT1";
        String name1 = "output14/Popsize100/"+Probname+"/prob.log";
        String name2 = "output14/Popsize100/"+Probname+"/qual.log";
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
              //double newProb1 = 1.0 / (double) operators.size();                
              double newProb2 = pmin + (1 - probabilities.size() * pmin)
                      * (qualities2.get(operator_i) / sum2);
              if (operator_i == leadOperator2) {
                  //probabilities.put(operator_i, prevProb+beta*(pmax-prevProb));
                  newProb2 = newProb2 + beta*(pmax - newProb2);
              } else {
                  //probabilities.put(operator_i, prevProb+beta*(pmin-prevProb));
                  newProb2 = newProb2 + beta*(pmin - newProb2);
              }
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
      }else if (Math.abs(sum2) < Math.pow(10.0, -14)) {
    	    while (iter.hasNext()) {
                Variation operator_i = iter.next();
                double newProb2 = 1.0 / (double) operators.size();
                //double newProb1 = 1.0 / (double) operators.size();
                double newProb1 = pmin + (1 - probabilities.size() * pmin)
                        * (qualities1.get(operator_i) / sum1);
                if (operator_i == leadOperator1) {
                    //probabilities.put(operator_i, prevProb+beta*(pmax-prevProb));
                    newProb1 = newProb1 + beta*(pmax - newProb1);
                } else {
                    //probabilities.put(operator_i, prevProb+beta*(pmin-prevProb));
                    newProb1 = newProb1 + beta*(pmin - newProb1);
                }
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
      }  else { //else update probabilities proportional to quality
          while (iter.hasNext()) {
              Variation operator_i = iter.next();
              double newProb1 = pmin + (1 - probabilities.size() * pmin)
                      * (qualities1.get(operator_i) / sum1);
              double newProb2 = pmin + (1 - probabilities.size() * pmin)
                      * (qualities2.get(operator_i) / sum2);
              if (operator_i == leadOperator1) {
                  //probabilities.put(operator_i, prevProb+beta*(pmax-prevProb));
                  newProb1 = newProb1 + beta*(pmax - newProb1);
              } else {
                  //probabilities.put(operator_i, prevProb+beta*(pmin-prevProb));
                  newProb1 = newProb1 + beta*(pmin - newProb1);
              }
              if (operator_i == leadOperator2) {
                  //probabilities.put(operator_i, prevProb+beta*(pmax-prevProb));
                  newProb2 = newProb2 + beta*(pmax - newProb2);
              } else {
                  //probabilities.put(operator_i, prevProb+beta*(pmin-prevProb));
                  newProb2 = newProb2 + beta*(pmin - newProb2);
              }
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
     * Want to find the operator that has the maximum quality
     *
     * @param operator
     * @return the current quality of the specified operator
     */
    @Override
    protected double function2maximize1(Variation operator) {
    	return qualities1.get(operator);
    }
    protected double function2maximize2(Variation operator) {
        return qualities2.get(operator);
    }
    @Override
    public String toString() {
        return "AdaptivePursuit";
    }
    
}
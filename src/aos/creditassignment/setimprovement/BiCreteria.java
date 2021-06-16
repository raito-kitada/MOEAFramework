	/*
	 * To change this license header, choose License Headers in Project Properties.
	 * To change this template file, choose Tools | Templates
	 * and open the template in the editor.
	 */
	package aos.creditassignment.setimprovement;
	
	import aos.creditassigment.AbstractSetImprovement;
	//import org.moeaframework.core.FitnessEvaluator;
	import org.moeaframework.core.NondominatedPopulation;
	import org.moeaframework.core.Population;
	import org.moeaframework.core.Solution;
	//import org.moeaframework.core.comparator.ObjectiveComparator;
	
	/**
	 * This credit definition gives credit if the specified solution lies on the
	 * Pareto front. Credit is only assigned to the specified solution
	 *
	 * @author Nozomi
	 */
	public class BiCreteria extends AbstractSetImprovement {
		  
	
	    /**
	     * Credit received if a new solution is nondominated with respect to the
	     * population
	     */
	    protected final double conv;
	
	    /**
	     * Credit received if a new solution is dominated with respect to the
	     * population
	     */
	    protected final double div;
	    
	    protected final double none;
	    
	    /**
	     * Constructor to specify the credits that are assigned when a solution is
	     * nondominated or dominated with respect to the given population
	     *
	     * @param inPF credit to assign when solution is nondominated with respect
	     * to the given population
	     * @param notInPF credit to assign when solution is dominated with respect
	     * to the given population
	     */
	    public BiCreteria(double conv, double div, double none) {
	        this.div = div;
	        this.conv = conv;
	        this.none = none;
	    }
	
	    @Override
	    public double compute(Solution offspring, Population population) {
	    	//credit
	    	
	    	//convergence evaluate(dominated or non-dominated)
	        for (int i = population.size() - 1; 
	                i  >= population.size() - getNumberOfNewOffspring();
	                i--) {
	            if (population.get(i).equals(offspring)) {
	                return conv;
	            }
	        } /*
	        //Crowding distance evaluate
	         //parent
	        int numberOfObjectives = population.get(0).getNumberOfObjectives();
	        double[] distance = new double[population.size()];
	        for (int i = 0; i < numberOfObjectives; i++) {
				population.sort(new ObjectiveComparator(i));
	        int n = population.size();
	        double minObjective = population.get(0).getObjective(i);
			 double maxObjective = population.get(n - 1).getObjective(i);
			 distance[0] += 2 * (population.get(1).getObjective(i) - minObjective)/(maxObjective - minObjective);
			 distance[n] += 2 * (maxObjective - population.get(n - 1).getObjective(i))/(maxObjective - minObjective);
			 for (int j = 1; j < n - 1; j++) {
				distance[j] += (population.get(j + 1).getObjective(i) - 
						population.get(j - 1).getObjective(i))
						/ (maxObjective - minObjective);
				}
			}
	        double sum = 0;
	        double vars = 0;
	        for(int i=0;i<distance.length;i++){
	        	sum += distance[i];
	        }
	        double ave = ((double)sum)/distance.length;
	        for(int i=0;i<distance.length;i++){
	        	vars += ((distance[i]-ave)*(distance[i]-ave));
	        }
	         double cdv = Math.sqrt(vars/distance.length);
	        //offspring 
	         Population newpopulation = population;
	         newpopulation.add(offspring);
		        double[] newdistance = new double[newpopulation.size()];
		        for (int i = 0; i < numberOfObjectives; i++) {
					newpopulation.sort(new ObjectiveComparator(i));
		        int n = newpopulation.size();
		        double minObjective = newpopulation.get(0).getObjective(i);
				 double maxObjective = newpopulation.get(n - 1).getObjective(i);
				 distance[0] += 2 * (newpopulation.get(1).getObjective(i) - minObjective)/(maxObjective - minObjective);
				 distance[n] += 2 * (maxObjective - newpopulation.get(n - 1).getObjective(i))/(maxObjective - minObjective);
				 for (int j = 1; j < n - 1; j++) {
					newdistance[j] += (newpopulation.get(j + 1).getObjective(i) - 
							newpopulation.get(j - 1).getObjective(i))
							/ (maxObjective - minObjective);
					}
				}
		        double newsum = 0;
		        double newvars = 0;
		        for(int i=0;i<newdistance.length;i++){
		        	newsum += newdistance[i];
		        }
		        double newave = ((double)newsum)/newdistance.length;
		        for(int i=0;i<newdistance.length;i++){
		        	newvars += ((newdistance[i]-newave)*(newdistance[i]-newave));
		        }
		         double newcdv = Math.sqrt(newvars/newdistance.length);
		         if(newcdv < cdv){
		        	 return div;
		         }
		         newpopulation.remove(offspring);
			      */
	        return none;
	    }
	
	  
	    
	    @Override
	    public Population getSet(Population population, NondominatedPopulation paretoFront, NondominatedPopulation archive) {
	        return paretoFront;
	    }
	}
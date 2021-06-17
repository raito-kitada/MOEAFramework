package sdpsd;

import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.EvolutionaryAlgorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PRNG;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Variation;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.GAVariation;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.operator.real.SBX;

import lab.problem.CustomProblemFactory;
import lab.operator.real.SDPSD;
import lab.operator.CustomRandomInitialization;
import lab.algorithm.CustomAlgorithmFactory;

public class SDPSDMain {
	private static int npop = 100;
	private static int ngen = 100;
	private static int nobj = 2;
	private static int ncon = 0;
	private static int nvar = 10;

	private static int ntrial = 2;
	
	private static int max_evaluation = npop * ngen;
 	
	private static String[] algorithmName = {"NSGAIIPSD"};
	private static String[] problemName = {"DTLZ2"};
	
	private static void opt(int ndigit) {
     	SimplePIO.setPath("output/digitprec/sdpsd/"+ndigit+"/");

     	SDPSD fd = new SDPSD(ndigit, 2, 8);
		
     	for (int trial = 0; trial < ntrial; trial++) {
     		long seed = trial*10 + 1000;
     		
     		PRNG.setSeed(seed);
     		
 			for (String pName : problemName) {
 				for (String aName : algorithmName) {
        			Util.print(aName, pName, trial, ndigit, seed);

        			/**
             		 * Open history file 
             		 */
                 	String historyName = Util.makeFileName("history", aName, pName, trial, "txt");     		
                 	SimplePIO.setHistoryFile(historyName, false);

                 	/**
                 	 * Define optimization problem
                 	 */
        			Problem problem = CustomProblemFactory.getProblem(pName, nobj, nvar, ncon, null);
        	
        			/**
        			 * Create an initial random population.
        			 * The population size(=npop) and the number of digit(=ndigit) are specified here.
        			 */
        			Initialization initialization = new CustomRandomInitialization(
        					problem,
        					npop,
        					fd);
        			
        			/**
        			 * Define the crossover and mutation operator.
        			 */
        			TournamentSelection selection = new TournamentSelection(2, 
        					new ChainedComparator(
        							new ParetoDominanceComparator(),
        							new CrowdingComparator()
        							)
        					);
        			
        			/**
        			 * Define the crossover and mutation operator.
        			 */
        			Variation variation = new GAVariation(
        					new SBX(1.0, 25.0),
        					new PM(1.0 / nvar, 30.0)
        					);
        			((GAVariation)variation).appendOperator(fd);
        	
        			
        			/**
        			 * Construct the algorithm
        			 */
        			Algorithm algorithm = CustomAlgorithmFactory.getAlgorithm(
        					aName, problem, selection, variation, initialization, null);
        			        			
        			/**
        			 * Run the algorithm for the specified number of evaluation. 
        			 */
        			int gen = 0;
        			while (algorithm.getNumberOfEvaluations() < max_evaluation) {
        				algorithm.step();
        				
        				SimplePIO.writeHistory(gen, ((EvolutionaryAlgorithm) algorithm).getPopulation());
        	
        				gen++;
        			}
        			
        			/**
        			 * Get the Pareto approximate results
        			 */
        			NondominatedPopulation result = algorithm.getResult();
                 	String paretoName = Util.makeFileName("pareto", aName, pName, trial, "txt");     		
        			SimplePIO.writeSolutions(paretoName, result);
        			
        			/**
        			 * Plot final result
        			 */
        			Plot plt = new Plot();
        			plt.add(aName, result);
        			plt.setTitle(pName+" ("+ndigit+" digit)");
        			plt.show();
        			        			
        			/**
        			 * Close all files
        			 */
        			SimplePIO.closeAll();
     			}
     		}
     	}		
	}
	
	public static void main(String[] args) {
		int[] ndigitList = {2};
		
		for (int ndigit : ndigitList) {
			opt(ndigit);
		}
		
		System.out.println("Finish");
	}
}

package cpsd;

import java.io.File;
import java.io.IOException;

import org.moeaframework.Instrumenter;
import org.moeaframework.analysis.collector.Accumulator;
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
import org.moeaframework.core.operator.real.CPSD;
import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.operator.real.SBX;

public class CPSDMain {
	private static int npop = 100;
	private static int ngen = 100;
	private static int nobj = 3;
	private static int ncon = 0;
	private static int nvar = 38;

	private static int[] ndigits = {2};
	private static int ntrial = 10;

	private static int max_evaluation = npop * ngen;
 	
	private static String[] algorithmName = {"NSGAIII"};
	private static String[] problemName = {"DTLZ2"};
	
	private static void opt(int trial) {
		long seed = trial * 10 + 1000;
 		PRNG.setSeed(seed);

 		for (int ndigit : ndigits) {
     		CPSD fd = new CPSD(ndigit);
     		
 			for (String pName : problemName) {
 		     	SimplePIO.setPath("output/adaptivesearch/cpsd/"+pName+"/"+ndigit+"/");

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
        			Problem problem = CustomProblemFactory.getProblem(pName, nobj, nvar, ncon);
        	
        			/**
        			 * Construct instrumenter
        			 */
        			Instrumenter instrumenter = new Instrumenter()
        					.withProblem(pName+"_"+nobj)
        					.withFrequency(100)
//        					.attachElapsedTimeCollector()
        					.attachGenerationalDistanceCollector()
        					.attachInvertedGenerationalDistanceCollector()
        					.attachHypervolumeCollector();
        			
        			NondominatedPopulation referenceSet = instrumenter.getReferenceSet();
        			
        			
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
        			 * 
        			 * disable swapping variables in SBX operator to remain consistent with
        			 * Deb's implementation
        			 */
        			Variation variation = new GAVariation(
        					new SBX(1.0, 25.0, false, true),
        					new PM(1.0 / nvar, 30.0)
        					);
        			((GAVariation)variation).appendOperator(fd);
        	
        			/**
        			 * Construct the algorithm
        			 */
        			Algorithm algorithm = CustomAlgorithmFactory.getAlgorithm(
        					aName, problem, selection, variation, initialization);
        			
        			/**
        			 * 
        			 */
        			Algorithm ialgorithm = instrumenter.instrument(algorithm);
        			
        			/**
        			 * Run the algorithm for the specified number of evaluation. 
        			 */
        			int gen = 0;
        			while (algorithm.getNumberOfEvaluations() < max_evaluation) {
        				ialgorithm.step();
        				
//        				SimplePIO.writeHistory(gen, ((EvolutionaryAlgorithm) algorithm).getPopulation());
        				
        				NondominatedPopulation result = algorithm.getResult();
        				SimplePIO.writeHistory(gen, result);
        	
        				gen++;
        			}
        			
        			/**
        			 * Get the Pareto approximate results
        			 */
        			NondominatedPopulation result = algorithm.getResult();
                 	String paretoName = Util.makeFileName("pareto", aName, pName, trial, "txt");     		
        			SimplePIO.writeSolutions(paretoName, result);
        			
        			Accumulator accumulator = instrumenter.getLastAccumulator();
        			        			
        			/**
        			 * Save the runtime dynamics to png and csv
        			 */
                 	String ImgName = Util.makeFileName("Img", aName, pName, trial, "png");     		
        			SimplePIO.writeAccumToImg(ImgName, accumulator);

                 	String CSVName = Util.makeFileName("ImgData", aName, pName, trial, "csv");     		
        			SimplePIO.writeAccumToCSV(CSVName, accumulator);
        			
        			/**
        			 *  Print the runtime dynamics
        			 */
//        			System.out.format("  NFE    Time      Generational Distance%n");
//        			
//        			for (int i=0; i<accumulator.size("NFE"); i++) {
//        				System.out.format("%5d    %-8.4f  %-8.4f%n",
//        						accumulator.get("NFE", i),
//        						accumulator.get("Elapsed Time", i),
//        						accumulator.get("GenerationalDistance", i));
//        			}        			
        			
        			/**
        			 * Plot final result
        			 */
//        			Plot plt = new Plot();
//        			plt.add(aName, result);
//        			plt.setTitle(pName+" ("+ndigit+" digit)");
//        			plt.show();
        			        			
        			/**
        			 * Close all files
        			 */
        			SimplePIO.closeAll();
     			}
     		}
     	}		
	}
	
	public static void main(String[] args) {
		
		for (int i=0; i<ntrial; i++) {
			opt(i);
		}
		
		System.out.println("Finish");
	}
}

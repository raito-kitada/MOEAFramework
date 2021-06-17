package aos_sample;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

import org.moeaframework.Instrumenter;
import org.moeaframework.algorithm.AbstractEvolutionaryAlgorithm;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.PRNG;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Variation;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.core.spi.OperatorFactory;

import aos.IO.IOCreditHistory;
import aos.IO.IOQualityHistory;
import aos.IO.IOSelectionHistory;
import aos.aos.AOSMOEA;
import aos.aos.AOSStrategy;
import aos.creditassigment.ICreditAssignment;
import aos.creditassignment.offspringparent.ParentDomination;
import aos.creditassignment.offspringparent.ParentIndicator;
import aos.creditassignment.setcontribution.ParetoFrontContribution;
import aos.creditassignment.setimprovement.OffspringParetoFrontDominance;
import aos.nextoperator.IOperatorSelector;
import aos.operator.AOSVariation;
import aos.operatorselectors.ProbabilityMatching;
import lab.algorithm.AlgorithmFactory;
import lab.problem.ProblemFactory;
import cpsd.SimplePIO;
import cpsd.Util;

public class aos_sample {
	private static int npop = 12;
	private static int ngen = 10;
	private static int nobj = 3;
	private static int ncon = 0;
	private static int nvar = 4;

	private static int ntrial = 1;

	private static int max_evaluation = npop * ngen;
 	
	private static String[] algorithmName = {"NSGAII"};
	private static String[] problemName = {"DTLZ2",};

	private static void opt(int trial) {
		long seed = trial * 10 + 1000;
 		PRNG.setSeed(seed);

		for (String pName : problemName) {
	     	SimplePIO.setPath("output/aos/"+pName+"/");

	     	for (String aName : algorithmName) {

    			/**
         		 * Open history file 
         		 */
             	String historyName = Util.makeFileName("history", aName, pName, trial, "txt");     		
             	SimplePIO.setHistoryFile(historyName, false);

             	/**
             	 * Define optimization problem
             	 */
             	Properties prob_properties = new Properties();
             	prob_properties.setProperty("l","10");
    			Problem problem = ProblemFactory.getProblem(pName, nobj, nvar, ncon, prob_properties);
    	
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
    			
//        			NondominatedPopulation referenceSet = instrumenter.getReferenceSet();
    			
    			
    			/**
    			 * Create an initial random population.
    			 */
    			Initialization initialization = new RandomInitialization(
    					problem,
    					npop
    					);
    			
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
    			 * Define variation.
    			 * 
    			 */
    			AOSVariation variation = new AOSVariation(); 
    	
    			/**
    			 * Construct the algorithm
    			 */
    			Properties alg_properties = new Properties();
//    			alg_properties.setProperty("use_archive", "1");
    			Algorithm algorithm = AlgorithmFactory.getAlgorithm(
    					aName, problem, selection, variation, initialization, alg_properties);
    			
    			/**
    			 * create operators
    			 */
    	        ArrayList<Variation> operators = new ArrayList<Variation>();
    	        Properties property = new Properties();
    	        property.put("populationSize", npop);
    	        OperatorFactory of = OperatorFactory.getInstance();
    	        
    	        operators.add(of.getVariation("um", property, problem));
    	        operators.add(of.getVariation("sbx+pm", property, problem));
    	        operators.add(of.getVariation("de+pm", property, problem));
    	        operators.add(of.getVariation("pcx+pm", property, problem));
    	        operators.add(of.getVariation("undx+pm", property, problem));
    	        operators.add(of.getVariation("spx+pm", property, problem));  

    	        // create operator selector
    	        IOperatorSelector operatorSelector = new ProbabilityMatching(operators, 0.8, 0.1);//(operators, alpha, pmin)
    	        
    	        //create credit assignment
    	        ICreditAssignment creditAssignment1 = new ParentDomination(1, 0, 0);
    	        ICreditAssignment creditAssignment2 = new ParetoFrontContribution(1, 0);
    	        ICreditAssignment creditAssignment3 = new ParentIndicator(problem, 0.6);
    	        ICreditAssignment creditAssignment4 = new OffspringParetoFrontDominance(1, 0);
    	        
    	        /**
    	         * create aos
    	         */
    	        AOSStrategy aosStrategy = new AOSStrategy(creditAssignment2, creditAssignment2, operatorSelector);
    	        AOSMOEA aos = new AOSMOEA((AbstractEvolutionaryAlgorithm) algorithm, variation, aosStrategy);
    	        
    			/**
    			 * attach collectors
    			 */
    			Algorithm ialgorithm = instrumenter.instrument(aos);
    			
    			/**
    			 * Run the algorithm for the specified number of evaluation. 
    			 */
    			int gen = 0;
    			while (ialgorithm.getNumberOfEvaluations() < max_evaluation) {
    				System.out.println("trial = "+trial+", gen = "+gen);
    				
    				ialgorithm.step();
    				
    				//aos.getArchive();
    				
    				NondominatedPopulation result = aos.getArchive();
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

    			//save AOS results
    			String sfname = Util.makeFileName("selection", aName, pName, trial, "csv");
    			String cfname = Util.makeFileName("credit", aName, pName, trial, "csv");
    			String qfname = Util.makeFileName("quality", aName, pName, trial, "csv");

    			IOSelectionHistory.saveHistory(aos.getSelectionHistory(), "output/aos/"+pName+"/"+sfname, ",");
				IOCreditHistory.saveHistory(aos.getCreditHistory(), "output/aos/"+pName+"/"+cfname, ",");
				IOQualityHistory.saveHistory(aos.getQualityHistory(), "output/aos/"+pName+"/"+qfname, ",");
			
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
	
	public static void main(String[] args) {
		ignoreJava9Warning();
		
		for (int i=0; i<ntrial; i++) {
			opt(i);
		}
		
		System.out.println("Finish");
	}


	@SuppressWarnings("restriction")
	public static void ignoreJava9Warning() {
	  try {
	    Field theUnsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
	    theUnsafe.setAccessible(true);
	    sun.misc.Unsafe u = (sun.misc.Unsafe) theUnsafe.get(null);
	    Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
	    Field logger = cls.getDeclaredField("logger");
	    u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
	  } catch (Exception e) {
	    // Java9以前では例外
	  }
	}	
}

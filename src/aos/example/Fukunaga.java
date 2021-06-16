/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.example;

import aos.IO.IOCreditHistory;
import aos.IO.IOQualityHistory;
import aos.IO.IOSelectionHistory;
import aos.aos.AOSMOEA;
import aos.aos.AOSStrategy;
import aos.creditassigment.ICreditAssignment;
import aos.creditassignment.offspringparent.ParentDomination;
import aos.creditassignment.setcontribution.ParetoFrontContribution;
import aos.nextoperator.IOperatorSelector;
import aos.operator.AOSVariation;
import aos.operatorselectors.ProbabilityMatching;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.moeaframework.Instrumenter;
import org.moeaframework.algorithm.MOEAD;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.analysis.collector.InstrumentedAlgorithm;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.FrameworkException;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.PopulationIO;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.Variation;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.CompoundVariation;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.operator.real.SBX;
import org.moeaframework.core.spi.OperatorFactory;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.CEC2009.UF1;
import org.moeaframework.problem.DTLZ.DTLZ2;
import org.moeaframework.problem.DTLZ.DTLZ3;
import org.moeaframework.problem.DTLZ.DTLZ4;
import org.moeaframework.problem.WFG.WFG1;
import org.moeaframework.problem.WFG.WFG2;
import org.moeaframework.problem.WFG.WFG6;
import org.moeaframework.problem.WFG.WFG8;
import org.moeaframework.problem.WFG.WFG9;
import org.moeaframework.problem.ZDT.ZDT1;
import org.moeaframework.problem.ZDT.ZDT4;
import org.moeaframework.util.TypedProperties;

/**
 *
 * @author nozomihitomi
 */
public class Fukunaga {
	private static boolean checkType(Class<? extends Variable> type, Problem problem) {
		Solution solution = problem.newSolution();
		
		for (int i=0; i<solution.getNumberOfVariables(); i++) {
			if (!type.isInstance(solution.getVariable(i))) {
				return false;
			}
		}
		
		return true;
	}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        //create the desired problem
       // UF1 prob = new UF1();
    	//ArrayList<Problem> prob = new ArrayList();
        
     	int obj = 3;
        DTLZ2 prob = new DTLZ2(obj);
        //DTLZ3 prob = new DTLZ3(obj);
        //DTLZ4 prob = new DTLZ4(obj);
        //WFG1 prob = new WFG1(obj-1,10,obj);
        //WFG2 prob = new WFG2(obj-1,10,obj);
        //WFG6 prob = new WFG6(obj-1,10,obj);
        //WFG8 prob = new WFG8(obj-1,10,obj);
        //WFG9 prob = new WFG9(obj-1,10,obj);
        //ZDT1 prob = new ZDT1();
        //ZDT4 prob = new ZDT4();


      
        //create the desired algorithm
     // for(int s=0; s < prob.size();s++){
        int numberofSeeds = 10;
        int[] populationSize = new int[]{100};//,300,500,1000};
        //int populationSize = 1000;
        for(int i=1;i<=numberofSeeds;i++){
        	System.out.println("Seeds=" + i);
        	for(int k=0;k<populationSize.length;k++){
        	System.out.println("PopulationSize=" + populationSize[k]);
        //	AOSVariation variation = new AOSVariation(); 
  /*      	
        NondominatedSortingPopulation population = new NondominatedSortingPopulation();
        EpsilonBoxDominanceArchive archive = new EpsilonBoxDominanceArchive(0.01);
        Variation variation = new CompoundVariation(new SBX(1.0, 15), new PM(1.0 / prob.getNumberOfVariables(),20));
        TournamentSelection selection = new TournamentSelection(2, 
				new ChainedComparator(
						new ParetoDominanceComparator(),
						new CrowdingComparator()));
        RandomInitialization initialization = new RandomInitialization(prob, populationSize[k]);
        NSGAII nsgaii = new NSGAII(prob, population, archive, selection, variation, initialization);
*/
        if (!checkType(RealVariable.class, prob)) {
			throw new FrameworkException("unsupported decision variable type");
		}
        TypedProperties prop = new TypedProperties();
	

		Initialization initialization = new RandomInitialization(prob,populationSize[k]);

		Variation variation = new CompoundVariation(new SBX(1.0, 15), new PM(1.0 / prob.getNumberOfVariables(),20));

		int neighborhoodSize = 20;
		int eta = 2;
		
		if (neighborhoodSize > populationSize[k]) {
			neighborhoodSize = populationSize[k];
		}
		
	
		MOEAD moead = new MOEAD(prob,neighborhoodSize,initialization,variation,prop.getDouble("delta", 0.9),eta,(int)prop.getDouble("updateUtility", -1));

	    
   
        //example of operators you might use
     /*   ArrayList<Variation> operators = new ArrayList();
        Properties prop = new Properties();
        prop.put("populationSize", populationSize[k]);
        OperatorFactory of = OperatorFactory.getInstance();

        operators.add(of.getVariation("um", prop, prob));
        operators.add(of.getVariation("sbx+pm", prop, prob));
        operators.add(of.getVariation("de+pm", prop, prob));
        operators.add(of.getVariation("pcx+pm", prop, prob));
        operators.add(of.getVariation("undx+pm", prop, prob));
        operators.add(of.getVariation("spx+pm", prop, prob));

        //create operator selector
        IOperatorSelector operatorSelector = new ProbabilityMatching(operators, 0.8, 0.1);//(operators,alpha,pmin)

        //create credit assignment
        ICreditAssignment creditAssignment = new ParetoFrontContribution(1, 0);
        //ICreditAssignment creditAssignment = new ParetoDomination(1, 0, 0);
        //ICreditAssignment creditAssignment = new OffspringParetoFrontDominace(1, 0);

        //create AOS
        AOSStrategy aosStrategy = new AOSStrategy(creditAssignment, operatorSelector);
        AOSMOEA aos = new AOSMOEA(nsgaii,variation, aosStrategy);
*/
        //attach collectors
        Instrumenter instrumenter = new Instrumenter()
        		  .withFrequency(5)
                .attachElapsedTimeCollector();

        InstrumentedAlgorithm instAlgorithm = instrumenter.instrument(moead);


        //conduct search
        int maxEvaluations = populationSize[k] * 100;
        int gen = 0;
        
        while (!instAlgorithm.isTerminated() && 
                (instAlgorithm.getNumberOfEvaluations() < maxEvaluations)) {
        	gen += 1;
            instAlgorithm.step();
            
            try {
                //one way to save current population
            	//System.out.println(prob);
            	//System.out.println("generation=" + gen);
              //PopulationIO.writeObjectives(new File("output6/Popsize"+populationSize[k]+"/DTLZ2_3/archive_gen" + gen +"_seed"+i+".txt"), moead.getResult());
              PopulationIO.writeObjectives(new File("output6/Popsize"+populationSize[k]+"/DTLZ2_3/Test/gen"+gen+"_seed"+i+".txt"), moead.getResult());
                } catch (IOException ex) {
                Logger.getLogger(TestCase.class.getName()).log(Level.SEVERE, null, ex);
            
        }
       // save AOS results
       // IOSelectionHistory iosh = new IOSelectionHistory();
       // iosh.saveHistory(aos.getSelectionHistory(), "selection.csv", ",");
       // IOCreditHistory ioch = new IOCreditHistory();
       // ioch.saveHistory(aos.getCreditHistory(), "credit.csv", ",");
       // IOQualityHistory ioqh = new IOQualityHistory();
       // ioqh.saveHistory(aos.getQualityHistory(), "quality.csv", ",");
   //       }
        		}
        	}
        }
    }

}

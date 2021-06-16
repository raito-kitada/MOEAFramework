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
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.analysis.collector.InstrumentedAlgorithm;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.PopulationIO;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Variation;
import org.moeaframework.core.operator.RandomInitialization;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.operator.real.SBX;
import org.moeaframework.core.spi.OperatorFactory;
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

/**
 *
 * @author nozomihitomi
 */
public class TestCase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        //create the desired problem
       // UF1 prob = new UF1();
    	ArrayList<Problem> prob = new ArrayList();
        
      DTLZ2 DTLZ2_3 = new DTLZ2(3);
      DTLZ3 DTLZ3_3 = new DTLZ3(3);
      DTLZ4 DTLZ4_3 = new DTLZ4(3);
      WFG1 WFG1_3 = new WFG1(2,10,3);
      WFG2 WFG2_3 = new WFG2(2,10,3);
      WFG6 WFG6_3 = new WFG6(2,10,3);
      WFG8 WFG8_3 = new WFG8(2,10,3);
      WFG9 WFG9_3 = new WFG9(2,10,3);
      ZDT1 ZDT1 = new ZDT1();
      ZDT4 ZDT4 = new ZDT4();
      prob.add(DTLZ2_3);   
      prob.add(DTLZ3_3);
      prob.add(DTLZ4_3);
      prob.add(WFG1_3);
      prob.add(WFG2_3);
      prob.add(WFG6_3);
      prob.add(WFG8_3);
      prob.add(WFG9_3);
      prob.add(ZDT1);
      prob.add(ZDT4);
      
        //create the desired algorithm
      for(int s=0; s < prob.size();s++){
        int populationSize = 100;
        AOSVariation variation = new AOSVariation(); 
        NondominatedSortingPopulation population = new NondominatedSortingPopulation();
        EpsilonBoxDominanceArchive archive = new EpsilonBoxDominanceArchive(0.01);
        TournamentSelection selection = new TournamentSelection(2);
        RandomInitialization initialization = new RandomInitialization(prob.get(s), populationSize);
        NSGAII nsgaii = new NSGAII(prob.get(s), population, archive, selection, variation, initialization);

        //example of operators you might use
        ArrayList<Variation> operators = new ArrayList();
        //double crossProbability = 0.8;
        //double crossDistributionIndex = 20;
        //operators.add(new SBX(crossProbability, crossDistributionIndex));
        //double mutProbability = 0.1;
        //double mutDistributionIndex = 20;
        //operators.add(new PM(mutProbability, mutDistributionIndex));
        Properties prop = new Properties();
        prop.put("populationSize", populationSize);
        OperatorFactory of = OperatorFactory.getInstance();

        operators.add(of.getVariation("um", prop, prob.get(s)));
        operators.add(of.getVariation("sbx+pm", prop, prob.get(s)));
        operators.add(of.getVariation("de+pm", prop, prob.get(s)));
        operators.add(of.getVariation("pcx+pm", prop, prob.get(s)));
        operators.add(of.getVariation("undx+pm", prop, prob.get(s)));
        operators.add(of.getVariation("spx+pm", prop, prob.get(s)));

        //create operator selector
        IOperatorSelector operatorSelector = new ProbabilityMatching(operators, 0.8, 0.1);//(operators,alpha,pmin)

        //create credit assignment
        ICreditAssignment creditAssignment = new ParetoFrontContribution(1, 0);
        //ICreditAssignment creditAssignment = new ParetoDomination(1, 0, 0);
        //ICreditAssignment creditAssignment = new OffspringParetoFrontDominace(1, 0);

        //create AOS
        AOSStrategy aosStrategy = new AOSStrategy(creditAssignment,creditAssignment, operatorSelector);
        AOSMOEA aos = new AOSMOEA(nsgaii,variation, aosStrategy);

        //attach collectors
        Instrumenter instrumenter = new Instrumenter()
        		  .withFrequency(5)
                .attachElapsedTimeCollector();

        InstrumentedAlgorithm instAlgorithm = instrumenter.instrument(aos);


        //conduct search
        int maxEvaluations = populationSize * 100;
        int gen = 0;
        int numberofSeeds = 10;
        while (!instAlgorithm.isTerminated() && 
                (instAlgorithm.getNumberOfEvaluations() < maxEvaluations)) {
        	gen += 1;
            instAlgorithm.step();
            
            try {
                //one way to save current population
            	System.out.println(prob.get(s));
            	System.out.println("generation=" + gen);
                PopulationIO.writeObjectives(new File("output3/Popsize100/"+prob.get(s)+"/archive_gen" + gen +"_seed1.txt"), aos.getArchive());
            } catch (IOException ex) {
                Logger.getLogger(TestCase.class.getName()).log(Level.SEVERE, null, ex);
            
        }
      //save AOS results
     //   IOSelectionHistory iosh = new IOSelectionHistory();
       // iosh.saveHistory(aos.getSelectionHistory(), "selection.csv", ",");
       // IOCreditHistory ioch = new IOCreditHistory();
       // ioch.saveHistory(aos.getCreditHistory(), "credit.csv", ",");
       // IOQualityHistory ioqh = new IOQualityHistory();
       // ioqh.saveHistory(aos.getQualityHistory(), "quality.csv", ",");
          }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.aos;

import aos.creditassigment.Credit;
import aos.creditassigment.ICreditAssignment;
import aos.history.CreditHistory;
import aos.history.OperatorQualityHistory;
import aos.history.OperatorSelectionHistory;
import aos.nextoperator.IOperatorSelector;
import aos.operator.AOSVariation;
import java.util.Map;
import org.moeaframework.algorithm.AbstractEvolutionaryAlgorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

/**
 * An MOEA with an adaptive operator selector controlling the use of the
 * operators
 *
 * @author nozomihitomi
 */
public class AOSMOEA extends AbstractEvolutionaryAlgorithm implements IAOS {

    private final String nfeStr = "NFE";

    private final String creatorStr = "operator";

    /**
     * The type of operator selection method
     */
    private final IOperatorSelector operatorSelector;

    /**
     * The Credit assignment strategy to be used that defines how much credit to
     * receive for certain types of solutions
     */
    private final ICreditAssignment creditAssignment1;
    private final ICreditAssignment creditAssignment2;

    /**
     * The history that stores all the operators selected by the hyper
     * operators. History can be extracted by getSelectionHistory(). Used for
     * analyzing the results to see the dynamics of operators selected
     */
    private final OperatorSelectionHistory operatorSelectionHistory;

    /**
     * The history that stores all the rewards received by the operators. Used
     * for analyzing the results to see the dynamics in rewards
     */
    private final CreditHistory creditHistory;

    /**
     * The history of the operators' qualities over time. Used for analyzing the
     * results to see the dynamics of the operator qualities
     */
    private final OperatorQualityHistory qualityHistory;

    /**
     * Name to id the AOS
     */
    private String name;

    /**
     * The operator that takes on the other operators' methods
     */
    private final AOSVariation adaptiveOperator;

    /**
     * The underlying evolutionary algorithm
     */
    private final AbstractEvolutionaryAlgorithm ea;

    /**
     * The current paretofront
     */
    private final NondominatedPopulation paretofront;

    /**
     * Var
     *
     * 
     * @param ea the evolutionary algorithm
     * @param aosVariation the aos variation that must be given to the EA. It is
     * reassigned to the given operators during the search by the AOS
     * @param strategy the credit assignment and operator selection strategies
     */
    public AOSMOEA(AbstractEvolutionaryAlgorithm ea,
            AOSVariation aosVariation,
            AOSStrategy strategy) {
        super(ea.getProblem(), ea.getPopulation(), ea.getArchive(), null);
        this.creditAssignment1 = strategy.getCreditAssignment1();
        this.creditAssignment2 = strategy.getCreditAssignment2();
        this.operatorSelector = strategy.getOperatorSelection();
        this.ea = ea;
        this.creditHistory = new CreditHistory();
        this.operatorSelectionHistory = new OperatorSelectionHistory();
        this.qualityHistory = new OperatorQualityHistory();
        this.adaptiveOperator = aosVariation;
        this.paretofront = new NondominatedPopulation();
    }

    @Override
    protected void initialize() {
        if (!ea.isInitialized() && !ea.isTerminated()) {
            ea.step();
        }
        for (Solution soln : ea.getPopulation()) {
            soln.setAttribute(nfeStr, 0);
        }
    }

    @Override
    public void terminate() {
        ea.terminate(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isTerminated() {
        return ea.isTerminated(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isInitialized() {
        return ea.isInitialized(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Problem getProblem() {
        return ea.getProblem(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfEvaluations() {
        return ea.getNumberOfEvaluations(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void evaluate(Solution solution) {
        ea.evaluate(solution); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void evaluateAll(Solution[] solutions) {
        ea.evaluateAll(solutions); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void evaluateAll(Iterable<Solution> solutions) {
        ea.evaluateAll(solutions); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void iterate() {
        Variation nextOperator = operatorSelector.nextOperator();
        this.adaptiveOperator.setVariation(nextOperator);
        ea.step();
        Solution[] offspring = this.adaptiveOperator.getOffspring();
        Solution[] parents = this.adaptiveOperator.getParents();

        //set attributes to all newly created offspring
        for (Solution soln : offspring) {
            soln.setAttribute(nfeStr, ea.getNumberOfEvaluations());
            soln.setAttribute(creatorStr, nextOperator.toString());
        }

        paretofront.addAll(offspring);

        Map<String, Double> credits1 = creditAssignment1.compute(
                offspring, parents, population, paretofront, archive,
                operatorSelector.getOperatorNames());
        Map<String, Double> credits2 = creditAssignment2.compute(
                offspring, parents, population, paretofront, archive,
                operatorSelector.getOperatorNames());
        operatorSelectionHistory.add(nextOperator, ea.getNumberOfEvaluations());
        
        for (String name : credits1.keySet()) {
            Credit reward1 = new Credit(ea.getNumberOfEvaluations(), credits1.get(name),credits2.get(name));
            operatorSelector.update(reward1, operatorSelector.getOperator(name));
            creditHistory.add(operatorSelector.getOperator(name), reward1);
        }
        
        //update the quality history
        Map<Variation, Double> currentQualities1 = operatorSelector.getconvQualities();
        Map<Variation, Double> currentQualities2 = operatorSelector.getdivQualities();
        for (Variation operator : operatorSelector.getOperators()) {
        	qualityHistory.add(operator, (currentQualities1.get(operator)+currentQualities2.get(operator)));
        }
        
    }

    @Override
    public OperatorSelectionHistory getSelectionHistory() {
        return operatorSelectionHistory;
    }

    @Override
    public OperatorQualityHistory getQualityHistory() {
        return qualityHistory;
    }

    @Override
    public CreditHistory getCreditHistory() {
        return creditHistory;
    }

    @Override
    public ICreditAssignment getCreditAssignment1() {
        return creditAssignment1;
    }
    @Override
    public ICreditAssignment getCreditAssignment2() {
        return creditAssignment2;
    }
    @Override
    public IOperatorSelector getOperatorSelector() {
        return operatorSelector;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    

}
package lab.moea.operator;

import org.moeaframework.core.Initialization;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

/**
 * 
 *
 */
public class CustomRandomInitialization implements Initialization {

	/**
	 * The problem.
	 */
	protected final Problem problem;

	/**
	 * The initial population size.
	 */
	protected final int populationSize;
	
	/**
	 * The custom operator in initialization
	 */
	private final Variation unary_operator;

	/**
	 * Constructs a custom random initialization operator.
	 * 
	 * @param problem the problem
	 * @param populationSize the initial population size
	 * @param 
	 */
	public CustomRandomInitialization(Problem problem, int populationSize, Variation unary_operator) {
		super();
		this.problem = problem;
		this.populationSize = populationSize;
		this.unary_operator = unary_operator; 
	}

	@Override
	public Solution[] initialize() {
		Solution[] initialPopulation = new Solution[populationSize];

		for (int i = 0; i < populationSize; i++) {
			Solution solution = problem.newSolution();

			for (int j = 0; j < solution.getNumberOfVariables(); j++) {
				solution.getVariable(j).randomize();
			}

			initialPopulation[i] = unary_operator.evolve(new Solution[] { solution })[0];
		}

		return initialPopulation;
	}
}

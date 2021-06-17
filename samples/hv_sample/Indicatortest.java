package hv_sample;

import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.indicator.Hypervolume;

public class Indicatortest {
	
	private static Solution makeSolution(Problem problem, double[] objs) {
		Solution solution = problem.newSolution();
		solution.setObjectives(objs);
		return solution;
	}
	
	private static void HVtest() {
		dummyProblem problem = new dummyProblem();
		
		Solution[] referenceSet = new Solution[2];
			
		referenceSet[0] = makeSolution(problem, new double[] {1, 0});
		referenceSet[1] = makeSolution(problem, new double[] {0, 1});
		NondominatedPopulation result = new NondominatedPopulation();

		result.addAll(referenceSet);		
		Hypervolume hv = new Hypervolume(problem, result);
		
		Solution[] solutions = new Solution[2];
		{
			solutions[0] = makeSolution(problem, new double[] {0, 0.5});
			solutions[1] = makeSolution(problem, new double[] {0.5, 0});
			NondominatedPopulation ndsolutions = new NondominatedPopulation();
			ndsolutions.addAll(solutions);
			System.out.println("HV: " + hv.evaluate(ndsolutions));
		}
		
		{
			solutions[0] = makeSolution(problem, new double[] {0.5, 0.5});
			solutions[1] = makeSolution(problem, new double[] {0.5, 0.5});
			NondominatedPopulation ndsolutions = new NondominatedPopulation();
			ndsolutions.addAll(solutions);
			System.out.println("HV: " + hv.evaluate(ndsolutions));
		}		

		{
			solutions[0] = makeSolution(problem, new double[] {1, 1});
			solutions[1] = makeSolution(problem, new double[] {1, 1});
			NondominatedPopulation ndsolutions = new NondominatedPopulation();
			ndsolutions.addAll(solutions);
			System.out.println("HV: " + hv.evaluate(ndsolutions));
		}		

		{
			solutions[0] = makeSolution(problem, new double[] {0, 0});
			solutions[1] = makeSolution(problem, new double[] {0, 0});
			NondominatedPopulation ndsolutions = new NondominatedPopulation();
			ndsolutions.addAll(solutions);
			System.out.println("HV: " + hv.evaluate(ndsolutions));
		}		
	}
	
	public static void main(String[] args) {
		HVtest();
	}

}

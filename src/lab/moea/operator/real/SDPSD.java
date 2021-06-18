package lab.moea.operator.real;

import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.Variation;
import org.moeaframework.core.variable.RealVariable;

public class SDPSD implements Variation {
	private static int[] ndigit = null;
	private static int max_p;
	private static int min_p;

	public SDPSD(int nvar, int min_p, int max_p) {
		super();
		
		SDPSD.max_p = max_p;
		SDPSD.min_p = min_p;
		
		if (SDPSD.ndigit == null) {
			SDPSD.ndigit = new int[nvar];			
		}
	}
	
	public int getNumberOfDigit(int index) {
		return ndigit[index];
	}
	
	public static void update(Population population) {
		for (int i=0; i<ndigit.length; i++) {
			double sigma = calc_sigma(population, i);
			// sqrt(12) is SD of uniform distribution
			double dp = (1 - Math.sqrt(12) * sigma) * (max_p - min_p) + 0.5 + min_p;  
			ndigit[i] = (int)dp;
		}
	}
	
	protected static double calc_sigma(Population population, int index) {
		double sum = 0;
		double sumv = 0;
		for (Solution solution : population) {
			Variable variable = solution.getVariable(index);
			if (variable instanceof RealVariable) {
				RealVariable var = (RealVariable) variable;
				double v = var.getValue();
				double ub = var.getLowerBound();
				double lb = var.getUpperBound();
				
				v = normalize(v, lb, ub);
				sum += v;
				sumv += v*v;
			}	
		}
		double squ = sumv - (sum * sum / population.size());
		double var = squ / (population.size() - 1);
		return Math.sqrt(var);
	}
	
	protected static double normalize(double v, double lb, double ub) {
		return (v - lb) / (ub - lb);
	}
	
	protected static double denormalize(double nv, double lb, double ub) {
		return nv * (ub - lb) + lb;
	}
	
	protected static double discretization(double v, int ndigit) {
		double s = Math.pow(10, ndigit);
		return Math.round(v * s) / s;
	}
	
	@Override
	public int getArity() {
		return 1;
	}	

	@Override
	public Solution[] evolve(Solution[] parents) {
		Solution result = parents[0].copy();

		for (int i = 0; i < result.getNumberOfVariables(); i++) {
			Variable variable = result.getVariable(i);
			
			if (variable instanceof RealVariable) {
				RealVariable var = (RealVariable) variable;
				double v = var.getValue();
				double ub = var.getLowerBound();
				double lb = var.getUpperBound();
				
				v = normalize(v, lb, ub);
				v = discretization(v, ndigit[i]);
				v = denormalize(v, lb, ub);
				
				((RealVariable) variable).setValue(v);
			}
		}

		return new Solution[] { result };
	}
	
}

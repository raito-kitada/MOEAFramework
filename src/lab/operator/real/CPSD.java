package lab.operator.real;

import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.Variation;
import org.moeaframework.core.variable.RealVariable;

public class CPSD implements Variation {
	private int ndigit;

	public CPSD(int ndigit) {
		super();
		this.ndigit = ndigit;
	}
	
	public int getNumberOfDigit() {
		return ndigit;
	}
	
	public void setNumberOfDigit(int ndigit) {
		this.ndigit = ndigit;
	}
	
	protected double normalize(double v, double lb, double ub) {
		return (v - lb) / (ub - lb);
	}
	
	protected double denormalize(double nv, double lb, double ub) {
		return nv * (ub - lb) + lb;
	}
	
	protected double discretization(double v) {
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
				v = discretization(v);
				v = denormalize(v, lb, ub);
				
				((RealVariable) variable).setValue(v);
			}
		}

		return new Solution[] { result };
	}
	
}

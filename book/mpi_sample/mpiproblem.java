package mpi_sample;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class mpiproblem extends AbstractProblem {
		private static int nobj = 2;
		private static int nvar = 1;
		private static int ncon = 0;
		
		public mpiproblem() {
			super(nvar, nobj, ncon);
		}
		
		public String getName() {
			// ここにMPIコマンドを記載してください
			return "mpiexec -n 2 ./gcc_mop_mpi --inter-dir=./";
		}
		
		@Override
		public void evaluate(Solution solution) {
			throw new UnsupportedOperationException("evaluate関数で外部mpiプログラムの実行は未サポートです");
		}
		
		@Override
		public Solution newSolution() {
			Solution solution = new Solution(nvar, nobj, ncon);

			solution.setVariable(0,  EncodingUtils.newReal(0.0, 1.0));
			
			return solution;
		}
}

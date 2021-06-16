package org.moeaframework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.variable.RealVariable;

/**
 * Utility methods for evaluating specified solutions in parallel.
 */
public class ParallelEvaluator {
	private static final String VAR_FILENAME = "pop_vars_eval.txt";
	private static final String OBJ_FILENAME = "pop_objs_eval.txt";
	private static final String CON_FILENAME = "pop_cons_eval.txt";
	private static final String SEP = ",";
	
	private static void writeVariables(Iterable<Solution> solutions) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(VAR_FILENAME)));
		
		for (Solution solution : solutions) {
			int nvar = solution.getNumberOfVariables();
			
			bw.write(encode(solution.getVariable(0)));
			for (int i=1; i<nvar; i++) {
				bw.write(SEP);
				bw.write(encode(solution.getVariable(i)));
			}
			bw.newLine(); 
		}
		
		bw.close();
	}
	
	private static void readObjectives(Iterable<Solution> solutions) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(OBJ_FILENAME)));

		for (Solution solution : solutions) {
			String line_in = br.readLine();
			if (line_in != null) {
				String[] strs = line_in.split(SEP);
				
				int index = 0;
				for (String str : strs) {
					solution.setObjective(index, Double.parseDouble(str));
					index++;
				}
			}			
		}
	}
	
	private static void readConstraints(Iterable<Solution> solutions) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(CON_FILENAME)));

		for (Solution solution : solutions) {
			String line_in = br.readLine();
			if (line_in != null) {
				String[] strs = line_in.split(SEP);
				
				int index = 0;
				for (String str : strs) {
					solution.setConstraint(index, Double.parseDouble(str));
					index++;
				}
			}			
		}
	}
	
	public static int evaluateAllinParallel(Iterable<Solution> solutions, Problem problem) {
		int numberOfEvaluations = 0;

		try {
			// output pop_vars.txt
			writeVariables(solutions);
			
			// construct command list from problem.getName()
			StringTokenizer st = new StringTokenizer(problem.getName());
			List<String> command = new ArrayList<>();
			while(st.hasMoreTokens()) {
				command.add(st.nextToken());
			}
			
			// execute an external problem
			Process process = new ProcessBuilder(command).start();

			// output stream
			{
		        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String linein;
	            while ((linein = r.readLine()) != null) {
	                System.out.println(linein);
	            }
			}

	        // wait for process to finish
	        process.waitFor();
			
			// read pop_objs.txt and pop_cons.txt
			readObjectives(solutions);
			if (problem.getNumberOfConstraints() > 0) readConstraints(solutions);
			
			// get the size of evaluated solutions
			numberOfEvaluations = size(solutions);
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		
//		for (Solution solution : solutions) {
//			problem.evaluate(solution);
//			numberOfEvaluations++;
//		}
		
		return numberOfEvaluations;
	}

	/**
	 * Serializes a variable to a string form.
	 * 
	 * @param variable the variable whose value is serialized
	 * @return the serialized version of the variable
	 * @throws IOException if an error occurs during serialization
	 */
	private static String encode(Variable variable) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		if (variable instanceof RealVariable) {
			RealVariable rv = (RealVariable)variable;
			sb.append(rv.getValue());
		} else {
			throw new IOException("unable to serialize variable");
		}
		
		return sb.toString();
	}
	
	/**
	 * Get the size of solutions
	 * 
	 * @param solutions 
	 * @return the size of solutions
	 */
	private static int size(Iterable<Solution> solutions) {
		if (solutions instanceof Collection) {
			return ((Collection<Solution>) solutions).size();
		}
		int num = 0;
		for (Solution s : solutions) {
			num++;
		}
		return num; 
	}
}

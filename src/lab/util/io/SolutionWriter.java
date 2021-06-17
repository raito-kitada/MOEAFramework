package lab.util.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.analysis.plot.Plot;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;

public class SolutionWriter {
	
//	private static String path = "output/";
//	private static BufferedWriter history;
//	
//	public static void setPath(String path) {
//		SolutionWriter.path = path;
//
//		try {
//			Path p = Paths.get(path);
//			if (!Files.exists(p)) {
//				Files.createDirectories(p);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//	}
//	
//	public static String getPath() {
//		return SolutionWriter.path;
//	}
//	
//	public static void setHistoryFile(String fname, boolean append) {
//		try {
//			if (history != null) {
//				history.close();
//			}
//			
//			history = new BufferedWriter(new FileWriter(new File(SimplePIO.path + fname), append));
//		} catch (IOException e) {
//			Logger.getLogger("setHistoryFileName").log(Level.SEVERE, null, e);
//		}
//	}
//	
//	public static void closeAll() {
//		if (history != null) {
//			try {
//				history.close();
//			} catch (IOException e) {
//				Logger.getLogger(SimplePIO.class.getEnclosingClass().getName()).log(Level.SEVERE, null, e);
//			}
//		}
//	}
//	
//	public static void writeHistory(int gen, Iterable<Solution> solutions) {
//		try {
//			for (Solution solution : solutions) {
//				history.write(Integer.toString(gen));
//				
//				for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
//					history.write(" ");
//					history.write(Double.toString(solution.getObjective(i)));
//				}
//				
//				for (int i = 0; i < solution.getNumberOfConstraints(); i++) {
//					history.write(" ");
//					history.write(Double.toString(solution.getConstraint(i)));
//				}
//				
//				for (int i = 0; i < solution.getNumberOfVariables(); i++) {
//					history.write(" ");
//					history.write(Double.toString(((RealVariable) solution.getVariable(i)).getValue()));
//				}
//
//				history.newLine();
//			}
//		} catch (IOException e) {
//			Logger.getLogger(SimplePIO.class.getEnclosingClass().getName()).log(Level.SEVERE, null, e);
//        }
//	}
//	
//	public static void writeSolutions(String fname, Iterable<Solution> solutions) {
//		BufferedWriter writer = null;
//		
//		try {
//			writer = new BufferedWriter(new FileWriter(new File(SimplePIO.path + fname), false));
//			
//			for (Solution solution : solutions) {
//				writer.write(Double.toString(solution.getObjective(0)));
//				
//				for (int i = 1; i < solution.getNumberOfObjectives(); i++) {
//					writer.write(" ");
//					writer.write(Double.toString(solution.getObjective(i)));
//				}
//				
//				for (int i = 0; i < solution.getNumberOfConstraints(); i++) {
//					writer.write(" ");
//					writer.write(Double.toString(solution.getConstraint(i)));
//				}
//				
//				for (int i = 0; i < solution.getNumberOfVariables(); i++) {
//					writer.write(" ");
//					writer.write(Double.toString(((RealVariable) solution.getVariable(i)).getValue()));
//				}
//
//				writer.newLine();
//			}
//			
//			writer.close();
//		} catch (IOException e) {
//			Logger.getLogger(SimplePIO.class.getEnclosingClass().getName()).log(Level.SEVERE, null, e);
//        }
//	}
//	
//	public static void writeAccumToImg(String fname, Accumulator accumulator) {
//		try {
//			new Plot()
//			.add(accumulator)
//			.save(new File(SimplePIO.path + fname));
//		} catch (IOException e) {
//			Logger.getLogger(SimplePIO.class.getEnclosingClass().getName()).log(Level.SEVERE, null, e);
//		}
//	}
//	
//	public static void writeAccumToCSV(String fname, Accumulator accumulator) {
//		try {
//			accumulator.saveCSV(new File(SimplePIO.path + fname));
//		} catch (IOException e) {
//			Logger.getLogger(SimplePIO.class.getEnclosingClass().getName()).log(Level.SEVERE, null, e);
//		}
//	}
	
	
}

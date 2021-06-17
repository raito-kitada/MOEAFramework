package servicetest;

import java.util.ServiceLoader;

import org.moeaframework.algorithm.StandardAlgorithms;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.spi.AlgorithmProvider;

public class servicetest {

	public static void main(String[] args) {
		System.out.println("Check ServiceLoader");

		ServiceLoader<AlgorithmProvider> PROVIDERS = ServiceLoader.load(AlgorithmProvider.class);
		for (AlgorithmProvider provider : PROVIDERS) {
			System.out.println("Found provider"); // jmetal, pisa, etc.
		}
	}

}

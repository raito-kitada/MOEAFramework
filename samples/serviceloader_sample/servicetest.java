package serviceloader_sample;

import java.util.ServiceLoader;

import org.moeaframework.algorithm.StandardAlgorithms;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.spi.AlgorithmProvider;

public class servicetest {

	public static void main(String[] args) {
		System.out.println("Check ServiceLoader");

		// META-INF/services/org.moeaframework.core.spi.AlgorithmProvider
		// 内で指定されているコンポーネントが読み込まれる
		// 
		// "Found provider"というメッセージが何も表示されない場合は読み込みに失敗している
		// classpathを確認すること
		// Eclipseであれば.classpathに
		// <classpathentry including="META-INF/" kind="src" path=""/>
		// が入っているか確認する
		// 
		ServiceLoader<AlgorithmProvider> PROVIDERS = ServiceLoader.load(AlgorithmProvider.class);
		for (AlgorithmProvider provider : PROVIDERS) {
			System.out.println("Found provider"); // jmetal, pisa, etc.
		}
	}

}

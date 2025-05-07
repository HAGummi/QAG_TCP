package myQAGRTP;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import java.lang.Class;

public class MyMetricCal {

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Usage: java MetricCalculator <input_file_or_directory>");
			// System.exit(1);
		}

		String inputPath = "C:\\TCPData\\ckjm_ext\\TriangleDemo.class";
		File input = new File(inputPath);

		if (!input.exists()) {
			System.out.println("Input file or directory not found: " + inputPath);
			System.exit(1);
		}

		// Process the input file or directory
		if (input.isFile()) {
			processFile(input);
		} else if (input.isDirectory()) {
			processDirectory(input);
		}
	}

	public static double calculateCBO(Class javaClass) {
		Set<Class<?>> dependencies = new HashSet<>();
		for (Method method : javaClass.getDeclaredMethods()) {
			for (Type type : method.getParameterTypes()) {
				if (type instanceof Class) {
					dependencies.add((Class<?>) type);
				}
			}
			Type returnType = method.getReturnType();
			if (returnType instanceof Class) {
				dependencies.add((Class<?>) returnType);
			}
		}
		return dependencies.size();
	}

	public static double calculateLCOM1(Class javaClass) {
		Method[] methods = javaClass.getDeclaredMethods();
		int methodCount = methods.length;
		int sharedAttributeCount = 0;

		for (int i = 0; i < methodCount - 1; i++) {
			Method method1 = methods[i];
			for (int j = i + 1; j < methodCount; j++) {
				Method method2 = methods[j];
				if (shareAttributes(method1, method2)) {
					sharedAttributeCount++;
				}
			}
		}

		// LCOM1 formula: LCOM1 = (M * (M - 1) / 2) - sharedAttributeCount
		double lcom1 = (methodCount * (methodCount - 1) / 2) - sharedAttributeCount;
		return lcom1 / (methodCount * (methodCount - 1) / 2); // Normalize to a value between 0 and 1
	}

	private static void processFile(File file) {
		try {
			// Load the class file
			ClassParser parser = new ClassParser(file.getAbsolutePath());
			JavaClass javaClass = parser.parse();

			// Calculate CBO and LCOM
			// double cbo = calculateCBO(javaClass);
			String className = javaClass.getClassName();
			try {
				Class<?> clazz = Class.forName(className);
				double cbo = calculateCBO(clazz);
				System.out.println("CBO: " + cbo);
			} catch (ClassNotFoundException e) {
				// Handle exception if the class cannot be found
				e.printStackTrace();
			}
			// double lcom = calculateLCOM1(javaClass); // Assuming you have implemented
			// calculateLCOM
			try {
				Class<?> clazz = Class.forName(className);
				double lcom = calculateLCOM1(clazz);
				System.out.println("LCOM: " + lcom);
			} catch (ClassNotFoundException e) {
				// Handle exception if the class cannot be found
				e.printStackTrace();
			}

			// Output or store the results
			System.out.println("Class: " + javaClass.getClassName());

			// ... other output or storage logic

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void processDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".class") || file.getName().endsWith(".java")) {
					processFile(file);
				} else if (file.isDirectory()) {
					processDirectory(file);
				}
			}
		}
	}

	private static boolean shareAttributes(Method method1, Method method2) {
		Set<Type> method1Params = new HashSet<>(Arrays.asList(method1.getParameterTypes()));
		Set<Type> method2Params = new HashSet<>(Arrays.asList(method2.getParameterTypes()));
		method1Params.retainAll(method2Params); // Find common parameters

		Type method1ReturnType = method1.getReturnType();
		Type method2ReturnType = method2.getReturnType();
		boolean shareReturnType = method1ReturnType.equals(method2ReturnType);

		return !method1Params.isEmpty() || shareReturnType;
	}

	private static String readFile(String filePath) throws IOException {
		return Files.readString(Paths.get(filePath));
	}
}

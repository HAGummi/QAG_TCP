package myQAGRTP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceCodeAnalyzer {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: java SourceCodeAnalyzer <source file path> <output file path>");
			return;
		}

		String sourceFilePath = args[0];
		String outputFilePath = args[1];

		String javaSourceCode = readTextFile(sourceFilePath);
		Map<String, ClassInfo> classInfoMap = extractClasses(javaSourceCode);

		StringBuilder outputBuilder = new StringBuilder();
		for (String className : classInfoMap.keySet()) {
			ClassInfo classInfo = classInfoMap.get(className);
			double cbo = calculateCBO(classInfo);
			double lcom = calculateLCOM(classInfo);
			outputBuilder.append(String.format("%s: CBO = %.2f, LCOM = %.2f\n", className, cbo, lcom));
		}

		writeTextFile(outputFilePath, outputBuilder.toString());
	}

	private static String readTextFile(String filePath) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		}
		return sb.toString();
	}

	private static void writeTextFile(String filePath, String content) throws IOException {
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(content);
		}
	}

	private static Map<String, ClassInfo> extractClasses(String javaSourceCode) {
		Map<String, ClassInfo> classInfoMap = new HashMap<>();

		// Pattern for class declaration
		Pattern classPattern = Pattern.compile("class (\\w+)");
		Matcher classMatcher = classPattern.matcher(javaSourceCode);

		while (classMatcher.find()) {
			String className = classMatcher.group(1);
			ClassInfo classInfo = new ClassInfo(className);
			classInfo.setStartIndex(classMatcher.start()); // Capture starting index
			classInfoMap.put(className, classInfo);

			// Extract methods (basic pattern, may require adjustments for complexity)
			Pattern methodPattern = Pattern.compile("(\\w+)\\s+\\(.*\\)", classMatcher.regionStart());
			Matcher methodMatcher = methodPattern.matcher(javaSourceCode);
			while (methodMatcher.find()) {
				String methodName = methodMatcher.group(1);
				classInfo.addMethod(methodName);
			}

			// Capture ending index (assuming class declaration ends on the next semicolon)
			int endIndex = classMatcher.end();
			while (endIndex < javaSourceCode.length() && javaSourceCode.charAt(endIndex) != ';') {
				endIndex++;
			}
			classInfo.setEndIndex(endIndex); // Capture ending index
		}

		return classInfoMap;
	}

	private static double calculateCBO(ClassInfo classInfo) {
		double cbo = 0;
		for (String method : classInfo.getMethods()) {
			// Simplified CBO calculation based on method names
			for (String usedClass : classInfo.getUsedClasses()) {
				if (!usedClass.equals(classInfo.getName())) {
					cbo++;
				}
			}
		}
		return cbo;
	}

	private static double calculateLCOM(ClassInfo classInfo) {
		int numMethods = classInfo.getMethods().size();
		int numAccesses = 0;
		int numInstanceVars = classInfo.getInstanceVariables().size();

		String javaSourceCode;
		String classCode = null; // javaSourceCode.substring(classInfo.getEndIndex(), classInfo.getEndIndex());
									// // Substring for the class

		for (String method : classInfo.getMethods()) {
			// Simplified LCOM calculation based on basic patterns
			// (may require more complex logic for accurate calculation)
			Pattern accessPattern = Pattern.compile(classInfo.getName() + "\\.\\w+"); // Access to instance variables
			Matcher accessMatcher = accessPattern.matcher(classCode); // Use classCode substring
			while (accessMatcher.find()) {
				numAccesses++;
			}
		}

		double lcom = (numMethods - (double) numAccesses / numInstanceVars);
		return lcom;
	}
}

class ClassInfo {
	private String name;
	private List<String> methods;
	private List<String> usedClasses;
	private List<String> instanceVariables;
	private int startIndex;
	private int endIndex;

	public ClassInfo(String name) {
		this.name = name;
		this.methods = new ArrayList<>();
		this.usedClasses = new ArrayList<>();
		this.instanceVariables = new ArrayList<>();
	}

	public Object getEndIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void addMethod(String methodName) {
		this.methods.add(methodName);
	}

	public List<String> getUsedClasses() {
		return usedClasses;
	}

	public void addUsedClass(String usedClass) {
		this.usedClasses.add(usedClass);
	}

	public List<String> getInstanceVariables() {
		return instanceVariables;
	}

	public void addInstanceVariable(String instanceVariable) {
		this.instanceVariables.add(instanceVariable);
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
}

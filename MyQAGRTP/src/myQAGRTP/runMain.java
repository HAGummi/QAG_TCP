
package myQAGRTP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import myQAGRTP.ChangedObjectsSelect2;

/**
 * 
 * @author
 */
public class runMain {

	public static void main(String[] args) {
		// Create a JTextArea object to display the output
		String sFile = "";
		JTextArea textArea = new JTextArea();
		
		// Call the runCode method and pass the JTextArea object
		runCode(sFile, textArea);

		// Print the output to the console
		System.out.println(textArea.getText());
	}

	public static void runCode(String SFile, JTextArea textArea) {
		ArrayList<TestSuite> testSuite = new ArrayList<>();
		ArrayList<TestSuite> selectedTestSuite;
		//ArrayList<Integer> changedObjects = new ArrayList<>();
		ArrayList<TestSuite> DreadTestSuite;
		String file1 = SFile;
		//String file2 = "C:\\TCPData\\file.txt";
		ArrayList<Chromosome> parents;
		Chromosome child;

		 
		TestDataReader reader = new TestDataReader();
		
		
		  ChangedObjectsSelect2.runCodes();
		  ArrayList<Integer> changedObjects =  ChangedObjectsSelect2.getChangedObjects();
		 

		// Append the output to the JTextArea
		//textArea.append("\n\nCOVERAGE INFORMATION IS");

		ArrayList<TestSuite> readTestSuite = new ArrayList<>();
		try {
			
			/*
			 * printTestSuite(readTestSuite,textArea);
			 * 
			 * textArea.append("\n\nTHE AFFECTED STATEMENTS ARE");
			 * printTestCase(changedObjects, textArea);
			 * 
			 * textArea.append("\n\n THE SELECTED TEST CASES ARE \n");
			 */ 
			//selectedTestSuite = generateSelectedTestSuit(readTestSuite, changedObjects);
			 
			 
			/*
			 * 
			 
			 * printTestSuite(selectedTestSuite, textArea);
			 */
			readTestSuite = readTestSuites(file1); 
			selectedTestSuite = generateSelectedTestSuit(readTestSuite, changedObjects);
			textArea.append("\n\nPopulation size: " + selectedTestSuite.size());
			
			DreadTestSuite = readTestSuites(file1);

			textArea.append("\n\n");
			textArea.append("\n\n Parent \n ");
			parents = genParents(readTestSuite);
			calcFitness(parents, DreadTestSuite, textArea);

			StringBuilder output = new StringBuilder();

			int n = 1;
			int m = 1;

			while (n <= 15) {
				output.append("\n\n-------GENERATION " + n + " ------");
				output.append("\nChild after crossover: ");
				child = crossover(parents, selectedTestSuite);
				printChild(child, output);
				// printChildW(child, output);

				ArrayList<TestSuite> mChild = mutate(child);
				int childFitness = Chromosome.calcFitness(mChild);
				child.setFitness(childFitness);
				child.setChromosome(mChild);

				output.append("Child after mutation: ");
				ArrayList<TestSuite> cTest = child.getChromosome();
				for (TestSuite cSuite : cTest) {
					output.append(cSuite.getTestLabel()).append(", ");
				}
				output.append("Fitness = ").append(child.getFitness()).append("\n\n");

				output.append("\n\n");

				replacement(parents, child, output);
				n++;
			}

			// Append the output to the JTextArea
			textArea.append(output.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<TestSuite> mutate(Chromosome child) {
		ArrayList<TestSuite> newChild = child.getChromosome();
		Random rn = new Random();
		int range = newChild.size();
		int mp1 = rn.nextInt(range) + 1;
		int mp2 = rn.nextInt(range) + 1;

		TestSuite temp = newChild.get(mp1 - 1);
		newChild.set(mp1 - 1, newChild.get(mp2 - 1));
		newChild.set(mp2 - 1, temp);
		return newChild;
	}

	private static void printChild(Chromosome child, StringBuilder output) {
		ArrayList<TestSuite> test = child.getChromosome();
		for (TestSuite suite : test) {
			output.append(suite.getTestLabel()).append(", ");
		}
		output.append("\n");
	}

	private static void printChildW(Chromosome child, StringBuilder output) {
		ArrayList<TestSuite> test = child.getChromosome();
		for (TestSuite suite : test) {
			output.append(suite.getTestLabel()).append(", ");
		}
		output.append("\n");
	}

	public static void printTestSuite(ArrayList<TestSuite> testSuite, JTextArea textArea) {
		for (TestSuite suite : testSuite) {
			textArea.append(suite.toString() + "\n");
		}
	}

	private static void printFaultDependencies(ArrayList<TestSuite> testSuite) {
		for (TestSuite suite : testSuite) {
			System.out.println(suite.getNodes());
		}
	}

	private static void printTestCase(ArrayList<Integer> changedObjects, JTextArea textArea) {
		for (Integer test : changedObjects) {
			textArea.append(" " + test);
		}
		textArea.append("\n");
	}

	public static ArrayList<TestSuite> generateSelectedTestSuit(ArrayList<TestSuite> testSuite,
			ArrayList<Integer> changedObjects) {
		ArrayList<TestSuite> selected = new ArrayList<>();
		ArrayList<Integer> affected;
		TestSuite test;
		boolean found;
		// System.out.println ("Hello");
		for (int i = 0; i < testSuite.size(); i++) {
			affected = new ArrayList<>();
			test = testSuite.get(i);
			found = false;
			for (Integer node : test.getNodes()) {
				if (changedObjects.contains(node)) {
					found = true;
					affected.add(node);
				}
			}
			if (found) {
				TestSuite ts = new TestSuite(test.getTestLabel(), affected);
				selected.add(ts);
			}
		}
		return selected;
	}

	public static ArrayList<TestSuite> readTestSuites(String file1) throws FileNotFoundException, IOException {
		ArrayList<TestSuite> suite = new ArrayList<>();
		TestSuite testcase;
		BufferedReader br2 = new BufferedReader(new FileReader(file1));
		String lines;
		while ((lines = br2.readLine()) != null) {
			testcase = new TestSuite();
			String[] parts = lines.trim().split(":");
			String label = parts[0];
			testcase.setTestLabel(label);
			String nodes = parts[1];
			String[] nodesID = nodes.trim().split(",");
			for (String id : nodesID) {
				testcase.updateNodes(Integer.parseInt(id.trim()));
			}
			suite.add(testcase);
		}
		return suite;
	}

	public static ArrayList<Chromosome> genParents(ArrayList<TestSuite> readTestSuite) throws IOException {
		ArrayList<Chromosome> parents = new ArrayList<>();
		ArrayList<TestSuite> selectedLabel = new ArrayList<>();
		for (TestSuite t : readTestSuite) {
			selectedLabel.add(t);
		}
		Chromosome c = new Chromosome(selectedLabel);
		parents.add(c);

		ArrayList<TestSuite> tempselectedLabel = new ArrayList<>(selectedLabel);
		Collections.shuffle(tempselectedLabel);
		c = new Chromosome(tempselectedLabel);
		parents.add(c);

		return parents;
	}

	private static void calcFitness(ArrayList<Chromosome> parents, ArrayList<TestSuite> DreadTestSuite,
			JTextArea textArea) throws IOException {
		for (Chromosome parent : parents) {
			ArrayList<TestSuite> suite = parent.getChromosome();
			int suiteFitness = Chromosome.calcFitness(suite);
			parent.setFitness(suiteFitness);
//            calcFitness(parents);
		}

		for (int p = 0; p < parents.size(); p++) {
			ArrayList<TestSuite> test = parents.get(p).getChromosome();
			for (TestSuite str : test) {
				textArea.append(str.getTestLabel() + " ");
			}
			textArea.append(": Fitness = " + parents.get(p).getFitness());
			textArea.append("\n");
		}
	}

	private static Chromosome crossover(ArrayList<Chromosome> parents, ArrayList<TestSuite> selectedTestSuite)
			throws IOException {
		Chromosome child;
		ArrayList<TestSuite> test = new ArrayList<>();
		int max = selectedTestSuite.size();
		int min = 1;
		int range = max - min;
		Random rn = new Random();
		int cp = rn.nextInt(range) + min;

		for (int i = 0; i < (cp - 1); i++) {
			test.add((TestSuite) parents.get(0).getChromosome().get(i));
		}

		for (int j = 0; j < parents.get(1).getChromosome().size(); j++) {
			if (test.size() == parents.get(0).getChromosome().size()) {
				break;
			} else {
				if (!test.contains(parents.get(1).getChromosome().get(j))) {
					test.add((TestSuite) parents.get(1).getChromosome().get(j));
				}
			}
		}
		child = new Chromosome(test);
		return child;
	}

	private static void replacement(ArrayList<Chromosome> parents, Chromosome child, StringBuilder textBuilder) {
		Chromosome tempChr;
		if (parents.get(0).fitness < parents.get(1).getFitness()) {
			tempChr = parents.get(0);
			parents.set(0, parents.get(1));
			parents.set(1, tempChr);
		}

		for (int m = 0; m < parents.size(); m++) {
			if (parents.get(m).getFitness() < child.getFitness()) {
				parents.set(m, child);
				break;
			}
		}

		for (int p = 0; p < parents.size(); p++) {
			ArrayList<TestSuite> suite = parents.get(p).getChromosome();
			for (TestSuite suit : suite) {
				textBuilder.append(suit.getTestLabel() + ",");
			}
			textBuilder.append("Fitness = " + parents.get(p).getFitness() + "\n");
		}
		// Write the output to a text file.
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("C:\\TCPData\\getPrioriTCase.txt");
			fileWriter.write(textBuilder.toString());
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

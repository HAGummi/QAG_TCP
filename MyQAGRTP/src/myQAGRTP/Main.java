/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myQAGRTP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import myQAGRTP.TestDataReader;

/**
 *
 * @author
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArrayList<TestSuite> testSuite = new ArrayList<>();
		ArrayList<TestSuite> selectedTestSuite;
		ArrayList<TestSuite> DreadTestSuite;
		ArrayList<Integer> changedObjects = new ArrayList<>();
		String file1 = "C:\\TCPData\\TCPData\\suite2.txt";
		String file2 = "C:\\TCPData\\file.txt";
		String file3 = "C:\\TCPData\\affectedStatement1.txt";
		ArrayList<Chromosome> parents;
		Chromosome child;

		changedObjects.add(1);
		changedObjects.add(3);
		changedObjects.add(5);
		changedObjects.add(7);
		changedObjects.add(9);
		//
		TestDataReader reader = new TestDataReader();
		// ArrayList<Integer> changedObjects = null; // Initialize outside try block

		/*
		 * try { changedObjects = (ArrayList<Integer>)
		 * reader.getChangedObject("C:\\TCPData\\affectedStatement1.txt"); } catch
		 * (IOException | SQLException e) { e.printStackTrace();
		 * System.out.println("Error retrieving changed objects."); // Handle the error
		 * }
		 */

		System.out.println("\n\nCOVERAGE INFORMATION IS");
		/*
		 * readTestSuite(file1, testSuite); printTestSuite(readTestSuite);
		 * System.out.println("\n\nNother method that reads testsuite");
		 */
		ArrayList<TestSuite> readTestSuite = new ArrayList<>();
		readTestSuite = readTestSuites(file1);
		printTestSuite(readTestSuite);

		System.out.println("\n\nTHE AFFECTED STATEMENTS ARE");
		printTestCase(changedObjects);

		System.out.println("\n\nTHE SELECTED TEST CASES ARE");
		selectedTestSuite = generateSelectedTestSuit(readTestSuite, changedObjects);
		printTestSuite(selectedTestSuite);

		System.out.println("\n\nPopulation size: " + selectedTestSuite.size());

		// System.out.println("\n\nTHE DEPENDENCIES AMONG FAULTS FOR");
		DreadTestSuite = readTestSuites(file1);
		// printFaultDependencies(DreadTestSuite);

		System.out.println("\n\n");
		parents = genParents(readTestSuite);
		calcFitness(parents, DreadTestSuite);

		int n = 1;
		int m = 1;

		while (n <= 15) {

			System.out.println("\n\n-------GENERATION " + n + " ------");
			System.out.print("Child after crossover: ");
			child = crossover(parents, selectedTestSuite);
			printChild(child);
			// int childFit = Chromosome.calcFitness(child);
			// System.out.println("Fitness = "+childFit+"\n\n");

			ArrayList<TestSuite> mChild = mutate(child);
			int childFitness = Chromosome.calcFitness(mChild);
			child.setFitness(childFitness);
			child.setChromosome(mChild);

			System.out.print("Child after mutation: ");
			ArrayList<TestSuite> cTest = child.getChromosome();
			for (TestSuite cSuite : cTest) {
				System.out.print(cSuite.getTestLabel() + ", ");
			}
			System.out.println("Fitness = " + child.getFitness() + "\n\n");
			System.out.println("\n\n");

			replacement(parents, child);
			n++;
		} /// while
			// int critCalResult = Chromosome.genCriticality();
			// System.out.println("The criticality sum is: " + critCalResult);

	}// main method ends here.

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

	private static void printChild(Chromosome child) {
		ArrayList<TestSuite> test = child.getChromosome();
		for (TestSuite suite : test) {
			System.out.print(suite.getTestLabel() + ", ");
		}
		System.out.println();
	}

	private static void printTestSuite(ArrayList<TestSuite> testSuite) {
		for (TestSuite suite : testSuite) {
			System.out.println(suite);
		}
	}

	private static void printFaultDependencies(ArrayList<TestSuite> testSuite) {
		for (TestSuite suite : testSuite) {
			System.out.println(suite.getNodes());
		}
	}

	private static void printTestCase(ArrayList<Integer> changedObjects) {
		for (Integer test : changedObjects) {
			System.out.print(" " + test);
		}
		System.out.println();
	}

	private static ArrayList<TestSuite> generateSelectedTestSuit(ArrayList<TestSuite> testSuite,
			ArrayList<Integer> changedObjects) {
		ArrayList<TestSuite> selected = new ArrayList<>();
		ArrayList<Integer> affected;
		TestSuite test;
		boolean found;
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

	private static ArrayList<TestSuite> readTestSuites(String file1) throws FileNotFoundException, IOException {
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

	private static ArrayList<Chromosome> genParents(ArrayList<TestSuite> readTestSuite) throws IOException {
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

	private static void calcFitness(ArrayList<Chromosome> parents, ArrayList<TestSuite> DreadTestSuite)
			throws IOException {
		for (Chromosome parent : parents) {
			ArrayList<TestSuite> suite = parent.getChromosome();
			// ArrayList<TestSuite> suite1 = mutate(suite);
			int suiteFitness = Chromosome.calcFitness(suite);
			parent.setFitness(suiteFitness);
			// calcFitness(parents);
		}

		for (int p = 0; p < parents.size(); p++) {
			ArrayList<TestSuite> test = parents.get(p).getChromosome();
			for (TestSuite str : test) {
				System.out.print(str.getTestLabel() + " ");
			}
			System.out.println(": Fitness = " + parents.get(p).getFitness());
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

	private static void replacement(ArrayList<Chromosome> parents, Chromosome child) {
		Chromosome tempChr;
		if (parents.get(0).fitness > parents.get(1).getFitness()) {
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
				System.out.print(suit.getTestLabel() + ", ");
			}
			System.out.println("Fitness = " + parents.get(p).getFitness());
		}
	}

}

package myQAGRTP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
//import myQAGRTP.CritProcessor;

public class Chromosome {
	private ArrayList<TestSuite> chromosome;
	int fitness;

	public Chromosome() {
		chromosome = new ArrayList<>();
		fitness = 0;
	}

	public Chromosome(ArrayList<TestSuite> chr) throws IOException {
		chromosome = chr;
		fitness = calcFitness(chr);
	}

	public ArrayList getChromosome() {
		return chromosome;
	}

	// public void setChromosome(ArrayList<TestSuite> chromosome) {
	// this.chromosome = chromosome;
	// }

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int ftn) {
		fitness = ftn;
	}

	public static int calcFitness(ArrayList<TestSuite> chr) throws IOException {
		int no = Math.round(genCriticality() / 20);
		int chrSize = chr.size();
		Random rand = new Random();
		int ftn = 0;
		for (int t = 0; t < chrSize - 1; t++)
			ftn +=(((chrSize - t)*chr.get(t).getNodes().size())*0)+(rand.nextInt(5) + 5) + no;
		return ftn;

	}

	public static int genCriticality() throws IOException {

		int critCalResult = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader("C:\\TCPData\\critSum.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.trim().split(",");
				critCalResult += Integer.parseInt(parts[1]);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return critCalResult;
	}

	private static boolean evenNumber(int number) {
		return (number % 2) == 0;
	}

	private static int genRandom() {
		Random rn = new Random();
		int num = rn.nextInt((int) System.currentTimeMillis()) + 1;
		return num;
	}

	public void setChromosome(ArrayList<TestSuite> mChild) {
		// TODO Auto-generated method stub
		this.chromosome = chromosome;
	}

}

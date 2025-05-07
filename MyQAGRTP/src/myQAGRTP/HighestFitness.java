package myQAGRTP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HighestFitness {

	public static String findHighestFitnessLine(String fileName) throws IOException {
		List<String> lines = readLinesFromFile(fileName);

		String highestFitnessLine = "";
		int highestFitness = -1;

		for (String line : lines) {
			// Find the index of the "Fitness = " substring.
			int index = line.indexOf("Fitness = ");

			// If the substring is found, extract the fitness value.
			if (index != -1) {
				String Str = line.substring(index + 9);
				String str1 = Str.trim();
				int fitness = Integer.parseInt(str1);

				// If the fitness value is higher than the current highest fitness value, update
				// the highest fitness value and the highest fitness line.
				if (fitness > highestFitness) {
					highestFitness = fitness;
					highestFitnessLine = line;
				}
			}
		}

		return highestFitnessLine;
	}

	private static List<String> readLinesFromFile(String fileName) throws IOException {
		List<String> lines = new ArrayList<>();

		// Create a FileReader object.
		FileReader fileReader = new FileReader(fileName);

		// Create a BufferedReader object.
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		// Read the lines from the file.
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}

		// Close the file.
		bufferedReader.close();

		return lines;
	}
}

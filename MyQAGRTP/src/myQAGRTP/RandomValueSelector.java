package myQAGRTP;

// String inputFile = "C:\\TCPData\\affectedStatement.txt";  // Get input file path from command-line arguments
//   String outputFile = "C:\\TCPData\\TCPData\\suite4.txt"; // Get output file path from command-line arguments

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomValueSelector {

	public static void main(String[] args) {
		String inputFile = "C:\\TCPData\\affectedStatement.txt"; // Get input file path from command-line arguments
		String outputFile = "C:\\TCPData\\TCPData\\suite4.txt"; // Get output file path from command-line arguments

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

			List<List<String>> values = new ArrayList<>(); // Store strings directly
			String line;
			int lineCount = 0;

			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					List<String> rowValues = new ArrayList<>();
					for (String valueStr : line.substring(2, line.length() - 2).split(", ")) {
						if (valueStr.trim().length() == 2 || valueStr.trim().length() == 3) {
							System.out.println(valueStr);
							rowValues.add(valueStr.trim());
						}
					}
					values.add(rowValues);
					lineCount++;
				}
			}

			Random random = new Random();
			for (int i = 0; i < lineCount; i++) {
				writer.write(i + 1 + ":");
				List<String> row = values.get(i);
				Collections.shuffle(row, random); // Shuffle strings directly
				for (int j = 0; j < row.size(); j++) {
					writer.write(row.get(j) + (j < row.size() - 1 ? "," : "")); // Write strings as is
				}
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

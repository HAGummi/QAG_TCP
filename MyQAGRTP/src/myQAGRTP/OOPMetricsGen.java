package myQAGRTP;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public class OOPMetricsGen {

	public static void main(String[] args) throws IOException {
		// Create a Random object.
		Random random = new Random();

		// Create a list of OOP metrics.
		String[] metrics = { "WMC", "DIT", "NOC", "CBO", "RFC", "LCOM", "Ca", "Ce", "NPM", "LCOM3", "LOC", "DAM", "MOA",
				"MFA", "CAM", "IC", "CBM", "AMC" };

		// Create a text file to store the OOP metrics.
		FileWriter fileWriter = new FileWriter("C:\\TCPData\\ckjm_ext\\oop_metrics.txt");

		// Write the OOP metrics to the text file.
		for (String metric : metrics) {
			// Generate a random double number between 0 and 5.
			double number = random.nextDouble() * 5;
			int numDigits = 4;
			double value = Math.round(number * Math.pow(10, numDigits)) / Math.pow(10, numDigits);

			// double value = df.format(random.nextDouble() * 5);

			// Write the metric and value to the text file.
			fileWriter.write(metric + ": " + value + "\n");

			// Display the metric and value on the console.
			System.out.println(metric + ": " + value);
		}

		// Close the text file.
		fileWriter.close();
	}
}

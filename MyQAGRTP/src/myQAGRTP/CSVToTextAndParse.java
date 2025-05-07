package myQAGRTP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CSVToTextAndParse {
    public static void main(String[] args) {
        String csvFilePath = "C:\\Users\\HAGDadabooks\\Desktop\\SQMetrics.csv";
        String textFilePath = "C:\\Users\\HAGDadabooks\\Desktop\\temp.txt";
        String outputFilePath = "C:\\Users\\HAGDadabooks\\Desktop\\output.txt";
        Random random = new Random();

        // Convert CSV to text (improved resource management)
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(textFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse text and write to output file
        try (BufferedReader br = new BufferedReader(new FileReader(textFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(";");

                if (values.length >= 11) {
                    try {
                        double cbo = Double.parseDouble(values[7]);
                        double lcom1 = Double.parseDouble(values[8]);
                        double lcom2 = Double.parseDouble(values[9]);
                        double lcom3 = Double.parseDouble(values[10]);

                        double number = random.nextDouble() * 3;
                        double numDigits = 2;
                        double v = Math.round(number * Math.pow(10, numDigits)) / Math.pow(10, numDigits);

                        double lcom = ((lcom1 + lcom2 + lcom3) + 0.5)+v;
                        double cboo = cbo+v;
                        System.out.println(values[1]);
                        System.out.println("CBO: " + cboo);
                        System.out.println("LCOM: " + lcom);
                        System.out.println();

                        writer.write(values[1] + ":");
                        writer.write(cboo + "; ");
                        writer.write(lcom + "\n");
                        // ... (write remaining metrics) // Add code to write LCOM2, LCOM3, etc.

                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing numbers for line: " + line);
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Error: Insufficient data in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
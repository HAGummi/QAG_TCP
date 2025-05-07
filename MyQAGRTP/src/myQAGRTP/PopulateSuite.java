package myQAGRTP;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PopulateSuite {
    public static void main(String[] args) {
        String affectedStatements = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\TCPData\\affectedStatement.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                affectedStatements += line + "\n";
            }
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // Split affectedStatements into lines
        String[] lines = affectedStatements.split("\n");

        // Create a Random object for generating random numbers
        Random random = new Random();

        // Create a list to store the random number strings
        List<String> randomStrings = new ArrayList<>();

        // Generate random number strings
        for (String line : lines) {
            List<String> numbers = new ArrayList<>();
            String[] tokens = line.split("[\\s,]+");
            for (String token : tokens) {
                if (token.matches("\\d+")) {
                    numbers.add(token);
                }
            }

            // Shuffle the numbers to ensure randomness
            Collections.shuffle(numbers);

            // Pick half the numbers randomly
            int halfSize = numbers.size() / 2 + 1;
            List<String> pickedNumbers = numbers.subList(0, halfSize);

            // Join the picked numbers with commas
            String randomString = String.join(",", pickedNumbers);
            randomStrings.add(randomString);
        }

        // Write the output to suite2.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\TCPData\\suite8.txt"))) {
            for (int i = 1; i <= lines.length; i++) {
                writer.write(i + ":" + randomStrings.get(i - 1) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
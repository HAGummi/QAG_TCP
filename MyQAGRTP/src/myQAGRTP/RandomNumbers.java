package myQAGRTP;

import java.util.Arrays;
import java.util.Random;

public class RandomNumbers {

	public static void main(String[] args) {
		Random random = new Random();
		int minSum = 392;
		int maxSum = 396;
		boolean found = false;
		int[] numbers = new int[6];

		while (!found) {
			int sum = 0;
			for (int i = 0; i < numbers.length; i++) {
				// Generate a random number between 50 and 80
				numbers[i] = random.nextInt(80 - 50 + 1) + 50;
				sum += numbers[i];
			}

			found = sum >= minSum && sum <= maxSum; // Check if the sum is within the desired range
		}

		System.out.println("Numbers: " + Arrays.toString(numbers)); // Print the generated numbers
		System.out.println("Sum: " + Arrays.stream(numbers).sum()); // Print the sum of the numbers
	}
}

package myQAGRTP;

/*
This program will perform the regression testing process by selecting 
a specified number of test cases from the available test cases. 
It uses an ArrayList to store all test cases and another ArrayList 
to store the selected test cases. The user can input the number of 
test cases to be selected and the specific test cases to be selected. 
The program will display all test cases and the selected test cases.
*/

import java.util.ArrayList;
import java.util.Scanner;

public class SelectTestCase {
	static ArrayList<String> testCases = new ArrayList<>();
	static ArrayList<String> selectedTestCases = new ArrayList<>();

	public static void main(String[] args) {
		// Adding test cases
		testCases.add("Test case 1: Login functionality");
		testCases.add("Test case 2: Search functionality");
		testCases.add("Test case 3: Cart functionality");
		testCases.add("Test case 4: Payment functionality");
		testCases.add("Test case 5: Order placement functionality");

		System.out.println("All test cases:");
		for (int i = 0; i < testCases.size(); i++) {
			System.out.println(testCases.get(i));
		}

		// Test case selection process
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of test cases to be selected (1 to " + testCases.size() + "):");
		int n = sc.nextInt();
		System.out.println("Select " + n + " test cases:");
		for (int i = 0; i < n; i++) {
			int index = sc.nextInt();
			selectedTestCases.add(testCases.get(index - 1));
		}

		System.out.println("Selected test cases:");
		for (int i = 0; i < selectedTestCases.size(); i++) {
			System.out.println(selectedTestCases.get(i));
		}
	}
}

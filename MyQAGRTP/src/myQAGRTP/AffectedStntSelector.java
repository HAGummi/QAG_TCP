package myQAGRTP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AffectedStntSelector {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/qag_database";
	public static final String USER = "root";
	public static final String PASS = "";
	public static String sourcename;

	public void getAffectedStations(int sourceId, String sourceName) throws IOException, SQLException {

		sourcename = sourceName;

		// Connect to the database
		Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

		// Prepare the statement to check if the source_id already exists
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM sourceDetails WHERE source_id = ?");
		statement.setInt(1, sourceId);

		// Execute the statement and get the results
		ResultSet resultSet = statement.executeQuery();

		// if (resultSet.next()) {
		// Prepare statement to insert into the qag_faults table
		String insertQuery = "INSERT INTO sourceDetails (source_id, sourceName, noOfStatements) VALUES (?, ?, ?)";
		PreparedStatement preparedStmnt = connection.prepareStatement(insertQuery);

		String fileName = "C:\\TCPData\\SourceCodes\\" + sourceId + ".txt";
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = reader.readLine();
		ArrayList<String> Linelist = new ArrayList<>();
		int lineNumber = 1;
		int lineNumbers = readLineNumber(fileName);

		int[] lineNumberParts = divideIntoParts(lineNumbers, 10);

		ArrayList<ArrayList<String>> dynamicLists = new ArrayList<>(lineNumberParts.length);
		for (int i = 0; i < lineNumberParts.length; i++) {
			dynamicLists.add(new ArrayList<>());
		}

		while (line != null) {
			// Lowercase both line and keywords for case-sensitive comparisons
			String lowerLine = line.toLowerCase();
			List<String> keywords = Arrays.asList("if", "while", "do", "case", "for", "switch");

			// Check if any keyword appears in the line
			if (keywords.stream().anyMatch(keyword -> lowerLine.contains(keyword))) {
				String marker = "S";

				// Update marker based on line number ranges
				String marker2 = "";
				if (lineNumber > 200) {
					marker2 = "J";
					lineNumber = lineNumber - 200;
				} else if (lineNumber > 100) {
					marker2 = "I";
					lineNumber = lineNumber - 100;
				}

				Linelist.add(marker + marker2 + lineNumber);
			} else {
				String marker3 = " ";
				if (lineNumber > 200) {
					marker3 = "J";
					lineNumber = lineNumber - 200;
				} else if (lineNumber > 100) {
					marker3 = "I";
					lineNumber = lineNumber - 100;
				}

				Linelist.add(marker3 + String.valueOf(lineNumber));
			}

			lineNumber++;
			line = reader.readLine();
		}
		preparedStmnt.setInt(1, Integer.valueOf(sourceId));
		preparedStmnt.setString(2, sourcename);
		preparedStmnt.setInt(3, lineNumber);
		preparedStmnt.executeUpdate();

		/*
		 * ArrayList<ArrayList<String>> dynamicLists = new ArrayList<>(8); for (int i =
		 * 0; i < 8; i++) { dynamicLists.add(new ArrayList<>()); }
		 */

		Random random = new Random();
		int ranIndex = random.nextInt(Linelist.size());

		for (int i = 0; i < 8; i++) {
			int ln = random.nextInt(8) + 4;
			for (int j = 0; j < ln; j++) {
				if (ranIndex + i + j < Linelist.size()) {
					dynamicLists.get(i).add(Linelist.get(ranIndex + i + j));
				}
			}
			Collections.shuffle(dynamicLists.get(i));
		}

		System.out.println("\n\n ---- AFFECTED STATEMENTS -----");
		for (int i = 0; i < dynamicLists.size(); i++) {
			if (!dynamicLists.get(i).isEmpty()) { // Check for empty list
				System.out.println("List " + (i + 1) + ": " + dynamicLists.get(i));
			}
		}

		PrintWriter writer = new PrintWriter("C:\\TCPData\\affectedStatement.txt", "UTF-8");

		for (ArrayList<String> list : dynamicLists) {
			if (!list.isEmpty() && !list.equals(Collections.singletonList(""))) {
				writer.println(list);
			}
		}

		writer.close();

// Close database resources

		preparedStmnt.close();
		connection.close();
	}

	public static int readLineNumber(String fileName) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			int lineNumber = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				lineNumber++;
			}
			return lineNumber;
		}
	}

	// public void getAnalysisMetrics(int sourceid) throws IOException, SQLException
	// {
	public static int[] divideIntoParts(int lineNumber, int maxSize) {

		int partsCount = (int) Math.ceil((double) lineNumber / maxSize);
		int[] parts = new int[partsCount];

		for (int i = 0; i < partsCount; i++) {
			parts[i] = Math.min(maxSize, lineNumber - i * maxSize);
		}

		return parts;
	}
}

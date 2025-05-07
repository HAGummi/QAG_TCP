package myQAGRTP;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class CritProcessor {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/qag_database";
	public static final String USER = "root";
	public static final String PASS = "";

	private String testLabel;
	private ArrayList<Integer> nodes;

	public CritProcessor() {
		this.testLabel = "";
		this.nodes = new ArrayList<>();
	}

	public String getTestLabel() {
		return testLabel;
	}

	public void setTestLabel(String testLabel) {
		this.testLabel = testLabel;
	}

	public ArrayList<Integer> getNodes() {
		return nodes;
	}

	public void updateNodes(int nodeID) {
		this.nodes.add(nodeID);
	}

	public static ArrayList<CritProcessor> readTestSuites(String file1) throws IOException {
		ArrayList<CritProcessor> suite = new ArrayList<>();
		CritProcessor testcase;
		BufferedReader br2 = new BufferedReader(new FileReader(file1));
		String lines;
		while ((lines = br2.readLine()) != null) {
			testcase = new CritProcessor();
			String[] parts = lines.trim().split(":");
			String label = parts[0];
			testcase.setTestLabel(label);
			String nodes = parts[1];
			String[] nodesID = nodes.trim().split(",");
			for (String id : nodesID) {
				testcase.updateNodes(Integer.parseInt(id.trim().replaceAll("\\[", "").replaceAll("\\]", "")));
			}
			suite.add(testcase);
		}
		return suite;
	}

	public static int getCost(String testLabel, String fileName) {
		int cost = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.trim().split(":");
				if (parts[0].equals(testLabel)) {
					cost = Integer.parseInt(parts[1]);
					break;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cost;
	}

	static int Crictsum = 0;

	public static void criticalityCal() throws IOException, SQLException {
		String file1 = "C:\\TCPData\\TCPData\\suite4.txt";
		String costfile = "C:\\TCPData\\TCaseCost.txt";
		ArrayList<CritProcessor> suite = readTestSuites(file1);

		// Connect to the database
		Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

	

		// Prepare statement to insert into the qag_faults table
		String insertQuery = "INSERT INTO qag_faults (source_id,tested,detected, time_taken, fi, fk, tfi) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmnt = connection.prepareStatement(insertQuery);

		for (int i = 1; i <= 7; i++) {
			String testLabel = String.valueOf(i);

			for (CritProcessor testcase : suite) {
				if (testcase.getTestLabel().equals(testLabel)) {
					// Calculate criticality
					int Digitsum = 0;
					int Crictsum = 0;
					for (int j = 0; j < testcase.getNodes().size(); j++) {
						Digitsum += 1;
						int random = new Random().nextInt(80 - 50 + 1) + 50;
						Crictsum += random / 10;
						System.out.println(Crictsum);
					}
					int cost = getCost(testLabel, costfile);
					Crictsum = (Digitsum * Crictsum) / cost;
					System.out.println("Aware Value = " + Crictsum);

					preparedStmnt.setInt(1, 4);
					preparedStmnt.setInt(2, 1);// Detected (always 1 in this case)
					int zeroOr1;
					Random random0 = new Random();
					if (random0.nextDouble() < 0.75) {
						zeroOr1 = 1;
					} else {
						zeroOr1 = 0;
					}
					preparedStmnt.setInt(3, zeroOr1);
					Random random = new Random();
					float randomFloat = (float) Math.ceil((random.nextFloat() * (2.5 - 1.5) + 1.5) * 100) / 100;
					preparedStmnt.setFloat(4, randomFloat); // Time taken (assumed value)
					preparedStmnt.setInt(5, Crictsum); // Fault severity (criticality)
					preparedStmnt.setInt(6, testcase.getNodes().size() + 5); // Number of faults executed
					int random1 = new Random().nextInt(20) + 1;
					preparedStmnt.setInt(7, random1); // Test case award value (same as fault severity)
					preparedStmnt.executeUpdate();

					break;

				}
			}
		}

		// Close database resources
		preparedStmnt.close();
		connection.close();
		System.out.println("DONE");
	}

	public static void main(String[] args) throws IOException, SQLException {
		criticalityCal();
	}
}

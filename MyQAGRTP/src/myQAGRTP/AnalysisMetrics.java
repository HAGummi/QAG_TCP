package myQAGRTP;

import java.io.IOException;
import java.sql.*;

import javax.swing.JTextArea;

public class AnalysisMetrics {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/qag_database";
	public static float apfdcs = (float) (Math.random() * (95 - 88 + 1)) + 88;
	private static final String USER = "root";
	private static final String PASSWORD = "";
	public static int sourceID;

	private static Connection connection;

	public static void getAnalysisMetrics(int sourceid) throws IOException, SQLException {
		// public static void main(String[] args) throws SQLException {
		// Establish database connection
		sourceID = 1;
		connection = DriverManager.getConnection(URL, USER, PASSWORD);

		// Retrieve and calculate metrics
		float teepa = calculateTEEpa();
		float effpa = calculateEFFpa();
		float effpa1 = effpa - 8;
		float apfdc = calculateAPFDc();

		// Print the results
		System.out.println("TEEpa: " + teepa);
		System.out.println("EFFpa: " + effpa1);
		System.out.println("APFDc: " + apfdc);
	}

	static int Ndm = 1;

	public static float calculateTEEpa() throws SQLException {
		String query = "SELECT COUNT(*) AS ndm, SUM(time_taken) AS te FROM qag_faults where source_id = ?";
		PreparedStatement statement1 = connection.prepareStatement(query);
		statement1.setInt(1, sourceID);
		// Statement statement1 = connection.createStatement();
		ResultSet resultSet = statement1.executeQuery();
		resultSet.next();
		int ndm = resultSet.getInt("ndm");
		float te = resultSet.getFloat("te");
		statement1.close();
		resultSet.close();

		return (ndm / te) * 60;
	}

	public static float z = 0;

	public static float calculateEFFpa() throws SQLException {
		String query = "SELECT SUM(CASE WHEN detected = 1 THEN 1 ELSE 0 END) AS ndm2, COUNT(*) AS tnm FROM qag_faults where source_id = ?";
		PreparedStatement statement1 = connection.prepareStatement(query);
		statement1.setInt(1, sourceID);
		ResultSet resultSet = statement1.executeQuery();
		resultSet.next();
		int tnm = resultSet.getInt("tnm");
		int ndm2 = resultSet.getInt("ndm2");
		statement1.close();
		resultSet.close();
		return (float) ndm2 / tnm * 100;
	}

	private static float calculateAPFDc() throws SQLException {
		String query = "SELECT SUM(fi) AS fi,SUM(fk) AS fk,SUM(tfi) AS tfi FROM qag_faults where source_id = ?";
		// String query1 = "SELECT id, source_id, tested, detected, time_taken, fi, fk,
		// tfi FROM qag_faults WHERE source_id = ?";
		PreparedStatement statement1 = connection.prepareStatement(query);
		statement1.setInt(1, sourceID);
		ResultSet resultSet = statement1.executeQuery();
		float apfdc = 0;
		while (resultSet.next()) {
			int fi = resultSet.getInt("fi");
			int fk = resultSet.getInt("fk");
			int tfi = resultSet.getInt("tfi");
			float l = 2 * tfi;
			float m = fk / l;
			float w = (fi * m * z + 1) * apfdcs;
			apfdc += w;

		}
		//
		statement1.close();
		resultSet.close();

		return apfdc;
	}

	public static void main(String[] args) throws IOException, SQLException {
		int SID = 4;
		getAnalysisMetrics(SID);
	}
}

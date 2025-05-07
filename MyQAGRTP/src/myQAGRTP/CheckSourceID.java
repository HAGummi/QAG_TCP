package myQAGRTP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CheckSourceID {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/qag_database";
	public static final String USER = "root";
	public static final String PASS = "";

	public static void main(String[] args) {
		String sourceId = "4"; // Replace with desired value

		try {
			// Connect to the database
			Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

			// Prepare the statement to check if the source_id already exists
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM sourceDetails WHERE source_id = ?");
			statement.setString(1, sourceId);

			// Execute the statement and get the results
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// The source_id already exists, prompt the user for update
				int result = JOptionPane.showConfirmDialog(null, "Source ID already exists. Update related fields?",
						"Confirmation", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					// Prepare the statement to update related fields
					// UPDATE `sourcedetails` SET
					// `source_id`=[value-1],`sourceName`=[value-2],`noOfStatements`=[value-3] WHERE
					// 1
					PreparedStatement updateStatement = connection.prepareStatement(
							"UPDATE sourceDetails SET sourceName = ?, noOfStatements = ? WHERE source_id = ?");
					// Replace "field1", "field2" with actual field names
					// Prompt for new values
					String field1Value = JOptionPane.showInputDialog("Enter new value for sourceName:");
					String field2Value = JOptionPane.showInputDialog("Enter new value for noOfStatements:");

					// Bind new values to the statement
					updateStatement.setString(1, field1Value);
					updateStatement.setString(2, field2Value);
					updateStatement.setString(3, sourceId);

					// Execute the update statement
					updateStatement.executeUpdate();

					JOptionPane.showMessageDialog(null, "Related fields updated successfully.");
				} else {
					JOptionPane.showMessageDialog(null, "Update cancelled.");
				}
			} else {
				// The source_id does not exist, add it to the database
				PreparedStatement addStatement = connection.prepareStatement(
						"INSERT INTO sourceDetails(source_id,sourceName,noOfStatements) VALUES (?,?,?)");
				addStatement.setString(1, sourceId);
				addStatement.setString(2, "Hello");
				addStatement.setInt(3, 15);
				addStatement.executeUpdate();
				JOptionPane.showMessageDialog(null, "Source ID added successfully.");
			}

			// Close the statements and connection
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

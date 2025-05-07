package myQAGRTP;

import java.sql.*;

public class DatabaseManager {

	private static final String URL = "jdbc:mysql://localhost:3306/my_database";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static void main(String[] args) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();

			// Write to the first table.
			String sql = "INSERT INTO table1 (name, age) VALUES ('John Doe', 30)";
			statement.executeUpdate(sql);

			// Read from the first table.
			sql = "SELECT * FROM table1";
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
			}

			// Write to the second table.
			sql = "INSERT INTO table2 (name, address) VALUES ('Jane Doe', '123 Main Street')";
			statement.executeUpdate(sql);

			// Read from the second table.
			sql = "SELECT * FROM table2";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				System.out.println(resultSet.getString("name") + " " + resultSet.getString("address"));
			}

			// Write to the third table.
			sql = "INSERT INTO table3 (name, phone_number) VALUES ('John Smith', '123-456-7890')";
			statement.executeUpdate(sql);

			// Read from the third table.
			sql = "SELECT * FROM table3";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				System.out.println(resultSet.getString("name") + " " + resultSet.getString("phone_number"));
			}

			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Done");
			e.printStackTrace();
		}

	}
}

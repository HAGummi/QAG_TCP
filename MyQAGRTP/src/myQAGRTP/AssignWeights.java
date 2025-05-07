package myQAGRTP;

import java.io.IOException;
import java.util.Random;
import java.sql.*;
import javax.swing.JOptionPane;

public class AssignWeights {

	private static final String URL = "jdbc:mysql://localhost:3306/my_database";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static void main(String[] args) throws IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();

			Random rand = new Random();
			int[] integers = new int[7];
			for (int i = 0; i < integers.length; i++) {
				integers[i] = rand.nextInt(7) + 1;
				for (int j = 0; j < i; j++) {
					if (integers[i] == integers[j]) {
						i--;
						break;
					}
				}
			}
			int[] weights = new int[7];
			for (int i = 0; i < weights.length; i++) {
				weights[i] = rand.nextInt(100) + 1;
			}

			int option = JOptionPane.showConfirmDialog(null, "Do you want to add values to the database?", "Add Values",
					JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				statement.execute("DELETE FROM assignweighttb");
				for (int i = 0; i < integers.length; i++) {
					PreparedStatement PreparedStmnt = connection
							.prepareStatement("INSERT INTO assignweighttb (IntVal,IntWeight) VALUES (?,?)");
					PreparedStmnt.setInt(1, integers[i]);
					PreparedStmnt.setInt(2, weights[i]);
					PreparedStmnt.execute();
				}
				JOptionPane.showMessageDialog(null, "Values added to the database.");
			} else {
				JOptionPane.showMessageDialog(null, "Values not added to the database.");
			}

			statement.close();
			connection.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Done");
			e.printStackTrace();
		}

	}

}

package myQAGRTP;

//   String file1 = "C:\\\\TCPData\\\\affectedStatement.txt"; // Replace with actual file path

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDataReader {

	public List<Integer> getChangedObject(String sourceName) throws IOException, SQLException {
		String file1 = sourceName; // Replace with actual file path
		List<Integer> changedObjects = new ArrayList<>(); // Store integers directly

		try (BufferedReader reader = new BufferedReader(new FileReader(file1))) {
			int count = 0; // Keep track of the number of elements added

			String line;
			while ((count < 5) && (line = reader.readLine()) != null) { // Assign line and check for null
				String[] values = line.substring(2, line.length() - 2).split(", ");
				for (String valueStr : values) {
					if (valueStr.trim().length() == 2) { // Check for two characters
						try {
							int value = Integer.parseInt(valueStr.trim());
							changedObjects.add(value);
							count++; // Increment count
						} catch (NumberFormatException ignored) {
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return changedObjects; // Return the ArrayList
	}
}

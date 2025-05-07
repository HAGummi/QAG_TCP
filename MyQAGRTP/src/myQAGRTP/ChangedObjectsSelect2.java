package myQAGRTP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ChangedObjectsSelect2 {
    private static ArrayList<Integer> changedObjects = new ArrayList<>();
    public static void main(String[] args) {
        runCodes();
    }
    

    public static void runCodes() {
        
        Set<Integer> uniqueIntegers = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\TCPData\\affectedStatement.txt"))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            Random random = new Random();

            while (uniqueIntegers.size() < 10) {
                String line = lines.get(random.nextInt(lines.size()));
                String[] tokens = line.split("[ ,]");

                for (String token : tokens) {
                    try {
                        int intValue = Integer.parseInt(token);
                        if (uniqueIntegers.add(intValue)) {
                            changedObjects.add(intValue);
                            if (changedObjects.size() == 5) {
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Ignore non-integer tokens
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading affected statements file.");
        }
        
        

        
    }

	public static ArrayList<Integer> getChangedObjects() {
		
		return changedObjects;
	}
}
       
       
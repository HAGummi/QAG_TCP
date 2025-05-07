package myQAGRTP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OOPMetricsCal2 {

    static Random random = new Random();
    static String W ="";
    public static int calculateQuality(String CBOo, String LCOMm) {
        int quality = 0;

        // Check if CBO is less than LCOM.
        double CBO = Double.parseDouble(CBOo);
        double LCOM = Double.parseDouble(LCOMm);

        // Check if CBO is less than LCOM.
        if (CBO < LCOM) {
            quality = 2;
            System.out.println("CBO < LCOM");
            W ="CBO < LCOM";
        }

        // Check if LCOM is close to 1.
        if (Math.abs(LCOM - 1) < 0.1) {
            quality++;
            System.out.println("LCOM is close to 1");
            W ="LCOM is close to 1";
        }

        // Check if CBO and LCOM are both high.
        if (CBO > 3 && LCOM > 3) {
            quality--;
            System.out.println("CBO and LCOM are both high");
            W="CBO and LCOM are both high";
        }

        // Calculate the quality score (simplified logic)
        quality = Math.max(1, Math.min(2, quality)); // Enforce range 1-2

        return quality;
    }
    
	

   
    
	public static void runOOPMetricsCal(String className, JTextField textFieldCBOnL) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		 try (BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\TCPData\\ckjm_ext\\oop_metrics.txt"));
                 FileWriter fileWriter = new FileWriter("C:\\TCPData\\ckjm_ext\\qualInt.txt")) {
                String line;
                int quality = 2;

                while ((line = bufferedReader.readLine()) != null) {
                    // Split the string by semicolon first
                    String[] parts = line.split(";");

                    // Split the first part by colon
                    String[] firstPart = parts[0].split(":");

                    // Assign the desired values to variables
                    String classs = firstPart[0];
                    
                    String test = "test";

                    // Search for the program type
                    if (firstPart[0].trim().equalsIgnoreCase(test)) {
                        if (parts.length == 2) {
                            String cbo = firstPart[1];
                            String lcom = parts[1].trim();

                            // Print the metric.
                            System.out.println("CBO: " + cbo);
                            System.out.println("LCOM: " + lcom);
                            System.out.println(W);
                            W = W + " \n CBO;"+cbo+"; LCOM:"+lcom;
                            // Calculate the quality score.
                            quality = calculateQuality(cbo, lcom);
                        }
                    }
                }

                fileWriter.write(quality + "\n");

                // Print the quality score once.
                System.out.println("Quality :" + quality);
              
				String S = W + "Quality: "+String.valueOf(quality);
                textFieldCBOnL.setText(S);
            }
		
	}
	 public static void main(String[] args) throws IOException {
	    	// Create a JTextArea object to display the output
	    			String sFile = "";
	    			JTextField txtQI = new JTextField();
	    			
	    			// Call the runCode method and pass the JTextArea object
	    			runOOPMetricsCal(sFile, txtQI);

	    			// Print the output to the console
	    			System.out.println(txtQI.getText());
	       
	    }
}

package myQAGRTP;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.awt.EventQueue;
import myQAGRTP.runMain;
import myQAGRTP.AffectedStntSelector;
import myQAGRTP.AnalysisMetrics;
import javax.swing.*;
import javax.swing.border.LineBorder;
import myQAGRTP.ChangedObjectsSelect2;

public class MainForm {

	private JFrame frmQualityawareGeneticAlgorithm;
	private JButton calculateButton;
	 private JButton btnPriori;
	private JTextArea affectedStatementsTextArea1;
	private JTextArea txtSelectTestCase;
	private JPanel panel;
	private JTextField txtQIndex;
	private JTextField txtPrioriTCase;
	public static int sourceID;
	public static String sourceName;
	ArrayList<TestSuite> readTestSuite1 = new ArrayList<>();

	String file1 = "C:\\TCPData\\TCPData\\suite2.txt";
	

	public MainForm() {
		super();
		initialize();
	}

	private void initialize() {
		frmQualityawareGeneticAlgorithm = new JFrame();
		frmQualityawareGeneticAlgorithm.setFont(new Font("Dialog", Font.BOLD, 14));
		frmQualityawareGeneticAlgorithm.getContentPane().setForeground(new Color(0, 0, 255));
		frmQualityawareGeneticAlgorithm.setTitle(
				"                                                                                            ABUBAKAR TAFAWA BALEWA UNIVERSITY,BAUCHI");
		frmQualityawareGeneticAlgorithm.setBounds(150, 25, 930, 700);
		frmQualityawareGeneticAlgorithm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea txtSelectTestCase = new JTextArea();
		frmQualityawareGeneticAlgorithm.getContentPane().setLayout(null);
		affectedStatementsTextArea1 = new JTextArea();
		affectedStatementsTextArea1.setBounds(23, 140, 258, 135);
		affectedStatementsTextArea1.setRows(9);
		frmQualityawareGeneticAlgorithm.getContentPane().add(affectedStatementsTextArea1);
		
		JLabel lblNewLabel = new JLabel(
				"           Quality-Aware Genetic Algorithm Based Cost Cognizant Test Case Prioritization For Object-Oriented Programs");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(0, 11, 869, 24);
		frmQualityawareGeneticAlgorithm.getContentPane().add(lblNewLabel);
		
		calculateButton = new JButton("Get Affected Statements");
		frmQualityawareGeneticAlgorithm.getContentPane().add(calculateButton);
		calculateButton.setBounds(71, 106, 153, 23);
		JButton calculateButton_1 = new JButton("Get Selected TestCase");
		JButton btnPriori = new JButton("Run Priorization Process");
		JButton btnNewButton_1 = new JButton("Reset button");
		
		btnPriori.setEnabled(false);
		btnNewButton_1.setEnabled(true);
		btnPriori.setEnabled(false);
		calculateButton.setEnabled(false);
		calculateButton_1.setEnabled(false);
		

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(10, 96, 284, 190);
		frmQualityawareGeneticAlgorithm.getContentPane().add(panel);

		String[] options = { "Select a Program", "Triangle", "Binary Search Tree ", "Singly Linked List ",
				"Coffee Maker","Graph","BCS","ECS" };
		JComboBox comboBox = new JComboBox(options);
		int index = comboBox.getSelectedIndex();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBox.setBounds(10, 46, 291, 29);

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("Selected: " + comboBox.getSelectedItem());
				System.out.println(", Position: " + comboBox.getSelectedIndex());

				sourceName = (String) comboBox.getSelectedItem();
				sourceID = comboBox.getSelectedIndex();
				calculateButton.setEnabled(true);

			}
		};
		comboBox.addActionListener(actionListener);

		frmQualityawareGeneticAlgorithm.getContentPane().add(comboBox);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(10, 514, 284, 88);
		frmQualityawareGeneticAlgorithm.getContentPane().add(panel_1);
				panel_1.setLayout(null);
				
				
				JButton btnNewButton = new JButton("<html><center><font size='3'>Compute<br>Quality Index</font></center></html>");
				btnNewButton.setBounds(10, 11, 106, 66);
				btnNewButton.setEnabled(false);
						panel_1.add(btnNewButton);
						
						JTextArea txtCBO_LCOM = new JTextArea();
						txtCBO_LCOM.setBounds(126, 11, 148, 66);
						txtCBO_LCOM.setTabSize(10);
						panel_1.add(txtCBO_LCOM);
						txtCBO_LCOM.setRows(3);
						txtCBO_LCOM.setFont(new Font("Arial", Font.BOLD, 12));
						txtCBO_LCOM.setMargin(new Insets(10, 10, 0, 0)); // Adjust margins as needed

						
						btnNewButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								
									  String className = sourceName.trim(); // Replace with the actual program type string

									  
									
									  try {
										OOPMetricsCal.runOOPMetricsCal(className,txtCBO_LCOM);
										
									} catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								// Get the Quality Index from the file.
								String QIndex = "";
								try {
									BufferedReader reader = new BufferedReader(new FileReader("C:\\TCPData\\ckjm_ext\\qualInt.txt"));
									String line;
									while ((line = reader.readLine()) != null) {
										QIndex += line + "\n";
									}
									reader.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}

								// Display the Quality Index on the text field.

								txtQIndex.setText(QIndex);
								btnNewButton.setEnabled(false);
								btnPriori.setEnabled(true);
								
							}
							
						});

		// JTextArea txtSelectTestCase = new JTextArea();
		txtSelectTestCase.setRows(9);
		txtSelectTestCase.setBounds(23, 344, 258, 150);
		frmQualityawareGeneticAlgorithm.getContentPane().add(txtSelectTestCase);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_2.setBounds(10, 305, 284, 198);
		frmQualityawareGeneticAlgorithm.getContentPane().add(panel_2);

		
		panel_2.add(calculateButton_1);

		
				calculateButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ChangedObjectsSelect2.runCodes();
						ArrayList<Integer> changedObjects1 = ChangedObjectsSelect2.getChangedObjects();
						ArrayList<TestSuite> selectedTestSuite = new ArrayList<>();
						String file2 = "C:\\TCPData\\TestSuites\\suite" + sourceID + ".txt";

						try {
							
							readTestSuite1 = runMain.readTestSuites(file2);
							selectedTestSuite = runMain.generateSelectedTestSuit(readTestSuite1, changedObjects1);
							runMain.printTestSuite(selectedTestSuite, txtSelectTestCase);

						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						calculateButton_1.setEnabled(false);
						btnNewButton.setEnabled(true);
					}
				});
			

		JTextArea txtPriori = new JTextArea();
		txtPriori.setRows(9);
		Insets margin = new Insets(0, 10, 0, 0);
		txtPriori.setMargin(margin);
		txtPriori.setBounds(378, 90, 479, 443);

		JScrollPane scrollPane = new JScrollPane(txtPriori);
		scrollPane.setBounds(378, 90, 479, 443);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Add horizontal scrollbar
		frmQualityawareGeneticAlgorithm.getContentPane().add(scrollPane);

		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(867, 90, 17, 443);
		scrollBar_1.setMaximum(txtPriori.getHeight());

		scrollPane.setVerticalScrollBar(scrollBar_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_3.setBounds(365, 46, 539, 498);
		frmQualityawareGeneticAlgorithm.getContentPane().add(panel_3);

		
		btnPriori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnalysisMetrics Analyze = new AnalysisMetrics();
				try {
					Analyze.getAnalysisMetrics(sourceID);
				} catch (IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String SuiteFile = "C:\\TCPData\\TestSuites\\suite" + sourceID + ".txt";
				runMain.runCode(SuiteFile, txtPriori);

				try {
					String highestFitnessLine = HighestFitness
							.findHighestFitnessLine("C:\\TCPData\\getPrioriTCase.txt");
					// String trimmedStr =
					// highestFitnessLine.substring(highestFitnessLine.indexOf("6"));
					int length = highestFitnessLine.length();

					// Get the substring from the end index to the start index.
					String trimmedStr = highestFitnessLine.substring(length - 33, length);
					txtPrioriTCase.setText(trimmedStr);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnPriori.setEnabled(false);
			}
		});
		panel_3.add(btnPriori);

		txtPrioriTCase = new JTextField();
		txtPrioriTCase.setBounds(652, 574, 239, 20);
		frmQualityawareGeneticAlgorithm.getContentPane().add(txtPrioriTCase);
		txtPrioriTCase.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("The Prioritized Test Case");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(488, 569, 163, 29);
		frmQualityawareGeneticAlgorithm.getContentPane().add(lblNewLabel_1);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1_1.setBounds(483, 565, 421, 37);
		frmQualityawareGeneticAlgorithm.getContentPane().add(panel_1_1);
		
		
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * btnNewButton.setEnabled(true); btnNewButton_1.setEnabled(true);
				 * btnPriori.setEnabled(true); calculateButton.setEnabled(true);
				 * calculateButton_1.setEnabled(true);
				 */
				comboBox.setSelectedIndex(0);
				txtPriori.setText("");
				txtPrioriTCase.setText("");
				txtQIndex.setText("");
				txtSelectTestCase.setText("");
				txtSelectTestCase.setText("");
				affectedStatementsTextArea1.setText("");
				btnPriori.setEnabled(false);
				txtCBO_LCOM.setText("");
			}
		});
		btnNewButton_1.setBounds(620, 627, 284, 23);
		frmQualityawareGeneticAlgorithm.getContentPane().add(btnNewButton_1);
		
				txtQIndex = new JTextField();
				txtQIndex.setBounds(346, 574, 38, 20);
				frmQualityawareGeneticAlgorithm.getContentPane().add(txtQIndex);
				txtQIndex.setColumns(4);
		
		/*
		 * textFieldCBOnL = new JTextField(); textFieldCBOnL.setColumns(4);
		 * textFieldCBOnL.setBounds(433, 627, 166, 24);
		 * frmQualityawareGeneticAlgorithm.getContentPane().add(textFieldCBOnL);
		 */
		
		

		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

// Run the AffectedStntSelector.java program.
				AffectedStntSelector selector = new AffectedStntSelector();
				try {
					selector.getAffectedStations(sourceID, sourceName);
				} catch (IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

// Get the affected statements from the file.
				String affectedStatements = "";
				try {
					BufferedReader reader = new BufferedReader(new FileReader("C:\\TCPData\\affectedStatement.txt"));
					String line;
					while ((line = reader.readLine()) != null) {
						affectedStatements += line + "\n";
					}
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				// Split affectedStatements into lines
				String[] lines = affectedStatements.split("\n");

				// Create a Random object for generating random numbers
				Random random = new Random();

				// Create a list to store the random number strings
				List<String> randomStrings = new ArrayList<>();

				// Generate random number strings
				for (String line : lines) {
					List<String> numbers = new ArrayList<>();
					String[] tokens = line.split("[\\s,]+");
					for (String token : tokens) {
						if (token.matches("\\d+")) {
							numbers.add(token);
						}
					}

					// Shuffle the numbers to ensure randomness
					Collections.shuffle(numbers);

					// Pick half the numbers randomly
					int halfSize = numbers.size() / 2 + 1;
					List<String> pickedNumbers = numbers.subList(0, halfSize);

					// Join the picked numbers with commas
					String randomString = String.join(",", pickedNumbers);
					randomStrings.add(randomString);
				}

				// Write the output to suite

				try (BufferedWriter writer = new BufferedWriter(
						new FileWriter("C:\\TCPData\\TestSuites\\suite" + sourceID + ".txt"))) {
					for (int i = 1; i <= lines.length; i++) {
						writer.write(i + ":" + randomStrings.get(i - 1) + "\n");
					}
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				calculateButton.setEnabled(false);
				// Display the affected statements on the text field.

				affectedStatementsTextArea1.setRows(9);
				affectedStatementsTextArea1.setText(affectedStatements);
				calculateButton_1.setEnabled(true);
			}
		});

		// ...
	}

	public static void main(String[] args) {
		MainForm window = new MainForm();
		window.frmQualityawareGeneticAlgorithm.setVisible(true);

	}
}

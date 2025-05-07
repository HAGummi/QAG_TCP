package myQAGRTP;

import javax.swing.*;
import java.awt.*;

public class WelcomeForm extends JFrame {

	private JButton nextButton;
	private JLabel welcomeLabel;

	public WelcomeForm() {
		setTitle("Welcome");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		welcomeLabel = new JLabel("Welcome to the application!", SwingConstants.CENTER);
		add(welcomeLabel, BorderLayout.CENTER);

		nextButton = new JButton("Next");
		nextButton.addActionListener(e -> {
			// Display MainForm.java here
			// myQAGRTP.MainForm mainForm = new MainForm();
			// mainForm.setVisible(true);

			// while (!mainForm.isInitialized()) {
			// Thread.sleep(10);
			// }

			// dispose();
		});
		add(nextButton, BorderLayout.SOUTH);

		setVisible(true);
	}

	public static void main(String[] args) {
		new WelcomeForm();
	}
}

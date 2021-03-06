package cs3450.setup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class RunScreen extends Screen {

	// View
	private JButton mDone;

	// InstalationSetting


	public RunScreen(JFrame frame, Installation installation) {
		super(frame);


		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(10, 10));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		addPanel(mainPanel);

		JLabel title = new JLabel("Grocery Store Bandit Setup");
		title.setFont(new Font("Serif", Font.BOLD, 22));
		title.setMaximumSize(new Dimension(Integer.MAX_VALUE, title.getMinimumSize().height));
		mainPanel.add(title, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		JTextArea textArea = new JTextArea();
		textArea.setText(installation.getmIstallationStatus());
		textArea.setText(installation.getXlsxIstallationStatus());
		textArea.setEditable(false);
		JScrollPane scollPane = new JScrollPane(textArea);
		centerPanel.add(scollPane);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 10));
		mainPanel.add(panel, BorderLayout.SOUTH);

		mDone = new JButton("Done");
		mDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		panel.add(mDone);

		if (installation.ismInstalledSuccessfull() && installation.isXlsxInstalledSuccessfully()) {
			JButton run = new JButton("Run");
			run.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Start the program
					try {
						String[] cmdLine = {"java", "-jar", "program.jar", installation.getXlsxLocation()};
						Process p = Runtime.getRuntime().exec(cmdLine, null,
								new File(installation.getInstallationLocation()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					// Close current setup
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			});
			panel.add(run);
		}

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}

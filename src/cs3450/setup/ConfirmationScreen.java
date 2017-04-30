package cs3450.setup;

import cs3450.setup.location.LocationScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationScreen extends Screen {
	// View
	private JButton mNext;
	private JButton mPrevious;

	// InstalationSetting


	public ConfirmationScreen(JFrame frame, Installation installation) {
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
		String statusText = installation.getIstallationSetting();
		textArea.setText(statusText);
		textArea.setEditable(false);
		JScrollPane scollPane = new JScrollPane(textArea);
		centerPanel.add(scollPane);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 10));
		mainPanel.add(panel, BorderLayout.SOUTH);

		mPrevious = new JButton("Previous");
		mPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel(mainPanel);
				new LocationScreen(frame, installation);
			}
		});
		panel.add(mPrevious);

		mNext = new JButton("Install");
		mNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				installation.install();
				removePanel(mainPanel);
				new RunScreen(frame, installation);
			}
		});
		panel.add(mNext);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}

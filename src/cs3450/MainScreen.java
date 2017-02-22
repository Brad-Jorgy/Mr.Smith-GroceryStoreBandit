package cs3450;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MainScreen {
	private static JButton checkoutBtn = new JButton("Checkout");
	private static JLabel title = new JLabel("Store Management System");

	public static void addComponentsToPane(Container pane)
	{
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 100;
		c.weighty = 100;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		c.gridheight = 1;

		pane.add(title, c);
		c.gridx = 2;
		c.gridy = 2;
		pane.add(checkoutBtn, c);
	}
}

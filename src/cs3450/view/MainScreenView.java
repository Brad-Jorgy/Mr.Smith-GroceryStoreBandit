package cs3450.view;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.*;

import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;
import cs3450.model.Product;
import cs3450.control.MainScreenControl;
import cs3450.model.Order;

public class MainScreenView {
    // private static JButton checkoutBtn = new JButton("Checkout");
    // private static JLabel title = new JLabel("Store Management System", SwingConstants.CENTER);
    // private static JButton inventoryBtn = new JButton("Inventory");

    public static void addComponentsToPane(Container pane, Order order)
    {
		JButton checkoutBtn = new JButton("Checkout"); 
	    JLabel title = new JLabel("Store Management System", SwingConstants.CENTER);
		JButton inventoryBtn = new JButton("Inventory");
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100;
        c.weighty = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 1;

		inventoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreenControl.showInventoryScreen();
            }
        });

        checkoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreenControl.showCheckoutScreen();
            }
        });

        pane.add(title, c);
        c.gridx = 1;
        c.gridy = 1;
        pane.add(checkoutBtn, c);
        c.gridx = 2;
        c.gridy = 2;
        pane.add(inventoryBtn, c);

    }

}

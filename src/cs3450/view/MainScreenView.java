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
    public static void addComponentsToPane(Container pane)
    {
				JLabel title = new JLabel("Store Management System", SwingConstants.CENTER);
        JButton checkoutBtn = new JButton("Checkout");
				JButton inventoryBtn = new JButton("Inventory");
        JButton profileBtn = new JButton("Profile: " + MainScreenControl.getCurrEmployeeName());
        JButton returnBtn = new JButton("Return Item");
        JButton employeeBtn = new JButton("Employees");

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100;
        c.weighty = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;

        if(MainScreenControl.isCurrEmployeeManager()){
  		    inventoryBtn.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  MainScreenControl.showInventoryScreen();
              }
          });
          employeeBtn.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  MainScreenControl.showEmployeeScreen();
              }
          });
        }
        else{
          inventoryBtn.setEnabled(false);
          employeeBtn.setEnabled(false);
        }

        profileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreenControl.showProfileScreen();
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
        pane.add(profileBtn, c);
        c.gridy = 1;
        pane.add(returnBtn, c);
        c.gridx = 0;
        pane.add(checkoutBtn, c);
        c.gridy = 2;
        pane.add(inventoryBtn, c);
        c.gridx = 1;
        pane.add(employeeBtn, c);
    }

}

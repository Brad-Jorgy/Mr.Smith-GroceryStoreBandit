package cs3450.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

//import static cs3450.InventoryScreen.areValuesValid;
//import static cs3450.InventoryScreen.updateInventoryScreen;
//import static cs3450.Main.updateFrame;
//import static cs3450.PaymentScreen.frame;

import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;
import cs3450.model.Product;
import cs3450.control.MainScreenControl;
import cs3450.control.CheckoutScreenControl;

public class CheckoutScreenView {

    public static void addComponentsToPane(Container pane) {
        JButton backBtn = new JButton("Back to Main Screen");
        JLabel title = new JLabel("Check Out", SwingConstants.CENTER);
        JButton addItemBtn = new JButton("Add Item To Basket");
        JButton deleteBtn = new JButton("Delete Item From Basket");
        JButton checkoutBtn = new JButton("Check Out and Pay");
        DefaultListModel listModel = new DefaultListModel();
        JList list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        pane.setLayout(new GridBagLayout());
        list.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listInvenScrollPane = new JScrollPane(list);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;

        backBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                MainScreenControl.showMainScreen();
            }
        });

        addItemBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // add item to queue.
            }
        });

        pane.add(title, c);
        c.gridx = 0;
        c.gridy = 1;
        pane.add(listInvenScrollPane, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        pane.add(backBtn, c);
        c.gridx = 2;
        pane.add(addItemBtn, c);
        c.gridx = 3;
        pane.add(deleteBtn, c);
        c.gridx = 4;
        pane.add(checkoutBtn, c);

        checkoutBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                CheckoutScreenControl.showPaymentScreen(MainScreenControl.getFrame());
            }
        });

        addItemBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                CheckoutScreenControl.showAddItemInQueue();
            }
        });
    }

};

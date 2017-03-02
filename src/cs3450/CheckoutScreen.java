package cs3450;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static cs3450.Main.updateFrame;
//import static cs3450.PaymentScreen.frame;

public class CheckoutScreen {

    public static void addComponentsToPane(Container pane) {
        JButton backBtn = new JButton("Back to Main Screen");
        JLabel title = new JLabel("Check Out", SwingConstants.CENTER);
        JButton addBtn = new JButton("Add Item To Queue");
        JButton deleteBtn = new JButton("Delete Item From Queue");
        JButton checkoutBtn = new JButton("Check Out and Pay");
        JTextField itemInfo = new JTextField("Item Info");
        JTextField itemQueue = new JTextField("Items in queue go here yo.");
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100;
        c.weighty = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;
        c.gridheight = 1;

        backBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Main.showMainScreen();
            }
        });
        pane.add(title, c);
        c.gridx = 1;
        c.gridy = 1;
        pane.add(itemInfo, c);
        c.gridx = 2;
        c.gridy = 2;
        pane.add(itemQueue, c);
        c.gridx = 3;
        c.gridy = 3;
        pane.add(checkoutBtn, c);
        c.gridx = 4;
        c.gridy = 4;
        pane.add(addBtn, c);
        c.gridx = 5;
        c.gridy = 5;
        pane.add(deleteBtn, c);
        c.gridx = 6;
        c.gridy = 6;
        pane.add(backBtn, c);

        checkoutBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Main.showPaymentScreen();
            }
        });


    }
};



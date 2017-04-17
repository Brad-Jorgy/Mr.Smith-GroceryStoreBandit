package cs3450.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

import cs3450.control.CustomerPickScreenControl;
import cs3450.control.Main;
import cs3450.model.*;
import cs3450.control.MainScreenControl;
import cs3450.control.CheckoutScreenControl;
import java.util.List;
import java.util.Iterator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class CustomerPickScreenView{

    public static void addComponentsToPane(Container pane, Order order) {
        JButton backBtn = new JButton("Back to Basket Screen");
        JLabel title = new JLabel("Customer", SwingConstants.CENTER);
        JButton addCustomerBtn = new JButton("Add Customer");
        JButton editCustomerBtn = new JButton("Edit Customer");
        JButton checkoutBtn = new JButton("Check Out");
       // JButton cashCheckoutBtn = new JButton("Cash Check Out");
        DefaultListModel listModel = new DefaultListModel();
        List<PurchaseItem> olist = order.getOrderList();
        Iterator<PurchaseItem> orderIterator = olist.iterator();
        while( orderIterator.hasNext()) {
            PurchaseItem item = orderIterator.next();
            listModel.addElement(item);
        }
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

        pane.add(title, c);
        c.gridx = 0;
        c.gridy = 1;
        pane.add(listInvenScrollPane, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        pane.add(backBtn, c);
        c.gridx = 2;
        pane.add(addCustomerBtn, c);
        c.gridx = 3;
        pane.add(editCustomerBtn, c);
        c.gridx = 4;
        pane.add(checkoutBtn, c);
//        c.gridx = 5;
//        pane.add(cashCheckoutBtn, c);


        backBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
               // order.clearOrder();
                MainScreenControl.showCheckoutScreen();
            }
        });

        editCustomerBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                CheckoutScreenControl.showEditOrderPopup(order, (PurchaseItem)list.getSelectedValue());
            }
        });

        checkoutBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
               // CheckoutScreenControl.showCardPaymentScreen(MainScreenControl.getFrame(), order);
                CustomerPickScreenControl.showEditCustomerPopup();
            }
        });

//        cashCheckoutBtn.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent e) {
//                CheckoutScreenControl.showCashPaymentScreen(MainScreenControl.getFrame(), order);
//            }
//        });

        addCustomerBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                CheckoutScreenControl.addItemToBasketPopup(order);
            }
        });
    }

};


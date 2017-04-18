package cs3450.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cs3450.control.Main;
import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;
import cs3450.model.Product;
import cs3450.control.MainScreenControl;
import cs3450.model.Order;
import cs3450.model.Customer;

public class CashPaymentScreenView {

    public static void addComponentsToPane(Container pane, Order order)
    {

        JButton submitBtn = new JButton("SUBMIT PAYMENT");
        JLabel title = new JLabel(" Cash Payment", SwingConstants.CENTER);
        JButton cancelBtn = new JButton("   Cancel");
//        JLabel nameOnCard = new JLabel("    Name On Card");
//        JTextField name = new JTextField(" ");
//        JLabel cardNum = new JLabel("   Card Number");
//        JTextField number = new JTextField(" ");
//        JLabel expDate = new JLabel("   Exp. Date");
//        JTextField exp = new JTextField(" ");
//        JLabel CVC = new JLabel("   CVS");
//        JTextField cvc = new JTextField(" ");
        JTextField totalAmount = new JTextField("TOTAL:: " + Double.toString(order.getTotal()));
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100;
        c.weighty = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 1;

        cancelBtn.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                MainScreenControl.showCheckoutScreen();
            }
        });

        submitBtn.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                MainScreenControl.showCheckoutScreen();
                if (order.getOrderSize() > 0) {
                    DataAccess db = Main.getSQLiteAccess();
                    int customerId = db.saveNewCustomer(new Customer(0, "Cash Payment", "0", "0", "None", "None", "None", "USA","no"));
                    order.setOrderCustomer(customerId);
                    int orderId = db.saveNewOrder(order);
                    order.clearOrder();
            }
                MainScreenControl.showMainScreen();
            }
        });

        pane.add(title, c);
        c.gridx = 1;
        c.gridy = 1;
        pane.add(submitBtn, c);
        c.gridx = 2;
        c.gridy = 2;
        pane.add(cancelBtn, c);
        c.gridx = 3;
        c.gridy = 3;
        pane.add(totalAmount, c);

    }
}
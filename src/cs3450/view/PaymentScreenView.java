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
import cs3450.control.PaymentScreenControl;
import cs3450.control.CheckoutScreenControl;
import cs3450.model.Order;
import cs3450.model.Customer;

public class PaymentScreenView {

    public static void cashAddComponentsToPane(Container pane, Order order){
      if(CheckoutScreenControl.isLoyalCustomer() && CheckoutScreenControl.getCurrCustomerPoints() > 0)
        PaymentScreenControl.showRewardPointsPopup(order);

      JButton submitBtn = new JButton("SUBMIT PAYMENT");
      JLabel title = new JLabel(" Cash Payment", SwingConstants.CENTER);
      JButton cancelBtn = new JButton("   Cancel");
      JLabel totalAmount = new JLabel("", SwingConstants.CENTER);
      if(PaymentScreenControl.getRewardBool()){
        double pointsAmount = CheckoutScreenControl.getCurrCustomerPoints() / 100;
        totalAmount.setText("TOTAL:: $" + String.format("%.2f", (order.getTotal() - pointsAmount)) + "\t($-" + pointsAmount + " from points)");
      }
      else
        totalAmount.setText("TOTAL:: $" + String.format("%.2f", order.getTotal()));
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
              MainScreenControl.showCheckoutScreen(false);
          }
      });

      submitBtn.addMouseListener(new MouseAdapter(){
          public void mousePressed(MouseEvent e) {
              PaymentScreenControl.submitPayment(order);
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

    public static void cardAddComponentsToPane(Container pane, Order order){
      if(CheckoutScreenControl.isLoyalCustomer() && CheckoutScreenControl.getCurrCustomerPoints() > 0)
        PaymentScreenControl.showRewardPointsPopup(order);

      JButton submitBtn = new JButton("SUBMIT PAYMENT");
      JLabel title = new JLabel(" Card Payment", SwingConstants.CENTER);
      JButton cancelBtn = new JButton("   Cancel");
      JLabel nameOnCard = new JLabel("    Name On Card");
      JTextField name = new JTextField(" ");
      JLabel cardNum = new JLabel("   Card Number");
      JTextField number = new JTextField(" ");
      JLabel expDate = new JLabel("   Exp. Date");
      JTextField exp = new JTextField(" ");
      JLabel CVC = new JLabel("   CVS");
      JTextField cvc = new JTextField(" ");
      JLabel totalAmount = new JLabel("", SwingConstants.CENTER);
      if(PaymentScreenControl.getRewardBool()){
        double pointsAmount = CheckoutScreenControl.getCurrCustomerPoints() / 100;
        totalAmount.setText("TOTAL:: $" + String.format("%.2f", (order.getTotal() - pointsAmount)) + "\t($-" + pointsAmount + " from points)");
      }
      else
        totalAmount.setText("TOTAL:: $" + String.format("%.2f", order.getTotal()));
      pane.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 100;
      c.weighty = 100;
      c.gridx = 0;
      c.gridy = 0;
      c.gridwidth = 12;
      c.gridheight = 1;

      cancelBtn.addMouseListener(new MouseAdapter(){
          public void mousePressed(MouseEvent e) {
              MainScreenControl.showCheckoutScreen(false);
          }
      });

      submitBtn.addMouseListener(new MouseAdapter(){
          public void mousePressed(MouseEvent e) {
            PaymentScreenControl.submitPayment(order);
          }
      });

      pane.add(title, c);
      c.gridx = 1;
      c.gridy = 1;
      pane.add(nameOnCard, c);
      c.gridx = 2;
      c.gridy = 2;
      pane.add(name, c);
      c.gridx = 3;
      c.gridy = 3;
      pane.add(cardNum, c);
      c.gridx = 4;
      c.gridy = 4;
      pane.add(number, c);
      c.gridx = 5;
      c.gridy = 5;
      pane.add(expDate, c);
      c.gridx = 6;
      c.gridy = 6;
      pane.add(exp, c);
      c.gridx = 7;
      c.gridy = 7;
      pane.add(CVC, c);
      c.gridx = 8;
      c.gridy = 8;
      pane.add(cvc, c);
      c.gridx = 9;
      c.gridy = 9;
      pane.add(submitBtn, c);
      c.gridx = 10;
      c.gridy = 10;
      pane.add(cancelBtn, c);
      c.gridx = 11;
      c.gridy = 11;
      pane.add(totalAmount, c);
    }
}

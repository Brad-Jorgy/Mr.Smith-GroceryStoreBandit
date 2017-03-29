package cs3450.control;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import java.sql.ResultSet;

import cs3450.view.PaymentScreenView;
import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;

public class CheckoutScreenControl{
  static public void showAddItemInQueue(){
      JPanel popupPanel = new JPanel();
      popupPanel.setLayout(new GridLayout(5,2));
      JTextField nameTF = new JTextField();
      //JTextField priceTF = new JTextField();
      JTextField quantityTF = new JTextField();
      //JTextField providerTF = new JTextField();
      popupPanel.add(new JLabel("Name: "));
      popupPanel.add(nameTF);
      popupPanel.add(new JLabel("Quantity: "));
      popupPanel.add(quantityTF);
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Add To Basket:", JOptionPane.OK_CANCEL_OPTION);
      if(result ==JOptionPane.OK_OPTION) {
          DataAccess db = Main.getSQLiteAccess();
          //Use the loadProduct here, it returns type Product
          //ResultSet found = db.findProduct(Integer.parseInt(nameTF.getText()));

      }
      updateCheckoutScreen();
  }

  static public void updateCheckoutScreen(){
      try{
          TimeUnit.SECONDS.sleep(1);
      }
      catch(InterruptedException e){
          System.err.println(e.getMessage());
      }
      MainScreenControl.updateFrame();
  }

  public static void showPaymentScreen(JFrame frame)
	{
		frame.getContentPane().removeAll();
		PaymentScreenView.addComponentsToPane(frame.getContentPane());
		MainScreenControl.updateFrame();
	}

};

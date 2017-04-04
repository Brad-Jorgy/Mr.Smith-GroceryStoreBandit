package cs3450.control;

import javax.swing.SwingUtilities;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.*;
import java.awt.*;
import javax.swing.*;

import javax.swing.JFrame;

import cs3450.model.SQLiteAdapter;
import cs3450.view.MainScreenView;
import cs3450.view.InventoryScreenView;
import cs3450.view.CheckoutScreenView;
import cs3450.view.LoginScreenView;
import cs3450.model.Employee;
import cs3450.model.DataAccess;

public class LoginScreenControl{
  private static JFrame frame = new JFrame("Store Management System");
  public static JFrame getFrame(){ return frame; }

  LoginScreenControl(){
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(1000,550));
		LoginScreenView.addComponentsToPane(frame.getContentPane());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
  }

  public static int getUserId(String username, String password){
    DataAccess db = Main.getSQLiteAccess();
    return db.getUserId(username, password);
  }

  public static Employee loadEmployee(int id){
    DataAccess db = Main.getSQLiteAccess();
    return db.loadEmployee(id);
  }
};

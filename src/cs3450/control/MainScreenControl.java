package cs3450.control;

import javax.swing.SwingUtilities;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.*;

import javax.swing.JFrame;

import cs3450.view.MainScreenView;
import cs3450.view.InventoryScreenView;
import cs3450.view.CheckoutScreenView;
import cs3450.model.Order;
import cs3450.model.Employee;

public class MainScreenControl{
  private static JFrame frame = new JFrame("Store Management System");
  private static Employee currEmployee = null;
  private static final Order currentOrder = new Order();

  MainScreenControl(){
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(1000,550));
		MainScreenView.addComponentsToPane(frame.getContentPane());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
  }
  public static void showMainScreen()
	{
    frame = LoginScreenControl.getFrame();
		frame.getContentPane().removeAll();
		MainScreenView.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}
  public static void showMainScreen(Employee employee)
	{
    currEmployee = employee;
    frame = LoginScreenControl.getFrame();
		frame.getContentPane().removeAll();
		MainScreenView.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showInventoryScreen()
	{
		frame.getContentPane().removeAll();
		InventoryScreenView.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showCheckoutScreen()
	{
		frame.getContentPane().removeAll();
		CheckoutScreenView.addComponentsToPane(frame.getContentPane(), currentOrder);
		updateFrame();
	}

  	public static void updateFrame(){
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
	}

  public static boolean isCurrEmployeeManager(){
    if(currEmployee == null)
      return false;
    if("Manager".equals(currEmployee.getPosition())){
      return true;
    }
    return false;
  }
  public static String getCurrEmployeeName(){
    return currEmployee.getName();
  }

	public static JFrame getFrame(){ return frame; }
  //public static DataAccess getSQLiteAccess(){ return sqliteAccess; }
};

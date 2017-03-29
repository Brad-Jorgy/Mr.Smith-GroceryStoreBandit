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

public class MainScreenControl{
  private static JFrame frame = new JFrame("Store Management System");

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
		frame.getContentPane().removeAll();
		MainScreenView.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showInventoryScreen()
	{
		frame.getContentPane().removeAll();
    //System.out.println(Main.getSQLiteAccess().toString());
		InventoryScreenView.addComponentsToPane(frame.getContentPane());
    //System.out.println(Main.getSQLiteAccess().toString());
		updateFrame();
    //System.out.println(Main.getSQLiteAccess().toString());
	}

	public static void showCheckoutScreen()
	{
		frame.getContentPane().removeAll();
		CheckoutScreenView.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}
  public static void updateFrame(){
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
	}
  public static JFrame getFrame(){ return frame; }
  //public static DataAccess getSQLiteAccess(){ return sqliteAccess; }
};

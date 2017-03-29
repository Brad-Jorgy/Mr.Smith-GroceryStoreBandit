package cs3450;

import javax.swing.SwingUtilities;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.*;
import java.io.File;

import javax.swing.JFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static JFrame frame = new JFrame("Store Management System");

	public static void startProgram()
	{
		File file = new File("cs3450.db");
		if(!file.exists()){
			Connection connection = null;
			try{
				connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30);
				statement.executeUpdate("create table if not exists inventory (itemId integer, name string, price double, quantity integer, provider string)");
				//add other tables here.
				DataAccess db = new XLSAdapter();
	      ArrayList<Product> products = db.loadAllProducts();
	      for(int i = 0; i < products.size(); i++){
					System.out.println("insert into inventory values(" + products.get(i).getId() + ", '" + products.get(i).getName() + "', " + products.get(i).getPrice() + ", " + products.get(i).getQuantity() + ", '" + products.get(i).getProvider() + "')");
					statement.executeUpdate("insert into inventory values(" + products.get(i).getId() + ", '" + products.get(i).getName() + "', " + products.get(i).getPrice() + ", " + products.get(i).getQuantity() + ", '" + products.get(i).getProvider() + "')");
					System.out.println("reached");
	      }
			}
			catch(SQLException e){
				System.err.println(e.getMessage());
			}
			finally{
				try{
					if(connection != null)
						connection.close();
				}
				catch(SQLException e){
					System.err.println(e.getMessage());
				}
			}
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(1000,550));
		MainScreen.addComponentsToPane(frame.getContentPane());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void showMainScreen()
	{
		frame.getContentPane().removeAll();
		MainScreen.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showInventoryScreen()
	{
		frame.getContentPane().removeAll();
		InventoryScreen.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showCheckoutScreen()
	{
		frame.getContentPane().removeAll();
		CheckoutScreen.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void showPaymentScreen()
	{
		frame.getContentPane().removeAll();
		PaymentScreen.addComponentsToPane(frame.getContentPane());
		updateFrame();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Main().startProgram();
		}});


	}

	public static void updateFrame(){
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
	}
}

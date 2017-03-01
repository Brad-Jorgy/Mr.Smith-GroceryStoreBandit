package cs3450;

import javax.swing.SwingUtilities;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

//import MainScreen;

public class Main {
	public static JFrame frame = new JFrame("Store Management System");

	public static void startProgram()
	{
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

	public static void main(String[] args) {
		//System.out.print("start");
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

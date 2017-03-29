package cs3450.control;

import javax.swing.SwingUtilities;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.*;
import java.io.File;

import javax.swing.JFrame;

import cs3450.model.DataAccess;
import cs3450.model.InitSQLite;
import cs3450.model.SQLiteAdapter;
import cs3450.model.XLSAdapter;
import cs3450.model.Product;
import cs3450.view.MainScreenView;
import cs3450.view.CheckoutScreenView;
import cs3450.view.InventoryScreenView;
import cs3450.view.PaymentScreenView;

public class Main {
	private static DataAccess sqliteAccess;

	public static void startProgram()
	{
		InitSQLite.setupDatabaseIfNotSetup();
		try{
      sqliteAccess = new SQLiteAdapter();
    }
    catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
    }
		new MainScreenControl();
		//System.out.println(getSQLiteAccess().toString());
	}

	public static void main(String[] args) {
		//SwingUtilities.invokeLater(new Runnable(){
		//	public void run(){
				//new Main().startProgram();
		//}});
		startProgram();
	}

	public static DataAccess getSQLiteAccess(){
		return sqliteAccess;
	}
}

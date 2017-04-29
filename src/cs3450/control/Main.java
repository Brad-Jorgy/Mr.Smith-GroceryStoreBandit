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

import cs3450.model.*;
import cs3450.view.MainScreenView;
import cs3450.view.CheckoutScreenView;
import cs3450.view.InventoryScreenView;
//import cs3450.view.CardPaymentScreenView;
import cs3450.view.LoginScreenView;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Main {
	private static DataAccess sqliteAccess;
	private static Connection connection;

	public static void startProgram(String xlsxFile)
	{
		connection = InitSQLite.setupDatabaseIfNotSetup(xlsxFile);
		try{
      sqliteAccess = new SQLiteAdapter();
			Class.forName("org.sqlite.JDBC");
    }
    catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
    }
		new LoginScreenControl();
	}

	public static void main(String[] args) {
		if(args.length > 0) {
			startProgram(args[0]);
		}else{
			startProgram("");
		}
	}

	public static Connection getDbConnection() {
		return connection;
	}

	public static void closeConnection(){
    if(connection != null){
      try { connection.close(); }
      catch (SQLException e) { System.out.println(e.getMessage()); }
    }
  }

	public static DataAccess getSQLiteAccess(){
		return sqliteAccess;
	}
}

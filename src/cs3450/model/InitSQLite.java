package cs3450.model;

import java.io.File;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitSQLite{
  public static void setupDatabaseIfNotSetup(){
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
        statement.executeUpdate("create table if not exists employees (employeeId integer, name string, image blob, username string, password string, position string)");
        statement.executeUpdate("insert into employees values(1, 'New Manager', null, 'manager', '12345', 'Manager')");
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
  }
}

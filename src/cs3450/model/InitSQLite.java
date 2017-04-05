package cs3450.model;

import java.io.File;
import java.util.*;

import cs3450.control.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitSQLite {
    public static Connection setupDatabaseIfNotSetup() {
        Connection connection = null;
        Statement statement = null;
        File file = new File("cs3450.db");
        if (!file.exists()) {
            try {
              connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
              statement = connection.createStatement();
              statement.executeUpdate("create table if not exists inventory (itemId integer, name string, price double, quantity integer, provider string)");
              DataAccess db = new XLSAdapter();
              ArrayList<Product> products = db.loadAllProducts();
              for (int i = 0; i < products.size(); i++)
                statement.executeUpdate("insert into inventory values(" + products.get(i).getId() + ", '" + products.get(i).getName() + "', " + products.get(i).getPrice() + ", " + products.get(i).getQuantity() + ", '" + products.get(i).getProvider() + "')");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } finally{
              if(connection != null){
                try { connection.close(); }
                catch (SQLException e) { System.out.println(e.getMessage()); }
              }
            }
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("create table if not exists inventory (itemId integer, name string, price double, quantity integer, provider string)");
            statement.executeUpdate("create table if not exists orders (orderId integer, itemId integer, quantity integer)");
            statement.executeUpdate("create table if not exists customers (customerId integer, orderId integer, name string, creditCard string, address string, city string, state string, zipcode string, country string)");
            statement.executeUpdate("create table if not exists employees (employeeId integer, name string, image blob, username string, password string, position string)");
            ResultSet rs = statement.executeQuery("select * from employees");
            if(!rs.next())
              statement.executeUpdate("insert into employees values(1, 'Default Manager', null, 'manager', '12345', 'Manager')");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        Main.closeConnection();
        return connection;
    }
}

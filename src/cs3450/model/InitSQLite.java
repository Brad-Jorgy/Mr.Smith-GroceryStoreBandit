package cs3450.model;

import java.io.File;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitSQLite {
    public static Connection setupDatabaseIfNotSetup() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("create table if not exists inventory (itemId integer, name string, price double, quantity integer, provider string)");
            //add other tables here.
//            statement.executeUpdate("drop table orders");
//            statement.executeUpdate("drop table customers");
            statement.executeUpdate("create table if not exists orders (orderId integer, itemId integer, quantity integer)");
            statement.executeUpdate("create table if not exists customers (customerId integer, orderId integer, name string, creditCard string, address string, city string, state string, zipcode string, country string)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        File file = new File("cs3450.db");
        if (!file.exists()) {
            try {
                if (connection == null) {
                    connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
                } else {
                    DataAccess db = new XLSAdapter();
                    ArrayList<Product> products = db.loadAllProducts();
                    for (int i = 0; i < products.size(); i++) {
                        System.out.println("insert into inventory values(" + products.get(i).getId() + ", '" + products.get(i).getName() + "', " + products.get(i).getPrice() + ", " + products.get(i).getQuantity() + ", '" + products.get(i).getProvider() + "')");
                        statement.executeUpdate("insert into inventory values(" + products.get(i).getId() + ", '" + products.get(i).getName() + "', " + products.get(i).getPrice() + ", " + products.get(i).getQuantity() + ", '" + products.get(i).getProvider() + "')");
                        System.out.println("reached");
                    }
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } finally {
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return connection;
    }
}


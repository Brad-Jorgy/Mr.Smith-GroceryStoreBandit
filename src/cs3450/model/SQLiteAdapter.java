package cs3450.model;

import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs3450.view.MainScreenView;
import cs3450.view.InventoryScreenView;
import cs3450.control.MainScreenControl;

public class SQLiteAdapter implements DataAccess{
  private final Connection mConn;

	public SQLiteAdapter() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		mConn = connectToDatabase();
    // if(mConn==null)
    //   System.out.println("Null");
    // else
    //   System.out.println(mConn.toString());
	}

  public String toString(){
    try{
      return super.toString() + mConn.isValid(0);
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return "exception called";
  }

	private Connection connectToDatabase() {
		Connection connection = null;
		try {
			// db parameters
			String url = "jdbc:sqlite:cs3450.db";
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

			return connection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}




  public void saveProduct(Product product){
    Connection connection = null;
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      //Statement statement = mConn.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("update inventory set name='"+product.getName()+"', price="+product.getPrice()+", quantity="+product.getQuantity()+", provider='"+product.getProvider()+"' where itemId="+product.getId());
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    MainScreenControl.showMainScreen();
    MainScreenControl.showInventoryScreen();
  }

  public Product loadProduct(int id){
    Connection connection = null;
    Product empty = new Product(0,"Empty",0,0,"Empty");
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      //Statement statement = mConn.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from inventory");
      rs.next();
      int maxCount = rs.getInt(1);
      //System.out.println("maxCount: "+maxCount);
      if(id > maxCount || id < 1){
        System.out.println("Invalid itemId");
      }
      else{
        rs = statement.executeQuery("select * from inventory where itemId="+id);
        return new Product(id, rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("provider"));
      }
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return empty;
  }

  public void saveNewProduct(Product product){
    Connection connection = null;
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      //Statement statement = mConn.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from inventory");
      rs.next();
      int maxCount = rs.getInt(1);
      statement.executeUpdate("insert into inventory values(" + (maxCount + 1) + ", '" + product.getName() + "', " + product.getPrice() + ", " + product.getQuantity() + ", '" + product.getProvider() + "')");
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public ArrayList<Product> loadAllProducts(){
    Connection connection = null;
    ArrayList<Product> products = new ArrayList<Product>();
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      //System.out.println("Reached...");
      //Statement statement = mConn.createStatement();
      //System.out.println("Reached...");

      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from inventory");
      while(rs.next()){
        products.add(new Product(rs.getInt("itemId"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("provider")));
      }
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return products;
  }

  public void deleteProduct(Product product){
    Connection connection = null;
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      //Statement statement = mConn.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("delete from inventory where itemId="+product.getId());
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // public ResultSet findProduct(int Id){
  //   try{
  //     Statement statement = mConn.createStatement();
  //     statement.setQueryTimeout(30);
  //     ResultSet rs = statement.executeQuery("select * from inventory where itemId="+Id);
  //     return rs;
  //   }
  //   catch (SQLException e) {
  //     System.out.println(e.getMessage());
  //   }
  //   return null;
  // }
};

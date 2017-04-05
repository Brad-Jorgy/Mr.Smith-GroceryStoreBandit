package cs3450.model;

import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs3450.control.Main;
import cs3450.view.MainScreenView;
import cs3450.view.InventoryScreenView;
import cs3450.control.MainScreenControl;

public class SQLiteAdapter implements DataAccess{
  private final Connection mConn;

	public SQLiteAdapter() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		mConn = connectToDatabase();
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
    connection = Main.getDbConnection();
		System.out.println("Connection to SQLite has been established.");
		return connection;
	}

  public void saveProduct(Product product){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("update inventory set name='"+product.getName()+"', price="+product.getPrice()+", quantity="+product.getQuantity()+", provider='"+product.getProvider()+"' where itemId="+product.getId());
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    MainScreenControl.showInventoryScreen();
  }



  public void saveNewProduct(Product product){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
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

  public int saveNewOrder(Order order){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from orders");
      rs.next();
      int maxCount = rs.getInt(1);
      List<PurchaseItem> olist = order.getOrderList();
      Iterator<PurchaseItem> orderIterator = olist.iterator();
      PurchaseItem item = null;
      while( orderIterator.hasNext()) {
          item = orderIterator.next();
          statement.executeUpdate("insert into orders values(" + (maxCount + 1) + ", " + item.getId() + ", " + item.getQuantity() + ")");
      }
      return maxCount + 1;
    }
    catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return -1;
  }

  public int saveNewCustomer(Customer customer){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from customers");
      rs.next();
      int maxCount = rs.getInt(1);
      statement.executeUpdate("insert into customers values(" + (maxCount + 1) + ", " + customer.getOrderId() + ", '" + customer.getName() + "', '" + customer.getCreditCard() + "', '" + customer.getAddress() + "', '" + customer.getCity() + "', '" + customer.getState() + "', '" + customer.getZipcode() + "', '" + customer.getCountry() + "')");
      return maxCount + 1;
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return -1;
  }

  public Product loadProduct(int id){
    Connection connection = null;
    Product empty = new Product(0,"Empty",0,0,"Empty");
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from inventory");
      rs.next();
      int maxCount = rs.getInt(1);
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

  public Order getOrder(int id){
    Connection connection = null;
    Order empty = new Order();
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from orders");
      rs.next();
      int maxCount = rs.getInt(1);
      if(id > maxCount || id < 1){
          System.out.println("Invalid itemId");
      }
      else{
        rs = statement.executeQuery("select * from orders where itemId="+id);
        int iid = rs.getInt("itemId");
        Product prod = new Product(iid, rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("provider"));
        return new Order(prod, id);
      }
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return empty;
  }
  public ArrayList<Product> loadAllProducts(){
    Connection connection = null;
    ArrayList<Product> products = new ArrayList<Product>();
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
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
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("delete from inventory where itemId="+product.getId());
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public boolean isValidProductId(int id){
    Connection connection = null;
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from inventory where itemId="+id);
      return rs.next();
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  public void saveEmployee(Employee employee){}
  public Employee loadEmployee(int id){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from employees where employeeId="+id);
      if(rs.next())
        return new Employee(rs.getInt("employeeId"), rs.getString("name"), rs.getBinaryStream("image"), rs.getString("username"), rs.getString("password"), rs.getString("position"));
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
  public void saveNewEmployee(Employee employee){}
  public ArrayList<Employee> loadAllEmployees(){return null;}
  public void deleteEmployee(Employee employee){}
  public int getUserId(String username, String password){
    Connection connection = null;
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from employees where username='" + username +"' and password='" + password + "'");
      if(rs.next())
        return rs.getInt("employeeId");
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return -1;
  }
};

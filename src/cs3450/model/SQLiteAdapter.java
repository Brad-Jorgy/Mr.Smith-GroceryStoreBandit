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

  public void saveProduct(Product product){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("update inventory set name='"+product.getName()+"', price="+product.getPrice()+", discountPrice="+product.getDiscountPrice()+"quantity="+product.getQuantity()+", provider='"+product.getProvider()+"' where itemId="+product.getId());
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
      statement.executeUpdate("insert into inventory values(" + (maxCount + 1) + ", '" + product.getName() + "', " + product.getPrice() + ", " + product.getDiscountPrice() + ", " + product.getQuantity() + ", '" + product.getProvider() + "')");
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public Product loadProduct(int id){
    Connection connection = null;
    Product empty = new Product(0,"Empty",0,0,0,"Empty");
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
          return new Product(id, rs.getString("name"), rs.getDouble("price"), rs.getDouble("discountPrice"), rs.getInt("quantity"), rs.getString("provider"));
      }
    }
    catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return empty;
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
  public ArrayList<Product> loadAllProducts(){
    Connection connection = null;
    ArrayList<Product> products = new ArrayList<Product>();
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from inventory");
      while(rs.next()){
        products.add(new Product(rs.getInt("itemId"), rs.getString("name"), rs.getDouble("price"), rs.getDouble("discountPrice"), rs.getInt("quantity"), rs.getString("provider")));
      }
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return products;
  }
  public boolean isValidProductId(int id){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
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
  public int getNewProductId(){
    Connection connection = null;
    return -1;
  }


  public int saveNewOrder(Order order){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from orders");
      rs.next();
      int maxCount = rs.getInt(1) + 1;
      List<PurchaseItem> olist = order.getOrderList();
      Iterator<PurchaseItem> orderIterator = olist.iterator();
      PurchaseItem item = null;
      while( orderIterator.hasNext()) {
          item = orderIterator.next();
          statement.executeUpdate("insert into orders values(" + maxCount + ", " + item.getId() + ", " + item.getQuantity() + ")");
      }
      return maxCount;
    }
    catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return -1;
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
        Product prod = new Product(iid, rs.getString("name"), rs.getDouble("price"), rs.getDouble("discountPrice"), rs.getInt("quantity"), rs.getString("provider"));
        return new Order(prod, id);
      }
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return empty;
  }
  public void updateOrderInventory(PurchaseItem item) {
    Connection connection = null;
    Product prodIn = loadProduct(item.getId());
    int newCount = prodIn.getQuantity() - item.getQuantity();
    try {
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("update inventory set name='" + prodIn.getName() + "', price=" + prodIn.getPrice() + ", discountPrice=" + prodIn.getDiscountPrice() + ", quantity=" + newCount + ", provider='" + prodIn.getProvider() + "' where itemId=" + prodIn.getId());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    MainScreenControl.showInventoryScreen();
  }

  public int saveNewCustomer(Customer customer){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from customers");
      rs.next();
      int maxCount = rs.getInt(1) + 1;
      statement.executeUpdate("insert into customers values(" + maxCount + ", '" + customer.getName() + "', " + customer.getRewardPoints() + ")");
      return maxCount;
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return -1;
  }
  public Customer loadCustomer(int id){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from customers where customerId="+id);
      if(rs.next())
        return new Customer(rs.getInt("customerId"), rs.getString("name"), rs.getInt("rewardPoints"));
    }
    catch(SQLException e){
      System.out.println(e.getMessage());
    }
    return null;
  }
  public boolean isValidCustomerId(int id){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from customers where customerId="+id);
      return rs.next();
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return false;
  }
  public int getNextCustomerId(){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from customers");
      rs.next();
      return rs.getInt(1) + 1;
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return -1;
  }

  public void saveEmployee(Employee employee){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("update employees set name='"+employee.getName()+"', username='"+employee.getUsername()+"', password='"+employee.getPassword()+"', position='" + employee.getPosition()+"' where employeeId="+employee.getId());
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    MainScreenControl.showEmployeeScreen();
  }
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
  public void saveNewEmployee(Employee employee){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("insert into employees values(" + employee.getId() + ", '" + employee.getName() + "', " + employee.getImage() + ", '" + employee.getUsername() + "', '" + employee.getPassword() + "', '" + employee.getPosition() +"')");
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public ArrayList<Employee> loadAllEmployees(){
    Connection connection = null;
    ArrayList<Employee> employees = new ArrayList<Employee>();
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from employees");
      while(rs.next()){
        employees.add(new Employee(rs.getInt("employeeId"), rs.getString("name"), rs.getBinaryStream("image"), rs.getString("username"), rs.getString("password"), rs.getString("position")));
      }
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return employees;
  }
  public void deleteEmployee(Employee employee){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("delete from employees where employeeId="+employee.getId());
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public int getUserId(String username, String password){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
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
  public int getNewEmployeeId(){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from employees");
      rs.next();
      int maxCount = rs.getInt(1);
      return maxCount + 1;
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return -1;
  }
};

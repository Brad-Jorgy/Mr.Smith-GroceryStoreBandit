package cs3450.model;

import java.util.*;
import java.text.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

import cs3450.control.Main;
import cs3450.view.MainScreenView;
import cs3450.view.InventoryScreenView;
import cs3450.control.MainScreenControl;

public class SQLiteAdapter implements DataAccess{

  public void saveProduct(Product product){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      PreparedStatement pstmt = connection.prepareStatement("update inventory set name = ?, price = ?, discountPrice = ?, quantity = ?, provider = ? where itemId = ?");
      pstmt.setString(1,product.getName() );
      pstmt.setDouble(2,product.getPrice() );
      pstmt.setDouble(3,product.getDiscountPrice());
      pstmt.setInt(4,product.getQuantity());
      pstmt.setString(5,product.getProvider());
      pstmt.setInt(6, product.getId());
      pstmt.executeUpdate();
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

  public void updateItemsCount(PurchaseItem orderItem, int countChange){
    Connection connection = null;
    try {
      connection = Main.getDbConnection();
      PreparedStatement statement = connection.prepareStatement("update orderItem set quantity = ? where itemId = ? and orderId = ?");
      statement.setQueryTimeout(30);
      statement.setInt(1, orderItem.getQuantity()+ countChange);
      statement.setInt(2, orderItem.getProduct().getId());
      statement.setInt(3,orderItem.getOrderId());
      statement.executeUpdate();

      statement = connection.prepareStatement("update inventory set quantity = ? where itemId = ?");
      statement.setQueryTimeout(30);
      statement.setInt(1, orderItem.getProduct().getQuantity() - countChange);
      statement.setInt(2, orderItem.getProduct().getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public int saveNewOrder(Order order) {
    Connection connection = null;
    int orderId = -1, orderItemId;
    try {
      connection = Main.getDbConnection();
      List<PurchaseItem> olist = order.getOrderList();
      Iterator<PurchaseItem> orderIterator = olist.iterator();
      PurchaseItem item = null;
      PreparedStatement pstmt = connection.prepareStatement("insert into orders (itemId)values(?)");
      pstmt.setInt(1, order.getOrderCustomer());
      pstmt.executeUpdate();
      try {
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
          orderId = generatedKeys.getInt(1);
        }
      } catch(SQLException e){
        System.out.println(e.getMessage());
      }
      while (orderIterator.hasNext()) {
        pstmt = connection.prepareStatement("insert into orderItem (orderId,itemId,quantity)values(?,?,?)");
        item = orderIterator.next();
        pstmt.setInt(1, orderId);
        pstmt.setInt(2, item.getId());
        pstmt.setInt(3, item.getQuantity());
        pstmt.executeUpdate();
        pstmt = connection.prepareStatement("update inventory set quantity = ? where itemId = ?");
        pstmt.setQueryTimeout(30);
        pstmt.setInt(1, item.getProduct().getQuantity() - item.getQuantity());
        pstmt.setInt(2, item.getId());
        pstmt.executeUpdate();
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        try {
          if (generatedKeys.next()) {
            orderItemId = generatedKeys.getInt(1);
            return orderId;
          }
        } catch(SQLException e){
          System.out.println(e.getMessage());
        }
      }
    } catch(SQLException e){
      System.out.println(e.getMessage());
    }
    return -1;
  }
  public Order getOrder(int id) {
    Connection connection = null;
    Order newOrder = new Order();
    try {
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from orders where orderId=" + id);
      rs.next();
      int orderId = rs.getInt(1);
      if (id != orderId) {
        System.out.println("Invalid itemId");
      } else {
        rs = statement.executeQuery("select * from orderItem where orderId=" + orderId);
        while(rs.next()){
          newOrder.addItem(loadProduct(rs.getInt("itemId")),rs.getInt("quantity"));
        };
        newOrder.setOrderNumber(id);
        return newOrder;
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return newOrder;
  }
  public void updateOrderInventory(PurchaseItem item, boolean h) {
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

  public void saveCustomer(Customer customer) {
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("update customers set rewardPoints=" + customer.getRewardPoints() + " where customerId=" + customer.getId());
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
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

  public void updateHistory(int customerId, Order order){
    Connection connection = null;
    try{
      connection = Main.getDbConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date curDate = new Date();
      String date = df.format(curDate);
      ResultSet rs;
      if(customerId > 0){
        int total = 0;
        rs = statement.executeQuery("select * from customerHistory where date='" + date +"'");
        if(rs.next()){
          total = rs.getInt("moneySpent");
          statement.executeUpdate("update customerHistory set moneySpent=" + order.getTotal() + " where date='" + date + "'");
        }
        else
          statement.executeUpdate("insert into customerHistory values('" + date + "', " + customerId + ", " + order.getTotal() + ")");
      }
      List<PurchaseItem> items = order.getOrderList();
      int quantity;
      for(int i = 0; i < items.size(); i++){
        quantity = 0;
        rs = statement.executeQuery("select * from purchaseHistory where date='" + date + "'");
        if(rs.next()){
          quantity = rs.getInt("quantitySold") + items.get(i).getQuantity();
          statement.executeUpdate("update purchaseHistory set quantitySold=" + quantity + " where date='" + date + "'");
        }
        else
          statement.executeUpdate("insert into purchaseHistory values('" + date + "', " + items.get(i).getProduct().getId() + ", " + items.get(i).getQuantity() + ")");
      }
    }
    catch(SQLException e){
      System.out.println(e.getMessage());
    }
  }
  public void generateInventoryReport(String timeFrame){
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date curDate = new Date();
    String startDate = df.format(getPreviousDate(curDate, timeFrame));
    String endDate = df.format(curDate);
    String fileName = getReportName(timeFrame, "Inventory Report", startDate, endDate);
    BufferedWriter writer = null;
    Connection connection = null;
    try{
      File report = new File("../../" + fileName);
      writer = new BufferedWriter(new FileWriter("../../" + fileName));
      writer.write(fileName + "\n");
      try{
        connection = Main.getDbConnection();
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("select * from purchaseHistory where date between '" + startDate + "' and '" + endDate + "'");
        double profit = 0;
        while(rs.next()){
          Product product = loadProduct(rs.getInt("productId"));
          writer.write(rs.getString("date") + ": " +product.getName() + " (id = " + product.getId() + "): " + rs.getInt("quantitySold") + " sold, profit of $" + String.format("%.2f",(product.getPrice() * rs.getInt("quantitySold"))) + "\n");
          profit = profit + (product.getPrice() * rs.getInt("quantitySold"));
        }
        writer.write("Total profit: $" + String.format("%.2f",profit));
      }
      catch(SQLException e){
        System.out.println(e.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        writer.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  public void generateCustomerReport(String timeFrame){
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date curDate = new Date();
    String startDate = df.format(getPreviousDate(curDate, timeFrame));
    String endDate = df.format(curDate);
    String fileName = getReportName(timeFrame, "Customer Report", startDate, endDate);
    BufferedWriter writer = null;
    Connection connection = null;
    try{
      File report = new File("../../" + fileName);
      writer = new BufferedWriter(new FileWriter("../../" + fileName));
      writer.write(fileName + "\n");
      try{
        connection = Main.getDbConnection();
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("select * from customerHistory where date between '" + startDate + "' and '" + endDate + "'");
        while(rs.next()){
          Customer customer = loadCustomer(rs.getInt("customerId"));
          writer.write(rs.getString("date") + ": " + customer.getName() + " (id = " + customer.getId() + "): spent $" + String.format("%.2f",rs.getDouble("moneySpent")) + "\n");
        }
      }
      catch(SQLException e){
        System.out.println(e.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        writer.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  private Date getPreviousDate(Date curDate, String timeFrame){
    Calendar c = Calendar.getInstance();
    c.setTime(curDate);
    if(timeFrame == "Today")
      c.add(Calendar.DAY_OF_YEAR, 0);
    else if(timeFrame == "Week")
      c.add(Calendar.DAY_OF_YEAR, -7);
    else if(timeFrame == "Month")
      c.add(Calendar.MONTH, -1);
    else if(timeFrame == "Quarter")
      c.add(Calendar.MONTH, -3);
    else
      c.add(Calendar.YEAR, -1);
    return c.getTime();
  }
  private String getReportName(String timeFrame, String reportType, String startDate, String endDate){
    if(startDate == endDate)
      return "Daily " + reportType + " " + startDate;
    return timeFrame + " " + reportType + " " + startDate + " to " + endDate;
  }
};

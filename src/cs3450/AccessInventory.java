package cs3450;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;


class Product{
  private int itemId;
  private String name;
  private double price;
  private int quantity;
  private String provider;
  public Product(int id, String name, double price, int quantity, String provider){
    this.itemId = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.provider = provider;
  }
  public int getId(){
    return this.itemId;
  }
  public void setId(int id){
    this.itemId = id;
  }
  public String getName(){
    return this.name;
  }
  public void setName(String name){
    this.name = name;
  }
  public double getPrice(){
    return this.price;
  }
  public void setPrice(double price){
    this.price = price;
  }
  public int getQuantity(){
    return this.quantity;
  }
  public void setQuantity(int quantity){
    this.quantity = quantity;
  }
  public String getProvider(){
    return this.provider;
  }
  public void setProvider(String provider){
    this.provider = provider;
  }
};

interface DataAccess{
  public void saveProduct(Product product);
  public Product loadProduct(int id);
  public void saveNewProduct(Product product);
  public ArrayList<Product> loadAllProducts();
  public void deleteProduct(Product product);
};

class sqliteAdapter implements DataAccess{
  public void saveProduct(Product product){
    Connection connection = null;
    try{
        connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("update inventory set name='"+product.getName()+"', price="+product.getPrice()+", quantity="+product.getQuantity()+", provider='"+product.getProvider()+"' where itemId="+product.getId());
        Main.showMainScreen();
        Main.showInventoryScreen();
    }
    catch(SQLException e){
      System.err.println(e.getMessage());
    }
  }
  public Product loadProduct(int id){
    Connection connection = null;
    Product empty = new Product(0,"Empty",0,0,"Empty");
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
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
    catch(SQLException e){
      System.err.println(e.getMessage());
    }
    return empty;
  }

  public void saveNewProduct(Product product){
    Connection connection = null;
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select count(*) from inventory");
      rs.next();
      int maxCount = rs.getInt(1);
      //				statement.executeUpdate("create table if not exists inventory (itemId integer, name string, price double, quantity integer, provider string)");
      //					statement.executeUpdate("insert into inventory values(" + products.get(i).getId() + ", '" + products.get(i).getName() + "', " + products.get(i).getPrice() + ", " + products.get(i).getQuantity() + ", '" + products.get(i).getProvider() + "')");
      rs = statement.executeQuery("insert into inventory values(" + (maxCount + 1) + ", '" + product.getName() + "', " + product.getPrice() + ", " + product.getQuantity() + ", '" + product.getProvider() + "')");
    }
    catch(SQLException e){
      System.err.println(e.getMessage());
    }
  }

  public ArrayList<Product> loadAllProducts(){
    Connection connection = null;
    ArrayList<Product> products = new ArrayList<Product>();
    try{
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from inventory");
      while(rs.next()){
        products.add(new Product(rs.getInt("itemId"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("provider")));
      }
    }
    catch(SQLException e){
      System.err.println(e.getMessage());
    }
    return products;
  }

  public void deleteProduct(Product product){
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:cs3450.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("delete from inventory where itemId="+product.getId());
    }
    catch(SQLException e){
      System.err.println(e.getMessage());
    }
  }
};

class XLSAdapter implements DataAccess{
  public void deleteProduct(Product product) { }
  public void saveProduct(Product product){  }
  public Product loadProduct(int id){
    Product asdf = new Product(1,"Asd",2.1,5,"BEN");
    return asdf;
  }
  public void saveNewProduct(Product product){}
  public ArrayList<Product> loadAllProducts(){
    ArrayList<Product> products = new ArrayList<Product>();
    try{
      FileInputStream file = new FileInputStream(new File("C:\\Users\\bradt\\Mr.Smith-Grocery\\bin\\sampleInventory.xlsx"));
      XSSFWorkbook workbook = new XSSFWorkbook(file);
      XSSFSheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rowIter = sheet.iterator();
      Row row = rowIter.next(); //Grabbing first row of titles.
      while(rowIter.hasNext()){
        row = rowIter.next();
        Iterator<Cell> cellIter = row.cellIterator();
        Cell cell = cellIter.next();
        int id = (int)cell.getNumericCellValue();
        cell = cellIter.next();
        String name = cell.getStringCellValue();
        cell = cellIter.next();
        double price = cell.getNumericCellValue();
        cell = cellIter.next();
        int quantity = (int)cell.getNumericCellValue();
        cell = cellIter.next();
        String provider = cell.getStringCellValue();
        products.add(new Product(id, name, price, quantity, provider));
      }
      file.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return products;
  }
};

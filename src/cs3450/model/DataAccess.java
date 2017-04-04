package cs3450.model;

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

import cs3450.view.MainScreenView;
import cs3450.view.InventoryScreenView;
import cs3450.control.Main;

public interface DataAccess{
  public void saveProduct(Product product);
  public Product loadProduct(int id);
  public void saveNewProduct(Product product);
  public int saveNewOrder(Order order);
  public int saveNewCustomer(Customer customer);
  public ArrayList<Product> loadAllProducts();
  public void deleteProduct(Product product);
  //public ResultSet findProduct(int id);
};

// class XLSAdapter implements DataAccess{
//   public void deleteProduct(Product product) { }
//   public void saveProduct(Product product){  }
//   public ResultSet findProduct(int id){return null;}
//   public Product loadProduct(int id){
//     Product asdf = new Product(1,"Asd",2.1,5,"BEN");
//     return asdf;
//   }
//   public void saveNewProduct(Product product){}
//   public ArrayList<Product> loadAllProducts(){
//     ArrayList<Product> products = new ArrayList<Product>();
//     try{
//       FileInputStream file = new FileInputStream(new File("C:\\Users\\bradt\\Mr.Smith-Grocery\\bin\\sampleInventory.xlsx"));
//       XSSFWorkbook workbook = new XSSFWorkbook(file);
//       XSSFSheet sheet = workbook.getSheetAt(0);
//       Iterator<Row> rowIter = sheet.iterator();
//       Row row = rowIter.next(); //Grabbing first row of titles.
//       while(rowIter.hasNext()){
//         row = rowIter.next();
//         Iterator<Cell> cellIter = row.cellIterator();
//         Cell cell = cellIter.next();
//         int id = (int)cell.getNumericCellValue();
//         cell = cellIter.next();
//         String name = cell.getStringCellValue();
//         cell = cellIter.next();
//         double price = cell.getNumericCellValue();
//         cell = cellIter.next();
//         int quantity = (int)cell.getNumericCellValue();
//         cell = cellIter.next();
//         String provider = cell.getStringCellValue();
//         products.add(new Product(id, name, price, quantity, provider));
//       }
//       file.close();
//     }
//     catch(Exception e){
//       e.printStackTrace();
//     }
//     return products;
//   }
// };

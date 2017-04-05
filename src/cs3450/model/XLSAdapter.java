package cs3450.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import java.sql.ResultSet;

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

public class XLSAdapter implements DataAccess{
  public void deleteProduct(Product product) { }
  public void saveProduct(Product product){  }
  public Product loadProduct(int id){
    Product asdf = new Product(1,"Asd",2.1,5,"BEN");
    return asdf;
  }
  public void saveNewProduct(Product product){}
  public int saveNewCustomer(Customer customer) {return 0;}
  public int saveNewOrder(Order order){return 0;}
  public ArrayList<Product> loadAllProducts(){
    ArrayList<Product> products = new ArrayList<Product>();
    try{
      FileInputStream file = new FileInputStream(new File("..\\bin\\sampleInventory.xlsx"));
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
  public boolean isValidProductId(int id){return true;}
  public void saveEmployee(Employee employee){}
  public Employee loadEmployee(int id){return null;}
  public void saveNewEmployee(Employee employee){}
  public ArrayList<Employee> loadAllEmployees(){return null;}
  public void deleteEmployee(Employee employee){}
  public int getUserId(String username, String password){return -1;}
  public int getNewEmployeeId() { return -1; }
  public void updateOrderInventory(PurchaseItem item){};

};

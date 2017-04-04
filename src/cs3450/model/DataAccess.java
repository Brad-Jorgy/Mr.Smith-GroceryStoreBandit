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
  public boolean isValidProductId(int id);

  public void saveEmployee(Employee employee);
  public Employee loadEmployee(int id);
  public void saveNewEmployee(Employee employee);
  public ArrayList<Employee> loadAllEmployees();
  public void deleteEmployee(Employee employee);
  public int getUserId(String username, String password);
};

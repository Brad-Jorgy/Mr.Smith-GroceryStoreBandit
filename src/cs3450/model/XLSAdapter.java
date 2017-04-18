package cs3450.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class XLSAdapter implements DataAccess{
  public void deleteProduct(Product product) { }
  public void saveProduct(Product product){  }
  public Product loadProduct(int id){
    Product asdf = new Product(1,"Asd",2.1,5,"BEN");
    return asdf;
  }
  public void saveNewProduct(Product product){}
  public int saveNewCustomer(Customer customer) {return (0);}
  public int saveNewOrder(Order order){return (0);}
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
  public void updateOrderInventory(PurchaseItem item){}
  public Order getOrder(int id){return null;}

}

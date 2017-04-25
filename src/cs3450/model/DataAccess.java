package cs3450.model;

import java.util.*;

public interface DataAccess{
  public void saveProduct(Product product);
  public Product loadProduct(int id);
  public void saveNewProduct(Product product);
  public int saveNewOrder(Order order);
  public int saveNewCustomer(Customer customer);
  public ArrayList<Product> loadAllProducts();
  public void deleteProduct(Product product);
  public boolean isValidProductId(int id);
  public Order getOrder(int id);

  public void saveEmployee(Employee employee);
  public Employee loadEmployee(int id);
  public void saveNewEmployee(Employee employee);
  public ArrayList<Employee> loadAllEmployees();
  public void deleteEmployee(Employee employee);
  public int getUserId(String username, String password);
  public int getNewEmployeeId();
  public void updateItemsCount(PurchaseItem item);
  public void updateOrderInventory(PurchaseItem item, boolean itemReturn);

};

package cs3450.model;

import java.util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import cs3450.control.Main;
import cs3450.control.MainScreenControl;

public class SQLiteAdapter implements DataAccess {

    public void saveProduct(Product product) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("update inventory set name='" + product.getName() + "', price=" + product.getPrice() + ", quantity=" + product.getQuantity() + ", provider='" + product.getProvider() + "' where itemId=" + product.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        MainScreenControl.showInventoryScreen();
    }

    public void saveNewProduct(Product product) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select count(*) from inventory");
            rs.next();
            int maxCount = rs.getInt(1);
            statement.executeUpdate("insert into inventory values(" + (maxCount + 1) + ", '" + product.getName() + "', " + product.getPrice() + ", " + product.getQuantity() + ", '" + product.getProvider() + "')");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int saveOrderItem(PurchaseItem orderItem) {
        Connection connection = null;
        int newId;
        try {
            connection = Main.getDbConnection();
            String sqlString = "insert into orderItems (customerId,orderId,itemId,quantity)values(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlString);
            pstmt.setInt(1, orderItem.getCustomerId());
            pstmt.setInt(2, orderItem.getOrderId());
            pstmt.setInt(3, orderItem.getId());
            pstmt.setInt(4, orderItem.getQuantity());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating orderItem failed, no ID obtained.");
                }
            }
            return newId;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void updateItemsCount(PurchaseItem orderItem){
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            PreparedStatement statement = connection.prepareStatement("update inventory set quantity = ? where itemId = ?");
            statement.setInt(1, orderItem.getQuantity());
            statement.setInt(2, orderItem.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int saveNewOrder(Order order) {
        Connection connection = null;
        int orderId, orderItemId;
        try {
            connection = Main.getDbConnection();
            List<PurchaseItem> olist = order.getOrderList();
            Iterator<PurchaseItem> orderIterator = olist.iterator();
            PurchaseItem item = null;
            PreparedStatement pstmt = connection.prepareStatement("insert into orders (customerId)values(?)");
            pstmt.setInt(1, order.getOrderCustomer());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

            pstmt = connection.prepareStatement("insert into orderItem (customerId,orderId,itemId,quantity)values(?,?,?,?)");
            while (orderIterator.hasNext()) {
                item = orderIterator.next();
                pstmt.setInt(1, order.getOrderCustomer());
                pstmt.setInt(2, orderId);
                pstmt.setInt(3, item.getId());
                pstmt.setInt(4, item.getQuantity());
                pstmt.executeUpdate();
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderItemId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating orderItem failed, no ID obtained.");
                    }
                }
            }
            return orderId;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int saveNewCustomer(Customer customer) {
        Connection connection = null;
        int newId;
        try {
            connection = Main.getDbConnection();
            String sqlString = "insert into customers (name,creditCard,address,city,state,zipcode,country,loyalty)values(?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sqlString);
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getCreditCard());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getCity());
            pstmt.setString(5, customer.getState());
            pstmt.setString(6, customer.getZipcode());
            pstmt.setString(7, customer.getCountry());
            pstmt.setString(8, customer.getLoyalty());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            return newId;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public Product loadProduct(int id) {
        Connection connection = null;
        Product empty = new Product(0, "Empty", 0, 0, "Empty");
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select count(*) from inventory");
            rs.next();
            int maxCount = rs.getInt(1);
            if (id > maxCount || id < 1) {
                System.out.println("Invalid itemId");
            } else {
                rs = statement.executeQuery("select * from inventory where itemId=" + id);
                return new Product(id, rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("provider"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return empty;
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
                newOrder.setOrderCustomer(rs.getInt("customerId"));
                newOrder.setOrderNumber(orderId);
                rs = statement.executeQuery("select * from orderItem where orderId=" + orderId);
                while(rs.next()){
                    newOrder.addItem(loadProduct(rs.getInt("itemId")),rs.getInt("quantity"));
                };
            return newOrder;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newOrder;
    }

    public ArrayList<Product> loadAllProducts() {
        Connection connection = null;
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from inventory");
            while (rs.next()) {
                products.add(new Product(rs.getInt("itemId"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("provider")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public void deleteProduct(Product product) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("delete from inventory where itemId=" + product.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isValidProductId(int id) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from inventory where itemId=" + id);
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int getNewProductId() {
        Connection connection = null;
        return -1;
    }

    public void updateOrderInventory(PurchaseItem item, boolean isReturn) {
        Connection connection = null;
        Product prodIn = loadProduct(item.getId());
        try {
            connection = Main.getDbConnection();
            int currentQuantity = prodIn.getQuantity();
            if(isReturn) {
                currentQuantity += item.getQuantity();
            } else {
                currentQuantity -= item.getQuantity();
            }
            PreparedStatement statement = connection.prepareStatement("update inventory set quantity = ? where itemId = ?");
            statement.setInt(1, currentQuantity);
            statement.setInt(2, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveEmployee(Employee employee) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("update employees set name='" + employee.getName() + "', username='" + employee.getUsername() + "', password='" + employee.getPassword() + "', position='" + employee.getPosition() + "' where employeeId=" + employee.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        MainScreenControl.showEmployeeScreen();
    }

    public Employee loadEmployee(int id) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from employees where employeeId=" + id);
            if (rs.next())
                return new Employee(rs.getInt("employeeId"), rs.getString("name"), rs.getBinaryStream("image"), rs.getString("username"), rs.getString("password"), rs.getString("position"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void saveNewEmployee(Employee employee) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("insert into employees values(" + employee.getId() + ", '" + employee.getName() + "', " + employee.getImage() + ", '" + employee.getUsername() + "', '" + employee.getPassword() + "', '" + employee.getPosition() + "')");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Employee> loadAllEmployees() {
        Connection connection = null;
        ArrayList<Employee> employees = new ArrayList<Employee>();
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from employees");
            while (rs.next()) {
                employees.add(new Employee(rs.getInt("employeeId"), rs.getString("name"), rs.getBinaryStream("image"), rs.getString("username"), rs.getString("password"), rs.getString("position")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    public void deleteEmployee(Employee employee) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("delete from employees where employeeId=" + employee.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getUserId(String username, String password) {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from employees where username='" + username + "' and password='" + password + "'");
            if (rs.next())
                return rs.getInt("employeeId");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getNewEmployeeId() {
        Connection connection = null;
        try {
            connection = Main.getDbConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select count(*) from employees");
            rs.next();
            int maxCount = rs.getInt(1);
            return maxCount + 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
};

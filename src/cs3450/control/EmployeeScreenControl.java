package cs3450.control;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import cs3450.model.Employee;
import cs3450.model.Product;
import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;

public class EmployeeScreenControl{

  static public ArrayList<Employee> getAllEmployees(){
    return Main.getSQLiteAccess().loadAllEmployees();
  }

  static public void showUpdateEmployeePopup(Employee employee){
      JPanel popupPanel = new JPanel();
      popupPanel.setLayout(new GridLayout(5,2));
      JTextField nameTF = new JTextField(employee.getName());
      JTextField usernameTF = new JTextField("" + employee.getUsername(), 20);
      JTextField passwordTF = new JTextField("" + employee.getPassword(), 20);
      JTextField positionTF = new JTextField(employee.getPosition(), 20);
      popupPanel.add(new JLabel("Id: "));
      popupPanel.add(new JLabel("" + employee.getId()));
      popupPanel.add(new JLabel("Name: "));
      popupPanel.add(nameTF);
      popupPanel.add(new JLabel("Username: "));
      popupPanel.add(usernameTF);
      popupPanel.add(new JLabel("Password: "));
      popupPanel.add(passwordTF);
      popupPanel.add(new JLabel("Position: "));
      popupPanel.add(positionTF);
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Update Employee:", JOptionPane.OK_CANCEL_OPTION);
      if(result ==JOptionPane.OK_OPTION){
        if(areValuesValid(nameTF.getText(), usernameTF.getText(), passwordTF.getText(), positionTF.getText())){
          DataAccess db = Main.getSQLiteAccess();
          employee.setName(nameTF.getText());
          employee.setUsername(usernameTF.getText());
          employee.setPassword(passwordTF.getText());
          employee.setPosition(positionTF.getText());
          db.saveEmployee(employee);
        }
        else{
          System.out.println("Fail save");
        }
      }
      updateEmployeeScreen();
    }

    static public void showAddEmployeePopup(){
      JPanel popupPanel = new JPanel();
      popupPanel.setLayout(new GridLayout(5,2));
      JTextField nameTF = new JTextField("");
      JTextField usernameTF = new JTextField("", 20);
      JTextField passwordTF = new JTextField("", 20);
      JTextField positionTF = new JTextField("", 20);
      popupPanel.add(new JLabel("Name: "));
      popupPanel.add(nameTF);
      popupPanel.add(new JLabel("Username: "));
      popupPanel.add(usernameTF);
      popupPanel.add(new JLabel("Password: "));
      popupPanel.add(passwordTF);
      popupPanel.add(new JLabel("Position: "));
      popupPanel.add(positionTF);
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Add Employee:", JOptionPane.OK_CANCEL_OPTION);
      if(result ==JOptionPane.OK_OPTION){
        if(areValuesValid(nameTF.getText(), usernameTF.getText(), passwordTF.getText(), positionTF.getText())){
          DataAccess db = new SQLiteAdapter();
          int newId = db.getNewEmployeeId();
          db.saveNewEmployee(new Employee(newId, nameTF.getText(), null, usernameTF.getText(), passwordTF.getText(), positionTF.getText()));
        }
        else{
          System.out.println("Fail save");
        }
      }
      updateEmployeeScreen();
    }

    static public void showDeleteEmployeePopup(Employee employee){
      JPanel popupPanel = new JPanel();
      popupPanel.setLayout(new GridLayout(5,2));
      popupPanel.add(new JLabel("Id: "));
      popupPanel.add(new JLabel("" + employee.getId()));
      popupPanel.add(new JLabel("Name: "));
      popupPanel.add(new JLabel(employee.getName()));
      popupPanel.add(new JLabel("Username: "));
      popupPanel.add(new JLabel(employee.getUsername()));
      popupPanel.add(new JLabel("Password: "));
      popupPanel.add(new JLabel(employee.getPassword()));
      popupPanel.add(new JLabel("Position: "));
      popupPanel.add(new JLabel(employee.getPosition()));
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Delete Product??", JOptionPane.YES_NO_OPTION);
      if(result ==JOptionPane.YES_OPTION){
       DataAccess db = Main.getSQLiteAccess();
       db.deleteEmployee(employee);
      }
      updateEmployeeScreen();
    }

    static public boolean areValuesValid(String name, String username, String password, String position){
      if("".equals(name)){
        System.out.println("Error: Empty Name Entry.");
        return false;
      }
      if("".equals(username)){
        System.out.println("Error: Empty Username Entry.");
        return false;
      }
      if("".equals(password)){
        System.out.println("Error: Empty Password Entry.");
        return false;
      }
      if(!"Manager".equals(position)&&!"Cashier".equals(position)&&!"Customer Support".equals(position)){
        System.out.println("Error: Invalid Position (Must be set to Manager, Cashier, or Customer Support.)");
        return false;
      }
      return true;
    }

    static public void updateEmployeeScreen(){
      try{
          TimeUnit.SECONDS.sleep(1);
      }
      catch(InterruptedException e){
        System.err.println(e.getMessage());
      }
      MainScreenControl.showEmployeeScreen();
    }
}

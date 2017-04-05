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

public class ProfileScreenControl{

  static public void showUpdateInfoPopup(Employee employee){
      JPanel popupPanel = new JPanel();
      popupPanel.setLayout(new GridLayout(5,2));
      JTextField usernameTF = new JTextField("" + employee.getUsername(), 20);
      JTextField passwordTF = new JTextField("", 20);
      popupPanel.add(new JLabel("Username: "));
      popupPanel.add(usernameTF);
      popupPanel.add(new JLabel("Password: "));
      popupPanel.add(passwordTF);
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Update Info:", JOptionPane.OK_CANCEL_OPTION);
      if(result ==JOptionPane.OK_OPTION){
        if(areValuesValid(usernameTF.getText(), passwordTF.getText())){
          DataAccess db = Main.getSQLiteAccess();
          employee.setUsername(usernameTF.getText());
          employee.setPassword(passwordTF.getText());
          db.saveEmployee(employee);
        }
      }
      updateProfileScreen();
    }

    static public boolean areValuesValid(String username, String password){
      if("".equals(username)){
        JOptionPane.showMessageDialog(null, "Invalid Username: Username cannot be empty.");
        return false;
      }
      if("".equals(password)){
        JOptionPane.showMessageDialog(null, "Invalid Password: Password cannot be empty.");
        return false;
      }
      return true;
    }

    static public void logout(){
      MainScreenControl.showLoginScreen();
    }

    static public void updateProfileScreen(){
      try{
          TimeUnit.SECONDS.sleep(1);
      }
      catch(InterruptedException e){
        System.err.println(e.getMessage());
      }
      MainScreenControl.showProfileScreen();
    }
}

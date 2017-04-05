package cs3450.control;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import cs3450.model.Product;
import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;

public class InventoryScreenControl{

  static public ArrayList<Product> getInventoryProducts(){
    return Main.getSQLiteAccess().loadAllProducts();
  }

  static public void showUpdateProductPopup(Product product){
      JPanel popupPanel = new JPanel();
      popupPanel.setLayout(new GridLayout(5,2));
      JTextField nameTF = new JTextField(product.getName());
      JTextField priceTF = new JTextField("" + product.getPrice(), 20);
      JTextField quantityTF = new JTextField("" + product.getQuantity(), 20);
      JTextField providerTF = new JTextField(product.getProvider(), 20);
      popupPanel.add(new JLabel("Id: "));
      popupPanel.add(new JLabel("" + product.getId()));
      popupPanel.add(new JLabel("Name: "));
      popupPanel.add(nameTF);
      popupPanel.add(new JLabel("Price($): "));
      popupPanel.add(priceTF);
      popupPanel.add(new JLabel("Quantity: "));
      popupPanel.add(quantityTF);
      popupPanel.add(new JLabel("Provider: "));
      popupPanel.add(providerTF);
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Update Product:", JOptionPane.OK_CANCEL_OPTION);
      if(result ==JOptionPane.OK_OPTION){
        if(areValuesValid(nameTF.getText(), priceTF.getText(), quantityTF.getText(), providerTF.getText())){
          DataAccess db = Main.getSQLiteAccess();
          product.setName(nameTF.getText());
          product.setPrice(Double.parseDouble(priceTF.getText()));
          product.setQuantity(Integer.parseInt(quantityTF.getText()));
          product.setProvider(providerTF.getText());
          db.saveProduct(product);
        }
      }
      updateInventoryScreen();
    }

    static public void showAddProductPopup(){
      JPanel popupPanel = new JPanel();
      popupPanel.setLayout(new GridLayout(5,2));
      JTextField nameTF = new JTextField();
      JTextField priceTF = new JTextField();
      JTextField quantityTF = new JTextField();
      JTextField providerTF = new JTextField();
      popupPanel.add(new JLabel("Name: "));
      popupPanel.add(nameTF);
      popupPanel.add(new JLabel("Price($): "));
      popupPanel.add(priceTF);
      popupPanel.add(new JLabel("Quantity: "));
      popupPanel.add(quantityTF);
      popupPanel.add(new JLabel("Provider: "));
      popupPanel.add(providerTF);
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Add Product:", JOptionPane.OK_CANCEL_OPTION);
      if(result ==JOptionPane.OK_OPTION){
        if(areValuesValid(nameTF.getText(), priceTF.getText(), quantityTF.getText(), providerTF.getText())){
          DataAccess db = Main.getSQLiteAccess();
          Product product = new Product(9867, nameTF.getText(), Double.parseDouble(priceTF.getText()), Integer.parseInt(quantityTF.getText()), providerTF.getText());
          db.saveNewProduct(product);
        }
      }
      updateInventoryScreen();
    }

    static public void showDeleteProductPopup(Product product){
      JPanel popupPanel = new JPanel();
       popupPanel.setLayout(new GridLayout(5,2));
      popupPanel.add(new JLabel("Id: "));
      popupPanel.add(new JLabel("" + product.getId()));
      popupPanel.add(new JLabel("Name: "));
      popupPanel.add(new JLabel(product.getName()));
      popupPanel.add(new JLabel("Price($): "));
      popupPanel.add(new JLabel("" + product.getPrice()));
      popupPanel.add(new JLabel("Quantity: "));
      popupPanel.add(new JLabel("" + product.getQuantity()));
      popupPanel.add(new JLabel("Provider: "));
      popupPanel.add(new JLabel(product.getProvider()));
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Delete Product??", JOptionPane.YES_NO_OPTION);
      if(result ==JOptionPane.YES_OPTION){
        DataAccess db = Main.getSQLiteAccess();
        db.deleteProduct(product);
      }
      updateInventoryScreen();
    }

    static public boolean areValuesValid(String name, String price, String quantity, String provider){
      if("".equals(name)){
        JOptionPane.showMessageDialog(null, "Invalid Name: Name cannot be empty.");
        return false;
      }
      if(!price.matches("[0-9]*.?[0-9]?[0-9]?")){
        JOptionPane.showMessageDialog(null, "Error: Invalid Price.");
        return false;
      }
      if(!quantity.matches("[0-9]*")){
        JOptionPane.showMessageDialog(null, "Error: Invalid Quantity.");
        return false;
      }
      if("".equals(provider)){
        JOptionPane.showMessageDialog(null, "Invalid Provider: Provider cannot be empty.");
        return false;
      }
      return true;
    }

    static public void updateInventoryScreen(){
      try{
          TimeUnit.SECONDS.sleep(1);
      }
      catch(InterruptedException e){
        System.err.println(e.getMessage());
      }
      MainScreenControl.showInventoryScreen();
    }
}

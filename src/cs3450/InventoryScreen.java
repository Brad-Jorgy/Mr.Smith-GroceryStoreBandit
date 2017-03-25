package cs3450;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.mongodb.MongoClient;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class InventoryScreen {
    public static void addComponentsToPane(Container pane)
    {
      JLabel title = new JLabel("Inventory", SwingConstants.CENTER);
      DefaultListModel listModel = new DefaultListModel();
      JButton backBtn = new JButton("Back to Main Screen");
      JButton deleteBtn = new JButton("Delete Selected Product");
      JButton updateBtn = new JButton("Update Selected Product");
      JButton addBtn = new JButton("Add Product");
      DataAccess db = new sqliteAdapter();

      // DataAccess db = new XLSAdapter();
      ArrayList<Product> products = db.loadAllProducts();
      for(int i = 0; i < products.size(); i++){
        listModel.addElement(products.get(i).getId()+": "+products.get(i).getName()+" $"+products.get(i).getPrice()+", "+products.get(i).getQuantity()+" in stock, ("+products.get(i).getProvider()+")");
      }
      JList list = new JList(listModel);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.setSelectedIndex(0);
      list.setVisibleRowCount(5);
      list.setLayoutOrientation(JList.VERTICAL);
      JScrollPane listScrollPane = new JScrollPane(list);
      pane.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 1;
      c.weighty = 1;
      c.gridx = 0;
      c.gridy = 0;
      c.gridwidth = 4;
      backBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e) {
          Main.showMainScreen();
        }
      });
      deleteBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
            showDeleteProductPopup(products.get(list.getSelectedIndex()));
        }
      });
      updateBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
            showUpdateProductPopup(products.get(list.getSelectedIndex()));
        }
      });
      addBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
          showAddProductPopup();
        }
      });
      pane.add(title, c);
      c.gridx = 0;
      c.gridy = 1;
      pane.add(listScrollPane, c);
      c.gridx = 0;
      c.gridy = 2;
      c.gridwidth = 1;
      pane.add(backBtn, c);
      c.gridx = 1;
      pane.add(deleteBtn, c);
      c.gridx = 2;
      pane.add(updateBtn, c);
      c.gridx = 3;
      pane.add(addBtn, c);
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
        if(areValuesValid(priceTF.getText(), quantityTF.getText())){
          DataAccess db = new sqliteAdapter();
          product.setName(nameTF.getText());
          product.setPrice(Double.parseDouble(priceTF.getText()));
          product.setQuantity(Integer.parseInt(quantityTF.getText()));
          product.setProvider(providerTF.getText());
          db.saveProduct(product);
        }
        else{
          System.out.println("Fail save");
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
        if(areValuesValid(priceTF.getText(), quantityTF.getText())){
          DataAccess db = new sqliteAdapter();
          Product product = new Product(9867, nameTF.getText(), Double.parseDouble(priceTF.getText()), Integer.parseInt(quantityTF.getText()), providerTF.getText());
          db.saveNewProduct(product);
        }
        else{
          System.out.println("Fail save new product.");
        }
      }
      updateInventoryScreen();
    }

    static public void showDeleteProductPopup(Product product){
      JPanel popupPanel = new JPanel();
      popupPanel.setLayout(new GridLayout(5,2));
      JTextField nameTF = new JTextField(product.getName());
      JTextField priceTF = new JTextField("" + product.getPrice(), 20);
      JTextField quantityTF = new JTextField("" + product.getQuantity(), 20);
      JTextField providerTF = new JTextField(product.getProvider(), 20);
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
        if(areValuesValid(priceTF.getText(), quantityTF.getText())){
          DataAccess db = new sqliteAdapter();
          db.deleteProduct(product);
        }
        else{
          System.out.println("Fail save");
        }
      }
      updateInventoryScreen();
    }

    static public boolean areValuesValid(String price, String quantity){
      if(!price.matches("[0-9]*.?[0-9]?[0-9]?")){
        System.out.println("Error: invalid price");
        return false;
      }
      if(!quantity.matches("[0-9]*")){
        System.out.println("Error: invalid quantity");
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
      Main.showInventoryScreen();
    }
}

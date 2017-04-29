package cs3450.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import cs3450.control.MainScreenControl;
import cs3450.control.InventoryScreenControl;
import cs3450.model.Product;
import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;

public class InventoryScreenView {
    public static void addComponentsToPane(Container pane)
    {
      JLabel title = new JLabel("Inventory", SwingConstants.CENTER);
      DefaultListModel listModel = new DefaultListModel();
      JButton backBtn = new JButton("Back to Main Screen");
      JButton deleteBtn = new JButton("Delete Selected Product");
      JButton updateBtn = new JButton("Update Selected Product");
      JButton addBtn = new JButton("Add Product");
      JButton reportsBtn = new JButton("Generate Reports");
      ArrayList<Product> products = InventoryScreenControl.getInventoryProducts();
      for(int i = 0; i < products.size(); i++){
        listModel.addElement(products.get(i).getId()+": "+products.get(i).getName()+" $"+products.get(i).getPrice()+" (discount: $"+products.get(i).getDiscountPrice()+"), "+products.get(i).getQuantity()+" in stock, ("+products.get(i).getProvider()+")");
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
      c.gridwidth = 5;
      backBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e) {
          MainScreenControl.showMainScreen();
        }
      });
      deleteBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
            InventoryScreenControl.showDeleteProductPopup(products.get(list.getSelectedIndex()));
        }
      });
      updateBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
          InventoryScreenControl.showUpdateProductPopup(products.get(list.getSelectedIndex()));
        }
      });
      addBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
          InventoryScreenControl.showAddProductPopup();
        }
      });
      reportsBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
          InventoryScreenControl.showReportsPopup();
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
      c.gridx = 4;
      pane.add(reportsBtn, c);
    }
}

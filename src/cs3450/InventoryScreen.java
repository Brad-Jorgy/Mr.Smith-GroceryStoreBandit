package cs3450;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.mongodb.MongoClient;

public class InventoryScreen implements ListSelectionListener{

    public static void addComponentsToPane(Container pane)
    {
      JLabel title = new JLabel("Inventory", SwingConstants.CENTER);
      DefaultListModel listModel = new DefaultListModel();
      JButton backBtn = new JButton("Back to Main Screen");
      JButton updateBtn = new JButton("Update Selected Product");
      JButton addBtn = new JButton("Add Product");
      listModel.addElement("Sam");
      listModel.addElement("Sam1");
      listModel.addElement("Sam2");
      listModel.addElement("Sam3");
      listModel.addElement("Sam4");
      JList list = new JList(listModel);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.setSelectedIndex(0);
      //list.addListSelectionListener(this);
      list.setVisibleRowCount(5);
      list.setLayoutOrientation(JList.VERTICAL);
      JScrollPane listScrollPane = new JScrollPane(list);
      //JButton inventoryBtn = new JButton("Noob");

      pane.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 1;
      c.weighty = 1;
      c.gridx = 0;
      c.gridy = 0;
      c.gridwidth = 3;
      backBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e) {
          Main.showMainScreen();
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
      pane.add(updateBtn, c);
      c.gridx = 2;
      pane.add(addBtn, c);
    }

    public void valueChanged(ListSelectionEvent e) {
      //  if (e.getValueIsAdjusting() == false) {
       //
      //      if (list.getSelectedIndex() == -1) {
      //      //No selection, disable fire button.
      //          fireButton.setEnabled(false);
       //
      //      } else {
      //      //Selection, enable the fire button.
      //          fireButton.setEnabled(true);
      //      }
      //  }
   }
}

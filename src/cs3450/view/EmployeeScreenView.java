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
import cs3450.control.EmployeeScreenControl;
import cs3450.model.Employee;
import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;

public class EmployeeScreenView {
    public static void addComponentsToPane(Container pane)
    {
      JLabel title = new JLabel("Employees", SwingConstants.CENTER);
      DefaultListModel listModel = new DefaultListModel();
      JButton backBtn = new JButton("Back to Main Screen");
      JButton deleteBtn = new JButton("Delete Selected Employee");
      JButton updateBtn = new JButton("Update Selected Employee");
      JButton addBtn = new JButton("Add Employee");
      ArrayList<Employee> employees = EmployeeScreenControl.getAllEmployees();
      for(int i = 0; i < employees.size(); i++){
        listModel.addElement(employees.get(i).getId()+") Name: "+employees.get(i).getName()+"     Username: "+employees.get(i).getUsername()+", Password: "+employees.get(i).getPassword()+"     Position: "+employees.get(i).getPosition());
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
          MainScreenControl.showMainScreen();
        }
      });
      deleteBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
            EmployeeScreenControl.showDeleteEmployeePopup(employees.get(list.getSelectedIndex()));
        }
      });
      updateBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
          EmployeeScreenControl.showUpdateEmployeePopup(employees.get(list.getSelectedIndex()));
        }
      });
      addBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
          EmployeeScreenControl.showAddEmployeePopup();
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
}

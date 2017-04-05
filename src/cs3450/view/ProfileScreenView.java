package cs3450.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import cs3450.control.*;
import cs3450.model.*;

public class ProfileScreenView {
    public static void addComponentsToPane(Container pane)
    {
      JLabel title = new JLabel("Profile", SwingConstants.CENTER);
      DefaultListModel listModel = new DefaultListModel();
      JButton backBtn = new JButton("Back to Main Screen");
      JButton updateBtn = new JButton("Update Username and Password");
      Employee employee = MainScreenControl.getCurrEmployee();

      JPanel idPanel = new JPanel();
      idPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      idPanel.add(new JLabel("Id: "));
      idPanel.add(new JLabel("" + employee.getId()));

      JPanel namePanel = new JPanel();
      namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      namePanel.add(new JLabel("Name: "));
      namePanel.add(new JLabel(employee.getName()));

      JPanel usernamePanel = new JPanel();
      usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      usernamePanel.add(new JLabel("Username: "));
      usernamePanel.add(new JLabel(employee.getUsername()));

      JPanel passwordPanel = new JPanel();
      passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      passwordPanel.add(new JLabel("Password: "));
      passwordPanel.add(new JLabel("********"));

      JPanel positionPanel = new JPanel();
      positionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      positionPanel.add(new JLabel("Position: "));
      positionPanel.add(new JLabel(employee.getPosition()));
      backBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e) {
          MainScreenControl.showMainScreen();
        }
      });
      updateBtn.addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e){
          ProfileScreenControl.showUpdateInfoPopup(employee);
        }
      });

      pane.setLayout(new GridLayout(9,1));
      pane.add(new JLabel(" "));
      pane.add(title);
      pane.add(idPanel);
      pane.add(namePanel);
      pane.add(usernamePanel);
      pane.add(passwordPanel);
      pane.add(positionPanel);
      pane.add(updateBtn);
      pane.add(backBtn);
    }
}

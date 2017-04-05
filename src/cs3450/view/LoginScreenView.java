package cs3450.view;

import java.awt.Container;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cs3450.model.DataAccess;
import cs3450.model.SQLiteAdapter;
import cs3450.model.Product;
import cs3450.control.MainScreenControl;
import cs3450.control.LoginScreenControl;

public class LoginScreenView {
    public static void addComponentsToPane(Container pane)
    {
				JLabel title = new JLabel("Store Management System", SwingConstants.CENTER);
        JButton loginBtn = new JButton("Login");

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JTextField usernameTF = new JTextField("", 15);
        usernamePanel.add(new JLabel("Username: "));
        usernamePanel.add(usernameTF);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPasswordField passwordTF = new JPasswordField("", 15);
        passwordPanel.add(new JLabel("Password: "));
        passwordPanel.add(passwordTF);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(loginBtn);

        loginBtn.addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e) {
            LoginScreenControl.attemptLogin(usernameTF.getText(), passwordTF.getText());
					}
				});

        pane.setLayout(new GridLayout(15,1));
        pane.add(new JLabel(" "));
        pane.add(new JLabel(" "));
        pane.add(new JLabel(" "));
        pane.add(new JLabel(" "));
        pane.add(title);
        pane.add(usernamePanel);
        pane.add(passwordPanel);
        pane.add(btnPanel);

    }
}

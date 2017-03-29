package cs3450;

import java.awt.*;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;
import static cs3450.SearchData.*;

import static cs3450.InventoryScreen.areValuesValid;
import static cs3450.InventoryScreen.updateInventoryScreen;
import static cs3450.Main.updateFrame;
//import static cs3450.PaymentScreen.frame;

public class CheckoutScreen {

    public static void addComponentsToPane(Container pane) {
        JButton backBtn = new JButton("Back to Main Screen");
        JLabel title = new JLabel("Check Out", SwingConstants.CENTER);
        JButton addItemBtn = new JButton("Add Item To Basket");
        JButton deleteBtn = new JButton("Delete Item From Basket");
        JButton checkoutBtn = new JButton("Check Out and Pay");
        DefaultListModel listModel = new DefaultListModel();
        JList list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        pane.setLayout(new GridBagLayout());
        list.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listInvenScrollPane = new JScrollPane(list);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;
        //c.gridheight = 1;

        backBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Main.showMainScreen();
            }
        });

        addItemBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // add item to queue.
            }
        });

        pane.add(title, c);
        c.gridx = 0;
        c.gridy = 1;
        pane.add(listInvenScrollPane, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        pane.add(backBtn, c);
        c.gridx = 2;
        pane.add(addItemBtn, c);
        c.gridx = 3;
        pane.add(deleteBtn, c);
        c.gridx = 4;
        pane.add(checkoutBtn, c);

        checkoutBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Main.showPaymentScreen();
            }
        });

        addItemBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                showAddItemInQueue();
            }
        });
    }


        static public void showAddItemInQueue(){
            JPanel popupPanel = new JPanel();
            popupPanel.setLayout(new GridLayout(5,2));
            JTextField nameTF = new JTextField();
            //JTextField priceTF = new JTextField();
            JTextField quantityTF = new JTextField();
            //JTextField providerTF = new JTextField();
            popupPanel.add(new JLabel("Name: "));
            popupPanel.add(nameTF);
            popupPanel.add(new JLabel("Quantity: "));
            popupPanel.add(quantityTF);
            int result = JOptionPane.showConfirmDialog(null, popupPanel, "Add To Basket:", JOptionPane.OK_CANCEL_OPTION);
            if(result ==JOptionPane.OK_OPTION) {
                SearchData db = new sqlSearchAdapter();
                ResultSet found = db.findProduct(Integer.parseInt(nameTF.getText()));

            }
            updateCheckoutScreen();
        }

    static public void updateCheckoutScreen(){
        try{
            TimeUnit.SECONDS.sleep(1);
        }
        catch(InterruptedException e){
            System.err.println(e.getMessage());
        }
        //Main.showCheckoutScreen();
        Main.updateFrame();
    }
    }





package cs3450.view;

import cs3450.control.ReturnItemScreenControl;
import cs3450.control.MainScreenControl;
import cs3450.model.Order;
import cs3450.model.PurchaseItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;


/**
 * Created by bradt on 4/4/2017.
 */
public class ReturnItemScreenView {
        public static void addComponentsToPane(Container pane, Order order) {
            JButton backBtn = new JButton("Back to Main Screen");
            JLabel title = new JLabel("Return Item", SwingConstants.CENTER);
            JButton selectOrderNumBtn = new JButton("Select Order");
            JButton deleteBtn = new JButton("Delete Item");
            JButton editQuantityBtn = new JButton("Edit Quantity");
            JButton processReturnBtn = new JButton("Process Return");


            DefaultListModel listModel = new DefaultListModel();
            java.util.List<PurchaseItem> olist = order.getOrderList();
            Iterator<PurchaseItem> orderIterator = olist.iterator();
            while( orderIterator.hasNext()) {
                PurchaseItem item = orderIterator.next();
                listModel.addElement(item);
//            listModel.addElement(item.getId()+": "+item.getProduct().getName() +" $"+item.getProduct().getPrice()+", "+item.getProduct().getQuantity()+" in stock, ("+item.getProduct().getProvider()+")");

            }
            JList list = new JList(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        ListSelectionModel listSelectionModel = list.getSelectionModel();
//        listSelectionModel.addListSelectionListener(
//                new SharedListSelectionHandler());
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

            pane.add(title, c);
            c.gridx = 0;
            c.gridy = 1;
            pane.add(listInvenScrollPane, c);
            c.gridx = 1;
            c.gridy = 3;
            c.gridwidth = 1;
            pane.add(backBtn, c);
            c.gridx = 2;
            pane.add(deleteBtn, c);
            c.gridx = 3;
            pane.add(editQuantityBtn, c);
            c.gridx = 4;
            pane.add(processReturnBtn, c);
            c.gridx = 5;
            pane.add(selectOrderNumBtn, c);



            backBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    MainScreenControl.showMainScreen();
                }
            });

            editQuantityBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    //ReturnItemScreenControl.showEditQuantityPopup((PurchaseItem)list.getSelectedValue());
                }
            });

           processReturnBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
//                    ReturnItemScreenControl.showCardPaymentScreen(MainScreenControl.getFrame(), order);
                    MainScreenControl.showMainScreen();
                }
            });

            selectOrderNumBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    ReturnItemScreenControl.selectOrderToEditPopup();
                }
            });

            deleteBtn.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    order.removeItem(((PurchaseItem)list.getSelectedValue()).getProduct());
                  // ReturnItemScreenControl.deleteItem((PurchaseItem)list.getSelectedValue() );
                }
            });

        }

    };



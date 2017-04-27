package cs3450.control;

import cs3450.model.DataAccess;
import cs3450.model.Order;
import cs3450.model.PurchaseItem;
import cs3450.view.CardPaymentScreenView;
import cs3450.view.CashPaymentScreenView;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by bradt on 4/16/2017.
 */
public class CustomerPickScreenControl {

    static public void showEditCustomerPopup(){

        NumberFormat nf = NumberFormat.getInstance();
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(3,8));
        JTextField name = new JTextField();
        JTextField address = new JTextField();

        popupPanel.add(new JLabel("Customer Info"));
        popupPanel.add(name);
        popupPanel.add(address);

        int result = JOptionPane.showConfirmDialog(null, popupPanel, "Customer Info", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
//            int count = Integer.parseInt(quantityTF.getText());
//            if(count <= 0) {
//                order.removeItem(purchaseItem.getProduct());
//            } else {
//                order.editItem(purchaseItem.getProduct(), Integer.parseInt(quantityTF.getText()));
//            }
//        }
//        else{
//            System.out.println("Fail save");
       }
//        updateCheckoutScreen(order);
    }

    static public void addItemToBasketPopup(Order order) {
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(5, 2));
        JTextField idTF = new JTextField();
        //JTextField priceTF = new JTextField();
        JTextField quantityTF = new JTextField();
        //JTextField providerTF = new JTextField();
        popupPanel.add(new JLabel("Product Id: "));
        popupPanel.add(idTF);
        popupPanel.add(new JLabel("Quantity: "));
        popupPanel.add(quantityTF);
        int result = JOptionPane.showConfirmDialog(null, popupPanel, "Add To Basket:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            DataAccess db = Main.getSQLiteAccess();
            order.addItem(db.loadProduct(Integer.parseInt(idTF.getText())),Integer.parseInt(quantityTF.getText()));
        }
        updateCheckoutScreen(order);
    }

    static public void updateDB(Order order) {
        java.util.List<PurchaseItem> olist = order.getOrderList();
        Iterator<PurchaseItem> orderIterator = olist.iterator();
        while( orderIterator.hasNext()) {
            PurchaseItem item = orderIterator.next();
            DataAccess db = Main.getSQLiteAccess();
            db.updateOrderInventory(item);
        }
    }



    static public void updateCheckoutScreen(Order order) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        MainScreenControl.showCheckoutScreen();
    }

    public static void showCardPaymentScreen(JFrame frame, Order order) {
        frame.getContentPane().removeAll();
        CardPaymentScreenView.addComponentsToPane(frame.getContentPane(), order);
        MainScreenControl.updateFrame();
    }

    public static void showCashPaymentScreen(JFrame frame, Order order) {
        frame.getContentPane().removeAll();
        CashPaymentScreenView.addComponentsToPane(frame.getContentPane(), order);
        MainScreenControl.updateFrame();
    }

}

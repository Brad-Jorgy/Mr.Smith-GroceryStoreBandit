package cs3450.control;

import java.awt.*;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.List;
import cs3450.model.*;

import cs3450.view.CardPaymentScreenView;
import cs3450.view.CashPaymentScreenView;


public class CheckoutScreenControl {

    static public void showEditOrderPopup(Order order, PurchaseItem purchaseItem){

        NumberFormat nf = NumberFormat.getInstance();
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(3,8));
        JTextField quantityTF = new JTextField(nf.format(purchaseItem.getQuantity()), 4);
        JTextField productNumTF = new JTextField(nf.format(purchaseItem.getId()), 4);
        popupPanel.add(new JLabel("Quantity: "));
        popupPanel.add(quantityTF);

        int result = JOptionPane.showConfirmDialog(null, popupPanel, "Edit Quantity", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            int requestedQuantity = Integer.parseInt(quantityTF.getText());
            DataAccess db = Main.getSQLiteAccess();
            int availableQuantity = db.loadProduct(purchaseItem.getProduct().getId()).getQuantity();
            if(availableQuantity < requestedQuantity){
                order.editItem(purchaseItem.getProduct(), availableQuantity);
            } else {
                order.editItem(purchaseItem.getProduct(), requestedQuantity);
            }
        }
        else{
            System.out.println("Fail save");
        }
        updateCheckoutScreen(order);
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
        List<PurchaseItem> olist = order.getOrderList();
        Iterator<PurchaseItem> orderIterator = olist.iterator();
        DataAccess db;
        while( orderIterator.hasNext()) {
            PurchaseItem item = orderIterator.next();
            db = Main.getSQLiteAccess();
            db.updateOrderInventory(item, false);
        }
    }

//    static public void showEditOrderPopup(Order order){
//        DataAccess db = Main.getSQLiteAccess();
//        db.loadProduct(order.getItem());
//        JPanel popupPanel = new JPanel();
//        popupPanel.setLayout(new GridLayout(5,2));
//        JTextField nameTF = new JTextField(order.getName());
//        JTextField priceTF = new JTextField("" + order.getPrice(), 20);
//        JTextField quantityTF = new JTextField("" + order.getQuantity(), 20);
//        JTextField providerTF = new JTextField(order.getProvider(), 20);
//        popupPanel.add(new JLabel("Id: "));
//        popupPanel.add(new JLabel("" + order.getId()));
//        popupPanel.add(new JLabel("Name: "));
//        popupPanel.add(nameTF);
//        popupPanel.add(new JLabel("Price($): "));
//        popupPanel.add(priceTF);
//        popupPanel.add(new JLabel("Quantity: "));
//        popupPanel.add(quantityTF);
//        popupPanel.add(new JLabel("Provider: "));
//        popupPanel.add(providerTF);
//        int result = JOptionPane.showConfirmDialog(null, popupPanel, "Update Product:", JOptionPane.OK_CANCEL_OPTION);
//        if(result ==JOptionPane.OK_OPTION){
//            if(areValuesValid(priceTF.getText(), quantityTF.getText())){
//                DataAccess db = Main.getSQLiteAccess();
//                order.setName(nameTF.getText());
//                order.setPrice(Double.parseDouble(priceTF.getText()));
//                order.setQuantity(Integer.parseInt(quantityTF.getText()));
//                order.setProvider(providerTF.getText());
//                db.saveProduct(order);
//                Product thisone = db.loadProduct(3);
//            }
//            else{
//                System.out.println("Fail save");
//            }
//        }
//        updateCheckoutScreen(order);
//    }

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

};

package cs3450.control;

import java.awt.*;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.lang.*;
import cs3450.model.*;

import cs3450.view.CardPaymentScreenView;
import cs3450.view.CashPaymentScreenView;


public class CheckoutScreenControl {
    private static Customer currCustomer = null;

    static public void showPremiumCustomerPopup(){
      JPanel popupPanel = new JPanel();
      popupPanel.add(new JLabel("Is the customer a premium customer?"));
      int result = JOptionPane.showConfirmDialog(null, popupPanel, "Premium?", JOptionPane.YES_NO_OPTION);
      if(result == JOptionPane.YES_OPTION){
        popupPanel = new JPanel();
        JTextField idTF = new JTextField();
        popupPanel.setLayout(new GridLayout(1,2));
        popupPanel.add(new JLabel("Customer Id: "));
        popupPanel.add(idTF);
        boolean continueAsking = true;
        while(continueAsking){
          result = JOptionPane.showConfirmDialog(null, popupPanel, "Enter Customer Id", JOptionPane.OK_CANCEL_OPTION);
          if(result == JOptionPane.OK_OPTION){
            DataAccess db = Main.getSQLiteAccess();
            if(db.isValidCustomerId(Integer.parseInt(idTF.getText()))){
                setCurrCustomer(Integer.parseInt(idTF.getText()));
                continueAsking = false;
            }
            else
              JOptionPane.showMessageDialog(null, "Invalid Customer Id.");
          }
          else{
            continueAsking = false;
            setCurrCustomer(-1);
          }
        }
      }
      else
        setCurrCustomer(-1);
    }

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
            purchaseItem.setQuantityChange(purchaseItem.getQuantity()+requestedQuantity);
//            order.addItemCount(purchaseItem, requestedQuantity+purchaseItem.getQuantity());
//            DataAccess db = Main.getSQLiteAccess();
//            db.updateItemsCount(purchaseItem, requestedQuantity+purchaseItem.getQuantity());
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

    static public void updateCheckoutScreen(Order order) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        MainScreenControl.showCheckoutScreen(false);
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

    public static boolean isCurrCustomerPremium(){
      if(currCustomer == null)
        return false;
      return currCustomer.getPremium();
    }

    public static void setCurrCustomer(int customerId) {
      DataAccess db = Main.getSQLiteAccess();
      if(customerId == -1){
        currCustomer = null;
        MainScreenControl.setOrderPremium(false);
      }
      else{
        currCustomer = db.loadCustomer(customerId);
        MainScreenControl.setOrderPremium(currCustomer.getPremium());
      }
    }
};

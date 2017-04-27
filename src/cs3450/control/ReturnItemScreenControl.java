package cs3450.control;

import cs3450.model.DataAccess;
import cs3450.model.Order;
import cs3450.model.PurchaseItem;
//import cs3450.view.CardPaymentScreenView;
import cs3450.view.PaymentScreenView;
import cs3450.view.ReturnItemScreenView;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.lang.*;

/**
 * Created by bradt on 4/4/2017.
 */
public class ReturnItemScreenControl {

    static public void showEditQuantityPopup( PurchaseItem purchaseItem, Order order){

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
            purchaseItem.setQuantityChange(requestedQuantity-purchaseItem.getQuantity());
            order.subtractItemCount(purchaseItem, requestedQuantity-purchaseItem.getQuantity());
            DataAccess db = Main.getSQLiteAccess();
            db.updateItemsCount(new PurchaseItem(0, purchaseItem.getOrderId(), purchaseItem.getProduct(), purchaseItem.getQuantity()), requestedQuantity-purchaseItem.getQuantity());
        }
        else{
            System.out.println("Fail save");
        }
       updateReturnScreen(order);
    }

    static public void selectOrderToEditPopup(Order order) {
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(5, 2));
        JTextField idTF = new JTextField();
        popupPanel.add(new JLabel("Order Id: "));
        popupPanel.add(idTF);

        int result = JOptionPane.showConfirmDialog(null, popupPanel, "Select Order For Return", JOptionPane.OK_CANCEL_OPTION);
//        Order order = null;
        if (result == JOptionPane.OK_OPTION) {
            DataAccess db = Main.getSQLiteAccess();
            order = db.getOrder(Integer.parseInt(idTF.getText()));
        }
        updateReturnScreen(order);
    }

    static public void deleteItem(PurchaseItem item) {

    }

    static public void updateDB(Order order, int countChange) {
        java.util.List<PurchaseItem> olist = order.getOrderList();
        Iterator<PurchaseItem> orderIterator = olist.iterator();
        while( orderIterator.hasNext()) {
            PurchaseItem item = orderIterator.next();
            DataAccess db = Main.getSQLiteAccess();
//            db.updateItemsCount(order. countChange);
            db.updateOrderInventory(item, true);
        }
    }

    static public void updateReturnScreen(Order order) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        MainScreenControl.showReturnScreen(order);
    }

    public static void showCardPaymentScreen(JFrame frame, Order order) {
        frame.getContentPane().removeAll();
        PaymentScreenView.cardAddComponentsToPane(frame.getContentPane(), order);
        MainScreenControl.updateFrame();
    }

    public static void showCashPaymentScreen(JFrame frame, Order order) {
        frame.getContentPane().removeAll();
        PaymentScreenView.cashAddComponentsToPane(frame.getContentPane(), order);
        MainScreenControl.updateFrame();
    }
}

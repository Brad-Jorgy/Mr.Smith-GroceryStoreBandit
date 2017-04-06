package cs3450.control;

import cs3450.model.DataAccess;
import cs3450.model.Order;
import cs3450.model.PurchaseItem;
import cs3450.view.CardPaymentScreenView;
import cs3450.view.CashPaymentScreenView;
import cs3450.view.ReturnItemScreenView;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by bradt on 4/4/2017.
 */
public class ReturnItemScreenControl {

    static public void showEditQuantityPopup( PurchaseItem purchaseItem){

        NumberFormat nf = NumberFormat.getInstance();
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(3,8));
        JTextField quantityTF = new JTextField(nf.format(purchaseItem.getQuantity()), 4);
        JTextField productNumTF = new JTextField(nf.format(purchaseItem.getId()), 4);

        popupPanel.add(new JLabel("Quantity: "));
        popupPanel.add(quantityTF);

        int result = JOptionPane.showConfirmDialog(null, popupPanel, "Edit Quantity", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            int count = Integer.parseInt(quantityTF.getText());
//            if(count <= 0) {
//                order.removeItem(purchaseItem.getProduct());
//            } else {
//                order.editItem(purchaseItem.getProduct(), Integer.parseInt(quantityTF.getText()));
//            }
        }
        else{
            System.out.println("Fail save");
        }
 //       updateReturnScreen();
    }

    static public Order selectOrderToEditPopup() {
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(5, 2));
        JTextField idTF = new JTextField();
        //JTextField priceTF = new JTextField();
        JTextField quantityTF = new JTextField();
        //JTextField providerTF = new JTextField();
        popupPanel.add(new JLabel("Order Id: "));
        popupPanel.add(idTF);

        int result = JOptionPane.showConfirmDialog(null, popupPanel, "Select Order For Return", JOptionPane.OK_CANCEL_OPTION);
        Order order = null;
        if (result == JOptionPane.OK_OPTION) {
            DataAccess db = Main.getSQLiteAccess();
            order = db.getOrder(Integer.parseInt(quantityTF.getText()));
        }
        return(order);
    }

    static public void deleteItem(PurchaseItem item) {

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

    static public void updateReturnScreen(JFrame frame, Order order) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        ReturnItemScreenView.addComponentsToPane(frame.getContentPane(), order);
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

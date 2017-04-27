package cs3450.control;

import java.awt.*;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.List;
import cs3450.model.*;

import cs3450.control.MainScreenControl;

public class PaymentScreenControl {
  private static boolean useRewardPoints = false;
  public static boolean getRewardBool() { return useRewardPoints; }

  static public void submitPayment(Order order){
    DataAccess db = Main.getSQLiteAccess();
    if (order.getOrderSize() > 0) {
        int orderId = db.saveNewOrder(order);
        updateDB(order);
    }
    updateRewardPoints(order);
    order.clearOrder();
    CheckoutScreenControl.saveCurrCustomer();
    MainScreenControl.showMainScreen();
  }
  static public void updateDB(Order order) {
      List<PurchaseItem> olist = order.getOrderList();
      Iterator<PurchaseItem> orderIterator = olist.iterator();
      DataAccess db = Main.getSQLiteAccess();
      while( orderIterator.hasNext()) {
          PurchaseItem item = orderIterator.next();
          db.updateOrderInventory(item, true);
      }
  }
  static public void showRewardPointsPopup(Order order){
    double pointsDollarAmmount = CheckoutScreenControl.getCurrCustomerPoints() / 100;
    JPanel popupPanel = new JPanel();
    popupPanel.add(new JLabel("Loyalty Customer can pay $" + String.format("%.2f",pointsDollarAmmount) + " with loyalty points. Use points?"));
    int result = JOptionPane.showConfirmDialog(null, popupPanel, "Loyalty Customer?", JOptionPane.YES_NO_OPTION);
    if(result == JOptionPane.YES_OPTION)
      useRewardPoints = true;
    else
      useRewardPoints = false;
  }
  static public void updateRewardPoints(Order order){
    if(CheckoutScreenControl.isLoyalCustomer()){
      if(useRewardPoints)
        CheckoutScreenControl.resetCurrCustomerPoints();
      CheckoutScreenControl.incrementCurrCustomerPoints(order.getTotal());
    }
    useRewardPoints = false;
  }
};

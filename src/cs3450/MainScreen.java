package cs3450;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainScreen {
    // private static JButton checkoutBtn = new JButton("Checkout");
    // private static JLabel title = new JLabel("Store Management System", SwingConstants.CENTER);
    // private static JButton inventoryBtn = new JButton("Inventory");

    public static void addComponentsToPane(Container pane)
    {
				JButton checkoutBtn = new JButton("Check Out");
				JLabel title = new JLabel("Store Management System", SwingConstants.CENTER);
				JButton inventoryBtn = new JButton("Inventory");
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100;
        c.weighty = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 1;

				inventoryBtn.addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e) {
						Main.showInventoryScreen();
						//System.out.print("clicked");
					}
				});
        checkoutBtn.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                Main.showCheckoutScreen();
                //System.out.print("clicked");
            }
        });
        pane.add(title, c);
        c.gridx = 1;
        c.gridy = 1;
        pane.add(checkoutBtn, c);
        c.gridx = 2;
        c.gridy = 2;
        pane.add(inventoryBtn, c);

    }

}

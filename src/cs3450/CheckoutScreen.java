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

public class CheckoutScreen {

    public static void addComponentsToPane(Container pane)
    {
        JButton backBtn = new JButton("Back to Main Screen");
        JLabel title = new JLabel("Check Out", SwingConstants.CENTER);
        JButton inventoryBtn = new JButton("Noob");
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100;
        c.weighty = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 1;

        backBtn.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                Main.showMainScreen();
            }
        });
        pane.add(title, c);
        c.gridx = 1;
        c.gridy = 1;
        pane.add(backBtn, c);
        c.gridx = 2;
        c.gridy = 2;
        pane.add(inventoryBtn, c);

    }
}

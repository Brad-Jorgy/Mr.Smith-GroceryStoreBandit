package cs3450;

import java.awt.*;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PaymentScreen {


    public static void addComponentsToPane(Container pane)
    {
        JButton submitBtn = new JButton("SUBMIT PAYMENT");
        JLabel title = new JLabel("Payment", SwingConstants.CENTER);
        JButton cancelBtn = new JButton("Cancel");

        JTextField nameOnCard = new JTextField("BOB SMITH");
        JTextField cardNum = new JTextField("1111 0000 2222 3333 4444");
        JTextField totalAmount = new JTextField("$100.50");
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100;
        c.weighty = 100;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 1;

        cancelBtn.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                Main.showCheckoutScreen();
            }
        });

        pane.add(title, c);
        c.gridx = 1;
        c.gridy = 1;
        pane.add(nameOnCard, c);
        c.gridx = 2;
        c.gridy = 2;
        pane.add(cardNum, c);
        c.gridx = 3;
        c.gridy = 3;
        pane.add(submitBtn, c);
        c.gridx = 4;
        c.gridy = 4;
        pane.add(cancelBtn, c);
        c.gridx = 5;
        c.gridy = 5;
        pane.add(totalAmount, c);

    }
}

package cs3450.setup.location;

import cs3450.setup.ConfirmationScreen;
import cs3450.setup.Installation;
import cs3450.setup.MainScreen;
import cs3450.setup.Screen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class xlsxScreen extends Screen {

    // View
    private JButton mNext;
    private JButton mPrevious;

    private JTextField mPathField;
    private JButton mBrowseButton;

    private JFileChooser mFileChooser;

    // InstalationSetting
    Installation xlsxInstallation;

    public xlsxScreen(JFrame frame, Installation installation) {
        super(frame);

        xlsxInstallation = installation;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        addPanel(mainPanel);

        JLabel title = new JLabel("Shop assistance system Setup");
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, title.getMinimumSize().height));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        mFileChooser = new JFileChooser();
        mFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        JPanel browsePanel = new JPanel();
        browsePanel.setLayout(new BoxLayout(browsePanel, BoxLayout.X_AXIS));
        centerPanel.add(browsePanel);

        browsePanel.add(new JLabel("Installation path:"));

        browsePanel.add(Box.createRigidArea(new Dimension(10, 0)));

        mPathField = new JTextField();
        mPathField.setText(xlsxInstallation.getInstallationLocation());
        mPathField.setMaximumSize(new Dimension(600, 32));
        browsePanel.add(mPathField);

        browsePanel.add(Box.createRigidArea(new Dimension(10, 0)));

        mBrowseButton = new JButton("Browse Folder");
        mBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = mFileChooser.showOpenDialog(frame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = mFileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    mPathField.setText(path);
                    xlsxInstallation.setInstallationLocation(path);
                }
            }
        });
        browsePanel.add(mBrowseButton);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        mainPanel.add(panel, BorderLayout.SOUTH);

        mPrevious = new JButton("Previous");
        mPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePanel(mainPanel);
                new LocationScreen(frame, xlsxInstallation);
            }
        });
        panel.add(mPrevious);

        mNext = new JButton("Next");
        mNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePanel(mainPanel);
                new ConfirmationScreen(frame, xlsxInstallation);
            }
        });
        panel.add(mNext);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}

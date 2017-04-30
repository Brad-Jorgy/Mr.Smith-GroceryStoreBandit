package cs3450.setup;

import javax.swing.*;
import java.awt.*;

public class SetupProgram {
	public static void main(String[] args) throws ClassNotFoundException {
		new MainScreen(SetupProgram.getInstance().getMainFrame());
	}

	private static SetupProgram mInstance;
	private JFrame mMainFrame;

	public static SetupProgram getInstance() {
		if (mInstance == null)
			mInstance = new SetupProgram();
		return mInstance;
	}

	private SetupProgram() {
		mMainFrame = new JFrame("Mr Smith's shop Setup Wizard");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mMainFrame.setPreferredSize(new Dimension(600, 300));
		mMainFrame.setLocation(dim.width / 2 - 300 / 2, dim.height / 2 - 600 / 2);
		mMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JFrame getMainFrame() {
		return mMainFrame;
	}

}

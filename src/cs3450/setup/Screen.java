package cs3450.setup;

import javax.swing.*;

public abstract class Screen {
	protected JFrame mMainFrame;

	public Screen(JFrame mainFrame) {
		mMainFrame = mainFrame;
	}

	protected void addPanel(JPanel panel) {
		mMainFrame.getContentPane().add(panel);
	}

	protected void removePanel(JPanel panel) {
		mMainFrame.getContentPane().remove(panel);
	}
}

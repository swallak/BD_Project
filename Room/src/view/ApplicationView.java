package view;

import javax.swing.JPanel;

public abstract class ApplicationView extends JPanel{

	private MainFrame parent;

	public ApplicationView(MainFrame parent) {
		this.parent = parent;
	}

	public MainFrame getParent() {
		return parent;
	}

}

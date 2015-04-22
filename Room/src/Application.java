import javax.swing.JFrame;

import view.MainFrame;
import view.SignInView;

public class Application extends JFrame{

	private final static String APPLICATION_TITLE = "Title";
	
	public static void main(String args[]) {
		// Enregistrement du driver JDBC.
		
		MainFrame frame = new MainFrame(APPLICATION_TITLE);
	}
}

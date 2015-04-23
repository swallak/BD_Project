import javax.swing.JFrame;

import view.SignUpViewFrame;
import view.SignInViewPanel;

public class Application extends JFrame{

	private final static String APPLICATION_TITLE = "Title";
	
	public static void main(String args[]) {
		// Enregistrement du driver JDBC.
		
		SignUpViewFrame frame = new SignUpViewFrame(APPLICATION_TITLE);
	}
}

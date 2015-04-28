package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import model.game.Match;
import model.user.User;

public class ObservationViewFrame extends MainFrame {

    private ObservationViewPanel observationViewPanel;     
    private Dimension size = new Dimension(1024, 768);
    protected User user;
    
	
	public ObservationViewFrame(String title, Match m, User u) {
		super(title);
		this.user = u;
		
		System.out.println("PIROCAAA");
		System.out.println(m);
		
        //set MatchController
        
        //Layout And size
        setLayout(new BorderLayout());
        setSize(size);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        
        //set Default closing operation
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Must be changed

//        //Creating Components
//        
        observationViewPanel = new ObservationViewPanel(m);
//        //Adding component
        add(observationViewPanel, BorderLayout.CENTER);
        
        observationViewPanel.returnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSwitchToFrame(new HomeViewFrame("Home" + user.getPseudo(), user));
                switchFrame();
			}
		});
        
	        
	}
}




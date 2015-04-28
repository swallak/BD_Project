package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import model.game.Match;

public class ObservationViewFrame extends MainFrame {

    private ObservationViewPanel observationViewPanel;     
    private Dimension size = new Dimension(500, 500);
    
	
	public ObservationViewFrame(String title, Match m) {
		super(title);
		
		System.out.println("PIROCAAA");
		System.out.println(m);
		
        //set MatchController
        
        //Layout And size
        setLayout(new BorderLayout());
        setSize(size);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        
        //set Default closing operation
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Must be changed

//        //Creating Components
//        
        observationViewPanel = new ObservationViewPanel();
//        //Adding component
        add(observationViewPanel, BorderLayout.CENTER);
	        
	}
}




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.game.Match;
import model.user.User;
import controller.HomeController;
import dao.jdbc.MatchDAO_JDBC;

/**
 *
 * @author swallak
 */
public class HomeViewFrame extends MainFrame{
    private final int HEIGHT = 768;
    private final int WIDTH = 1366;
    private Dimension size = new Dimension(WIDTH, HEIGHT);
    private Dimension infoPanelSize = new Dimension(WIDTH/5, HEIGHT);
    private Dimension mainPanelSize = new Dimension(WIDTH*4/5, HEIGHT);
    
    private HomeController controller;
    
    private User user;

	public HomeViewFrame(String title, User user) {
            
            super(title);
            
            controller = new HomeController();
            
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.user=user;
            
            //set Layout & size
            setSize(size);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLayout(new BorderLayout());
            
            //Components
            JPanel infoPanel  = new HomeViewInfoPanel();
            JPanel mainPanel = new HomeViewMainPanel();
            
            //Placements
            
            add(infoPanel, BorderLayout.WEST);
            add(mainPanel, BorderLayout.EAST);
            
		
	}
        
        public class HomeViewInfoPanel extends JPanel{
            
           private JLabel pseudoLabel = new JLabel(user.getPseudo());
           private JLabel cityLabel = new JLabel (user.getAddrCity());
           private JButton logoutButton = new JButton("Log out");
                  
           
           public HomeViewInfoPanel()
           {
               //set Layout & size
               setBorder(BorderFactory.createTitledBorder("Your Info"));
               setPreferredSize(infoPanelSize);
               setLayout(new GridBagLayout());
               
               GridBagConstraints gc = new GridBagConstraints();
               gc.weightx=1;
               // Adding component to the panel
               gc.weighty=0.5;
               gc.anchor= GridBagConstraints.LINE_START;
               gc.gridx=0;
               gc.gridy=0;
               add(pseudoLabel, gc);
               
               gc.anchor= GridBagConstraints.FIRST_LINE_START;
               gc.gridy=1;
               gc.weighty=10;
               add(cityLabel, gc);
               
               gc.gridy=2;
               gc.weighty=0.5;
               gc.anchor=GridBagConstraints.PAGE_END;
               add(logoutButton, gc);
               
               
               
           }
        }
        
	public class HomeViewMainPanel extends JPanel {

		private JButton playButton = new JButton("Play");
		private JButton watchButton = new JButton("Watch");

		public HomeViewMainPanel() {

			// set Layout & size
			setBorder(BorderFactory
					.createTitledBorder("What do you want to do?"));
			setPreferredSize(mainPanelSize);
			setLayout(new GridBagLayout());

			GridBagConstraints gc = new GridBagConstraints();

			// Adding component to the panel

			gc.gridy = 0;
			gc.weighty = 1;
			gc.weightx = 1;

			gc.fill = GridBagConstraints.HORIZONTAL;

			gc.gridx = 0;
			add(playButton, gc);

			gc.gridx = 2;
			add(watchButton, gc);

			playButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					controller.startMatch(HomeViewFrame.this);
				}
			});
			
			List<Match> result= controller.getPlayableMatch(HomeViewFrame.this);
			System.out.println("Nombre de match jouable " + result.size());
			System.out.println("Nombre de match observable " + controller.getObservableMatch(HomeViewFrame.this));

			
		}
            
        }

		public User getUser() {
			return user;
		}
}

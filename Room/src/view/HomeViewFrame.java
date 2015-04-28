/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

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
            HomeViewInfoPanel infoPanel  = new HomeViewInfoPanel();
            HomeViewMainPanel mainPanel = new HomeViewMainPanel();
            
            //Placements
            
            add(infoPanel, BorderLayout.WEST);
            add(mainPanel, BorderLayout.EAST);
            
           
           
            infoPanel.getLogoutButton().addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
               	setSwitchToFrame2(new SignInViewFrame("Sign In"));
                   switchFrame2();

               }
           });
            

	}
        
        public class HomeViewInfoPanel extends JPanel{
            
           private JLabel pseudoLabel = new JLabel("Pseudo: " + user.getPseudo());
           private JLabel cityLabel = new JLabel ("City: " + user.getAddrCity());
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
               gc.fill = GridBagConstraints.BOTH;
               add(logoutButton, gc);  
               
               
               //event handler
               
               	
               
   		
           }
           
           public JButton getLogoutButton()
           {
        	   return this.logoutButton;
           }
        }
        
	public class HomeViewMainPanel extends JPanel {

		private JButton playButton = new JButton("Play");
		private JButton watchButton = new JButton("Watch");
		private JList<Match> obsGamesList;	
		private JLabel obsLabel = new JLabel("If you want to observe a game, choose one "
				+ "from the list below and click 'Watch'");
        private JLabel playLabel = new JLabel("If you want to play a game, click on 'Play' "
        		+ "and the application will find a player for you");
		
		private void addObsList(){                                       
		
			List<Match> result = controller.getObservableMatch(HomeViewFrame.this);
			
		    DefaultListModel<Match> observableGames = new DefaultListModel<Match>();
		  
		    for (Match m : result) {
		    	System.out.println(m.toString());
		    	observableGames.addElement(m);
		    }

		  obsGamesList = new JList<Match>(observableGames);
		  obsGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		  obsGamesList.setSelectedIndex(0);
		  obsGamesList.setVisibleRowCount(3); 
		
		  JScrollPane obsGamesListScrollPane = new JScrollPane(obsGamesList);    

//		  JButton showButton = new JButton("Show");
//		
//		  showButton.addActionListener(new ActionListener() {
//		     public void actionPerformed(ActionEvent e) { 
//		        String data = "";
//		if (obsGamesList.getSelectedIndex() != -1) {                     
//		   data = "Fruits Selected: " + obsGamesList.getSelectedValue().getWinner(); 
//		   statusLabel.setText(data);
//		}
//		
//		        statusLabel.setText(data);
//		     }
//		  }); 

			
		  	

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth=2;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;

			add(obsGamesListScrollPane, gbc);  
		  //controlPanel.add(showButton);    
			              
		}

		public HomeViewMainPanel() {
			
			// set Layout & size
			setBorder(BorderFactory
					.createTitledBorder("What do you want to do?"));
			setPreferredSize(mainPanelSize);
			setLayout(new GridBagLayout());

			GridBagConstraints gc = new GridBagConstraints();

			// Adding component to the panel
			
			gc.weightx = 0.5;
			gc.weighty = 0.1;
			gc.gridy = 0;
			add(obsLabel, gc);
			
			gc.weightx = 0.5;
			gc.weighty = 0.1;
			gc.gridy = 1;
			add(playLabel, gc);

			
			addObsList();
			
			gc.weightx = 0.5;
			gc.fill = GridBagConstraints.BOTH;
			gc.gridy = 3;
			add(watchButton, gc);
			
			gc.fill = GridBagConstraints.BOTH;
			gc.gridx = 0;
			gc.gridy = 4;
			add(playButton, gc);

			watchButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setSwitchToFrame(new ObservationViewFrame("Observating Match", obsGamesList.getSelectedValue(), user));
	                switchFrame();
				}
			});

			
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



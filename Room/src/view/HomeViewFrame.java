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
import java.awt.Insets;
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

import com.sun.security.auth.module.JndiLoginModule;

import model.game.Match;
import model.user.User;
import controller.HomeController;

/**
 *
 * @author swallak
 */
public class HomeViewFrame extends MainFrame {
	private final int HEIGHT = 768;
	private final int WIDTH = 1366;
	private Dimension size = new Dimension(WIDTH, HEIGHT);
	private Dimension infoPanelSize = new Dimension(WIDTH / 5, HEIGHT);
	private Dimension mainPanelSize = new Dimension(WIDTH * 4 / 5, HEIGHT);

	private HomeController controller;

	private HomeViewInfoPanel infoPanel;
	private HomeViewMainPanel mainPanel;

	private User user;

	public HomeViewFrame(String title, User user) {
		super(title);

		controller = new HomeController();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.user = user;

		// set Layout & size
		setSize(size);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new GridBagLayout());

		// Components
		infoPanel = new HomeViewInfoPanel();
		mainPanel = new HomeViewMainPanel();

		// Placements
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0.3;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;

		add(infoPanel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.weightx = 0.7;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		add(mainPanel, gc);

		infoPanel.getLogoutButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSwitchToFrame2(new SignInViewFrame("Sign In"));
				switchFrame2();
			}
		});
		
		mainPanel.playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSwitchToFrame2(new MatchViewFrame("Match"));
				switchFrame2();
			}
		});

		controller.refresh(this);
	}

	public class HomeViewInfoPanel extends JPanel {

		private JLabel pseudoLabel = new JLabel("Pseudo: " + user.getPseudo());
		private JLabel cityLabel = new JLabel("City: " + user.getAddrCity());
		private JButton logoutButton = new JButton("Log out");

		public HomeViewInfoPanel() {
			// set Layout & size
			setBorder(BorderFactory.createTitledBorder("Your Info"));
			setPreferredSize(infoPanelSize);
			setLayout(new GridBagLayout());

			GridBagConstraints gc = new GridBagConstraints();
			gc.weightx = 1;
			// Adding component to the panel
			gc.weighty = 0.5;
			gc.anchor = GridBagConstraints.LINE_START;
			gc.gridx = 0;
			gc.gridy = 0;
			add(pseudoLabel, gc);

			gc.anchor = GridBagConstraints.FIRST_LINE_START;
			gc.gridy = 1;
			gc.weighty = 10;
			add(cityLabel, gc);

			gc.gridy = 2;
			gc.weighty = 0.5;
			gc.anchor = GridBagConstraints.PAGE_END;
			gc.fill = GridBagConstraints.BOTH;
			add(logoutButton, gc);
		}

		public JButton getLogoutButton() {
			return this.logoutButton;
		}
	}

	public class HomeViewMainPanel extends JPanel {

		private JButton playButton = new JButton("Play");
		private JButton watchButton = new JButton("Watch");
		private JButton refreshButton = new JButton("Refresh");
		private JButton createButton = new JButton("Create new game");

		private JList<Match> obsGamesList;
		private JList<Match> playableGamesList;
		
		private DefaultListModel<Match> obsGamesModel;
		private DefaultListModel<Match> playableGamesModel;

		private JLabel obsLabel = new JLabel(
				"If you want to observe a game, choose one "
						+ "from the list below and click 'Watch'");

		private JLabel playLabel = new JLabel(
				"If you want to play a game, select one and click on 'Play' or click on 'Create'");

		private void addList() {
			obsGamesModel = new DefaultListModel<Match>();
			obsGamesList = new JList<Match>(obsGamesModel);
			obsGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			obsGamesList.setSelectedIndex(0);
			obsGamesList.setVisibleRowCount(3);
			JScrollPane obsGamesListScrollPane = new JScrollPane(obsGamesList);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 0.5;
			gbc.weighty = 1;

			add(obsGamesListScrollPane, gbc);

			playableGamesModel = new DefaultListModel<Match>();
			playableGamesList = new JList<Match>(playableGamesModel);
			playableGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			playableGamesList.setSelectedIndex(0);
			playableGamesList.setVisibleRowCount(3);
			JScrollPane playableListScrollPane = new JScrollPane(
					playableGamesList);

			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 0.5;
			gbc.weighty = 1;
			add(playableListScrollPane, gbc);
		}

		public HomeViewMainPanel() {

			// set Layout & size
			setBorder(BorderFactory
					.createTitledBorder("What do you want to do?"));
			setPreferredSize(mainPanelSize);
			setLayout(new GridBagLayout());

			GridBagConstraints gc = new GridBagConstraints();
			gc.insets = new Insets(3, 3, 3, 3);

			// Adding component to the panel

			gc.weightx = 0.5;
			gc.gridx = 0;
			gc.gridy = 0;
			add(obsLabel, gc);

			gc.weightx = 0.5;
			gc.gridx = 1;
			gc.gridy = 0;
			add(playLabel, gc);

			addList();

			gc.weightx = 0.5;
			gc.gridx = 0;
			gc.gridy = 2;
			add(watchButton, gc);

			gc.gridx = 1;
			gc.gridy = 2;
			add(playButton, gc);

			gc.gridx = 0;
			gc.gridy = 3;
			add(refreshButton, gc);

			gc.gridx = 1;
			gc.gridy = 3;
			add(createButton, gc);

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
					// TODO switch sur le match jouable
				}
			});
			
			refreshButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					controller.refresh(HomeViewFrame.this);;
				}
			});
			
			createButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.startMatch(HomeViewFrame.this);
				}
			});
		}

		public void setDisplayedList(List<Match> observableMatches,
				List<Match> playableMatches) {
			obsGamesModel.removeAllElements();
			for (Match m : observableMatches) {
				obsGamesModel.addElement(m);
			}

			playableGamesModel.clear();
			for (Match m : playableMatches) {
				playableGamesModel.addElement(m);
			}
		}
	}

	public User getUser() {
		return user;
	}

	public HomeViewInfoPanel getInfoPanel() {
		return infoPanel;
	}

	public HomeViewMainPanel getMainPanel() {
		return mainPanel;
	}
}

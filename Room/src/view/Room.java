/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dao.jdbc.JDBCConnection;

/**
 *
 * @author swallak
 */
public class Room {

	public static final MainFrame SIGN_IN_FRAME = new SignInViewFrame("GameRoom-SignIn");
	public static final MainFrame SIGN_UP_FRAME = new SignUpViewFrame("GameRoom-SignUp");
	
	public static void main(String[] args) {

		try {
			JDBCConnection.registerJDBDriver();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Frames linking
				SIGN_UP_FRAME.setSwitchToFrame(SIGN_IN_FRAME);
				SIGN_IN_FRAME.setSwitchToFrame(SIGN_UP_FRAME);
				SIGN_IN_FRAME.setSize(300, 150);
				SIGN_IN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				SIGN_IN_FRAME.setVisible(true);
			}
		});

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				// MatchViewFrame match = new MatchViewFrame("Test");
				// match.setVisible(true);
			}

		});
	}

	@SuppressWarnings("unused")
	private void linkFrames(/* add parameters */) {
		// TODO
	}

}

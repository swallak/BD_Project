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
    	
        final MainFrame SignInFrame = new SignInViewFrame("GameRoom-SignIn");
        final MainFrame SignUpFrame = new SignUpViewFrame("GameRoom-SignUp");
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                //Frames linking
                SignUpFrame.setSwitchToFrame(SignInFrame);
                SignInFrame.setSwitchToFrame(SignUpFrame);
                SignInFrame.setSize(500, 500);
                SignInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                SignInFrame.setVisible(true);
            }
        });

       SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
               
            }

        });
    }
    
    private void linkFrames(/*Add params*/){
        // To be completed
    }
}

		

		
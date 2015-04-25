/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author swallak
 */
public class Room {

    public static void main(String[] args) {
        MainFrame SignInFrame = new SignInViewFrame("GameRoom-SignIn");
        MainFrame SignUpFrame = new SignUpViewFrame("GameRoom-SignUp");
        MainFrame HomeFrame = new HomeViewFrame("GameRoom-Home");
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                //Frames linking
                SignUpFrame.setSwitchToFrame(SignInFrame);
                SignInFrame.setSwitchToFrame(SignUpFrame);
                
                //SignIn Frame location and size
                SignInFrame.setLocation(0, 150);
                SignInFrame.setSize(500, 500);
                SignInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                SignInFrame.setVisible(true);
            }
        });

       SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                
                HomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                HomeFrame.setVisible(true);
            }

        });
    }
    
    private void linkFrames(/*Add params*/){
        // To be completed
    }

}

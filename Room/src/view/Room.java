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
        MainFrame SignInFrame = new SignInViewFrame("GameRoom");
        MainFrame SignUpFrame = new SignUpViewFrame("GameRoom");

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                SignInFrame.setLocation(0, 150);
                SignInFrame.setSize(500, 500);
                SignInFrame.setSwitchToFrame(SignUpFrame);
                SignInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                SignInFrame.setVisible(true);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                SignUpFrame.setSize(500, 500);
                SignUpFrame.setLocation(0, 150);
                SignUpFrame.setSwitchToFrame(SignInFrame);
                SignUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                SignUpFrame.setVisible(false);
            }

        });
    }

}

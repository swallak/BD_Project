/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.user.User;
import controller.LogInController;
import java.awt.Point;

/**
 *
 * @author swallak
 */
public class SignInViewFrame extends MainFrame {

    private Dimension size = new Dimension(500, 500);
    public final static Point DEFAULT_LOCATION = new Point(50,50);

    public SignInViewFrame(String title) {

        super(title);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(DEFAULT_LOCATION);
        //Set Layout
        setLayout(new BorderLayout());
        setSize(size);

        //Create component
        SignInViewPanel signIn = new SignInViewPanel();

        //add component
        Container container = getContentPane();
        container.add(signIn, BorderLayout.CENTER);
        //container.add(signUp, BorderLayout.CENTER,1);

        //Handle events
                // Buttons action
        final Point location = this.getLocation();
        signIn.connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				User user;

				// throw new
				// UnsupportedOperationException("Needs Database Access");
				user = new LogInController(signIn.getPseudo(), signIn
						.getBirthday()).connect(SignInViewFrame.this);
				setSwitchToFrame2(new HomeViewFrame("Home" + user.getPseudo(),
						user));
				switchFrame2();

			}
        });

        signIn.createAccountButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchFrame();

            }
        });
    }
    
    public void popErrorDialog(String message) {
    	//Pop window
        final JFrame accountNotFoundFrame = new JFrame(message);
        JButton closeButton = new JButton("Close");
        JLabel msgLabel = new JLabel(LogInController.NOT_FOUND_ERROR_MSG);
        
        accountNotFoundFrame.setUndecorated(true);
        accountNotFoundFrame.setSize(400, 80);
        accountNotFoundFrame.setLocation(this.getLocation());
        accountNotFoundFrame.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.weighty = 1;
        gc.weightx = 1;

        gc.gridy = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        accountNotFoundFrame.add(msgLabel, gc);

        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        accountNotFoundFrame.add(closeButton, gc);
        
        accountNotFoundFrame.setVisible(true);
        //Windows Behaviour
        
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //close pop window
                accountNotFoundFrame.dispose();
            }
        });
    }

}

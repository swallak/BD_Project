/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.LogInController;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import controller.ApplicationController;
import dao.UserDAO;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.user.User;

/**
 *
 * @author swallak
 */
public class SignInViewFrame extends MainFrame {

    private Dimension size = new Dimension(500, 500);

    public SignInViewFrame(String title) {

        super(title);
        final Point location = this.getLocation();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Set Layout
        setLayout(new BorderLayout());
        setPreferredSize(size);

        //Create component
        SignInViewPanel signIn = new SignInViewPanel();

        //add component
        Container container = getContentPane();
        container.add(signIn, BorderLayout.CENTER);
        //container.add(signUp, BorderLayout.CENTER,1);

        //Handle events
                // Buttons action
        signIn.connectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                User user;

                try {
                    //throw new UnsupportedOperationException("Needs Database Access");
                    user = new LogInController(signIn.getPseudo(), signIn.getBirthday()).connect();
                } catch (UserDAO.UserNotExistException ex) {
                                //System.out.println("ERROR");

                    //Pop window
                    final JFrame accountNotFoundFrame = new JFrame("Account not found");
                    JButton closeButton = new JButton("Close");
                    JLabel msgLabel = new JLabel(LogInController.NOT_FOUND_ERROR_MSG);
                    
                    accountNotFoundFrame.setUndecorated(true);
                    accountNotFoundFrame.setSize(400, 80);
                    accountNotFoundFrame.setLocation(location);
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
                    return;

                }
                setSwitchToFrame2(new HomeViewFrame("Home"+ user.getPseudo(), user));
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

}

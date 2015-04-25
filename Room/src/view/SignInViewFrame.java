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

/**
 *
 * @author swallak
 */
public class SignInViewFrame extends MainFrame {
    
    private Dimension size = new Dimension(500, 500);
    
    
    public SignInViewFrame(String title){
         
        super(title);
        
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
                //throw new UnsupportedOperationException("Needs Database Access");
                new LogInController(signIn.pseudoField.getText(), signIn.birthdayField.getText()).connect();
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
    


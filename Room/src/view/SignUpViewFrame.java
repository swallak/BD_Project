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
import controller.SignUpController;

/**
 *
 * @author swallak
 */
public class SignUpViewFrame extends MainFrame {
    
    private Dimension size = new Dimension(600, 600);
    
    
    public SignUpViewFrame(String title){
         
        super(title);
        
        //Set Layout
        setLayout(new BorderLayout());
        setPreferredSize(size);
        
        
        //Create component
        SignUpViewPanel signUp = new SignUpViewPanel();
        
        
        //add component
        Container container = getContentPane();
        container.add(signUp, BorderLayout.CENTER);
        //container.add(signUp, BorderLayout.CENTER,1);
              
        //Handle events
        
                // Buttons action
        signUp.createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpController(signUp).createAccount();
                
                // Still needs to be finished
                
            
            }
        
        });
        
        signUp.alreadyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchFrame();
            
            }
        });
        
        
        
    }
}
    


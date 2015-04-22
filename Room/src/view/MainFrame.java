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

/**
 *
 * @author swallak
 */
public class MainFrame extends JFrame {
    private Dimension size = new Dimension(600, 600);
    public MainFrame(String title){
         
        super(title);
        
        //Set Layout
        setLayout(new BorderLayout());
        setPreferredSize(size);
        
        
        //Create component
        SignInView signIn = new SignInView();
        
        
        //add component
        Container container = getContentPane();
        container.add(signIn, BorderLayout.CENTER);
        //container.remove(signIn);
        
        
        
        //Handle events
        
                // Buttons action
        
        signIn.connectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Needs Database Access");
                new LogInController(signIn.pseudoField.getText(), signIn.birthdayField.getText());
            }
        });
        
        
        signIn.createAccountButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
}

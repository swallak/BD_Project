/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
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
        
        //Handle events
    }
    
}

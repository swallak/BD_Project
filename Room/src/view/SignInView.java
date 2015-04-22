/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author swallak
 */
public class SignInView extends JPanel {
    

    private final int  WIDTH = 10;
    private Dimension size = new Dimension(500, 500);
    protected SignInView() {

        setPreferredSize(size);
        
        //set border and Layout
        
        setBorder(BorderFactory.createTitledBorder("SignIn"));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        //SignInView components
        
        JLabel pseudoLabel = new JLabel("Pseudo");
        JLabel birthdayLabel = new JLabel("Birthday");
        JTextField pseudoField = new JTextField(WIDTH);
        JTextField birthdayField = new JTextField(WIDTH);
        JButton connectButton = new JButton("Connect");
        
        //Placing Components
        
        gc.weightx=1;
        gc.weighty=1;
        
        //First Columns
        gc.gridx=0;
        gc.anchor= GridBagConstraints.LINE_START;
        
        gc.gridy=0;
        add(pseudoLabel,gc);
        
        gc.gridy=1;
        add(birthdayLabel,gc);
        
        //Second Column
        
        gc.gridx=1;
        
        add(birthdayField,gc);
        
        gc.gridy=0;
        add(pseudoField,gc);
        
        //Last row
        
        gc.gridy=2;
        gc.gridx=0;
        gc.weighty=10;
        gc.anchor=GridBagConstraints.PAGE_START;
        gc.gridwidth=GridBagConstraints.REMAINDER;
        //gc.fill= GridBagConstraints.HORIZONTAL;
        add(connectButton,gc);
        
        
        
        
        
        
    }

}

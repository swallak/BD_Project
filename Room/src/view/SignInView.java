/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.LogInController;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private LogInController logincontroller;
    
    protected SignInView() {

        setPreferredSize(size);
        
        //set border and Layout
        
        setBorder(BorderFactory.createTitledBorder("SignIn"));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        //SignInView components
        
        JLabel pseudoLabel = new JLabel("Pseudo  ");
        JLabel birthdayLabel = new JLabel("Birthday  ");
        JTextField pseudoField = new JTextField(WIDTH);
        JTextField birthdayField = new JTextField(WIDTH);
        JButton connectButton = new JButton("Connect");
        JButton createAccountButton = new JButton("Create an account");
        //Placing Components
        
        gc.weightx=1;
        gc.weighty=1;
        
        //First Columns
        gc.gridx=0;
        gc.anchor= GridBagConstraints.LINE_END;
        
        gc.gridy=0;
        add(pseudoLabel,gc);
        
        gc.gridy=1;
        add(birthdayLabel,gc);
        
        //Second Column
        
        gc.gridx=1;
        gc.anchor= GridBagConstraints.LINE_START;
        
        add(birthdayField,gc);
        
        gc.gridy=0;
        add(pseudoField,gc);
        
        //Last  2 rows
        
        gc.gridy=2;
        gc.gridx=0;
        gc.weighty=1;
        //gc.anchor=GridBagConstraints.PAGE_END;
        gc.anchor=GridBagConstraints.CENTER;
        gc.gridwidth=GridBagConstraints.REMAINDER;
        //gc.fill= GridBagConstraints.HORIZONTAL;
        add(connectButton,gc);
        
        gc.gridy=3;
        gc.weighty=10;
        gc.anchor=GridBagConstraints.PAGE_START;
        add(createAccountButton,gc);
        
        
        // Buttons action
        
        connectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Needs Database Access");
                new LogInController(pseudoField.getText(), birthdayField.getText());
            }
        });
        
        
        createAccountButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        
        
        
        
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import controller.LogInController;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author swallak
 */

public class SignInViewPanel extends JPanel {
    

    private final int  WIDTH = 10;
    private Dimension size = new Dimension(500, 500);

    
    private LogInController logincontroller;
    private final JLabel pseudoLabel = new JLabel("Pseudo  ");
    private final JLabel birthdayLabel = new JLabel("Birthday (YYYY/MM/DD)  ");
    protected final JTextField pseudoField = new JTextField(WIDTH);
    protected final JTextField birthdayField = new JTextField(WIDTH);
    protected final JButton connectButton = new JButton("Connect");
    protected final JButton createAccountButton = new JButton("Create an account");    
    
    protected SignInViewPanel() {

        setPreferredSize(size);
        
        //set border and Layout
        
        setBorder(BorderFactory.createTitledBorder("SignIn"));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

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
      
    }
    
    public String getPseudo()
    {
        return this.pseudoField.getText();
    }
    
    public String getBirthday()
    {
        return this.birthdayField.getText();
    }
}
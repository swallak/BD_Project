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
public class SignUpViewPanel extends JPanel{

    private final int  WIDTH = 10;
    private final Dimension size = new Dimension(500, 500);
    
    
    //Components
    private final JLabel pseudoLabel = new JLabel ("Pseudo  ");
    private final JLabel birthdayLabel = new JLabel("Birthday  ");
    private final JLabel lastNameLabel = new JLabel("Last Name  ");
    private final JLabel firstNameLabel = new JLabel("First Name  ");
    private final JLabel streetNameLabel = new JLabel("Street  ");
    private final JLabel streetNumberLabel = new JLabel("Number  ");
    private final JLabel cityLabel = new JLabel("City  ");
    private final JLabel zipcodeLabel = new JLabel("Zip Code  ");
    protected final JTextField pseudoField = new JTextField (WIDTH);
    protected final JTextField birthdayField = new JTextField(WIDTH);
    protected final JTextField lastNameField = new JTextField(WIDTH);
    protected final JTextField firstNameField = new JTextField(WIDTH);
    protected final JTextField streetNameField = new JTextField(WIDTH);
    protected final JTextField streetNumberField = new JTextField(WIDTH);
    protected final JTextField cityField = new JTextField(WIDTH);
    protected final JTextField zipcodeField = new JTextField(WIDTH);
    protected final JButton createButton = new JButton("Create");
    protected final JButton alreadyButton = new JButton("Already an account?");
    
    public SignUpViewPanel(){
        
        //Set Layout
        setBorder(BorderFactory.createTitledBorder("SignUp"));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        

        //placing components
        //first column 
            gc.gridx=0;
            gc.weighty=0.5;
            gc.weightx=0.5;
            gc.anchor= GridBagConstraints.LINE_END;
            
            gc.gridy=0;
            add(pseudoLabel,gc);
            
            gc.gridy=1;
            add(birthdayLabel,gc);
            
            gc.gridy=2;
            add(firstNameLabel,gc);
            
            gc.gridy=3;
            add(lastNameLabel,gc);
            
            gc.gridy=5;
            add(streetNumberLabel,gc);
            
            gc.gridy=4;
            add(streetNameLabel,gc);
            
            gc.gridy=6;
            add(cityLabel,gc);
            
            gc.gridy=7;
            add(zipcodeLabel, gc);
            
            //Second Column
            gc.gridx=1;
            gc.anchor=GridBagConstraints.LINE_START;
            //gc.weightx=10;
            //gc.fill=GridBagConstraints.HORIZONTAL;
            
            add(zipcodeField,gc);
            
            gc.gridy=0;
            add(pseudoField,gc);
            
            gc.gridy=1;
            add(birthdayField,gc);
            
            gc.gridy=2;
            add(firstNameField,gc);
            
            gc.gridy=3;
            add(lastNameField,gc);
            
            gc.gridy=5;
            add(streetNumberField,gc);
            
            gc.gridy=4;
            add(streetNameField,gc);
            
            gc.gridy=6;
            add(cityField,gc);
            
            //Last  2 rows
            
            gc.gridx=0;
            gc.weighty=1;
            gc.gridwidth=GridBagConstraints.REMAINDER;
            gc.anchor=GridBagConstraints.PAGE_START;
            
            gc.gridy=8;
            add(createButton,gc);
            
            gc.gridy=9;
            gc.weighty=3;
            add(alreadyButton,gc);
            
            }
    public String getPseudo()
    {
        return pseudoField.getText();
    }
    public String getFirstName()
    {
        return firstNameField.getText();
    }
    public String getLastName()
    {
        return lastNameField.getText();
        
    }
    public String getBirthday()
    {
        return birthdayField.getText();
        
    }
    
    public String getStreetNumber()
    {
        return streetNumberField.getText();
    }
    
    public String getStreetName()
    {
        return streetNameField.getText();
    }
    
    public String getCity()
    {
        return cityField.getText();
    }
    
    public String getZipCode()
    {
        return zipcodeField.getText();
    }
            
            
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
public class SignUpViewPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int WIDTH = 10;

	// Components
	private final JLabel pseudoLabel = new JLabel("Pseudo  : ");
	private final JLabel birthdayLabel = new JLabel("Birthday (YYYY/MM/DD) : ");
	private final JLabel lastNameLabel = new JLabel("Last Name : ");
	private final JLabel firstNameLabel = new JLabel("First Name : ");
	private final JLabel streetNameLabel = new JLabel("Street : ");
	private final JLabel streetNumberLabel = new JLabel("Number :  ");
	private final JLabel cityLabel = new JLabel("City : ");
	private final JLabel zipcodeLabel = new JLabel("Zip Code : ");
	private final JLabel mailLabel = new JLabel("Mail : ");

	protected final JTextField pseudoField = new JTextField(WIDTH);
	protected final JTextField birthdayField = new JTextField(WIDTH);
	protected final JTextField lastNameField = new JTextField(WIDTH);
	protected final JTextField firstNameField = new JTextField(WIDTH);
	protected final JTextField streetNameField = new JTextField(WIDTH);
	protected final JTextField streetNumberField = new JTextField(WIDTH);
	protected final JTextField cityField = new JTextField(WIDTH);
	protected final JTextField zipcodeField = new JTextField(WIDTH);
	protected final JTextField mailField = new JTextField(WIDTH);
	protected final JButton createButton = new JButton("Create");
	protected final JButton alreadyButton = new JButton(
			"Already have an account?");

	public SignUpViewPanel() {

		// Set Layout
		setBorder(BorderFactory.createTitledBorder("SignUp"));
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		// placing components
		// first column
		gc.gridx = 0;
		gc.weighty = 0.5;
		gc.weightx = 0.5;
		gc.anchor = GridBagConstraints.LINE_END;

		gc.gridy = 0;
		add(pseudoLabel, gc);

		gc.gridy = 1;
		add(birthdayLabel, gc);

		gc.gridy = 2;
		add(firstNameLabel, gc);

		gc.gridy = 3;
		add(lastNameLabel, gc);

		gc.gridy = 4;
		add(mailLabel, gc);

		gc.gridy = 5;
		add(streetNumberLabel, gc);

		gc.gridy = (6);
		add(streetNameLabel, gc);

		gc.gridy = 7;
		add(cityLabel, gc);

		gc.gridy = 8;
		add(zipcodeLabel, gc);

		// Second Column
		gc.weightx = 1;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		// gc.weightx=10;
		// gc.fill=GridBagConstraints.HORIZONTAL;

		add(zipcodeField, gc);

		gc.gridy = 0;
		add(pseudoField, gc);

		gc.gridy = 1;
		add(birthdayField, gc);

		gc.gridy = 2;
		add(firstNameField, gc);

		gc.gridy = 3;
		add(lastNameField, gc);

		gc.gridy = 4;
		add(mailField, gc);

		gc.gridy = 5;
		add(streetNumberField, gc);

		gc.gridy = 6;
		add(streetNameField, gc);

		gc.gridy = 7;
		add(cityField, gc);

		// Last 2 rows

		gc.gridx = 0;
		gc.weighty = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.PAGE_START;

		gc.gridy = 9;
		add(createButton, gc);

		gc.gridy = 10;
		gc.weighty = 3;
		add(alreadyButton, gc);

	}

	public String getPseudo() {
		return pseudoField.getText();
	}

	public String getFirstName() {
		return firstNameField.getText();
	}

	public String getLastName() {
		return lastNameField.getText();

	}

	public String getBirthday() {
		return this.birthdayField.getText();
	}

	public String getStreetNumber() {
		return streetNumberField.getText();
	}

	public String getStreetName() {
		return streetNameField.getText();
	}

	public String getCity() {
		return cityField.getText();
	}

	public String getZipCode() {
		return zipcodeField.getText();
	}

	public String getMail() {
		return mailField.getText();
	}

	public void addLabel(String labelText, int column, int row) {
		GridBagLayout layout = (GridBagLayout) this.getLayout();
		GridBagConstraints gc = layout.getConstraints(this);

		gc.gridx = column;
		gc.gridy = row;

		this.add(new JLabel(labelText), gc);
		return;
	}

}

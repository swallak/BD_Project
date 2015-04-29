/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import controller.SignUpController;

import java.awt.Point;

/**
 *
 * @author swallak
 */
public class SignUpViewFrame extends MainFrame {

	private static final long serialVersionUID = 1L;

	private Dimension size = new Dimension(300, 300);
	public final static Point DEFAULT_LOCATION = new Point(50, 50);

	private SignUpViewPanel signUp = new SignUpViewPanel();
	private SignUpController controller;

	public SignUpViewFrame(String title) {

		super(title);

		controller = new SignUpController();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(DEFAULT_LOCATION);
		setResizable(false);
		setSize(size);
		// Set Layout
		setLayout(new BorderLayout());

		// add component
		Container container = getContentPane();
		container.add(signUp, BorderLayout.CENTER);
		// container.add(signUp, BorderLayout.CENTER,1);

		// Handle events
		// Buttons action
		signUp.createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createAccount(SignUpViewFrame.this);
				setSwitchToFrame2(Room.SIGN_IN_FRAME); // new SignInViewFrame("SignIn")
				switchFrame2();
			}

		});

		signUp.alreadyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSwitchToFrame(Room.SIGN_IN_FRAME); // new SignInViewFrame("Sign in")
				switchFrame();

			}
		});
	}

	public SignUpViewPanel getSignUpViewPanel() {
		return this.signUp;
	}

}
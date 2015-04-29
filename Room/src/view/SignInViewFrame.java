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

import model.user.User;
import controller.LogInController;
import java.awt.Point;

/**
 *
 * @author swallak
 */
public class SignInViewFrame extends MainFrame {

	private static final long serialVersionUID = 1L;

	private Dimension size = new Dimension(300, 150);
	public final static Point DEFAULT_LOCATION = new Point(50, 50);
	SignInViewPanel signIn = new SignInViewPanel();

	public SignInViewFrame(String title) {

		super(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(DEFAULT_LOCATION);
		setResizable(false);
		// Set Layout
		setLayout(new BorderLayout());
		setSize(size);

		// Create component
		final SignInViewPanel signIn = new SignInViewPanel();

		// add component
		Container container = getContentPane();
		container.add(signIn, BorderLayout.CENTER);
		// container.add(signUp, BorderLayout.CENTER,1);

		// Handle events
		// Buttons action

		// final Point location = this.getLocation();
		signIn.connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				User user;

				// throw new
				// UnsupportedOperationException("Needs Database Access");
				user = new LogInController(signIn.getPseudo(), signIn
						.getBirthday()).connect(SignInViewFrame.this);
				setSwitchToFrame2(new HomeViewFrame("Home" + user.getPseudo(),
						user));
				switchFrame2();

			}
		});

		signIn.createAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSwitchToFrame(Room.SIGN_UP_FRAME); //new SignUpViewFrame("Sign up")
				switchFrame();

			}
		});
	}

	public SignInViewPanel getSignInViewPanel() {
		return this.signIn;
	}

}

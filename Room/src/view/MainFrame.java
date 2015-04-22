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
import javax.swing.JPanel;

import controller.ApplicationController;

/**
 *
 * @author swallak
 */
public class MainFrame extends JFrame {
	private ApplicationController applicationController;
	
	private Dimension size = new Dimension(600, 480);
	private Container container;

	public MainFrame(String title) {

		super(title);

		// Set Layout
		setLayout(new BorderLayout());
		setMinimumSize(size);

		// Create component
		SignInView signIn = new SignInView(this);

		// add component
		container = getContentPane();
		container.add(signIn, BorderLayout.CENTER);
		setVisible(true);

		// Handle events
	}

	public void changeContentPanel(JPanel newPan) {
		container.removeAll();
		container.add(newPan, BorderLayout.CENTER);
		container.revalidate();
	}
	
	public ApplicationController getApplicationController() {
		return applicationController;
	}

}

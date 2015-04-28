/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

/**
 *
 * @author swallak
 */
public class MainFrame extends JFrame {

    protected MainFrame switchToFrame;
    protected MainFrame switchToFrame2;

    public MainFrame(String title) {
        super(title);
       
    }

    protected void switchFrame() {
        switchToFrame.setLocation(this.getLocation());
        switchToFrame.setVisible(true);
        this.setVisible(false);
    }

    protected void setSwitchToFrame(MainFrame frame) {

        this.switchToFrame = frame;
            //this.switchToFrame.setVisible(false);

    }

    /**
     *
     * @param frame
     */
    protected void setSwitchToFrame2(MainFrame frame) {
        this.switchToFrame2 = frame;
    }

    protected void switchFrame2() {
        switchToFrame2.setLocation(this.getLocation());
        switchToFrame2.setVisible(true);
        this.setVisible(false);
    }
    
        
    public void popErrorDialog(String message) {
    	//Pop window
        final JFrame accountNotFoundFrame = new JFrame("ERROR");
        JButton closeButton = new JButton("Close");
        JLabel msgLabel = new JLabel(message);
        Point location = this.getLocation();
        location.setLocation(location.x+(this.getWidth()-400)/2, location.y+this.getHeight()/2-40);
        
        accountNotFoundFrame.setUndecorated(true);
        accountNotFoundFrame.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);
        accountNotFoundFrame.setSize(400, 80);
        accountNotFoundFrame.setLocation(location);
        accountNotFoundFrame.setLayout(new GridBagLayout());
        accountNotFoundFrame.setResizable(false);

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.weighty = 1;
        gc.weightx = 1;

        gc.gridy = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        accountNotFoundFrame.add(msgLabel, gc);

        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        accountNotFoundFrame.add(closeButton, gc);
        
        accountNotFoundFrame.setVisible(true);
        //Windows Behaviour
        
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //close pop window
                accountNotFoundFrame.dispose();
            }
        });
    }
    

}

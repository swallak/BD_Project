/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JFrame;

/**
 *
 * @author swallak
 */
public class MainFrame extends JFrame {

    protected MainFrame switchToFrame;
    protected MainFrame switchToFrame2;

    public MainFrame(String title) {
        super(title);
//            switchToFrame.setVisible(false);
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

}

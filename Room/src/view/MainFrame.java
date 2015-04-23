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
public class MainFrame extends JFrame{
    
        protected MainFrame switchToFrame;
        
        public MainFrame (String title)
        {
            super(title);
//            switchToFrame.setVisible(false);
        }
        protected void switchFrame (MainFrame jframe)
        {
            jframe.setVisible(true);
            this.setVisible(false);
        }
        
        protected void setSwitchToFrame(MainFrame frame)
        {
            
            this.switchToFrame=frame;
            this.switchToFrame.setVisible(false);
            
        }
    
}

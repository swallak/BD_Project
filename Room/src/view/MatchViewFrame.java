/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MatchController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import model.user.AbstractUser;

/**
 *
 * @author swallak
 */
public class MatchViewFrame extends MainFrame {
    
    private MatchController matchController;
    private MatchViewPanel matchViewPanel;
    
    private final Dimension size = new Dimension (1366, 768);    
    public MatchViewFrame(String title) {
        super(title);
        
        
        
        //set MatchController
        
        //Layout And size
        setLayout(new BorderLayout());
        setSize(size);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //set Default closing operation
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Must be changed
        
        
        //Creating Components
        
        matchViewPanel = new MatchViewPanel();
        //Adding component
        
        add(matchViewPanel, BorderLayout.CENTER);
        
    }
    public void setMatchController(MatchController matchController)
    {
        this.matchController=matchController;
    }
    
    public MatchViewPanel getMatchViewPanel()
    {
        return this.matchViewPanel;
    }

    public class MatchViewPanel extends JPanel {

        private JToolBar toolBar;
        private JScrollPane firstUserLog;
        private JScrollPane secondUserLog;
        public JToolBar getToolBat() {

            return this.toolBar;
        }

        public MatchViewPanel() {

            //setLayout
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder("matchController.toString()"));

            //addComponent
            toolBar = createToolBar("Actions");
            JPanel firstUserPanel = new MatchViewPanelPlayer("matchController.getFirstUser()");
            JPanel secondUserPanel = new MatchViewPanelPlayer("matchController.getSecondUser()");
            
            firstUserLog = createScrollPane(firstUserPanel);
            secondUserLog= createScrollPane(secondUserPanel);
            
            
            
            //Placing component
            GridBagConstraints gc = new GridBagConstraints();
            
            gc.gridx=0;
            gc.gridy=0;
            gc.weighty=0.1;
            gc.weightx=1;
            add(toolBar, gc);
            
            gc.weighty=10;
            gc.fill=GridBagConstraints.VERTICAL;
            gc.gridx=0;
            gc.gridy=1;
            add(firstUserLog,gc);
            
            gc.gridx=1;
            add(secondUserLog,gc);
            
            
            
            

        }

        private JToolBar createToolBar(String actions) {
            
            JToolBar bar = new JToolBar(actions);
            bar.setFloatable(false);
            return bar;
        }
        
        private JScrollPane createScrollPane(JPanel panel)
        {
            
            JScrollPane scroll = new JScrollPane(panel);
            
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setBounds(panel.getBounds());
            return scroll;
          
        }

    }

    public class MatchViewPanelPlayer extends JPanel {

        private int currentColumn=0;
        private int currentRow=0;
        
        public MatchViewPanelPlayer(String userName)
        {
            //setLayout
            
            /*GridBagConstraints gc = new GridBagConstraints();
            gc.gridx=currentColumn;
            gc.gridy=currentRow;
            gc.weightx=1;
            gc.weightx=1;
            gc.anchor=GridBagConstraints.LINE_START;
            */
            

            //GridBagLayout layout = new GridBagLayout();
            //layout.setConstraints(this, gc);
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder(userName));
            Dimension size = new Dimension( 200, 300);
            setPreferredSize(size);
            
            
            
        }
        public MatchViewPanelPlayer(AbstractUser user) {
            
            
            this(user.getPseudo());
            System.out.println("to complete");
        }
        public void addPanel(String logMsg)
        {
            GridBagConstraints gc = new GridBagConstraints();
            currentColumn++;
            currentRow++;
            gc.gridx = currentColumn;
            gc.gridy = currentRow;
            gc.weightx = 1;
            gc.weightx = 1;
            gc.anchor = GridBagConstraints.LINE_START;
            
            this.add(new JLabel(logMsg));
        }

    }

}

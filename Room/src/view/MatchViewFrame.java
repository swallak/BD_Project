/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MatchController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    
    private final Dimension size = new Dimension (1000, 700);    
    
    public MatchViewFrame(String title) {
        super(title);
        
        
        //set MatchController
        
        //Layout And size
        setLayout(new BorderLayout());
        setSize(size);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        
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
        private MatchViewPanelPlayer firstUserLog;
        private MatchViewPanelPlayer secondUserLog;
        private MatchViewGrid grid;
        public JToolBar getToolBat() {

            return this.toolBar;
        }

        public MatchViewPanel() {

            //setLayout
            Dimension size = new Dimension(1024,768);
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder("matchController.toString()"));
            setPreferredSize(size);
            
            //addComponent
            toolBar = createToolBar("Actions");
            size = new Dimension (250,300);
            firstUserLog = new MatchViewPanelPlayer("matchController.getFirstUser()");
            secondUserLog = new MatchViewPanelPlayer("matchController.getSecondUser()");
            firstUserLog.setPreferredSize(size);
            secondUserLog.setPreferredSize(size);
            size = new Dimension(500,500);
            grid = new MatchViewGrid(10, 10);
            grid.setPreferredSize(size);
            
            //toolBar
            
            JButton button = createButton("/icon/move.png", "Move Ship");
            toolBar.add(button);
            button= createButton("/icon/fire.png","Fire with a Ship");
            toolBar.add(button);
            button = createButton("/icon/refresh.png", "Refresh");
            toolBar.add(button);
            button = createButton("/icon/giveup.png", "Abandon");
            toolBar.add(button);
            
            
            //Placing component
            GridBagConstraints gc = new GridBagConstraints();
            
            gc.gridx=0;
            gc.gridy=0;
            gc.weighty=1;
            gc.weightx=1;
            gc.gridwidth=2;
            gc.anchor= GridBagConstraints.PAGE_END;
            add(toolBar, gc);
            
            gc.gridwidth=1;
            gc.weighty=1;
            gc.anchor= GridBagConstraints.CENTER;

            gc.gridy=1;
            add(firstUserLog,gc);
            
            gc.gridx=1;
            add(secondUserLog,gc);
            
            
            gc.gridx=2;
            gc.gridy=0;
            gc.gridheight=2;
            gc.anchor= GridBagConstraints.CENTER;
            gc.weightx=10;
            add(grid,gc);
            
            //toolBar Behaviour
            
            //FirstButton
            button = (JButton)toolBar.getComponentAtIndex(0);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    actionPopupWindow(0); //To change body of generated methods, choose Tools | Templates.
                }
            });
            }
            

        }
        public void actionPopupWindow(int i){
            
            
            
            switch (i){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                        break;
                    
            
            }
        }
        
        
        
        private JToolBar createToolBar(String actions) {
            
            JToolBar bar = new JToolBar(actions);
            bar.setFloatable(false);
            bar.setRollover(true);
            bar.setOrientation(JToolBar.HORIZONTAL);
            return bar;
        }
        
        private JButton createButton( String source, String rollOverMsg)
        {
            Image img;
            JButton button; 
            try {
                img = ImageIO.read(getClass().getResource(source));
                button = new JButton(new ImageIcon(img));
            } catch (IOException ex) {
                //Logger.getLogger(MatchViewFrame.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Image Introuvable");
                return new JButton("Img Introuvable");
            }catch(IllegalArgumentException ex){
               System.out.println("Image Introuvable");
                return new JButton("Img Introuvable");
            }

           button.setToolTipText(rollOverMsg);
           return button;
            
        }
        

    public class MatchViewPanelPlayer extends JPanel {

        private JTextArea playerLog = new JTextArea();
        
        public MatchViewPanelPlayer(String userName)
        {
            //setLayout
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder(userName));
            playerLog.setEditable(false);
            //Creating components
            JScrollPane playerLogScroll = createScrollPane(playerLog);
            add(playerLogScroll,BorderLayout.CENTER);
            
            
            
        }
        public MatchViewPanelPlayer(AbstractUser user) {
            
            
            this(user.getPseudo());
            System.out.println("to complete");
        }
        public void addMsg(String logMsg)
        {
            
            this.playerLog.append(logMsg);
        }
        public JTextArea getPlayerLog()
        {
            return this.playerLog;
        }
        private JScrollPane createScrollPane(JTextArea textArea)
        {
            
            JScrollPane scroll = new JScrollPane(textArea);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            //scroll.setBounds(textArea.getBounds());
            return scroll;
          
        }

    }
    
    public class MatchViewGrid extends JPanel {
        
        private JPanel[][] cells;
        public MatchViewGrid(int rows, int columns){
            
            cells = new JPanel[rows][columns];
            setLayout(new GridBagLayout());
            
            GridBagConstraints gc = new GridBagConstraints();
            gc.weightx=1;
            gc.weighty=1;
            gc.fill=GridBagConstraints.BOTH;
            
            initDecorateCells(Color.gray);
            
            for(int i=0; i<cells.length; i++){
                gc.gridy=i;
                for (int j=0; j<cells[i].length;j++)
                {gc.gridx=j; add(cells[i][j],gc);}
            }
            
            
        }
        
        private void initDecorateCells(Color color)
        {
        
            for (int i=0; i< cells.length; i++)
            {
                for(int j=0; j<cells[i].length; j++)
                {
                    cells[i][j]= new JPanel();
                    cells[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
                    cells[i][j].setBackground(color);
                }
            }
        }
        
        public void changeColor(int row, int column, Color color )
        {
            cells[row][column].setBackground(color);
        }
        
        
                
    }
    
    public class MatchActionFrame extends MainFrame{
        
        JTextField xPos = new JTextField("xPos");
        JTextField yPos = new JTextField("yPos");
        JComboBox<String> ships;
        JButton validate = new JButton ("validate");
        
        public MatchActionFrame(String title, String[]ships)
        {
            super(title,ships);
            setSize(new Dimension(300,200));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setResizable(false);
            
            this.ships = new JComboBox<>(ships);
            
            Container container = getContentPane();
            GridBagConstraints gc = new GridBagConstraints();
            
            gc.gridx=0;
            gc.gridy=0;
            gc.gridwidth=2;
            gc.weighty=1;
            
            gc.fill= GridBagConstraints.HORIZONTAL;
            container.add(this.ships,gc);
            
            gc.fill= GridBagConstraints.NONE;
            gc.gridwidth=1;
            
            gc.gridy=2;
            gc.weighty=10;
            container.add(this.validate,gc);
            
            gc.gridy=1;
            gc.weighty=1;
            container.add(this.xPos,gc);
            
            gc.gridx=1;
            container.add(this.yPos,gc);
            
            setVisible(true);
            
        }
        
    }
    
    public class MatchActionMoveFrame extends MatchActionFrame{
        

       
        public MatchActionMoveFrame(String title, String[] ships)
        {
            super(title,ships);
            
        }
        
        
       
    }
    
    
    public class MatchActionShootFrame extends MatchActionFrame{
        
        public MatchActionShootFrame(String title)
        {
            super(title);
        }
    }
    
}


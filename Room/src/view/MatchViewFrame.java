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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
        public JToolBar getToolBar() {

            return this.toolBar;
        }

        public MatchViewPanel() {

            //setLayout
            Dimension size = new Dimension(1000,700);
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder(matchController.toString()));
            setSize(size);
            
            //addComponent
            toolBar = createToolBar("Actions");
            size = new Dimension (250,300);
            firstUserLog = new MatchViewPanelPlayer(matchController.getFirstUser());
            secondUserLog = new MatchViewPanelPlayer(matchController.getSecondUser());
            firstUserLog.setSize(size);
            secondUserLog.setSize(size);
            size = new Dimension(500,500);
            grid = new MatchViewGrid(10, 10);
            grid.setSize(size);
            
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
            gc.anchor= GridBagConstraints.CENTER;
            add(toolBar, gc);
            
            gc.gridwidth=1;
            gc.weighty=1;
            gc.anchor= GridBagConstraints.CENTER;

            gc.fill=GridBagConstraints.BOTH;
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
                    actionPopupWindow(0);
                }
            });
            
            button =(JButton)toolBar.getComponentAtIndex(1);
            button.addActionListener(new ActionListener() {

                @Override
                   public void actionPerformed(ActionEvent e) {
                    actionPopupWindow(1);
                }
            });
            
            button =(JButton)toolBar.getComponentAtIndex(3);
            button.addActionListener(new ActionListener() {

                @Override
                   public void actionPerformed(ActionEvent e) {
                    actionPopupWindow(3);
                }
            });
            
            }
            

        }
        public void actionPopupWindow(int i){
            
            String[] ships = {"this", "is", "temporal"};
            
            switch (i){
                case 0: new MatchActionMoveFrame("Se Deplacer", ships);
                    break;
                case 1: new MatchActionShootFrame("Tirer", ships);
                    break;
                case 2:
                    break;
                case 3: new MatchActionAbandonFrame();
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
    
    public static class MatchActionFrame extends MainFrame{
        
        private JTextField xPos = new JTextField("xPos");
        private JTextField yPos = new JTextField("yPos");
        private JComboBox<String> ships;
        private JButton validate = new JButton ("validate");
        //selected ship's name Ship's coordinate
        String selectedShip;
        
        public MatchActionFrame(String title, String[]ships)
        {
            super(title);
            setSize(new Dimension(300,150));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setResizable(false);
            
            this.ships = new JComboBox<>(ships);
            
            Container container = getContentPane();
            container.setLayout(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            
            gc.gridx=0;
            gc.gridy=0;
            gc.gridwidth=2;
            gc.weightx=1;
            gc.weighty=1;
            container.add(this.ships,gc);
            
            gc.gridwidth=1;
            
            gc.gridy=3;
            gc.weighty=10;
            container.add(this.validate,gc);
            
            gc.weighty=1;
            gc.gridy=1;
            container.add(this.xPos,gc);
            
            gc.gridy=2;
            container.add(this.yPos,gc);
            
            setVisible(true);
            
            //Event Handler
                // the combobox
            
            
            this.ships.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    MatchActionFrame.this.selectedShip=(String)MatchActionFrame.this.ships.getSelectedItem();
                   
                }
            });
            
                //the textField
            
            this.xPos.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    
                    xPos.setText("");
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            this.yPos.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    
                    yPos.setText("");
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            
        }
        
        public String getSelectedShip(){
            return this.selectedShip;
        }
        public JComboBox<String> getShips(){
            return this.ships;
        }
        
        
        public int getXPos()
        {
            int pos;
            pos= Integer.parseInt(this.xPos.getText());
            return pos;
        }
        public int getYPos()
        {
            int pos;
            pos= Integer.parseInt(this.yPos.getText());
            return pos;
        }
    }
    
    public static class MatchActionMoveFrame extends MatchActionFrame{
        

       
        public MatchActionMoveFrame(String title, String[] ships)
        {
            super(title,ships);
            
            super.validate.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            
        }
        
        
       
    }
    
    
    public class MatchActionShootFrame extends MatchActionFrame{
        
        public MatchActionShootFrame(String title, String[] ships)
        {
            super(title, ships);
            super.validate.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }
    
    public class MatchActionAbandonFrame extends MainFrame{
        
        JButton yesButton = new JButton("Yes");
        public MatchActionAbandonFrame()
        {
            super("Surrender!");
            
            JLabel label = new JLabel("Are you sure? ");
            
            
            setSize(new Dimension(300,150));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setResizable(false);
            setVisible(true);
            
            add(label, BorderLayout.CENTER);
            add(yesButton, BorderLayout.PAGE_END);
            
            yesButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }
            );
        }
    }
    
    public class MatchInitFrame extends MainFrame{
        
        JButton validate = new JButton("Validate");
        JTextField[][] shipsPosition = new JTextField[3][2];
        JRadioButton optionnal;
        public MatchInitFrame ()
        {
            super("Choose your Ships");
            
            JLabel label = new JLabel("Select your ships");
            
            
            setSize(new Dimension(300,150));
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setLayout(new GridBagLayout());
            setResizable(false);
            setVisible(true);
            
            GridBagConstraints gc = new GridBagConstraints();
            
            gc.weightx=0.5;
            gc.weighty=0.5;
            
            gc.gridx=0;
            gc.gridy=1;
            add(new JLabel("Destroyer"),gc);
            
            gc.gridy=1;
            add(new JLabel("Escorteur"),gc );
            
            gc.gridy=2;
            add(new JLabel ("Escorteur(Optionnel)"),gc);
            
            
            gc.gridx=0;
            gc.gridy=0;
                    //Frame components
            for(int i = 0; i<shipsPosition.length; i++,gc.gridx++)
            {
                for (int j=0; j<shipsPosition[3].length; j++, gc.gridy++)
                {
                    
                }
            }
            
            
            
            
            
            
        }
    }
}


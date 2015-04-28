package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import model.game.Match;
import view.MatchViewFrame.MatchViewGrid;

public class ObservationViewPanel extends JPanel{
	
	private MatchViewGrid gridPlayer1;
	private MatchViewGrid gridPlayer2;
	private JToolBar toolBar;
	private JLabel grid1Label = new JLabel("Grid 1");
    private JLabel grid2Label = new JLabel ("Grid 2");
    private JLabel labelPlayer1;
    private JLabel labelPlayer2;
	
	protected ObservationViewPanel(Match m) {
		
		//Set Layout
		Dimension size = new Dimension(1024,768);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Game Observation"));
        setPreferredSize(size);
        
        //addComponent
        toolBar = createToolBar("Actions");
        
        //toolBar
        JButton button = createButton("/icon/skip_backward.png", "Return to the beginning");
        toolBar.add(button);
        button = createButton("/icon/forward.png", "One action forward");
        toolBar.add(button);
        
        //labels
        labelPlayer1 = new JLabel("Player: "+ m.getPlayerOne().getPseudo());
        labelPlayer2 = new JLabel("Player: "+ m.getPlayerTwo().getPseudo());
        
        //Placing component
        GridBagConstraints gc = new GridBagConstraints();
        
        size = new Dimension(300,300);
        gridPlayer1 = new MatchViewGrid(10, 10);
        gridPlayer1.setPreferredSize(size);
        
        size = new Dimension(300,300);
        gridPlayer2 = new MatchViewGrid(10, 10);
        gridPlayer2.setPreferredSize(size);
        

        gc.gridx=0;
        gc.gridy=0;
        gc.weighty=1.2;
        gc.anchor= GridBagConstraints.WEST;
        add(grid1Label,gc);
        
        gc.gridx=2;
        gc.gridy=0;
        gc.weighty=1.2;
        gc.anchor= GridBagConstraints.EAST;
        add(grid2Label,gc);
        
        gc.gridx=0;
        gc.gridy=1;
        gc.weighty=1.2;
        gc.anchor= GridBagConstraints.WEST;
        add(labelPlayer1,gc);

        gc.gridx=2;
        gc.gridy=1;
        gc.weighty=1.2;
        gc.anchor= GridBagConstraints.EAST;
        add(labelPlayer2,gc);
        
        
        gc.gridx=0;
        gc.gridy=2;
        gc.gridheight=2;
        gc.anchor= GridBagConstraints.WEST;
        gc.weightx=10;
        add(gridPlayer1,gc);

        
        gc.gridx=2;
        gc.gridy=2;
        gc.gridheight=2;
        gc.anchor= GridBagConstraints.EAST;
        gc.weightx=10;
        add(gridPlayer2,gc);
        
        //Toolbar
        gc.gridx=1;
        gc.gridy=2;
        gc.gridheight=2;
        gc.weightx=10;
        gc.anchor= GridBagConstraints.CENTER;
        add(toolBar, gc);
        
        
	}
	
	private JToolBar createToolBar(String actions) {
        
        JToolBar bar = new JToolBar(actions);
        bar.setFloatable(false);
        bar.setRollover(true);
        bar.setOrientation(JToolBar.VERTICAL);
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

}

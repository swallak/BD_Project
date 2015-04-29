package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import view.MatchViewGrid.SupperposedBoatException;
import model.game.Boat;
import model.game.Match;
import controller.ObservationController;

public class ObservationViewPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ObservationController controller;
	
	private MatchViewGrid gridPlayer1;
	private MatchViewGrid gridPlayer2;
	private JToolBar toolBar;
	private JLabel grid1Label = new JLabel("Grid 1");
    private JLabel grid2Label = new JLabel ("Grid 2");
    private JLabel labelPlayer1;
    private JLabel labelPlayer2;
    protected final JButton returnButton = createButton("/icon/home.png", "Return to home");
    protected final JButton restartButton = createButton("/icon/skip_backward.png", "Return to first turn");
    protected final JButton forwardButton = createButton("/icon/forward.png", "One action forward");
	
	protected ObservationViewPanel(Match m) {
		controller = new ObservationController(m);
		
		//Set Layout
		Dimension size = new Dimension(1024,768);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Game Observation"));
        setPreferredSize(size);
        
        //ToolBar
        toolBar = createToolBar("Actions");
        toolBar.add(this.forwardButton);
        toolBar.add(this.restartButton);
        toolBar.add(this.returnButton);

        
        
        
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
        
        restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.goToStart(ObservationViewPanel.this);
			}
		});
        
        forwardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.nextTurn(ObservationViewPanel.this);
			}
		});
        
        controller.initView(ObservationViewPanel.this);
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
    
    public void displayBoat(List<Boat> boatsPlayerOne, List<Boat> boatsPlayerTwo) throws SupperposedBoatException {
    		gridPlayer1.displayBoatList(boatsPlayerOne);
    		gridPlayer2.displayBoatList(boatsPlayerTwo);
    }

}

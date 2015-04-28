package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.game.Boat;

public class MatchViewGrid extends JPanel {
    
    private JPanel[][] cells;
    
    private final static Color EMPTY_CELL_COLOR = Color.BLUE;
    private final static Color INTACT_BOAT = Color.GREEN;
    private final static Color DAMAGE_BOAT = Color.YELLOW;
    
    private int rows;
    private int columns;
    
    public MatchViewGrid(int rows, int columns){
        this.columns = columns;
        this.rows = rows;
    	
        cells = new JPanel[rows][columns];
        setLayout(new GridBagLayout());
        
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx=1;
        gc.weighty=1;
        gc.fill=GridBagConstraints.BOTH;
        
        initDecorateCells(EMPTY_CELL_COLOR);
        
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
    
    public void displayBoatList(List<Boat> boats) {
    	 for (int i=0; i< cells.length; i++)
         {
             for(int j=0; j<cells[i].length; j++)
             {
                 changeColor(i, j, EMPTY_CELL_COLOR);
             }
         }
    	 
    	 for(Boat b : boats) {
			Color color = getBoatColor(b);
			int pivotX = b.getPosition().getX();
			int pivotY = rows - b.getPosition().getY();
			changeColor(pivotX, pivotY, color);
			switch (b.getOrientation()) {
			case BOTTOM:
				for (int y = 1; y <= b.getSize(); y++)
					changeColor(pivotY + y, pivotX, color);
				break;
			case LEFT:
				for (int x = 1; x <= b.getSize(); x++)
					changeColor(pivotY, pivotX - x, color);
				break;
			case RIGHT:
				for (int x = 1; x <= b.getSize(); x++)
					changeColor(pivotY, pivotX + x, color);
				break;
			case TOP:
				for (int y = 1; y <= b.getSize(); y++)
					changeColor(pivotY - y, pivotX, color);
				
				break;
			default:
				break;
    		 }
    	 }
    }
    
	public Color getBoatColor(Boat b) {
		if (b.getSize() == b.getHp())
			return INTACT_BOAT;
		else
			return DAMAGE_BOAT;
	}
}
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

	private final static Color[] COLOR_BOAT_SIZE_2 = {Color.BLUE, Color.RED, Color.GREEN};
	private final static Color[] COLOR_BOAT_SIZE_3 = {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN};
	
	private int rows;
	private int columns;

	public MatchViewGrid(int rows, int columns) {
		this.columns = columns;
		this.rows = rows;

		cells = new JPanel[rows][columns];
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;

		initDecorateCells(EMPTY_CELL_COLOR);

		for (int i = 0; i < cells.length; i++) {
			gc.gridy = i;
			for (int j = 0; j < cells[i].length; j++) {
				gc.gridx = j;
				add(cells[i][j], gc);
			}
		}

	}

	private void initDecorateCells(Color color) {

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new JPanel();
				cells[i][j].setBorder(BorderFactory
						.createLineBorder(Color.WHITE));
				cells[i][j].setBackground(color);
			}
		}
	}

	public void changeColor(int row, int column, Color color) {
		cells[row][column].setBackground(color);
	}

	public void displayBoatList(List<Boat> boats) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				changeColor(i, j, EMPTY_CELL_COLOR);
			}
		}

		for (Boat b : boats) {
			if (b.getHp() != 0) {
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
	}

	public Color getBoatColor(Boat b) {
		if(b.getSize() == 2)
			return COLOR_BOAT_SIZE_2[b.getHp()];
		if(b.getSize() == 3)
			return COLOR_BOAT_SIZE_3[b.getHp()];
		return Color.GREEN;
	}
}
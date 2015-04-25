package model.game;

import java.util.List;

import model.game.Boat.Orientation;

public class UndoAction {

	private final static UndoAction instance = new UndoAction();

	public static UndoAction getInstance() {
		return instance;
	}

	public void undo(Left actionMove) {
		actionMove.getBoat().setOrientation(
				Orientation.fromInteger(actionMove.getBoat().getOrientation()
						.getValue() - 1));
	}

	public void undo(Right actionMove) {
		actionMove.getBoat().setOrientation(
				Orientation.fromInteger(actionMove.getBoat().getOrientation()
						.getValue() + 1));
	}

	public void undo(Forward actionMove) {
		Boat boat = actionMove.getBoat();
		switch (boat.getOrientation()) {

		case BOTTOM:
			boat.getPosition().setX(boat.getPosition().getX() + 1);
			break;
		case LEFT:
			boat.getPosition().setY(boat.getPosition().getY() + 1);
			break;
		case RIGHT:
			boat.getPosition().setY(boat.getPosition().getY() - 1);
			break;
		case TOP:
			boat.getPosition().setX(boat.getPosition().getX() - 1);
			break;
		default:
			break;
		}
	}

	public void undo(Backward actionMove) {
		Boat boat = actionMove.getBoat();
		switch (boat.getOrientation()) {

		case BOTTOM:
			boat.getPosition().setX(boat.getPosition().getX() - 1);
			break;
		case LEFT:
			boat.getPosition().setY(boat.getPosition().getY() - 1);
			break;
		case RIGHT:
			boat.getPosition().setY(boat.getPosition().getY() + 1);
			break;
		case TOP:
			boat.getPosition().setX(boat.getPosition().getX() + 1);
			break;
		default:
			break;
		}
	}

	public void undo(ShotAction shot) {
		List<Boat> boatList = (shot.getTurn().getNbTurn() % 2 == 0 ? shot
				.getTurn().getMatch().getPlayerTwoBoats() : shot.getTurn()
				.getMatch().getPlayerOneBoats());
		
		for(Boat boat : boatList ) {
			if(boat.isTouchBy(shot.getTarget()))
				boat.setHp(boat.getHp() + 1);
		}
	}
}

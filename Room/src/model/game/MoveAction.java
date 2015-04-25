package model.game;

public abstract class MoveAction extends Action {

	public MoveAction(Turn turn, Boat boat, int numAction) {
		super(turn, boat, numAction);
	}

	public abstract String getType();
}

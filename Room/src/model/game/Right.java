package model.game;

public class Right extends MoveAction {

	public Right(Turn turn, Boat boat) {
		super(turn, boat);
	}

	@Override
	public void apply() {
		DoAction.getInstance().apply(this);
	}

	@Override
	public void undo() {
		UndoAction.getInstance().undo(this);
	}
}

package model.game;

public final class Left extends MoveAction {

	public Left(Turn turn, Boat boat) {
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

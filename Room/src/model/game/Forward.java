package model.game;

public class Forward extends MoveAction {

	public Forward(Turn turn, Boat boat) {
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

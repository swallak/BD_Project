package model.game;

public final class Backward extends MoveAction {

	public final static String TYPE = "reculer";

	public Backward(Turn turn, Boat boat, int numAction) {
		super(turn, boat, numAction);
	}

	@Override
	public void apply() {
		DoAction.getInstance().apply(this);
	}

	@Override
	public void undo() {
		UndoAction.getInstance().undo(this);
	}

	@Override
	public String getType() {
		return TYPE;
	}
}

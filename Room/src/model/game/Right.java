package model.game;

public final class Right extends MoveAction {

	public final static String TYPE = "droite";

	public Right(Turn turn, Boat boat, int numAction) {
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

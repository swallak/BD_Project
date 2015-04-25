package model.game;

public final class Left extends MoveAction {

	private final static String TYPE = "gauche";
	
	public Left(Turn turn, Boat boat, int numAction) {
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

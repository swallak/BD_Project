package model.game;

public final class Forward extends MoveAction {

	private final static String TYPE = "avancer";

	public Forward(Turn turn, Boat boat, int numAction) {
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

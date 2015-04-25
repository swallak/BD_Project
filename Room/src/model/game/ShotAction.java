package model.game;

public final class ShotAction extends Action {

	private Position target;

	public Position getTarget() {
		return target;
	}

	public ShotAction(Turn turn, Boat boat, int numAction) {
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
}

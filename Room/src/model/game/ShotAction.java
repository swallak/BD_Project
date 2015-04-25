package model.game;

public final class ShotAction extends Action {

	private Position target;

	public Position getTarget() {
		return target;
	}

	public ShotAction(Turn turn, Boat boat, int numAction, Position target) {
		super(turn, boat, numAction);
		this.target = target;
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

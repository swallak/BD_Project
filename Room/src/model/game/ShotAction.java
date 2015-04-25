package model.game;

public class ShotAction extends Action {

	private Position target;
	
	public Position getTarget() {
		return target;
	}

	public ShotAction(Turn turn) {
		super(turn);
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

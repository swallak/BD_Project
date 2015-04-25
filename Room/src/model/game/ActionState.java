package model.game;

public enum ActionState {
	DONE(true), NOT_DONE(false);

	private final boolean state;

	private ActionState(boolean state) {
		this.state = state;
	}

	public boolean isDone() {
		return state;
	}

}

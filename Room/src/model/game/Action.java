package model.game;

public abstract class Action implements Comparable<Action> {

	private Turn turn;
	private int order;

	public Turn getTurn() {
		return turn;
	}

	public Action(Turn turn) {
		this.turn = turn;
	}

	public abstract void apply();

	public abstract void undo();

	@Override
	public int compareTo(Action o) {
		return order - o.order;
	}
}

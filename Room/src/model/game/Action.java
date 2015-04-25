package model.game;

public abstract class Action implements Comparable<Action> {

	private Turn turn;
	private int numAction;
	private Boat boat;

	public Turn getTurn() {
		return turn;
	}

	public Action(Turn turn, Boat boat, int numAction) {
		this.turn = turn;
		this.boat = boat;
		this.numAction = numAction;
	}

	public abstract void apply();

	public abstract void undo();

	@Override
	public int compareTo(Action o) {
		return numAction - o.numAction;
	}

	public Boat getBoat() {
		return boat;
	}

	public int getNumAction() {
		return numAction;
	}
}

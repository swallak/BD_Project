package model.game;

public abstract class MoveAction extends Action {

	private Boat boat;

	public MoveAction(Turn turn, Boat boat) {
		super(turn);
		this.boat = boat;
	}

	public Boat getBoat() {
		return boat;
	}

}

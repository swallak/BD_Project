package model.game;

import java.util.List;

import model.user.AbstractUser;

public class Turn implements Comparable<Turn> {

	private int nbTurn;
	private int actionIndex;

	private ActionState state;

	private Match match;
	private AbstractUser player;

	private List<Action> actionList;

	public Turn(Match match, AbstractUser player, int nbTurn) {
		this.match = match;
		this.player = player;
		this.nbTurn = nbTurn;
		this.state = ActionState.NOT_DONE;
	}

	@Override
	public int compareTo(Turn t) {
		return nbTurn - t.nbTurn;
	}

	public void apply() {
		if (!state.isDone())
			for (Action action : actionList)
				action.apply();
		state = ActionState.DONE;
	}

	public void undo() {
		if (state.isDone())
			for (int i = actionList.size() - 1; i >= 0; i--)
				actionList.get(i).undo();
		state = ActionState.NOT_DONE;
	}

	public int getNbTurn() {
		return nbTurn;
	}

	public int getActionIndex() {
		return actionIndex;
	}

	public Match getMatch() {
		return match;
	}

	public AbstractUser getPlayer() {
		return player;
	}

	public void addAction(Action action) {
		actionList.add(action);
		actionIndex++;
	}
}

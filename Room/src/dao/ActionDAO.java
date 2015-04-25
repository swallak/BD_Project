package dao;

import java.sql.Connection;

import model.game.Match;
import model.game.MoveAction;
import model.game.ShotAction;
import model.game.Turn;

public abstract class ActionDAO {

	public abstract void createAction(Connection con, boolean withCommit,
			MoveAction moveAction) throws ActionNotCreatedExcetpion;

	public abstract void createAction(Connection con, boolean withCommit,
			ShotAction shotAction) throws ActionNotCreatedExcetpion;

	
	public abstract Turn getActionInTurn(Connection con, boolean withCommit,
			Match match, int turnNumber) throws TurnNotExistsException;

	public class ActionNotCreatedExcetpion extends Exception {
		static final long serialVersionUID = -8840435449808767299L;
	}
	
	public class TurnNotExistsException extends Exception {
		private static final long serialVersionUID = -6348607672381071504L;
	}
	
	public class TurnNotReadableException extends Exception {
		private static final long serialVersionUID = -7501403438407183867L;
	}
}

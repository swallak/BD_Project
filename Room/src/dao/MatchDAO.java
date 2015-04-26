package dao;

import java.sql.Connection;
import java.util.List;

import model.game.Match;
import model.user.User;

public abstract class MatchDAO {

	public abstract List<Match> geObservableMatchHeader(Connection con,
			boolean withCommit, User currentUser);

	public abstract void getCurrentMatchInfo(Connection con,
			boolean withCommit, Match match) throws ReadMatchException, MatchNotExistsException;

	public abstract List<Match> getPlayableMatchHeader(Connection con,
			boolean withCommit, User currentUser);

	public abstract void createMatch(Connection con, boolean withCommit,
			Match match) throws MatchNotCreatedException;

	public class ReadMatchException extends Exception {
		private static final long serialVersionUID = -3467985904450291349L;
	}

	public class MatchNotCreatedException extends Exception {
		private static final long serialVersionUID = -4009275843537792206L;
	}

	public class MatchNotExistsException extends Exception {
		private static final long serialVersionUID = 5331107150378400158L;
	}
}

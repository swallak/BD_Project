package dao;

import java.sql.Connection;
import java.util.List;

import model.game.Match;
import model.user.User;

public abstract class MatchDAO {

	public abstract List<Match> geObservableMatchHeader(Connection con,
			boolean withCommit, User currentUser);

	public abstract void getCurrentMatchInfo(Connection con,
			boolean withCommit, Match match) throws ReadMatchException,
			MatchNotStartedException;

	public abstract List<Match> getPlayableMatchHeader(Connection con,
			boolean withCommit, User currentUser);

	public abstract void createMatch(Connection con, boolean withCommit,
			Match match) throws MatchNotCreatedException, MatchStateNotSave;

	public abstract void addWinner(Connection con, boolean withCommit,
			Match match) throws MatchStateNotSave;

	public class ReadMatchException extends Exception {
		private static final long serialVersionUID = -3467985904450291349L;
	}

	public class MatchNotCreatedException extends Exception {
		private static final long serialVersionUID = -4009275843537792206L;
	}

	public class MatchNotExistsException extends Exception {
		private static final long serialVersionUID = 5331107150378400158L;
	}

	public class MatchStateNotSave extends Exception {
		private static final long serialVersionUID = 3647389126664459701L;
	}
	
	public class MatchNotStartedException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}

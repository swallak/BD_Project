package dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import model.game.Boat;
import model.game.Match;
import model.user.AbstractUser;

public abstract class BoatDAO {

	/**
	 * Renvoie les bateaux d'un joueur associé à leur id. Attention ! L'état de
	 * ces bateaux correspond à l'état actuel de la partie. Cette méthode ne
	 * devrait pas être appelée directement mais plutot en passant par
	 * {@link MatchDAO#refreshMatch} ou
	 * {@link MatchDAO#getAllInfoMatch(Connection, Match)}
	 */
	public abstract Map<Integer, Boat> findBoatMap(Connection con,
			boolean withCommit, Match match, AbstractUser player);

	public abstract void createBoatList(Connection con, boolean withCommit,
			List<Boat> boats) throws BoatNotCreatedException;

	public abstract void updateBoat(Connection con, boolean withCommit,
			Boat boat) throws BoatStateNotSaveException;

	public class BoatNotCreatedException extends Exception {
		private static final long serialVersionUID = -352196270508443774L;
	}

	public class BoatStateNotSaveException extends Exception {
		private static final long serialVersionUID = -2771475782970596977L;
	}
}

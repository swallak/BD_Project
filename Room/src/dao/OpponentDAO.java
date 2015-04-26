package dao;

import java.sql.Connection;

import model.user.Opponent;
import model.user.User;

public abstract class OpponentDAO {

	public abstract Opponent findOpponent(Connection con, boolean withCommit,
			String pseudo) throws OpponentNotExistException;

	public abstract Opponent findAvailableOpponent(Connection con,
			boolean withCommit, User currentUser) throws OpponentNotAvailableException;

	public class OpponentNotExistException extends Exception {
		private static final long serialVersionUID = 6305537615090513026L;

		public OpponentNotExistException(String pseudo) {
			super("Erreur impossible : l'adversaire " + pseudo
					+ " n'existe pas.");
		}
	}

	public class OpponentNotAvailableException extends Exception {
		private static final long serialVersionUID = 6645484244850608984L;

		public OpponentNotAvailableException() {
			super("Pas d'adversaire disponible.");
		}
	}
}

package dao;

import java.sql.Connection;

import model.user.Opponent;

public abstract class OpponentDAO {

	public abstract Opponent findAvailableOpponent(Connection con, boolean withCommit);

	public class OpponentNotExistException extends Exception {
		private static final long serialVersionUID = 6305537615090513026L;

		public OpponentNotExistException(String pseudo) {
			super("Erreur impossible : l'adversaire " + pseudo
					+ " n'existe pas.");
		}
	}
}

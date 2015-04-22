package dao;

import java.util.Date;

import model.user.User;

public abstract class UserDAO {

	/**
	 * Used for log in of an User.
	 * 
	 * @param pseudo
	 *            pseudo of user.
	 * @param date
	 *            date used as a password.
	 * @return User create from data base.
	 */
	public abstract User findUser(String pseudo, Date date)
			throws UserNotExistException;

	public abstract void createUser(String pseudo, String mail,
			String firstName, String lastName, Date date)
			throws UserAlreadyExistException;

	public class UserNotExistException extends Exception {
		private static final long serialVersionUID = 6305537615090513026L;

		public UserNotExistException(String pseudo) {
			super(
					"La connexion est impossible. "
							+ pseudo
							+ "n'existe pas ou la date de naissance entrée est incorrecte.");
		}
	}

	public class UserAlreadyExistException extends Exception {
		private static final long serialVersionUID = 6252612978981589155L;

		public UserAlreadyExistException(String pseudo) {
			super("Le pseudo " + pseudo + " est déjà utilisé.");
		}
	}
}

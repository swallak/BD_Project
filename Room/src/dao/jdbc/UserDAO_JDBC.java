package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.user.User;
import dao.UserDAO;

public class UserDAO_JDBC extends UserDAO {

	@Override
	public User findUser(Connection con, boolean withCommit, String pseudo,
			Date date) throws UserNotExistException {
		User user = null;
		PreparedStatement stmtUser = null;
		ResultSet rs = null;
		try {
			/* Verifie si l'utilisateur existe */
			stmtUser = con.prepareStatement("select * from "
					+ DataBaseConstant.PLAYER_TABLE_NAME + " where "
					+ DataBaseConstant.PLAYER_PSEUDO + "= ? AND "
					+ DataBaseConstant.PLAYER_BIRTHDAY + "= ?");

			stmtUser.setString(1, pseudo);
			stmtUser.setDate(2, new java.sql.Date(date.getTime()));

			rs = stmtUser.executeQuery();

			if (!rs.next())
				throw new UserNotExistException(pseudo);

			user = new User(rs.getString(DataBaseConstant.PLAYER_PSEUDO),
					rs.getString(DataBaseConstant.PLAYER_MAIL),
					rs.getString(DataBaseConstant.PLAYER_FIRSTNAME),
					rs.getString(DataBaseConstant.PLAYER_LASTNAME), date,
					rs.getInt(DataBaseConstant.PLAYER_ADDRESS_NUMBER),
					rs.getString(DataBaseConstant.PLAYER_ADDRESS_STREET),
					rs.getInt(DataBaseConstant.PLAYER_ADDRESS_POSTAL_CODE),
					rs.getString(DataBaseConstant.PLAYER_ADDRESS_CITY));

			/*
			 * Verifie si l'utilisateur a des partie en cours pour determiner
			 * son etat a la connexion.
			 */

			// TODO

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmtUser != null) {
				try {
					stmtUser.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}

	@Override
	public void createUser(Connection con, boolean withCommit, User user)
			throws UserAlreadyExistException {
		PreparedStatement stmt = null;
		try {

			/*
			 * INSERT INTO Player (pseudo, prenom, nom, mail, dateNaissance,
			 * adrNumero, adrRue , adrCodePostal, adrVille) values ( ?, ?, ?, ?,
			 * ?, ?, ?, ?, ? )
			 */
			stmt = con.prepareStatement("insert into "
					+ DataBaseConstant.PLAYER_TABLE_NAME + "("
					+ DataBaseConstant.PLAYER_PSEUDO + ", "
					+ DataBaseConstant.PLAYER_FIRSTNAME + ", "
					+ DataBaseConstant.PLAYER_LASTNAME + ", "
					+ DataBaseConstant.PLAYER_MAIL + ", "
					+ DataBaseConstant.PLAYER_BIRTHDAY + ", "
					+ DataBaseConstant.PLAYER_ADDRESS_NUMBER + ", "
					+ DataBaseConstant.PLAYER_ADDRESS_STREET + ", "
					+ DataBaseConstant.PLAYER_ADDRESS_POSTAL_CODE + ", "
					+ DataBaseConstant.PLAYER_ADDRESS_CITY
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			stmt.setString(1, user.getPseudo());
			stmt.setString(2, user.getFirstName());
			stmt.setString(3, user.getLastName());
			stmt.setString(4, user.getMail());
			stmt.setDate(5, new java.sql.Date(user.getBirthday().getTime()));
			stmt.setInt(6, user.getAddrNumber());
			stmt.setString(7, user.getAddrStreet());
			stmt.setInt(8, user.getAddrPostalCode());
			stmt.setString(9, user.getAddrCity());

			stmt.executeUpdate();

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (JDBCConnection.isConstraintViolation(e)) {
				throw new UserAlreadyExistException(user.getPseudo());
			}
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

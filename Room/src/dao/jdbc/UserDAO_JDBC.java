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
	public User findUser(String pseudo, Date date) throws UserNotExistException {
		User user = null;
		Connection con = null;
		PreparedStatement stmtUser = null;
		ResultSet rs = null;
		try {
			con = JDBCConnection.openConnection();

			/* Verifie si l'utilisateur existe */
			stmtUser = con.prepareStatement("select * into "
					+ DataBaseConstant.USER_TABLE_NAME + " where "
					+ DataBaseConstant.USER_PSEUDO + "= ? AND "
					+ DataBaseConstant.USER_BIRTHDAY + "= ?");

			stmtUser.setString(1, pseudo);
			stmtUser.setDate(2, new java.sql.Date(date.getTime()));

			rs = stmtUser.executeQuery();

			if (!rs.next())
				throw new UserNotExistException(pseudo);

			user = new User(rs.getString(DataBaseConstant.USER_PSEUDO),
					rs.getString(DataBaseConstant.USER_MAIL),
					rs.getString(DataBaseConstant.USER_FIRSTNAME),
					rs.getString(DataBaseConstant.USER_LASTNAME), date);

			/*
			 * Verifie si l'utilisateur a des partie en cours pour determiner
			 * son etat a la connexion.
			 */

			// TODO

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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}

	@Override
	public void createUser(String pseudo, String mail, String firstName,
			String lastName, Date date) throws UserAlreadyExistException {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = JDBCConnection.openConnection();
			stmt = con
					.prepareStatement("insert into "
							+ DataBaseConstant.USER_TABLE_NAME + "("
							+ DataBaseConstant.USER_PSEUDO + ","
							+ DataBaseConstant.USER_FIRSTNAME + ","
							+ DataBaseConstant.USER_LASTNAME + ","
							+ DataBaseConstant.USER_MAIL + ","
							+ DataBaseConstant.USER_BIRTHDAY
							+ ") values (?,?,?,?,?)");

			stmt.setString(1, pseudo);
			stmt.setString(2, mail);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);
			stmt.setString(5, mail);
			stmt.setDate(6, new java.sql.Date(date.getTime()));
			
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			if(JDBCConnection.isConstraintViolation(e)) {
				throw new UserAlreadyExistException(pseudo);
			}
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

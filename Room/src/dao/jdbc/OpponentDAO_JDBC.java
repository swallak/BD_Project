package dao.jdbc;

import dao.OpponentDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.user.Opponent;
import model.user.User;

public class OpponentDAO_JDBC extends OpponentDAO {

	/**
	 * Map used as a cache for opponent.
	 */
	private static Map<String, Opponent> opponentMap;

	public OpponentDAO_JDBC() {
		opponentMap = new HashMap<String, Opponent>();
	}

	@Override
	public Opponent findAvailableOpponent(Connection con, boolean withCommit,
			User currentUser) throws OpponentNotAvailableException {
		Opponent result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			/*
			 * SELECT pseudo FROM JoueurParPartie WHERE pseudo != ? HAVING
			 * COUNT(*) = (SELECT MIN(COUNT*) FROM JoueurParPartie WHERE pseudo
			 * != ? GROUP BY pseudo) GROUP BY pseudo UNION SELECT pseudo FROM
			 * Joueur WHERE NOT EXISTS(SELECT * FROM JoueurParPartie jp WHERE
			 * jp.pseudo = pseudo)
			 */
			stmt = con.prepareStatement("select "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO + " from "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME + " where "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " != ? having count(*) = (select min(count(*)) from "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME + " where "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ "!= ? group by "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " ) group by "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " union select " + DataBaseConstant.PLAYER_PSEUDO
					+ " from " + DataBaseConstant.PLAYER_TABLE_NAME
					+ " p1  where not exists (select * from "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " p2  where p1." + DataBaseConstant.PLAYER_PSEUDO
					+ " =  p2."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO + ")",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			stmt.setString(1, currentUser.getPseudo());
			stmt.setString(2, currentUser.getPseudo());

			rs = stmt.executeQuery();

			rs.last();
			int rowCount = rs.getRow();
			if (rowCount == 0)
				throw new OpponentNotAvailableException();

			Random rand = new Random();
			int rowSelected = rand.nextInt(rowCount) + 1;
			rs.absolute(rowSelected);

			result = findOpponent(
					con,
					withCommit,
					rs.getString(DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO));

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OpponentNotExistException e) {
			e.printStackTrace();
			throw new OpponentNotAvailableException();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public Opponent findOpponent(Connection con, boolean withCommit,
			String pseudo) throws OpponentNotExistException {
		Opponent result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		if (opponentMap.containsKey(pseudo)) {
			result = opponentMap.get(pseudo);
		} else {
			/*
			 * SELECT pseudo FROM Player WHERE pseudo = ?
			 */
			try {
				stmt = con.prepareStatement("select "
						+ DataBaseConstant.PLAYER_PSEUDO + " from "
						+ DataBaseConstant.PLAYER_TABLE_NAME + " where "
						+ DataBaseConstant.PLAYER_PSEUDO + " = ?");

				stmt.setString(1, pseudo);

				rs = stmt.executeQuery();

				if (rs.next()) {
					result = new Opponent(
							rs.getString(DataBaseConstant.PLAYER_PSEUDO));
				} else {
					throw new OpponentNotExistException(pseudo);
				}

				opponentMap.put(result.getPseudo(), result);

			} catch (SQLException e) {
				e.printStackTrace();
				throw new OpponentNotExistException(pseudo);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return result;
	}

}

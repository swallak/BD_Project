package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.game.Match;
import model.user.Opponent;
import model.user.User;
import dao.ActionDAO;
import dao.ActionDAO.TurnNotExistsException;
import dao.BoatDAO;
import dao.BoatDAO.BoatNotExistException;
import dao.MatchDAO;

public class MatchDAO_JDBC extends MatchDAO {

	@Override
	public List<Match> geObservableMatchHeader(Connection con,
			boolean withCommit, User currentUser) {
		LinkedList<Match> result = new LinkedList<Match>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		/*
		 * SELECT Partie.idPartie, Date, joueur1.pseudo, joueur2.pseudo FROM
		 * JoueursPartie joueur1, JoueursPartie joueur2, Partie WHERE
		 * joueur1.pseudo <> userPseudo AND joueur2.pseudo <> userPseudo AND
		 * joueur1.idPartie = joueur2.idPartie AND joueur1.idPartie = Partie
		 */
		try {
			stmt = con.prepareStatement("select "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + ", "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_START_DATE + ", player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ ", payer2."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO + "from "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player1, "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player2, " + DataBaseConstant.MATCH_TABLE_NAME
					+ " where player1. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " != ? and  player2. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " != ? and player1. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " !=   player2. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ "and player1. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO + "="
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + " and player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM + " = player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM);

			stmt.setString(1, currentUser.getPseudo());
			rs = stmt.executeQuery();

			while (rs.next()) {
				result.add(new Match(
						rs.getInt(DataBaseConstant.MATCH_TABLE_NAME + "."
								+ DataBaseConstant.MATCH_ID),

						new Opponent(
								rs.getString("player1. "
										+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO)),
						new Opponent(
								rs.getString("player2. "
										+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO)),
						null, rs.getDate(DataBaseConstant.MATCH_TABLE_NAME
								+ "." + DataBaseConstant.MATCH_START_DATE)));
			}

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
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public List<Match> getPlayableMatchHeader(Connection con,
			boolean withCommit, User currentUser) {
		LinkedList<Match> result = new LinkedList<Match>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		/*
		 * SELECT Partie.idPartie, Date, joueur1.pseudo, joueur2.pseudo FROM
		 * JoueursPartie joueur1, JoueursPartie joueur2, Partie WHERE
		 * joueur1.pseudo = userPseudo OR joueur2.pseudo = userPseudo AND
		 * joueur1.idPartie = joueur2.idPartie AND joueur1.idPartie = Partie AND
		 * joueur1.numJoueur = 1 AND joueur2.numJoueur = 2
		 */
		try {
			stmt = con.prepareStatement("select "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + ", "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_START_DATE + ", player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ ", payer2."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO + "from "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player1, "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player2, " + DataBaseConstant.MATCH_TABLE_NAME
					+ " where player1. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " = ? or  player2. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " = ? and player1. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " !=   player2. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ "and player1. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO + "="
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + " and player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM + " = player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM);

			stmt.setString(1, currentUser.getPseudo());
			rs = stmt.executeQuery();

			while (rs.next()) {
				result.add(new Match(
						rs.getInt(DataBaseConstant.MATCH_TABLE_NAME + "."
								+ DataBaseConstant.MATCH_ID),

						new Opponent(
								rs.getString("player1. "
										+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO)),
						new Opponent(
								rs.getString("player2. "
										+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO)),
						null, rs.getDate(DataBaseConstant.MATCH_TABLE_NAME
								+ "." + DataBaseConstant.MATCH_START_DATE)));
			}

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
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public void createMatch(Connection con, boolean withCommit, Match match)
			throws MatchNotCreatedException {
		PreparedStatement stmt = null;
		try {
			con.setSavepoint();

			/*
			 * INSERT INTO Partie (idPartie, dateDemarage) values (?,?);
			 */
			stmt = con.prepareStatement("insert into "
					+ DataBaseConstant.MATCH_TABLE_NAME + " ("
					+ DataBaseConstant.MATCH_START_DATE + ", "
					+ DataBaseConstant.MATCH_ID + ") values ( ?, ?)");

			stmt.setInt(1, match.getId());
			stmt.setDate(2, new java.sql.Date(match.getStartDate().getTime()));

			stmt.executeUpdate();
			stmt.close();

			/*
			 * INSERT INTO JoueursPartie (pseudo, idPartie, numJoueur) values
			 * (?,?, ?);
			 */
			stmt = con.prepareStatement("insert into "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME + "("
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO + ", "
					+ DataBaseConstant.PLAYER_ON_MATCH_MATCH_ID + ", "
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM
					+ ") values ( ?, ?, ?)");

			stmt.setString(1, match.getPlayerOne().getPseudo());
			stmt.setInt(2, match.getId());
			stmt.setInt(3, 1);

			stmt.executeUpdate();

			stmt.setString(2, match.getPlayerTwo().getPseudo());
			stmt.setInt(3, 1);

			stmt.executeUpdate();

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new MatchNotCreatedException();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getCurrentMatchInfo(Connection con, boolean withCommit,
			Match match) throws ReadMatchException, MatchNotExistsException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			/*
			 * SELECT MAX(tour) FROM Action WHERE idPartie = ?
			 */
			stmt = con.prepareStatement("select MAX("
					+ DataBaseConstant.ACTION_TURN + ") from "
					+ DataBaseConstant.ACTION_TABLE_NAME + " where "
					+ DataBaseConstant.ACTION_MATCH_ID + " = ?");

			stmt.setInt(1, match.getId());

			rs = stmt.executeQuery();

			if (!rs.next())
				throw new MatchNotExistsException();

			int currentTurn = rs.getInt(1);

			BoatDAO boatDAO = new BoatDAO_JDBC();

			match.setPlayerOneBoat(boatDAO.findBoatMap(con, false, match,
					match.getPlayerOne()));

			match.setPlayerTwoBoat(boatDAO.findBoatMap(con, withCommit, match,
					match.getPlayerTwo()));

			ActionDAO actionDAO = new ActionDAO_JDBC();

			while (match.getHistoric().size() < currentTurn) {
				match.addTurn(actionDAO.getActionInTurn(con, withCommit, match,
						match.getHistoric().size() + 1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ReadMatchException();
		} catch (TurnNotExistsException e) {
			e.printStackTrace();
			throw new ReadMatchException();
		} catch (BoatNotExistException e) {
			e.printStackTrace();
			throw new ReadMatchException();
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

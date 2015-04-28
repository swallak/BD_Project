package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.game.Match;
import model.user.AbstractUser;
import model.user.Opponent;
import model.user.User;
import dao.ActionDAO;
import dao.ActionDAO.TurnNotExistsException;
import dao.BoatDAO;
import dao.BoatDAO.BoatNotExistException;
import dao.MatchDAO;
import dao.OpponentDAO;
import dao.OpponentDAO.OpponentNotExistException;

public class MatchDAO_JDBC extends MatchDAO {

	@Override
	public List<Match> geObservableMatchHeader(Connection con,
			boolean withCommit, User currentUser) {
		LinkedList<Match> result = new LinkedList<Match>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			/*
			 * Selection des parties ou on ne joue pas sans vainqueur.
			 * 
			 * SELECT Partie.idPartie, Date, joueur1.pseudo as pseudo1,
			 * joueur2.pseudo as pseudo2 FROM JoueursPartie joueur1,
			 * JoueursPartie joueur2, Partie WHERE joueur1.pseudo != userPseudo
			 * AND joueur2.pseudo != userPseudo AND joueur1.idPartie =
			 * joueur2.idPartie AND joueur1.idPartie = Partie AND
			 * joueur1.numJoueur = 1 AND joueur2.numJoueur =2 AND NOT EXISTS
			 * (SELECT * FROM Vainqueurs v WHERE v.idPartie = Partie.idPartie)
			 */
			stmt = con.prepareStatement("select "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + ", "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_START_DATE + ", " + "player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " as pseudo1, player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " as pseudo2 from " + DataBaseConstant.MATCH_TABLE_NAME
					+ ", " + DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player1, "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player2 where  player1. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " != ? and player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " != ?  and player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM
					+ " = 1 and player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM
					+ " = 2 and player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_MATCH_ID + " = "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + "  and "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + "= player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_MATCH_ID
					+ " and not exists ( select * from "
					+ DataBaseConstant.WINNER_TABLE_NAME + " where "
					+ DataBaseConstant.WINNER_TABLE_NAME + "."
					+ DataBaseConstant.WINNER_MATCH_ID + " = "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + " )");

			stmt.setString(1, currentUser.getPseudo());
			stmt.setString(2, currentUser.getPseudo());
			rs = stmt.executeQuery();

			while (rs.next()) {
				OpponentDAO dao = new OpponentDAO_JDBC();

				Opponent player1 = dao.findOpponent(con, false,
						rs.getString("pseudo1"));
				Opponent player2 = dao.findOpponent(con, false,
						rs.getString("pseudo2"));

				result.add(new Match(player1, player2, null, rs
						.getDate(DataBaseConstant.MATCH_START_DATE),rs.getInt(DataBaseConstant.MATCH_ID)));
			}

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

			/*
			 * Selection des parties terminees.
			 * 
			 * SELECT Partie.idPartie, Partie.Date, joueur1.pseudo as pseudo1,
			 * joueur2.pseudo as pseudo2, Vainqueurs.pseudo FROM JoueursPartie
			 * joueur1, JoueursPartie joueur2, Vainqueurs, Partie WHERE
			 * joueur1.idPartie = Partie.idPartieAND joueur2.idPartie =
			 * Partie.idPartie AND Vainqueurs.idPartie = Partie.idPartie AND
			 * joueur1.numJoueur = 1 AND joueur2.numJoueur = 2
			 */

			stmt = con.prepareStatement("select "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + ", "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + ", player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " as pseudo1 ,player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " as pseudo2 ," + DataBaseConstant.WINNER_TABLE_NAME
					+ "." + DataBaseConstant.WINNER_PLAYER + " from "
					+ DataBaseConstant.MATCH_TABLE_NAME + ","
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player1, "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player2, " + DataBaseConstant.WINNER_TABLE_NAME
					+ " where " + "player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_MATCH_ID + " = "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + " and player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_MATCH_ID + " = "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + " and player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM
					+ " = 1 and player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM + " = 2 and "
					+ DataBaseConstant.WINNER_TABLE_NAME + "."
					+ DataBaseConstant.WINNER_MATCH_ID + " = "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID);

			rs = stmt.executeQuery();

			while (rs.next()) {
				String pseudo1 = rs.getString("pseudo1");
				String pseudo2 = rs.getString("pseudo2");
				AbstractUser player1;
				AbstractUser player2;
				AbstractUser winner;

				OpponentDAO dao = new OpponentDAO_JDBC();

				if (pseudo1.equals(currentUser.getPseudo())) {
					player1 = currentUser;
					player2 = dao.findOpponent(con, false, pseudo2);
				} else if (pseudo2.equals(currentUser.getPseudo())) {
					player1 = dao.findOpponent(con, false, pseudo1);
					player2 = currentUser;
				} else {
					player1 = dao.findOpponent(con, false, pseudo1);
					player2 = dao.findOpponent(con, false, pseudo2);
				}

				if (rs.getString(DataBaseConstant.WINNER_PLAYER)
						.equals(pseudo1))
					winner = player1;
				else
					winner = player2;

				result.add(new Match(player1, player2, winner, rs
							.getDate(DataBaseConstant.MATCH_START_DATE),rs.getInt(DataBaseConstant.MATCH_ID)));
			}

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OpponentNotExistException e) {
			// TODO Auto-generated catch block
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
		 * SELECT Partie.idPartie, Patie.Date, joueur1.pseudo as pseudo1,
		 * joueur2.pseudo as pseudo2 FROM JoueursPartie joueur1, JoueursPartie
		 * joueur2, Partie WHERE joueur1.idPartie = Partie.idPartie AND
		 * joueur2.idPartie = Partie.idPartie and AND joueur1.numJoueur = 1 AND
		 * joueur2.numJoueur = 2 AND NOT EXISTS (SELECT * FROM Vainqueurs WHERE
		 * Vainqueurs.idPartie = Partie.idPartie)
		 */
		try {
			stmt = con.prepareStatement("select "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + ", "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_START_DATE + ", player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " as pseudo1, player2. "
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ " as pseudo2 from "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player1, "
					+ DataBaseConstant.PLAYER_ON_MATCH_TABLE_NAME
					+ " player2, " + DataBaseConstant.MATCH_TABLE_NAME
					+ " where player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_MATCH_ID + " = "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + " and player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_MATCH_ID + " = "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + " and player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_NUM + " = 1"
					+ " and player2." + DataBaseConstant.PLAYER_ON_MATCH_NUM
					+ " = 2 " + " and (player1."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO
					+ "= ? or player2."
					+ DataBaseConstant.PLAYER_ON_MATCH_PLAYER_PSEUDO + "= ? )"
					+ " and not exists ( select * from "
					+ DataBaseConstant.WINNER_TABLE_NAME + " where "
					+ DataBaseConstant.WINNER_TABLE_NAME + "."
					+ DataBaseConstant.WINNER_MATCH_ID + " = "
					+ DataBaseConstant.MATCH_TABLE_NAME + "."
					+ DataBaseConstant.MATCH_ID + ")");

			System.out.println(stmt.toString());

			stmt.setString(1, currentUser.getPseudo());
			stmt.setString(2, currentUser.getPseudo());
			rs = stmt.executeQuery();

			OpponentDAO dao = new OpponentDAO_JDBC();

			while (rs.next()) {
				String pseudo1 = rs.getString("pseudo1");
				String pseudo2 = rs.getString("pseudo2");
				AbstractUser player1;
				AbstractUser player2;

				if (pseudo1.equals(currentUser.getPseudo())) {
					player1 = currentUser;
					player2 = dao.findOpponent(con, false, pseudo2);
				} else {
					player1 = dao.findOpponent(con, false, pseudo1);
					player2 = currentUser;
				}

				result.add(new Match(player1, player2, null, rs
						.getDate(DataBaseConstant.MATCH_START_DATE) ,rs.getInt(DataBaseConstant.MATCH_ID)));
			}

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OpponentNotExistException e) {
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

			stmt.setInt(2, match.getId());
			stmt.setDate(1, new java.sql.Date(match.getStartDate().getTime()));

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

			stmt.setString(1, match.getPlayerTwo().getPseudo());
			stmt.setInt(3, 2);

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
			Match match) throws ReadMatchException, MatchNotStartedException {
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
				throw new MatchNotStartedException();

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
			if(withCommit)
				con.commit();

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

	@Override
	public void addWinner(Connection con, boolean withCommit, Match match)
			throws MatchStateNotSave {
		PreparedStatement stmt = null;

		try {

			con.setSavepoint();

			/*
			 * INSERT INTO Partie (idPartie, idJoueur) values (?, ?)
			 */
			stmt = con.prepareStatement("insert into "
					+ DataBaseConstant.WINNER_TABLE_NAME + "("
					+ DataBaseConstant.WINNER_MATCH_ID + ", "
					+ DataBaseConstant.WINNER_PLAYER + ") values (?, ?, ?)");

			stmt.setInt(1, match.getId());
			stmt.setString(2, match.getWinner().getPseudo());
			stmt.setInt(3, match.getHistoric().size());

			stmt.executeUpdate();

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new MatchStateNotSave();
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

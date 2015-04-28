package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import model.game.Match;
import model.user.Opponent;
import view.HomeViewFrame;
import dao.MatchDAO;
import dao.MatchDAO.MatchNotCreatedException;
import dao.MatchDAO.MatchStateNotSave;
import dao.OpponentDAO;
import dao.OpponentDAO.OpponentNotAvailableException;
import dao.jdbc.JDBCConnection;
import dao.jdbc.MatchDAO_JDBC;
import dao.jdbc.OpponentDAO_JDBC;

public class HomeController {

	private OpponentDAO opponentDAO;
	private MatchDAO matchDAO;

	public HomeController() {
		opponentDAO = new OpponentDAO_JDBC();
		matchDAO = new MatchDAO_JDBC();
	}

	public void startMatch(HomeViewFrame frame) {
		Connection con = null;
		Savepoint savePoint = null;

		try {
			con = JDBCConnection.openConnection();
			con = JDBCConnection.openConnection();
			savePoint = con.setSavepoint("startMatch");
			Opponent opp = opponentDAO.findAvailableOpponent(con, true,
					frame.getUser());

			System.out.println("Opponent " + opp.getPseudo());

			Date startDate = new Date(System.currentTimeMillis());
			int id = Match.createId(startDate, frame.getUser(), opp);

			Match match = new Match(id, frame.getUser(), opp, null, startDate);

			matchDAO.createMatch(con, false, match);

			con.commit();
		} catch (SQLException e) {
			// TODO ERROR
			e.printStackTrace();
			try {
				con.rollback(savePoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (OpponentNotAvailableException e) {
			// TODO ERROR
			e.printStackTrace();
		} catch (MatchNotCreatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatchStateNotSave e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Match> getPlayableMatch(HomeViewFrame frame) {
		Connection con = null;
		List<Match> result = new ArrayList<Match>();
		try {
			con = JDBCConnection.openConnection();
			result = matchDAO
					.getPlayableMatchHeader(con, true, frame.getUser());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public List<Match> getObservableMatch(HomeViewFrame frame) {
		Connection con = null;
		List<Match> result = new ArrayList<Match>();
		try {
			con = JDBCConnection.openConnection();
			result = matchDAO.geObservableMatchHeader(con, true,
					frame.getUser());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}

package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.game.Boat;
import model.game.Match;
import view.ObservationViewPanel;
import dao.MatchDAO;
import dao.MatchDAO.MatchNotExistsException;
import dao.MatchDAO.MatchNotStartedException;
import dao.MatchDAO.ReadMatchException;
import dao.jdbc.JDBCConnection;
import dao.jdbc.MatchDAO_JDBC;

public class ObservationController {

	private Match match;

	private MatchDAO matchDAO;

	public ObservationController(Match match) {
		this.match = match;
		matchDAO = new MatchDAO_JDBC();
	}

	public void undoTurn(ObservationViewPanel panel) {
		match.undoCurrentTurn();
		refreshView(panel);
	}

	public void nextTurn(ObservationViewPanel panel) {
		if (match.getCurrentTurn() <= match.getHistoric().size()) {
			match.applyNextTurn();
		} else {
			java.sql.Connection con = null;
			try {
				con = JDBCConnection.openConnection();
				matchDAO.getCurrentMatchInfo(con, true, match);
				match.setCurrentTurn(match.getHistoric().size());
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ReadMatchException e) {
				e.printStackTrace();
			} catch (MatchNotExistsException e) {
				// TODO Auto-generated catch block
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
		}
		refreshView(panel);
	}

	public void goToStart(ObservationViewPanel panel) {
		match.undoToStart();
		refreshView(panel);
	}

	public void initView(ObservationViewPanel panel) {
		java.sql.Connection con = null;
		try {
			con = JDBCConnection.openConnection();
			matchDAO.getCurrentMatchInfo(con, true, match);
			if (match.getCurrentTurn() <= match.getHistoric().size()) {
				match.applyCurrentTurn();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ReadMatchException e) {
			e.printStackTrace();
		} catch (MatchNotExistsException e) {
			// TODO Auto-generated catch block
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
		refreshView(panel);
	}

	private void refreshView(ObservationViewPanel panel) {
		panel.displayBoat(new ArrayList<Boat>(match.getPlayerOneBoats()
				.values()), new ArrayList<Boat>(match.getPlayerTwoBoats()
				.values()));
	}
}

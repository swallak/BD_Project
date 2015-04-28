package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

import model.game.Action;
import model.game.Backward;
import model.game.Boat;
import model.game.Forward;
import model.game.Left;
import model.game.Match;
import model.game.MoveAction;
import model.game.Position;
import model.game.Right;
import model.game.ShotAction;
import model.game.Turn;
import dao.ActionDAO;

public class ActionDAO_JDBC extends ActionDAO {

	@Override
	public void createAction(Connection con, boolean withCommit,
			MoveAction moveAction) throws ActionNotCreatedExcetpion {
		PreparedStatement stmt = null;
		try {
			createActionCommun(con, stmt, moveAction);

			/*
			 * INSERT INTO ActionDeplacement (idPartie, tour, numAction,
			 * typeDeplacement) values ( ?, ?, ?, ?, ?)
			 */

			stmt = con.prepareStatement("insert into "
					+ DataBaseConstant.ACTION_MOVE_TABLE_NAME + " (  "
					+ DataBaseConstant.ACTION_MOVE_MATCH_ID + ", "
					+ DataBaseConstant.ACTION_MOVE_TURN + ", "
					+ DataBaseConstant.ACTION_MOVE_NUM_ACTION + " ,"
					+ DataBaseConstant.ACTION_MOVE_TYPE
					+ " ) values ( ?, ?, ?, ?, ? )");

			stmt.setInt(1, moveAction.getTurn().getMatch().getId());
			stmt.setInt(2, moveAction.getTurn().getNbTurn());
			stmt.setInt(3, moveAction.getNumAction());
			stmt.setString(4, moveAction.getType());

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ActionNotCreatedExcetpion();
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
	public void createAction(Connection con, boolean withCommit,
			ShotAction shotAction) throws ActionNotCreatedExcetpion {
		PreparedStatement stmt = null;
		try {
			createActionCommun(con, stmt, shotAction);

			/*
			 * INSERT INTO ActionTir (idPartie, tour, numAction, cibleTirX,
			 * cibleTirY ) values ( ?, ?, ?, ?, ?)
			 */

			stmt = con.prepareStatement("insert into "
					+ DataBaseConstant.ACTION_SHOT_TABLE_NAME + " (  "
					+ DataBaseConstant.ACTION_SHOT_MATCH_ID + ", "
					+ DataBaseConstant.ACTION_SHOT_TURN + ", "
					+ DataBaseConstant.ACTION_SHOT_NUM_ACTION + " ,"
					+ DataBaseConstant.ACTION_SHOT_TARGET_X + ", "
					+ DataBaseConstant.ACTION_SHOT_TARGET_Y
					+ " ) values ( ?, ?, ?, ?, ?, ? )");

			stmt.setInt(1, shotAction.getTurn().getMatch().getId());
			stmt.setInt(2, shotAction.getTurn().getNbTurn());
			stmt.setInt(3, shotAction.getNumAction());
			stmt.setInt(4, shotAction.getTarget().getX());
			stmt.setInt(5, shotAction.getTarget().getY());

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new ActionNotCreatedExcetpion();
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

	private void createActionCommun(Connection con, PreparedStatement stmt,
			Action action) throws SQLException {
		con.setSavepoint();

		/*
		 * INSERT INTO Action (idPartie, idBateau, tour, numAction) values( ?,
		 * ?, ?, ?)
		 */

		stmt = con.prepareStatement("inert into "
				+ DataBaseConstant.ACTION_TABLE_NAME + " ( "
				+ DataBaseConstant.ACTION_MATCH_ID + ", "
				+ DataBaseConstant.ACTION_BOAT_ID + " ,"
				+ DataBaseConstant.ACTION_TURN + " , "
				+ DataBaseConstant.ACTION_TURN + " ) values ( ?, ?, ?, ? )");

		stmt.setInt(1, action.getTurn().getMatch().getId());
		stmt.setInt(2, action.getBoat().getId());
		stmt.setInt(3, action.getTurn().getNbTurn());
		stmt.setInt(4, action.getNumAction());

		stmt.executeUpdate();

		stmt.close();
		stmt = null;

	}

	@Override
	public Turn getActionInTurn(Connection con, boolean withCommit,
			Match match, int turnNumber) throws TurnNotExistsException {
		PreparedStatement stmt = null;
		Turn result = new Turn(match, turnNumber);

		try {
			if (!isTurnExist(con, stmt, match, turnNumber))
				throw new TurnNotExistsException();

			LinkedList<Action> actionList = new LinkedList<Action>();

			actionList.addAll(getMoveAction(con, stmt, match, result));

			actionList.addAll(getShotActions(con, stmt, match, result));

			Collections.sort(actionList);
			result.setActionList(actionList);

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private boolean isTurnExist(Connection con, PreparedStatement stmt,
			Match match, int turnNumber) throws SQLException {
		boolean result = false;

		/*
		 * SELECT MAX(a.tour) FROM Action a WHERE a.idPartie = ?
		 */
		stmt = con.prepareStatement("select MAX(a."
				+ DataBaseConstant.ACTION_TURN + ") FROM "
				+ DataBaseConstant.ACTION_TABLE_NAME + " a where a."
				+ DataBaseConstant.ACTION_MATCH_ID + " = ?");

		stmt.setInt(1, match.getId());

		ResultSet st = stmt.executeQuery();
		if (st.next() && turnNumber <= st.getInt(1))
			result = true;

		stmt.close();
		stmt = null;

		return result;
	}

	private LinkedList<MoveAction> getMoveAction(Connection con,
			PreparedStatement stmt, Match match, Turn turn) throws SQLException {
		LinkedList<MoveAction> result = new LinkedList<MoveAction>();
		ResultSet rs;

		/*
		 * SELECT a.numAction, a.bateau, aMove.typeDeplacement FROM Action a,
		 * ActionDeplacement aMove WHERE Action.idPartie =
		 * ActionDeplacement.idPartie AND Action.tour = ActionDeplacement.tour
		 * AND Action.numAction = ActionDeplacement.numAction AND Action.tour =
		 * ? AND Action.idPartie = ?
		 */

		stmt = con.prepareStatement("select a."
				+ DataBaseConstant.ACTION_NUM_ACTION + ", a."
				+ DataBaseConstant.ACTION_BOAT_ID + ", aMove."
				+ DataBaseConstant.ACTION_MOVE_TYPE + " from "
				+ DataBaseConstant.ACTION_TABLE_NAME + " a, "
				+ DataBaseConstant.ACTION_MOVE_TABLE_NAME + " aMove where a."
				+ DataBaseConstant.ACTION_MATCH_ID + " = aMove."
				+ DataBaseConstant.ACTION_MOVE_MATCH_ID + " and a."
				+ DataBaseConstant.ACTION_TURN + " = aMove."
				+ DataBaseConstant.ACTION_MOVE_TURN + "and a."
				+ DataBaseConstant.ACTION_NUM_ACTION + " = aMove."
				+ DataBaseConstant.ACTION_MOVE_NUM_ACTION + " and a."
				+ DataBaseConstant.ACTION_MATCH_ID + " = ? and a."
				+ DataBaseConstant.ACTION_TURN + " = ?");

		stmt.setInt(1, match.getId());
		stmt.setInt(2, turn.getNbTurn());

		rs = stmt.executeQuery();

		Map<Integer, Boat> boatMap = (turn.getNbTurn() % 2 == 0 ? match
				.getPlayerTwoBoats() : match.getPlayerOneBoats());

		while (rs.next()) {
			MoveAction moveAction;
			String type = rs.getString("aMove."
					+ DataBaseConstant.ACTION_MOVE_TYPE);
			if (type.equals(Left.TYPE)) {
				moveAction = new Left(turn, boatMap.get(rs
						.getInt(DataBaseConstant.ACTION_BOAT_ID)),
						rs.getInt(DataBaseConstant.ACTION_NUM_ACTION));
			} else if (type.equals(Right.TYPE)) {
				moveAction = new Right(turn, boatMap.get(rs
						.getInt(DataBaseConstant.ACTION_BOAT_ID)),
						rs.getInt(DataBaseConstant.ACTION_NUM_ACTION));
			} else if (type.equals(Forward.TYPE)) {
				moveAction = new Forward(turn, boatMap.get(rs
						.getInt(DataBaseConstant.ACTION_BOAT_ID)),
						rs.getInt(DataBaseConstant.ACTION_NUM_ACTION));
			} else {
				moveAction = new Backward(turn, boatMap.get(rs
						.getInt(DataBaseConstant.ACTION_BOAT_ID)),
						rs.getInt(DataBaseConstant.ACTION_NUM_ACTION));
			}
			result.add(moveAction);
		}

		rs.close();
		stmt.close();
		stmt = null;

		return result;
	}

	private LinkedList<ShotAction> getShotActions(Connection con,
			PreparedStatement stmt, Match match, Turn turn) throws SQLException {
		LinkedList<ShotAction> result = new LinkedList<ShotAction>();
		ResultSet rs;

		/*
		 * SELECT a.numAction, a.bateau, aShot.cibleTir_X, aShot.cibleTir_Y FROM
		 * Action a, ActionTir aShot WHERE Action.idPartie = aShot.idPartie AND
		 * Action.tour = aShot.tour AND Action.numAction = aShot.numAction AND
		 * Action.tour = ? AND Action.idPartie = ?
		 */

		stmt = con.prepareStatement("select a."
				+ DataBaseConstant.ACTION_NUM_ACTION + ", a."
				+ DataBaseConstant.ACTION_BOAT_ID + ", aShot."
				+ DataBaseConstant.ACTION_SHOT_TARGET_X + ", "
				+ DataBaseConstant.ACTION_SHOT_TARGET_Y + " from "
				+ DataBaseConstant.ACTION_TABLE_NAME + " a, "
				+ DataBaseConstant.ACTION_SHOT_TABLE_NAME + " aShot where a."
				+ DataBaseConstant.ACTION_MATCH_ID + " = aMove."
				+ DataBaseConstant.ACTION_SHOT_MATCH_ID + " and a."
				+ DataBaseConstant.ACTION_TURN + " = aShot."
				+ DataBaseConstant.ACTION_SHOT_TURN + " and a."
				+ DataBaseConstant.ACTION_NUM_ACTION + " = aShot."
				+ DataBaseConstant.ACTION_SHOT_NUM_ACTION + " and a."
				+ DataBaseConstant.ACTION_MATCH_ID + " = ? and a."
				+ DataBaseConstant.ACTION_TURN + " = ?");

		stmt.setInt(1, match.getId());
		stmt.setInt(2, turn.getNbTurn());

		rs = stmt.executeQuery();

		Map<Integer, Boat> boatMap = (turn.getNbTurn() % 2 == 0 ? match
				.getPlayerTwoBoats() : match.getPlayerOneBoats());

		while (rs.next()) {
			ShotAction action = new ShotAction(turn, boatMap.get(rs
					.getInt(DataBaseConstant.ACTION_BOAT_ID)),
					rs.getInt(DataBaseConstant.ACTION_NUM_ACTION),
					new Position(rs
							.getInt(DataBaseConstant.ACTION_SHOT_TARGET_X), rs
							.getInt(DataBaseConstant.ACTION_SHOT_TARGET_Y)));
			result.add(action);
		}

		rs.close();
		stmt.close();
		stmt = null;

		return result;
	}
}

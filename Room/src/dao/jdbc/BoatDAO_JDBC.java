package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.game.Boat;
import model.game.Boat.Orientation;
import model.game.Match;
import model.game.Position;
import model.user.AbstractUser;
import dao.BoatDAO;

public class BoatDAO_JDBC extends BoatDAO {

	@Override
	public Map<Integer, Boat> findBoatMap(Connection con, boolean withCommit,
			Match match, AbstractUser player) {
		Map<Integer, Boat> result = new HashMap<Integer, Boat>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		/*
		 * SELECT * FROM Bateau WHERE idPartie = ? AND pseudo = ?
		 */
		try {
			stmt = con.prepareStatement("select * from "
					+ DataBaseConstant.BOAT_TABLE_NAME + " where "
					+ DataBaseConstant.BOAT_MATCH_ID + " = ? and "
					+ DataBaseConstant.BOAT_OWNER + " = ?");

			stmt.setInt(1, match.getId());
			stmt.setString(2, player.getPseudo());

			rs = stmt.executeQuery();

			while (rs.next()) {
				result.put(
						rs.getInt(DataBaseConstant.BOAT_ID),
						new Boat(
								match,
								player,
								rs.getInt(DataBaseConstant.BOAT_SIZE),
								rs.getInt(DataBaseConstant.BOAT_HP),
								Orientation.fromString(rs
										.getString(DataBaseConstant.BOAT_ORIENTATION)),
								new Position(rs
										.getInt(DataBaseConstant.BOAT_POS_X),
										rs.getInt(DataBaseConstant.BOAT_POS_Y)),
								rs.getInt(DataBaseConstant.BOAT_ID)));
			}

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void createBoatList(Connection con, boolean withCommit,
			List<Boat> boats) throws BoatNotCreatedException {
		PreparedStatement stmt = null;

		try {
			con.setSavepoint();

			/*
			 * insert into Bateau (idBateau, idPartie, pseudo, taille, pivotX,
			 * pivotY, orientation, pointVie) values (?, ?, ?, ?, ?, ?, ?, ?)
			 */
			stmt = con.prepareStatement("insert into "
					+ DataBaseConstant.BOAT_TABLE_NAME + " ( "
					+ DataBaseConstant.BOAT_ID + ", "
					+ DataBaseConstant.BOAT_MATCH_ID + " ,"
					+ DataBaseConstant.BOAT_OWNER + ", "
					+ DataBaseConstant.BOAT_SIZE + ", "
					+ DataBaseConstant.BOAT_POS_X + ", "
					+ DataBaseConstant.BOAT_POS_Y + ", "
					+ DataBaseConstant.BOAT_ORIENTATION + ", "
					+ DataBaseConstant.BOAT_HP
					+ ") values (?, ?, ?, ?, ?, ?, ?, ?)");

			for (Boat b : boats) {
				stmt.setInt(1, b.getId());
				System.out.print(b.getMatch());
				stmt.setInt(2, b.getMatch().getId());
				stmt.setString(3, b.getOwner().getPseudo());
				stmt.setInt(4, b.getSize());
				stmt.setInt(5, b.getPosition().getX());
				stmt.setInt(6, b.getPosition().getY());
				stmt.setString(7, b.getOrientation().getStringValue());
				stmt.setInt(8, b.getHp());

				stmt.executeUpdate();
			}

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BoatNotCreatedException();
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

	public void updateBoat(Connection con, boolean withCommit, Boat boat)
			throws BoatStateNotSaveException {
		PreparedStatement stmt = null;

		try {
			con.setSavepoint();

			/*
			 * update Bateau set pivotX = ?, pivotY = ?, hp = ?, orientation = ?
			 * where idBateau = ? and idPartie = ? and pseudo = ?
			 */
			stmt = con.prepareStatement("update "
					+ DataBaseConstant.BOAT_TABLE_NAME + " set "
					+ DataBaseConstant.BOAT_POS_X + " = ?, "
					+ DataBaseConstant.BOAT_POS_Y + " = ?, "
					+ DataBaseConstant.BOAT_HP + " = ?,"
					+ DataBaseConstant.BOAT_ORIENTATION + " = ? where "
					+ DataBaseConstant.BOAT_ID + " = ?  and "
					+ DataBaseConstant.BOAT_MATCH_ID + " = ? and "
					+ DataBaseConstant.BOAT_OWNER + " = ?");

			stmt.setInt(1, boat.getPosition().getX());
			stmt.setInt(2, boat.getPosition().getY());
			stmt.setString(3, boat.getOrientation().getStringValue());
			stmt.setInt(4, boat.getHp());
			stmt.setInt(5, boat.getId());
			stmt.setInt(6, boat.getMatch().getId());
			stmt.setString(7, boat.getOwner().getPseudo());

			int nbUpdate = stmt.executeUpdate();
			if (nbUpdate != 1) {
				throw new BoatStateNotSaveException();
			}

			if (withCommit)
				con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BoatStateNotSaveException();
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

package dao.jdbc;

import dao.OpponentDAO;
import java.sql.Connection;
import model.user.Opponent;

public class OpponentDAO_JDBC extends OpponentDAO {

    @Override
    public Opponent findAvailableOpponent(Connection con, boolean withCommit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

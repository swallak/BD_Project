package dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	private final static String DATABASE_URL = "jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1";

	public static Connection openConnection() throws SQLException {
		Connection con = DriverManager.getConnection(DATABASE_URL, "login",
				"password");
		con.setAutoCommit(false);
		return con;
	}
	
	public static boolean isConstraintViolation(SQLException e) {
	    return e.getSQLState().startsWith("23");
	}

}

package controller;

import dao.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.user.User;
import view.HomeViewFrame;
import view.SignInViewPanel;
import dao.UserDAO.UserNotExistException;
import dao.jdbc.JDBCConnection;
import dao.jdbc.UserDAO_JDBC;

import java.util.Date;

public class LogInController {

    private final String DATE_PATTERN ="yyyy/MM/dd";
    public static final  String NOT_FOUND_ERROR_MSG = "The pseudo or the birthday your entered is wrong";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
    
    
    private String pseudo;
    private Date birthday; 
    //private boolean loginSuccess = false;
    
    
    public LogInController(String pseudo, String birthday) {
        this.pseudo = pseudo;
        try{
        this.birthday = dateFormat.parse(birthday);
        }
        catch(ParseException e)
        {
            //System.out.println("");
        }
    }

    /**
     * Verify database and connect the user to the his room's home page
     *
     * @return true if connection succeded.
     */
    public User connect() throws UserNotExistException {
		System.out.println("connecting..." + pseudo);
		UserDAO user = new UserDAO_JDBC();

		Connection con = null;
		try {
			con = JDBCConnection.openConnection();
			return user.findUser(con, true, pseudo, birthday);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
}



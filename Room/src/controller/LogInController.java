package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.user.User;
import view.HomeViewFrame;
import view.SignInViewPanel;
import dao.UserDAO.UserNotExistException;
import dao.jdbc.UserDAO_JDBC;

public class LogInController {

    private String pseudo;
    private String birthday; 
    public LogInController(String pseudo, String birthday) {
        this.pseudo = pseudo;
        this.birthday = birthday;
    }

    /**
     * Verify database and connect the user to the his room's home page
     *
     * @return true if connection succeded.
     */
    public boolean connect() {
        System.out.println("connecting..."+pseudo);
        return false;
    
    }
}



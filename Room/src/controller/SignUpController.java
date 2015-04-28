package controller;

import dao.UserDAO;
import dao.jdbc.JDBCConnection;
import dao.jdbc.UserDAO_JDBC;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.user.User;
import view.SignUpViewFrame;
import view.SignUpViewPanel;

public class SignUpController {

    private final String DATE_PATTERN = "yyyy/MM/dd";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
    public static final String ALREADY_EXISTS_ERROR = "The pseudo you entered is not available";

    private String firstName;
    private String lastName;
    private String pseudo;

    private Date birthday;
    private String mail;

    private String addrCity;
    private int addrNumber;
    private int addrPostalCode;
    private String addrStreet;

    /**
     *
     * @return true if account creation succeeded
     */
	public void createAccount(SignUpViewFrame signUpViewFrame) {
		SignUpViewPanel signUp = signUpViewFrame.getSignUpViewPanel();
		
		boolean validFields = true;
		
		pseudo = signUp.getPseudo();
		try {
			
			birthday = dateFormat.parse(signUp.getBirthday());
		} catch (ParseException ex) {
			Logger.getLogger(SignUpController.class.getName()).log(
					Level.SEVERE, null, ex);
			validFields = false;

		}
		firstName = signUp.getFirstName();
		lastName = signUp.getLastName();
		addrStreet = (signUp.getStreetName());
		
		try {
			addrNumber = Integer.parseInt(signUp.getStreetNumber());
		} catch (NumberFormatException ex) {
			//signUp.addLabel("error", 2, 5);
			validFields = false;
		}

		addrCity = signUp.getCity();
		try {
			addrPostalCode = Integer.parseInt(signUp.getZipCode());

		} catch (NumberFormatException ex) {
			 //signUp.addLabel("error", 2, 6);
			validFields = false;
		}

		mail = signUp.getMail();
		
		if (!validFields)
			signUpViewFrame
					.popErrorDialog("The infos you entered are not correct");

		UserDAO user = new UserDAO_JDBC();

		Connection con = null;
		
		try {
			con = JDBCConnection.openConnection();
			user.createUser(con, true, createUser());
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			signUpViewFrame.popErrorDialog("Connection Problem");
		} catch (UserDAO.UserAlreadyExistException ex) {
			Logger.getLogger(SignUpController.class.getName()).log(
					Level.SEVERE, null, ex);
			signUpViewFrame.popErrorDialog(ex.getMessage());
		}

	}

        public User createUser()
        {
            return new User( pseudo,  mail,  firstName,  lastName,
			 birthday,  addrNumber,  addrStreet,
			 addrPostalCode,  addrCity);
        }
}

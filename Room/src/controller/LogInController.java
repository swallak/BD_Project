package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.user.User;
import view.HomeView;
import view.SignInView;
import dao.UserDAO.UserNotExistException;
import dao.jdbc.UserDAO_JDBC;

public class LogInController {

	private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private SignInView signInView;

	public LogInController(SignInView view) {
		this.signInView = view;
	}

	public void logIn(String pseudo, String dateTxt) {
		User user = null;
		UserDAO_JDBC dao = new UserDAO_JDBC();
		try {
			user = dao.findUser(pseudo, formatter.parse(dateTxt));
		} catch (UserNotExistException e) {
			e.printStackTrace();
			// TODO dialog d'erreur de connexion.
		} catch (ParseException e) {
			// TODO dialog d'erreur sur la date.
			e.printStackTrace();
		}

		if (user != null) {
			signInView.getParent().getApplicationController()
					.setCurrentUser(user);
			// TODO Changement de vue.
			signInView.getParent().setContentPane(
					new HomeView(signInView.getParent()));
		}
	}
}

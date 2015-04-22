package model.user;

import java.util.Date;

public class User extends AbstractUser {

	private Date birthday;
	private String mail;
	private String firstName;
	private String lastName;

	private UserState state;

	public User(String pseudo, String mail, String firstName, String lastName,
			Date birthday) {
		super(pseudo);
		this.mail = mail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
	}

}

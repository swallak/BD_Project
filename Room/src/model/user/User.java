package model.user;

import java.util.Date;

public class User extends AbstractUser {

	private String firstName;
	private String lastName;

	private Date birthday;
	private String mail;

	private String addrCity;
	private int addrNumber;
	private int addrPostalCode;
	private String addrStreet;

	private UserState state;

	public User(String pseudo, String mail, String firstName, String lastName,
			Date birthday, int addrNumber, String addrStreet,
			int addrPostalCode, String addrCity) {
		super(pseudo);
		this.mail = mail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.addrNumber = addrNumber;
		this.addrStreet = addrStreet;
		this.addrPostalCode = addrPostalCode;
		this.addrCity = addrCity;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public int getAddrNumber() {
		return addrNumber;
	}

	public int getAddrPostalCode() {
		return addrPostalCode;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMail() {
		return mail;
	}
}

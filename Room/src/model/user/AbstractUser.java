package model.user;

public abstract class AbstractUser {

	private String pseudo;

	public AbstractUser(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPseudo() {
		return pseudo;
	}

}

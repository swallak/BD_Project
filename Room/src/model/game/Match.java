package model.game;

import java.util.Date;
import java.util.List;
import java.util.Map;

import model.user.AbstractUser;

public class Match {

	private int id;
	private Date startDate;

	private int currentTurn;

	private AbstractUser playerOne;
	private AbstractUser playerTwo;

	private Map<Integer, Boat> playerOneBoats;
	private Map<Integer, Boat> playerTwoBoats;

	private AbstractUser winner;

	private List<Turn> historic;

	public Match(AbstractUser playerOne, AbstractUser playerTwo,
			AbstractUser winner, Date date) {
		this.id = createId(date, playerOne, playerTwo);
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.winner = winner;
		this.startDate = date;
	}

	public Match(AbstractUser playerOne, AbstractUser playerTwo,
			AbstractUser winner, Date date, int id) {
		this.id = id;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.winner = winner;
		this.startDate = date;
	}

	public Map<Integer, Boat> getPlayerOneBoats() {
		return playerOneBoats;
	}

	public Map<Integer, Boat> getPlayerTwoBoats() {
		return playerTwoBoats;
	}

	public AbstractUser getWinner() {
		return winner;
	}

	public void setWinner(AbstractUser winner) {
		this.winner = winner;
	}

	public Date getStartDate() {
		return startDate;
	}

	public AbstractUser getPlayerOne() {
		return playerOne;
	}

	public AbstractUser getPlayerTwo() {
		return playerTwo;
	}

	public List<Turn> getHistoric() {
		return historic;
	}

	public void addTurn(Turn turn) {
		historic.add(turn);
	}

	public void applyNextTurn() {
		if (currentTurn < historic.size())
			historic.get(currentTurn).apply();
		currentTurn++;
	}

	public void undoCurrentTurn() {
		if (currentTurn > 0)
			historic.get(currentTurn).undo();
		currentTurn--;
	}

	public void applyCurrentTurn() {
		while (currentTurn < historic.size())
			applyNextTurn();
	}

	public void undoToStart() {
		while (currentTurn > 0) {

		}
	}

	public int getId() {
		return id;
	}

	public static int createId(Date startDate, AbstractUser playerOne,
			AbstractUser playerTwo) {
		return (int) (startDate.getTime() + playerOne.getPseudo().hashCode() + playerTwo
				.hashCode());
	}

	public void setPlayerOneBoat(Map<Integer, Boat> boatMap) {
		playerOneBoats = boatMap;
	}

	public void setPlayerTwoBoat(Map<Integer, Boat> boatMap) {
		playerOneBoats = boatMap;
	}
}

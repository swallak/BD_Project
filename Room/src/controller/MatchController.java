package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.game.Action;
import model.game.Backward;
import model.game.Boat;
import model.game.Forward;
import model.game.Left;
import model.game.Match;
import model.game.MoveAction;
import model.game.MoveAction.MovementType;
import model.game.Position;
import model.game.Right;
import model.game.ShotAction;
import model.game.Turn;
import model.user.AbstractUser;
import model.user.User;
import dao.ActionDAO;
import dao.ActionDAO.ActionNotCreatedExcetpion;
import dao.BoatDAO;
import dao.BoatDAO.BoatStateNotSaveException;
import dao.MatchDAO;
import dao.MatchDAO.MatchNotExistsException;
import dao.MatchDAO.MatchStateNotSave;
import dao.MatchDAO.ReadMatchException;
import dao.jdbc.ActionDAO_JDBC;
import dao.jdbc.BoatDAO_JDBC;
import dao.jdbc.JDBCConnection;
import dao.jdbc.MatchDAO_JDBC;
import view.MatchViewFrame;
import view.MatchViewFrame.MatchViewPanel;
import view.MatchViewGrid.SupperposedBoatException;

public class MatchController {

	private Match match;

	private MatchDAO matchDAO = new MatchDAO_JDBC();
	private ActionDAO actionDAO = new ActionDAO_JDBC();
	private BoatDAO boatDAO = new BoatDAO_JDBC();

	private Connection turnConnection;
	private Turn currentTurn;
	private int actionCounter;
	private Savepoint turnSavePoint;

	public boolean isUserPlayerOne;
	private boolean isInitPhase;
	private boolean isUserTurn;

	private MatchViewFrame matchView;

	public MatchController(Match match, User currentUser) {
		this.match = match;
		if (match.getPlayerOne().getPseudo().equals(currentUser.getPseudo()))
			isUserPlayerOne = true;
		else
			isUserPlayerOne = false;
	}
        
    public void setMatchView(MatchViewFrame matchView){
        this.matchView=matchView;
    }

	/**
	 * Clique sur le bouton refresh quand ce n'est pas à nous de jouer.
	 */
	public void refresh() {
		Connection con = null;
		int oldTurn = match.getCurrentTurn();
		try {
			con = JDBCConnection.openConnection();
			matchDAO.getCurrentMatchInfo(con, true, match);
			match.setCurrentTurn(match.getHistoric().size());

			if(match.getPlayerOneBoats().size() == 0 || match.getPlayerTwoBoats().size() == 0)
			{
				isInitPhase = true;
				JOptionPane.showMessageDialog(matchView, "Let's play a game?");
			}
			else if (match.getHistoric().size() == 0){
				isUserTurn = isUserPlayerOne;
			} else {
				if (oldTurn != match.getCurrentTurn()) {
					isUserTurn = true;
					startUserTurn();
				}
			}
			if (!isInitPhase){
				String actions = "Results of last turn: \n";
				for (Action a: match.getHistoric().get(currentTurn.getNbTurn()-1).getActions()){
					if(a.isShot()){
						ShotAction shot = (ShotAction) a;
						if(shot.getTouchBoat() != null){
							actions = actions + "Your opponent hit your boat in the position: (" + 
						Integer.toString(shot.getTouchBoat().getPosition().getX()) + "," +
						Integer.toString(shot.getTouchBoat().getPosition().getY()) + (")\n");						
						}
					}
				}
				JOptionPane.showMessageDialog(matchView, actions);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ReadMatchException e) {
			e.printStackTrace();
		} catch (MatchNotExistsException e) {
			JOptionPane.showMessageDialog(matchView,
					"The Match doesn't exist!", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Appelée lorsqu'un nouveau tour de l'utilisateur commence.
	 */
	private void startUserTurn() {
		try {
			turnConnection = JDBCConnection.openConnection();
			currentTurn = new Turn(match, match.getCurrentTurn() + 1);
			turnSavePoint = turnConnection.setSavepoint("start_turn");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void submitTurn() {
		if (currentTurn.getActionIndex() == 0) {
			JOptionPane.showMessageDialog(matchView, "It is your turn!");
		} else {
			try {
				turnConnection.commit();
				match.addTurn(currentTurn);
				turnConnection.close();
				isUserTurn = false;
				String s = "";

				List<Action> actionsTurn = currentTurn.getActions();
				for (Action a : actionsTurn) {
					if (a.isShot()) {
						ShotAction shot = (ShotAction) a;
						
						Boat b = shot.getTouchBoat();
						if (b != null) {
							s += "Your shot have touched the boat in" + b.getPosition();
						}
													
					}
				}
				JOptionPane.showMessageDialog(matchView, s);

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(matchView,
						"Problem connecting to database.", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	public void initMatch(List<Boat> boats)
			throws BoatDAO.BoatNotCreatedException,
			MatchDAO.MatchNotCreatedException, MatchDAO.MatchStateNotSave {
		Connection con = null;

		try {
			con = JDBCConnection.openConnection();
			// matchDAO.createMatch(con, true, match);
			boatDAO.createBoatList(con, true, boats);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.refresh();
	}
	
	
	public void moveAction(Boat boat, MovementType type) {
		MoveAction moveAction = null;
		Savepoint savePoint = null;
		switch (type) {
		case BACKWARD:
			moveAction = new Backward(currentTurn, boat, actionCounter);
			break;
		case FORWARD:
			moveAction = new Forward(currentTurn, boat, actionCounter);
			break;
		case LEFT:
			moveAction = new Left(currentTurn, boat, actionCounter);
			break;
		case RIGHT:
			moveAction = new Right(currentTurn, boat, actionCounter);
			break;
		default:
			break;
		}

		if (moveAction != null) {
			try {
				savePoint = turnConnection.setSavepoint("before_action");
				actionDAO.createAction(turnConnection, false, moveAction);

				moveAction.apply();

				try {
					refreshView(matchView.getMatchViewPanel());
				} catch (SupperposedBoatException e) {
					JOptionPane.showMessageDialog(matchView, "The ships were superposed"
							+ ", you can't do that!", "Error",
							JOptionPane.ERROR_MESSAGE);
					moveAction.undo();
					try {
						turnConnection.rollback(savePoint);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

				boatDAO.updateBoat(turnConnection, false, boat);

				// Si on arrive ici, il n'y a pas d'erreur :
				currentTurn.addAction(moveAction);
				actionCounter++;
			} catch (SQLException e) {
				try {
					turnConnection.rollback(savePoint);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(matchView,
						"Problem connecting to database.", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();

			} catch (ActionNotCreatedExcetpion e) {
				try {
					turnConnection.rollback(savePoint);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(matchView,
						"Problem creating action.", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();

			} catch (BoatStateNotSaveException e) {
				moveAction.undo();
				try {
					turnConnection.rollback(savePoint);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(matchView,
						"Action is not possible.", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	
	public void shootAction(Boat boat, Position target) {
		ShotAction action = new ShotAction(currentTurn, boat, actionCounter,
				target);
		Savepoint savePoint = null;
		try {
			savePoint = turnConnection.setSavepoint("before_action");
			actionDAO.createAction(turnConnection, false, action);

			action.apply();

			if (action.getTouchBoat() != null)
				boatDAO.updateBoat(turnConnection, false, action.getTouchBoat());

			// Si on arrive ici, il n'y a pas d'erreur :
			currentTurn.addAction(action);
			actionCounter++;

			try {
				refreshView(matchView.getMatchViewPanel());
			} catch (SupperposedBoatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SQLException e) {
			try {
				turnConnection.rollback(savePoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(matchView,
					"Problem connecting to database.", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ActionNotCreatedExcetpion e) {
			try {
				turnConnection.rollback(savePoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(matchView,
					"Invalid target.", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (BoatStateNotSaveException e) {
			action.undo();
			try {
				turnConnection.rollback(savePoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(matchView,
					"Problem with boats' state.", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void matchHasFinished() {
		Connection con = null;
		
		try {
			con = JDBCConnection.openConnection();
			try {
				if (isUserPlayerOne) {
					match.setWinner(getFirstUser());
				}
				else {
					match.setWinner(getSecondUser());
				}
				matchDAO.addWinner(con, true, match);
			} catch (MatchStateNotSave e) {
				// Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void abandonMatch() {
		Connection con = null;
		
		try {
			con = JDBCConnection.openConnection();
			try {
				if (isUserPlayerOne) {
					match.setWinner(getSecondUser());
				}
				else {
					match.setWinner(getFirstUser());
				}
				matchDAO.addWinner(con, true, match);
			} catch (MatchStateNotSave e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}

	public AbstractUser getFirstUser() {
		return this.match.getPlayerOne();
	}

	public AbstractUser getSecondUser() {

		return this.match.getPlayerTwo();

    }
    public Match getMatch()
    {
        return this.match;
    }
    public String toString() {
		return this.getFirstUser().getPseudo() + "vs"
				+ this.getSecondUser().getPseudo();
	}
    
    private void refreshView(MatchViewPanel panel) throws SupperposedBoatException {
    	if (this.isUserPlayerOne) {
    		panel.displayBoat(new ArrayList<Boat>(match.getPlayerOneBoats()
    				.values()));
    	}
    	else {
    		panel.displayBoat(new ArrayList<Boat>(match.getPlayerTwoBoats()
    				.values()));
    	}
    		
    	
		
	}

}

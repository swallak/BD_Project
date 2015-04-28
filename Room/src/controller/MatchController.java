package controller;

import dao.ActionDAO;
import dao.BoatDAO;
import dao.MatchDAO;
import dao.jdbc.ActionDAO_JDBC;
import dao.jdbc.BoatDAO_JDBC;
import dao.jdbc.JDBCConnection;
import dao.jdbc.MatchDAO_JDBC;
import java.awt.Point;
import java.sql.Connection;
import java.util.List;
import model.game.Boat;
import model.game.Match;
import model.game.Turn;
import model.user.AbstractUser;
import view.MatchViewFrame;

public class MatchController {

    private Match match;
    private MatchDAO matchDAO = new MatchDAO_JDBC();
    private ActionDAO actionDAO = new ActionDAO_JDBC();
    private BoatDAO boatDAO = new BoatDAO_JDBC();
    private Connection con;

    public MatchController(Connection con, Match match) {

        this.con = con;
        this.match = match;
    }

    public void initMatch(List<Boat> boats) throws BoatDAO.BoatNotCreatedException, MatchDAO.MatchNotCreatedException, MatchDAO.MatchStateNotSave {
            
        matchDAO.createMatch(con, true, match);
        boatDAO.createBoatList(con, true, boats);
            //choix et placement des bateau: initialisation du jeu
    }

    public boolean moveAction(Point cible) {

        return true;
    }

    public boolean shootAction() {
        return true;
    }

    public void matchHasFinished() {

    }

    public void abandonMatch() {

    }

    public void updateBoats() {

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

        return this.getFirstUser().getPseudo() + "vs" + this.getSecondUser().getPseudo();
    }

}

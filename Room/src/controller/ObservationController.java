package controller;

import model.game.Turn;
import model.user.AbstractUser;
import dao.ActionDAO;

public class ObservationController {
	private AbstractUser firstUser;
    private AbstractUser secondUser;
    private ActionDAO action;
    private Turn turn;
    
    public AbstractUser getFirstUser()
    {
        return this.firstUser;
    }
    
    public AbstractUser getSecondUser( )
    {
        
        return this.secondUser;
        
    }
    public String toString ()
    {
            
            return this.firstUser.getPseudo() +"vs"+ this.secondUser.getPseudo();
    }
    public void setAction(ActionDAO action ){
        this.action = action;
    }
    
    public void setTurn (Turn turn ){
        this.turn=turn;
    }

}

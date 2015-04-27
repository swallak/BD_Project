package controller;

import model.user.AbstractUser;

public class MatchController {

    private AbstractUser firstUser;
    private AbstractUser secondUser;
    
    
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
    
}

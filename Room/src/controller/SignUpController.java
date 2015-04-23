package controller;

import view.SignUpView;

public class SignUpController {
    
    private String pseudo;
    private String birthday;
    private String firstName;
    private String lastName;
    private String streetNumber;
    private String streetName;
    private String city;
    private String zipCode;
    
    public SignUpController(SignUpView signUp)
    {
        pseudo=signUp.getPseudo();
        birthday=signUp.getBirthday();
        firstName=signUp.getFirstName();
        lastName=signUp.getLastName();
        streetName=signUp.getStreetName();
        streetNumber=signUp.getStreetNumber();
        city=signUp.getCity();
        zipCode=signUp.getZipCode();
    }
    
    /**
     * 
     * @return true if account creation succeeded 
     */
    public boolean  createAccount (){
        
        System.out.println("creating...\n"+"pseudo:"+pseudo+"\nbirthday:"+birthday+"\nfirstName:"
                +firstName+"\nlastName:"+lastName+"\nstreetName:"+streetName+"\nstreetnumbe:"+streetNumber
        +"\ncity:"+city+"\nzipcode:"+zipCode);
        return false;
    }

}

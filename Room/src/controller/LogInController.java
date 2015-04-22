package controller;

public class LogInController {

    private String pseudo;
    private String birthday;

    public LogInController(String pseudo, String birthday) {
        this.pseudo = pseudo;
        this.birthday = birthday;
        connect();
    }

    /**
     * Verify database and connect the user to the his room's home page
     *
     * @return true if connection succeded.
     */
    private boolean connect() {

        System.out.println("Connecting " + pseudo +"...");
        return true;
    }
}

package sample;

public class Session_Id {

    //Stores the session info of the current user
    private static String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
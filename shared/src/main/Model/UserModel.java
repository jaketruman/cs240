package Model;

/**
 * The User model and all the attributes assosiccated with a user
 */
public class UserModel {
    private String username;
    private String password;
    private String email;

    /**
     *
     * @param username the user's choosen username
     * @param password the user's password
     * @param email the user's email
     */
    public UserModel(
            String username,String password,String email
    ){
        this.email = email;
        this.password = password;
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

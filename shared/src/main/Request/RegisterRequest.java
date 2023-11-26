package Request;

/**
 * Oh you want to register hold on while this class requests it
 */
public class RegisterRequest {
    /**
     * @param username to check if already taken
     * @param password to set password for valid username
     * @param email to check if email adress is already being used
     */
    public RegisterRequest(String username, String password, String email){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    String username;
    String password;
    String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}

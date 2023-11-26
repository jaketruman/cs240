package Model;

/**
 * This is the model for the AuthToken, it takes 2 params an authToken stirng and Username
 */

public class AuthTokenModel {


    public AuthTokenModel(String username){
        this.username =username;
    }
    public AuthTokenModel(String username, String authToken){
        this.username =username;
        this.authToken = authToken;
    }
    private String authToken;
    private  String username;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

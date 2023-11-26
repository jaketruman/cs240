package Request;

/**
 * Hi this is the LogoutRequest class
 */
public class LogoutRequest {

    public LogoutRequest(){
    }
    public LogoutRequest(String authToken){
        this.authToken = authToken;
    }
    int code;
    String authToken;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

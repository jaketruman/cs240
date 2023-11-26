package Response;

/**
 * the login response
 */
public class LoginResponse {
    String authToken;
    String username;
    boolean success;
    String message;
    int code;
    public LoginResponse(){
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAuthtoken() {
        return authToken;
    }

    public void setAuthtoken(String authtoken) {
        this.authToken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

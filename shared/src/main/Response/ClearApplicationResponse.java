package Response;

/**
 * Really fun one here, going to take the response and pss it back to the handler
 * It should have a String for the response message and a boolean value to inidcate the success
 * or failure of it
 */
public class ClearApplicationResponse {
    int code;
    String response;
    boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String  getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}


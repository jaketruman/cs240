package Response;

import Model.GameModel;

import java.util.HashSet;

/**
 * The List games response
 */
public class ListGamesResponse {
    HashSet<GameModel> games;
    String message;
    boolean success;
    int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HashSet<GameModel> getGames() {
        return games;
    }

    public void setGames(HashSet<GameModel> games) {
        this.games = games;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

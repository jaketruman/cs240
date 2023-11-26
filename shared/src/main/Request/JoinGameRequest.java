package Request;

/**
 * This one is a little bit out there but it will do the Join Game Requests
 */
public class JoinGameRequest {
    /**
     * @param gameID will need this to find the game
     * @param color
     */

    public JoinGameRequest(String color, String gameID){
        this.gameID =gameID;
        this.playerColor =color;
    }
    public JoinGameRequest(String gameID){
        this.gameID = gameID;
    }
    String gameID;
    String playerColor;
    String authToken;
//    String username;
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getGameID() {
        return gameID;
    }


    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String color) {
        this.playerColor = color;
    }
}

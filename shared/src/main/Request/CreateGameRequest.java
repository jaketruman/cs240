package Request;

import chess.ChessGameImplmentation;

/**
 * This is going to take care of the CreateGame Requests
 */
public class CreateGameRequest {
    String blackUsername;
    String whiteUsername;
    String gameName;
    String authToken;
    ChessGameImplmentation gameImplmentation;
    public CreateGameRequest(String authToken, String blackUsername, String whiteUsername, String gameName){
        this.authToken =  authToken;
        this.gameName = gameName;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
    }
    public CreateGameRequest(String authToken, String blackUsername, String whiteUsername, String gameName,ChessGameImplmentation chessGameImplmentation){
        this.authToken =  authToken;
        this.gameName = gameName;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameImplmentation = chessGameImplmentation;
    }
    public CreateGameRequest(String gameName){
        this.gameName = gameName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ChessGameImplmentation getGameImplmentation() {
        return gameImplmentation;
    }

    public void setGameImplmentation(ChessGameImplmentation gameImplmentation) {
        this.gameImplmentation = gameImplmentation;
    }
}

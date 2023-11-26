package Model;

import chess.ChessGameImplmentation;

/**
 * This is the GameModel, it will idtentiy who is playing what gameID it is and the name of the game
 */
public class GameModel {

    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGameImplmentation gameImplmentation;

    /**
     * @param gameID unique identifer for this game (int)
     * @param whiteUsername white player username (string)
     * @param blackUsername black player username (string)
     * @param gameName the name of the game being played (string)
     * @param gameImplmentation the game implmentation being played (ChessGame)

     */
    public GameModel(
            int gameID,
            String whiteUsername,
            String blackUsername,
            String gameName,
            ChessGameImplmentation gameImplmentation
    ){
        this.blackUsername = blackUsername;
        this.gameID =gameID;
        this.gameImplmentation =gameImplmentation;
        this.gameName = gameName;
        this.whiteUsername = whiteUsername;
    }
    public GameModel(
            int gameID,
            String whiteUsername,
            String blackUsername,
            String gameName
    ){
        this.blackUsername = blackUsername;
        this.gameID =gameID;
        this.gameName = gameName;
        this.whiteUsername = whiteUsername;
    }

    public GameModel() {

    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUser) {
        this.whiteUsername = whiteUser;
    }

    public String getBlackUser() {
        return blackUsername;
    }

    public void setBlackUser(String blackUser) {
        this.blackUsername = blackUser;
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

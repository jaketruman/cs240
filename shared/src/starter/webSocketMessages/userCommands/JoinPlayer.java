package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {

//    public final String gameID;
    //public final String userID;
    //String auth;
    public final ChessGame.TeamColor playerColor;

    public JoinPlayer(String gameId, ChessGame.TeamColor playerColor, String username, String  auth){
        super(CommandType.JOIN_PLAYER,auth, gameId, username);
        this.playerColor = playerColor;

    }
//    public JoinPlayer(String authToken) {
//        super(authToken);
//    }
//    public String getAuthString(String auth){
//        return auth;
//    }
//
    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
//    public String getGameID() {
//        return gameID;
//    }

//    public String getUserID() {
//        return userID;
//    }
}

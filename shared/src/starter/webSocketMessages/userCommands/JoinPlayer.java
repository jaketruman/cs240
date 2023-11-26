package webSocketMessages.userCommands;

public class JoinPlayer extends UserGameCommand {

//    public final String gameID;
    //public final String userID;
    //String auth;
    public final String color;

    public JoinPlayer(String gameId, String color, String username, String  auth){
        super(CommandType.JOIN_PLAYER,auth, gameId, username);
        //this.commandType = CommandType.JOIN_PLAYER;
        //this.gameID = gameId;
        this.color =color;
        //this.auth = auth;
        //this.userID = userID;
    }
//    public JoinPlayer(String authToken) {
//        super(authToken);
//    }
//    public String getAuthString(String auth){
//        return auth;
//    }
//
    public String getColor() {
        return color;
    }
//    public String getGameID() {
//        return gameID;
//    }

//    public String getUserID() {
//        return userID;
//    }
}

package webSocketMessages.userCommands;

import chess.ChessMoveImplmentation;

public class MakeMove  extends UserGameCommand{
    ChessMoveImplmentation move;
    String userID;
    String auth;
//    ChessPositionImplmentation start;
//    ChessPositionImplmentation end;

//    public MakeMove(CommandType commandType, String authToken, String gameID, String username) {
//        super(commandType, authToken, gameID, username);
//    }


//    public MakeMove(String gameID ,ChessMoveImplmentation move, String auth) {
//        super(CommandType.MAKE_MOVE, auth);
//        this.commandType = CommandType.MAKE_MOVE;
//        this.gameID = gameID;
//        this.move =move;
//        this.auth = auth;
//
//    }
    public MakeMove(String gameID , String auth, String username,ChessMoveImplmentation move) {
        super(CommandType.MAKE_MOVE, auth, gameID,username);
        this.commandType = CommandType.MAKE_MOVE;
        this.auth = auth;
        this.move =move;
    }


    public ChessMoveImplmentation getMove() {
        return move;
    }

    public void setMove(ChessMoveImplmentation move) {
        this.move = move;
    }

//    public String getGameID() {
//        return gameID;
//    }
//
//    public void setGameID(String gameID) {
//        this.gameID = gameID;
//    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAuthToken() {
        return auth;
    }

//    public ChessPositionImplmentation getStart() {
//        return start;
//    }
//
//    public void setStart(ChessPositionImplmentation start) {
//        this.start = start;
//    }
//
//    public ChessPositionImplmentation getEnd() {
//        return end;
//    }
//
//    public void setEnd(ChessPositionImplmentation end) {
//        this.end = end;
//    }

    public void setAuthToken(String authToken) {
        this.auth = authToken;
    }
}

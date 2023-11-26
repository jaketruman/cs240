package ui;

import chess.ChessGameImplmentation;

public interface NotificationHandler {
    void updateBoard(ChessGameImplmentation gameImplmentation);
    void message(String message);
    void error(String error);


}

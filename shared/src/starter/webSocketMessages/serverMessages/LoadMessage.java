package webSocketMessages.serverMessages;

import chess.ChessGameImplmentation;

public class LoadMessage extends ServerMessage {

    public final ChessGameImplmentation gameImplmentation;

    public LoadMessage(ChessGameImplmentation gameImplmentation) {
        super(ServerMessageType.LOAD_GAME);
        //this should update the game implmentation right?
        //but how to do that from the ServerMessage, what is the ServerMEssageType
        ///will this even work?
         this.gameImplmentation = gameImplmentation;
    }
}

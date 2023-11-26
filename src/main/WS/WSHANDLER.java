package WS;

import Model.GameModel;
import chess.ChessGameImplmentation;
import chess.ChessMove;
import chess.ChessMoveImplmentation;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.Database;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.MakeMove;
import webSockets.UserGameCommand;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;

    @WebSocket
    public class WSHANDLER {

        //more of a handler class, deserilize and redirect the wwebsocket messages
            UserDAO userDAO;
            AuthDAO authDAO;
            GameDAO gameDAO;
            Connection connection;
            Session session;

            Database db = new Database();
            ChessGameImplmentation gameImplmentation;


        public WSHANDLER(Connection connection){
            this.connection = connection;
        }
        WS.ConnectionManager connectionManager = new ConnectionManager();

        @OnWebSocketMessage
        public void onMessage(Session session, String message) throws Exception {
            System.out.println(message);
            //this.session = session;
            connection  =db.getConnection();
            authDAO = new AuthDAO(connection);
            gameDAO = new GameDAO(connection);
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            System.out.println("COMMAND TYPE "+command.getCommandType());
            //session.getRemote().sendString("WebSocket response: " + message);
//            WS.Connection connection = getConnection(command.getAuthString(), session);
//            if (connection != null) {
                switch (command.getCommandType()) {
                    case JOIN_PLAYER -> join(session, new Gson().fromJson(message, JoinPlayer.class));
//                    case JOIN_OBSERVER -> observe(connection, command);
                    case MAKE_MOVE -> move(session, new Gson().fromJson(message, MakeMove.class));
//                    case LEAVE -> leave(connection, command);
//                    case RESIGN -> resign(connection, command);
                }
//            } else {
//                connection.sendError(session.getRemote(), "unknown user");
//            }

        }
        public void join(Session session, JoinPlayer command) throws IOException {
            //need a print board function to check user color id and then print corresponding board
           GameModel game = gameDAO.find(command.getGameID());
           String gameUserID;
           //check for matching usernames on command and connection
//           if (command.getColor().equalsIgnoreCase("white")){
//               gameUserID = game.getGameImplmentation().whiteUsername;
//           }else{
//               gameUserID = game.getGameImplmentation().blackUsername;
//           }
               game.getGameImplmentation().whiteUsername = command.getUsername();
               LoadMessage loadMessage = new LoadMessage(game.getGameImplmentation());
            try {
                session.getRemote().sendString(new Gson().toJson(loadMessage));
            } catch (IOException e) {
                e.printStackTrace();
            }
               String message = (command.getUsername() + " has joined as " + command.getColor());
               NotificationMessage notificationMessage = new NotificationMessage(message);
               //session.getRemote().sendString(new Gson().toJson(notificationMessage));
               // notification
             connectionManager.add(command.getUsername(), session);
             connectionManager.broadcast(command.getUsername(),notificationMessage);
        }
        public void observe(Connection connection, JoinPlayer command){

        }
        public void move(Session session, MakeMove command) throws InvalidMoveException, IOException {
            GameModel game = gameDAO.find(command.getGameID());
            gameImplmentation = game.getGameImplmentation();
            ChessMoveImplmentation moveImplmentation = new ChessMoveImplmentation(command.getStart(), command.getEnd());
            Collection<ChessMove> moves = gameImplmentation.validMoves(command.getStart());
            if (moves.contains(moveImplmentation)){
                // need to updat game implmeantion in db here
                gameImplmentation.makeMove(moveImplmentation);
                LoadMessage loadMessage = new LoadMessage(gameImplmentation);
                session.getRemote().sendString(new Gson().toJson(loadMessage));
                connectionManager.add(command.getUsername(), session);
                connectionManager.broadcastBoard(command.getUsername(), loadMessage);
            }
            else{
                System.out.println("GAY");
            }


        }
        public void leave(Connection connection, JoinPlayer command){

        }
        public void resign(Connection connection, JoinPlayer command){

        }



    }


package WS;

import Model.AuthTokenModel;
import Model.GameModel;
import chess.*;
import com.google.gson.*;
import dataAccess.AuthDAO;
import dataAccess.Database;
import dataAccess.GameDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.*;
import webSockets.UserGameCommand;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.Collection;
import java.util.Objects;

/**
 * This class handles all the server side actions that happen when a websocket command comes in.
 *
 * All the logic for sending messages back to the client websocket is the same but make move and join are the most
 * complex because of all the checks that have to be made.
 *
 * Also, the connection manager class is a huge part of this, where it takes a connection that is created with the session
 * and username.Then handles all the notification sending to other clients associated with the game
 *
 * Basically, the websocket command comes in and then is deserialized (this happens in the onMessage function),
 * the checks are made to see if the command is possible, if it doesn't pass the checks
 * or is an illegal command, an error message is sent back through the websocket,
 * if the command passes all the checks then depending on what command either a notification message is sent or
 * a load message is sent or both are sent.
 *
 * The actual sending of notification and load message happens in the ConnectionManager class,
 * what to do on a specific message takes place on the client side
 */

@WebSocket
    public class WSHANDLER {
            AuthDAO authDAO;
            GameDAO gameDAO;
            Connection connection;
            Session session;
            Database db = new Database();
            ChessGameImplmentation gameImplmentation;


        public WSHANDLER(Connection connection){
            this.connection = connection;
        }
        public final WS.ConnectionManager connectionManager = new ConnectionManager();

        @OnWebSocketMessage
        public void onMessage(Session session, String message) throws Exception {
            System.out.println(message);
            connection  =db.getConnection();
            authDAO = new AuthDAO(connection);
            gameDAO = new GameDAO(connection);
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ChessPosition.class, new ListAdapter());
            Gson gson = gsonBuilder.create();
                switch (command.getCommandType()) {
                    case JOIN_PLAYER -> join(session, new Gson().fromJson(message, JoinPlayer.class));
                    case JOIN_OBSERVER -> observe(session, new Gson().fromJson(message, JoinObserver.class));
                    case MAKE_MOVE -> move(session, gson.fromJson(message, MakeMove.class));
                    case LEAVE -> leave(session, new Gson().fromJson(message, Leave.class));
                    case RESIGN -> resign(session, new Gson().fromJson(message, Resign.class));
                }
        }


        public void join(Session session, JoinPlayer command) throws IOException {
            GameModel game = gameDAO.find(command.getGameID());
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = setUsername(tokenModel);
            if (authDAO.find(command.getAuthString()) == null){
                sendError(session,"That user can not join");
            }
           else if (game == null){
                sendError(session,"That game is null");
            }
           else if (command.getPlayerColor() == null){
                sendError(session,"That user has no color");
            }
            else if (command.getPlayerColor() == ChessGame.TeamColor.WHITE && !Objects.equals(game.getWhiteUsername(), username)){
                sendError(session,"Can't join as that color");
            }
            else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK && !Objects.equals(game.getBlackUser(), username)){
                sendError(session,"Can't join as that color");
            }
          else {
            if (command.getPlayerColor() == ChessGame.TeamColor.WHITE){
                game.setWhiteUsername(username);
                gameDAO.updateWhite(username, command.getGameID());
            }
            if (command.getPlayerColor() == ChessGame.TeamColor.BLACK){
                game.setBlackUser(username);
                gameDAO.updateBlack(username,command.getGameID());
            }
            LoadMessage loadMessage = new LoadMessage(game.getGameImplmentation());
            session.getRemote().sendString(new Gson().toJson(loadMessage));
            connectionManager.add(username, session);
            String message = (command.getUsername() + " has joined as " + command.getPlayerColor());
            NotificationMessage notificationMessage = new NotificationMessage(message);
            notificationMessage.message =message;
            connectionManager.broadcast(username, notificationMessage);
            }
        }
        public void observe(Session session, JoinObserver command) throws IOException {
            GameModel game = gameDAO.find(command.getGameID());
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = setUsername(tokenModel);

            if (authDAO.find(command.getAuthString()) == null){
                sendError(session,"That user is not authorized");
            }
            else if (game == null){
                sendError(session,"That game isn't real");
            }
            else{
                LoadMessage loadMessage = new LoadMessage(game.getGameImplmentation());
                session.getRemote().sendString(new Gson().toJson(loadMessage));
                connectionManager.add(username, session);
                String message = (username + " has joined as an observer");
                NotificationMessage notificationMessage = new NotificationMessage(message);
                notificationMessage.message = message;
                connectionManager.broadcast(username, notificationMessage);
            }

        }
        public void move(Session session, MakeMove command) throws InvalidMoveException, IOException {
            GameModel game = gameDAO.find(command.getGameID());
            if (game == null){
                sendError(session,"That game is over");
            }else{
            gameImplmentation = game.getGameImplmentation();
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = setUsername(tokenModel);
            Collection<ChessMove> moves = gameImplmentation.validMoves(command.getMove().getStartPosition());
            if (moves == null){
                sendError(session,"There are no valid moves");
            }
            else if (!Objects.equals(game.getWhiteUsername(), username) && !Objects.equals(game.getBlackUser(), username)){
                sendError(session,"That user cant make a move");
            } else if (gameImplmentation.getTeamTurn() == ChessGame.TeamColor.WHITE && !Objects.equals(game.getWhiteUsername(), username)) {
                sendError(session,"Wrong user tried to move");
            } else if (gameImplmentation.getTeamTurn() == ChessGame.TeamColor.BLACK && !Objects.equals(game.getBlackUser(), username)) {
                sendError(session,"Wrong user tried to move");
            }else if (moves.contains(command.getMove())){

                gameImplmentation.makeMove(command.getMove());
                gameDAO.update(gameImplmentation, command.getGameID());
                ChessGame.TeamColor oppColor = null;
                if (game.getWhiteUsername().equals(username)){
                    oppColor = ChessGame.TeamColor.BLACK;
                }
                if (game.getBlackUser().equals(username)){
                    oppColor = ChessGame.TeamColor.WHITE;
                }
                LoadMessage loadMessage = new LoadMessage(gameImplmentation);
                session.getRemote().sendString(new Gson().toJson(loadMessage));
                connectionManager.broadcastBoard(username, loadMessage);
                NotificationMessage notificationMessage = new NotificationMessage("Gay");
                notificationMessage.message = "Move made";
                if (oppColor!= null){
                if (gameImplmentation.isInCheck(oppColor)){
                    notificationMessage.message = "Move made, " + oppColor+ " is now in Check";
                    }
                }
                    connectionManager.broadcast(username,notificationMessage);
            }
            else{
                sendError(session,"Error making that move");
                }
            }


        }
        public void leave(Session session, Leave command) throws IOException {
            GameModel game = gameDAO.find(command.getGameID());
            if (gameDAO.find(command.getGameID())== null){
                sendError(session,"No game here");
                return;
            }
            gameImplmentation = game.getGameImplmentation();
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = setUsername(tokenModel);
            if (Objects.equals(game.getBlackUser(), username)){
                game.setBlackUser(null);
                gameDAO.updateBlack(username, command.getGameID());
            }
            if (Objects.equals(game.getWhiteUsername(), username)){
                game.setWhiteUsername(null);
                gameDAO.updateWhite(username, command.getGameID());
            }
            String message = (username + " has left the game");
            NotificationMessage notificationMessage = new NotificationMessage(message);
            notificationMessage.message = message;
            connectionManager.broadcast(username, notificationMessage);
            connectionManager.remove(username);
        }
        public void resign(Session session, Resign command) throws IOException {
            GameModel game = gameDAO.find(command.getGameID());
            if (game == null){
                sendError(session,"Game is null");
            }else {
            gameImplmentation = game.getGameImplmentation();
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = setUsername(tokenModel);
            if (Objects.equals(game.getBlackUser(), username)){
            gameDAO.delete(command.getGameID());
            }
            if (Objects.equals(game.getWhiteUsername(), username)){
                gameDAO.delete(command.getGameID());
            }
            if (!Objects.equals(game.getBlackUser(), username) && !Objects.equals(game.getWhiteUsername(), username)){
                sendError(session,"That user cannot resign");
            }else{
            String message = (username + " has left the game");
            NotificationMessage notificationMessage = new NotificationMessage(message);
            notificationMessage.message = message;
            connectionManager.broadcastALL(username, notificationMessage);
            connectionManager.remove(username);
                }
            }
        }
        public void sendError(Session session,String message) throws IOException {
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage =message;
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        public String setUsername(AuthTokenModel tokenModel){
            String username = null;
            if (tokenModel!= null){
                username = tokenModel.getUsername();
            }
            return username;
        }

    static class ListAdapter implements JsonDeserializer<ChessPosition> {
        @Override
        public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return jsonDeserializationContext.deserialize(jsonElement,ChessPositionImplmentation.class);
        }
    }


    }


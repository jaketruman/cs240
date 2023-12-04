package WS;

import Model.AuthTokenModel;
import Model.GameModel;
import chess.*;
import com.google.gson.*;
import dataAccess.AuthDAO;
import dataAccess.Database;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
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
        public final WS.ConnectionManager connectionManager = new ConnectionManager();

        @OnWebSocketMessage
        public void onMessage(Session session, String message) throws Exception {
            System.out.println(message);
            //this.session = session;
            connection  =db.getConnection();
            authDAO = new AuthDAO(connection);
            gameDAO = new GameDAO(connection);
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            System.out.println("COMMAND TYPE "+command.getCommandType());
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ChessPosition.class, new ListAdapter());
            Gson gson = gsonBuilder.create();
            //session.getRemote().sendString("WebSocket response: " + message);
//            WS.Connection connection = getConnection(command.getAuthString(), session);
//            if (connection != null) {
                switch (command.getCommandType()) {
                    case JOIN_PLAYER -> join(session, new Gson().fromJson(message, JoinPlayer.class));
                    case JOIN_OBSERVER -> observe(session, new Gson().fromJson(message, JoinObserver.class));
                    case MAKE_MOVE -> move(session, gson.fromJson(message, MakeMove.class));
                    case LEAVE -> leave(session, new Gson().fromJson(message, Leave.class));
                    case RESIGN -> resign(session, new Gson().fromJson(message, Resign.class));
                }
//            } else {
//                connection.sendError(session.getRemote(), "unknown user");
//            }

        }
//        @OnWebSocketError
//        public void onError(Session session, String message) throws IOException {
//            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
//            errorMessage.errorMessage =message;
//            session.getRemote().sendString(new Gson().toJson(errorMessage));
//        }


        public void join(Session session, JoinPlayer command) throws IOException {
            //need a print board function to check user color id and then print corresponding board
            GameModel game = gameDAO.find(command.getGameID());
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = null;
            if (tokenModel!= null){
                username = tokenModel.getUsername();

            }
            if (authDAO.find(command.getAuthString()) == null){
               ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
               errorMessage.errorMessage ="Error that game is no good";
               session.getRemote().sendString(new Gson().toJson(errorMessage));
           }
           else if (game == null){
               ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
               errorMessage.errorMessage ="Error that game is no good";
               session.getRemote().sendString(new Gson().toJson(errorMessage));
           }
          else if (command.getPlayerColor() == null){
               ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
               errorMessage.errorMessage ="Error there is no color";
               session.getRemote().sendString(new Gson().toJson(errorMessage));
           }
            else if (command.getPlayerColor() == ChessGame.TeamColor.WHITE && !Objects.equals(game.getWhiteUsername(), username)){
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK && !Objects.equals(game.getBlackUser(), username)){
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
          else {
           //check for matching usernames on command and connection
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
            String username = null;
            if (tokenModel!= null){
                username = tokenModel.getUsername();

            }
            if (authDAO.find(command.getAuthString()) == null){
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error that game is no good";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            else if (game == null){
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error that game is no good";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            else{
                LoadMessage loadMessage = new LoadMessage(game.getGameImplmentation());
                session.getRemote().sendString(new Gson().toJson(loadMessage));
                connectionManager.add(username, session);
                //this part needs work
                String message = (username + " has joined as an observer");
                NotificationMessage notificationMessage = new NotificationMessage(message);
                notificationMessage.message = message;
                //session.getRemote().sendString(new Gson().toJson(notificationMessage));
                connectionManager.broadcast(username, notificationMessage);
            }

        }
        public void move(Session session, MakeMove command) throws InvalidMoveException, IOException {
            GameModel game = gameDAO.find(command.getGameID());
            if (game == null){
                System.out.println("CUNT");
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }else{
            gameImplmentation = game.getGameImplmentation();
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = null;
            if (tokenModel!= null){
                username = tokenModel.getUsername();
            }

            Collection<ChessMove> moves = gameImplmentation.validMoves(command.getMove().getStartPosition());
            if (moves == null){
                System.out.println("Null moves");
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            else if (!Objects.equals(game.getWhiteUsername(), username) && !Objects.equals(game.getBlackUser(), username)){
                System.out.println("white usernames");
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            } else if (gameImplmentation.getTeamTurn() == ChessGame.TeamColor.WHITE && !Objects.equals(game.getWhiteUsername(), username)) {
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            } else if (gameImplmentation.getTeamTurn() == ChessGame.TeamColor.BLACK && !Objects.equals(game.getBlackUser(), username)) {
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage = "Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }else if (moves.contains(command.getMove())){
                System.out.println("making move");
                gameImplmentation.makeMove(command.getMove());
                gameDAO.update(gameImplmentation, command.getGameID());
                LoadMessage loadMessage = new LoadMessage(gameImplmentation);
                session.getRemote().sendString(new Gson().toJson(loadMessage));
                connectionManager.broadcastBoard(username, loadMessage);
                NotificationMessage notificationMessage = new NotificationMessage("Gay");
                notificationMessage.message = "Move made";
                connectionManager.broadcast(username,notificationMessage);
            }
            else{
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            }


        }
        public void leave(Session session, Leave command) throws IOException {
            GameModel game = gameDAO.find(command.getGameID());
            if (gameDAO.find(command.getGameID())== null){
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage = "Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
                return;
            }
            gameImplmentation = game.getGameImplmentation();
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = null;
            if (tokenModel!= null){
                username = tokenModel.getUsername();
            }
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
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }else {
            gameImplmentation = game.getGameImplmentation();
            AuthTokenModel tokenModel= authDAO.find(command.getAuthString());
            String username = null;
            if (tokenModel!= null){
                username = tokenModel.getUsername();
            }
            if (Objects.equals(game.getBlackUser(), username)){
                gameDAO.delete(command.getGameID());
            }
            if (Objects.equals(game.getWhiteUsername(), username)){
                gameDAO.delete(command.getGameID());
            }
            if (!Objects.equals(game.getBlackUser(), username) && !Objects.equals(game.getWhiteUsername(), username)){
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }else{
            String message = (username + " has left the game");
            NotificationMessage notificationMessage = new NotificationMessage(message);
            notificationMessage.message = message;
            connectionManager.broadcastALL(username, notificationMessage);
            connectionManager.remove(username);
            //connectionManager.removeALl();
                }
            }
        }

    static class ListAdapter implements JsonDeserializer<ChessPosition> {
        @Override
        public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return jsonDeserializationContext.deserialize(jsonElement,ChessPositionImplmentation.class);
        }
    }


    }


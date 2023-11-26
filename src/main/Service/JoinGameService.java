package Service;

import Model.AuthTokenModel;
import Model.GameModel;
import Request.JoinGameRequest;
import Response.JoinGameResponse;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;

import java.sql.Connection;
import java.util.Objects;

public class JoinGameService {

    public JoinGameResponse Join(JoinGameRequest request, Connection connection){
        JoinGameResponse response = new JoinGameResponse();
        try{
            AuthDAO authDAO = new AuthDAO(connection);
            GameDAO gameDAO = new GameDAO(connection);
            String team = request.getPlayerColor();
            String gameID = request.getGameID();
            System.out.println("GAME ID "+ gameID);
            GameModel game = gameDAO.find(gameID);
            AuthTokenModel authTokenModel = authDAO.find(request.getAuthToken());
            if (authTokenModel == null){
                response.setSuccess(false);
                response.setMessage("Error: That AuthToken is not real ");
                response.setCode(401);
                return response;
            }
            if (game == null){
                response.setCode(400);
                response.setMessage("Error: That gameID is invalid");
                return response;
            }
            if (Objects.equals(request.getPlayerColor(), "WHITE")) {
                if (game.getWhiteUsername() != null){
                    response.setCode(403);
                    response.setMessage("Error: [White is already taken]");
                    return response;
                }
            }
            if (Objects.equals(request.getPlayerColor(), "BLACK")) {
                if (game.getBlackUser() != null){
                    response.setCode(403);
                    response.setMessage("Error: [Black is already taken]");
                    return response;
                }
            }
            if (request.getPlayerColor() == null){
                response.setCode(200);
                return response;
            }
            gameDAO.joinGame(team, gameID,authTokenModel.getUsername());
            response.setCode(200);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

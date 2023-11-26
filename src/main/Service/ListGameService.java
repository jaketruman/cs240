package Service;

import Model.AuthTokenModel;
import Request.ListGamesRequests;
import Response.ListGamesResponse;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;

import java.sql.Connection;

public class ListGameService {
    /**
     * List game will take in a request and return a respomnse with sucess or failure
     * it will also contain all the games
     * @param requests the request to get all the games
     */

    public ListGamesResponse findGames(ListGamesRequests requests, Connection connection){
        ListGamesResponse response = new ListGamesResponse();
        try{
            GameDAO gameDAO = new GameDAO(connection);
            AuthDAO authDAO = new AuthDAO(connection);
            String authToken = requests.getAuthToken();
            AuthTokenModel authTokenModel = authDAO.find(authToken);
            if (authTokenModel == null){
                System.out.println("found null");
                response.setSuccess(false);
                response.setMessage("Error: That AuthToken is not real ");
                response.setCode(401);
                return response;
            }
            response.setGames(gameDAO.findall());
            if (response.getGames() == null){
                response.setMessage("Error: [The are no games in database]");
                response.setSuccess(false);
                response.setCode(401);
                return response;
            }
            response.setSuccess(true);
            response.setCode(200);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

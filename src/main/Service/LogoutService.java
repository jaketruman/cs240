package Service;

import dataAccess.AuthDAO;
import Model.AuthTokenModel;
import Request.LogoutRequest;
import Response.LogoutResponse;
import dataAccess.DataAccessException;

import java.sql.Connection;

public class LogoutService {
    public LogoutResponse logout(LogoutRequest request, Connection connection) throws DataAccessException {
        LogoutResponse response = new LogoutResponse();
        String authToken = request.getAuthToken();
        AuthDAO authDAO = new AuthDAO(connection);
        AuthTokenModel authTokenModel = authDAO.find(authToken);
        if (authTokenModel == null){
            response.setMessage("Error: That AuthToken is not real ");
            response.setCode(401);
            return response;
        }
        // make sure that the delete removes entire object from db
        authDAO.delete(authTokenModel.getAuthToken());
        if (authDAO.find(authToken) != null){
            response.setSuccess(false);
            response.setCode(401);
            response.setMessage("NOT DELETed");
        }
        response.setSuccess(true);
        response.setCode(200);
        System.out.println("LOGOUT WORKED HERE");
        return response;

    }
    /**
     * Will take in a request, check if the user is logged in if so log them out,
     * the request will have the user data
     * @param request the request with user data
     */


}

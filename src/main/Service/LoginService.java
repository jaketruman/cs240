package Service;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import Model.AuthTokenModel;
import Model.UserModel;
import Request.LoginRequest;
import Response.LoginResponse;
import dataAccess.DataAccessException;

import java.sql.Connection;
import java.util.UUID;

public class LoginService {
    /**
     * This will take in a request that contains a username and passowrd, it will return a response
     * with either success or failure or user not found
     * @param request the request object containing user data
     */
    public LoginResponse loginAttempt(LoginRequest request, Connection connection) throws DataAccessException {
        LoginResponse response = new LoginResponse();
        String username = request.getUsername();
        String password = request.getPassword();
        UserDAO userDAO = new UserDAO(connection);
        AuthDAO authDAO = new AuthDAO(connection);
        UserModel userModel = userDAO.findUser(username);
        if (userModel == null){
            response.setMessage("Error: [No user with that username]");
            response.setSuccess(false);
            response.setCode(401);
            return response;
        }
        if (!userModel.getPassword().equals(password)){
            response.setMessage("Error: [Wrong Password]");
            response.setSuccess(false);
            response.setCode(401);
            return response;
        }
        AuthTokenModel token = new AuthTokenModel(username);
//        if (authDAO.findbyUsername(username)!= null){
//            response.setMessage("Error: [That user is already logged in]");
//            response.setSuccess(false);
//            response.setCode(401);
//            return response;
//        }
        String created =UUID.randomUUID().toString();
        token.setAuthToken(created);
        authDAO.insert(token);
        response.setAuthtoken(created);
        response.setUsername(username);
        response.setCode(200);
        System.out.println("LOGIN RESPONSE");
        System.out.println(response);
        return response;

    }

}

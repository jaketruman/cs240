package Service;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import Model.UserModel;
import Request.RegisterRequest;
import Response.RegisterResponse;
import dataAccess.DataAccessException;

import java.sql.Connection;

public class RegisterService {
    /**
     * This will take in a request that has the username, password and email,
     * if the username is already in use or email taken,
     * it will return failed
     * @param request with the potenital info
     */

    public RegisterResponse add(RegisterRequest request, Connection connection) throws DataAccessException {
        UserDAO userDAO = new UserDAO(connection);
        AuthDAO authDAO = new AuthDAO(connection);
        RegisterResponse response = new RegisterResponse();
        if (request.getUsername() == null | request.getPassword() ==null | request.getEmail() ==null){
            response.setCode(400);
            response.setMessage("Error: [Bad Request]");
            return response;
        }
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        if (userDAO.findUser(username) != null){
            response.setCode(403);
            response.setMessage("Error: [That username is already taken]");
            return response;
        }
        try{
            UserModel user = new UserModel(username,password,email);
            userDAO.insert(user);

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        String auth = authDAO.createAuthtoken(username);
        response.setUsername(username);
        response.setAuthToken(auth);
        response.setCode(200);
        return response;
    }
}

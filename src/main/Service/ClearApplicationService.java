package Service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import Response.ClearApplicationResponse;
import dataAccess.DataAccessException;

import java.sql.Connection;

public class ClearApplicationService {
    /**
     * This will take a clear request and return a clear response that indicates
     * if the clear was succesfully or not, just going to pass in the clear request
     *
     */
    public ClearApplicationResponse clear(Connection connection) throws DataAccessException {
        ClearApplicationResponse obj = new ClearApplicationResponse();
        try {
            AuthDAO authDAO = new AuthDAO(connection);
            GameDAO gameDAO = new GameDAO(connection);
            UserDAO userDAO = new UserDAO(connection);
            authDAO.clear();
            userDAO.clear();
            gameDAO.clear();
            if (userDAO.findSize()!=0  | authDAO.findSize() != 0 | gameDAO.findSize()!=0){
                obj.setResponse("Error");
                obj.setCode(400);
                obj.setSuccess(false);
                return obj;
            }
        } catch (DataAccessException exception) {
            obj.setCode(400);
            obj.setSuccess(false);
            obj.setResponse("{}");
      }
        obj.setCode(200);
        return obj;
    }
}

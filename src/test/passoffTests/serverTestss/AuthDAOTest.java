package passoffTests.serverTestss;

import Model.AuthTokenModel;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class AuthDAOTest {
    Database db;
    AuthTokenModel token;
    AuthDAO authDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        token = new AuthTokenModel("Kevin","password");
        Connection connection = db.getConnection();
        authDAO = new AuthDAO(connection);
        authDAO.clear();
    }

    //Insert and find a game model
    @Test
    @DisplayName("Test Insert Pass")
    public void insertPass() throws DataAccessException{
        authDAO.insert(token);
        AuthTokenModel result = authDAO.find(token.getAuthToken());
        Assertions.assertNotNull(result, "UH That isn't supposed to be NULL");
        Assertions.assertEquals(result.getUsername(), token.getUsername(),"The usernames do not match");
        Assertions.assertEquals(result.getAuthToken(), token.getAuthToken(), "The tokens do no match");
    }
    @Test
    @DisplayName("Test Insert Fail")
    public void insertFail() throws DataAccessException{
        authDAO.insert(token);
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.insert(token));
    }



    @Test
    @DisplayName("Test Clear")
    public void clearPass() throws DataAccessException{
        authDAO.insert(token);
        authDAO.clear();
        Assertions.assertEquals(authDAO.findSize(), 0, "The Database is not empty");
    }

    @Test
    @DisplayName("Test Find Pass")
    public void findByUsernamePass() throws DataAccessException {
        AuthTokenModel temp = new AuthTokenModel("FEMBOY","GOTHGIRL");
        authDAO.insert(temp);
        authDAO.insert(token);
        AuthTokenModel result = authDAO.findbyUsername("FEMBOY");
        Assertions.assertNotNull(result,"That shouldn't be null");
        Assertions.assertEquals(result.getUsername(), temp.getUsername(),"The usernames do not match");
        Assertions.assertEquals(result.getAuthToken(), temp.getAuthToken(), "The passwords do no match");
    }
    @Test
    @DisplayName("Test Find Pass")
    public void findByUsernameFail() throws DataAccessException {
        AuthTokenModel temp = new AuthTokenModel("FEMBOY","GOTHGIRL");
        authDAO.insert(temp);
        authDAO.insert(token);
        AuthTokenModel result = authDAO.findbyUsername("NOTAUSERNAME");
        Assertions.assertNull(result,"That shouldn't be null");
    }

    @Test
    @DisplayName("Test Find Fail")
    public void findByAuthPass() throws DataAccessException {
        //Test finding a user that is not in the database returns null
        AuthTokenModel temp = new AuthTokenModel("FEMBOY","GOTHGIRL");
        authDAO.insert(temp);
        authDAO.insert(token);
        AuthTokenModel result = authDAO.find("GOTHGIRL");
        Assertions.assertNotNull(result,"That shouldn't be null");
        Assertions.assertEquals(result.getUsername(), temp.getUsername());
        Assertions.assertEquals(result.getAuthToken(), temp.getAuthToken());
    }

    @Test
    @DisplayName("Test Find Fail")
    public void findByAuthFail() throws DataAccessException {
        //Test finding a user that is not in the database returns null
        AuthTokenModel temp = new AuthTokenModel("FEMBOY","GOTHGIRL");
        authDAO.insert(temp);
        authDAO.insert(token);
        AuthTokenModel result = authDAO.find("NotAnAuthtoken");
        Assertions.assertNull(result,"That should be null");
    }
    @Test
    @DisplayName("Test Create Authtoken")
    public void createPass() throws DataAccessException{
        authDAO.createAuthtoken("CRINGEBOY");
        AuthTokenModel result = authDAO.findbyUsername("CRINGEBOY");
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getAuthToken());
        Assertions.assertEquals(result.getUsername(), "CRINGEBOY");
    }
    //try to create an authToken for a user that already has one
    @Test
    @DisplayName("Test Create Authtoken")
    public void createFail() throws DataAccessException{
        authDAO.insert(token);
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.createAuthtoken(null));
    }

    @Test
    @DisplayName("Test Delete Pass")
    public void deletePass() throws DataAccessException {
        authDAO.insert(token);
        authDAO.delete(token.getAuthToken());
        Assertions.assertEquals(authDAO.findSize(),0);
    }

    @Test
    @DisplayName("Test Delete Fail")
    public void deleteFail() throws DataAccessException {
        authDAO.insert(token);
        authDAO.delete("NotAnAuthtoken");
        Assertions.assertEquals(authDAO.findSize(),1);
    }


    @Test
    @DisplayName("Test Find Pass")
    public void findSizePass() throws DataAccessException {
        AuthTokenModel temp = new AuthTokenModel("FEMBOY","WOMENINSTEM");
        authDAO.insert(temp);
        authDAO.insert(token);
        int result = authDAO.findSize();
        Assertions.assertEquals(result,2, "The number of users found doesn't match the actual size");
    }

}

package passoffTests.serverTestss;

import Model.UserModel;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class UserDAOTest {
    Database db;
    UserModel user;
    UserDAO userDAO;


    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        user = new UserModel("Kevin","password","kevin@gmail.com");
        Connection connection = db.getConnection();
        userDAO = new UserDAO(connection);
        userDAO.clear();
    }

    //Insert and find a game model
    @Test
    @DisplayName("Test Insert Pass")
    public void insertPass() throws DataAccessException{
        userDAO.insert(user);
        UserModel result = userDAO.findUser(user.getUsername());
        Assertions.assertNotNull(result, "UH That isn't supposed to be NULL");
        System.out.println("Reslut "+result.getEmail());
        System.out.println("User "+user.getEmail());
        Assertions.assertEquals(result.getUsername(), user.getUsername(),"The usernames do not match");
        Assertions.assertEquals(result.getPassword(), user.getPassword(), "The passwords do no match");
        Assertions.assertEquals(result.getEmail(), user.getEmail(), "The emails do not match");

    }

    @Test
    @DisplayName("Test Insert FAil")
    public void insertFails() throws DataAccessException{
        userDAO.insert(user);
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.insert(user));
    }

    @Test
    @DisplayName("Test Clear")
    public void clearPass() throws DataAccessException{
        userDAO.insert(user);
        userDAO.clear();
        Assertions.assertEquals(userDAO.findSize(), 0, "The Database is not empty");
    }

    @Test
    @DisplayName("Test Find Pass")
    public void findPass() throws DataAccessException {
        UserModel temp = new UserModel("FEMBOY","GOTHGIRL","WOMENINSTEM");
        userDAO.insert(temp);
        userDAO.insert(user);
        UserModel result = userDAO.findUser("FEMBOY");
        Assertions.assertNotNull(result,"That shouldn't be null");
        Assertions.assertEquals(result.getUsername(), temp.getUsername(),"The usernames do not match");
        Assertions.assertEquals(result.getPassword(), temp.getPassword(), "The passwords do no match");
        Assertions.assertEquals(result.getEmail(), temp.getEmail(), "The emails do not match");
    }

    @Test
    @DisplayName("Test Find Fail")
    public void findFail() throws DataAccessException {
        //Test finding a user that is not in the database returns null
        UserModel temp = new UserModel("FEMBOY","GOTHGIRL","WOMENINSTEM)");
        userDAO.insert(temp);
        userDAO.insert(user);
        UserModel result = userDAO.findUser("NOTAUSERNAME");
        Assertions.assertNull(result,"That shouldn be null");

        //Test that clear works within find
        userDAO.clear();
        UserModel noUsers =userDAO.findUser("ANYONE");
        Assertions.assertNull(noUsers,"That shouldn be null");

    }

    @Test
    @DisplayName("Test Find Pass")
    public void findSizePass() throws DataAccessException {
        UserModel temp = new UserModel("FEMBOY","GOTHGIRL","WOMENINSTEM");
        userDAO.insert(temp);
        userDAO.insert(user);
        int result = userDAO.findSize();
        Assertions.assertEquals(result,2, "The number of users found doesn't match the actual size");
    }


}

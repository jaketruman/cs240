package passoffTests.serverTestss.MYTESTS;

import Request.RegisterRequest;
import Response.RegisterResponse;
import Service.RegisterService;
import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class RegisterTest {
    public  String username;
    private  String password;
    private  String email;
    public Connection connection;
    public Database database;
    public RegisterRequest registerRequest;
    public RegisterResponse registerResponse;
    public Database db;
    public AuthDAO authDAO;
    public UserDAO userDAO;
    public GameDAO gameDAO;
    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        Connection connection = db.getConnection();
        gameDAO= new GameDAO(connection);
        userDAO = new UserDAO(connection);
        authDAO = new AuthDAO(connection);
        authDAO.clear();
        userDAO.clear();
        gameDAO.clear();
    }
    @Test
    @DisplayName("Test Register 1")
    public void successRegister() throws DataAccessException {
        database = new Database();
        connection = database.getConnection();
        AuthDAO authDAO = new AuthDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        authDAO.clear();
        userDAO.clear();
        registerRequest = new RegisterRequest("STINNKY", "SUGMA", "NOTCOOL@gmail.com");
        RegisterService registerService = new RegisterService();
        registerResponse =  registerService.add(registerRequest,connection);
        Assertions.assertNotNull(registerResponse);
        Assertions.assertEquals(registerResponse.getCode(), 200);
        String user = (authDAO.findbyUsername("STINNKY")).getAuthToken();
        Assertions.assertEquals(registerResponse.getAuthToken(),user);
        Assertions.assertEquals(registerResponse.getUsername(), "STINNKY");
    }
    @Test
    @DisplayName("Test Fail Register")
    public void failRegister() throws DataAccessException {
        database = new Database();
        connection = database.getConnection();
        AuthDAO authDAO = new AuthDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        authDAO.clear();
        userDAO.clear();
        registerRequest = new RegisterRequest("STINNKY", "SUGMA", "NOTCOOL@gmail.com");
        RegisterService registerService = new RegisterService();
        registerService.add(new RegisterRequest("STINNKY","PEEE","GMAIL@gmail.com"), connection);
        registerResponse =  registerService.add(registerRequest,connection);
        Assertions.assertNotNull(registerResponse);
        Assertions.assertEquals(registerResponse.getCode(), 403);
        Assertions.assertEquals(registerResponse.getMessage(), "Error: [That username is already taken]");
    }
}

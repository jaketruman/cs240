package passoffTests.serverTestss.MYTESTS;

import Request.LoginRequest;
import Request.RegisterRequest;
import Response.LoginResponse;
import Service.LoginService;
import Service.RegisterService;
import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class LoginTest {
    public Connection connection;
    public Database database;
    public LoginRequest request;
    public RegisterRequest registerRequest;
    public LoginResponse response;


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
    @DisplayName("Test Login Good")
    public void successLogin() throws DataAccessException {
        database = new Database();
        connection = database.getConnection();
        AuthDAO authDAO = new AuthDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        authDAO.clear();
        userDAO.clear();
        //register the user
        registerRequest = new RegisterRequest("STINNKY", "SUGMA", "NOTCOOL@gmail.com");
        RegisterService registerService = new RegisterService();
        registerService.add(registerRequest,connection);
        authDAO.clear();

        //Login the user
        request = new LoginRequest("STINNKY","SUGMA");
        LoginService service = new LoginService();
        response = service.loginAttempt(request,connection);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getCode(), 200);
        Assertions.assertEquals(response.getUsername(),"STINNKY");
        Assertions.assertNotNull((authDAO.find(response.getAuthtoken())));
    }

    @Test
    @DisplayName("Test Login Fail")
    public void failLogin() throws DataAccessException {
        database = new Database();
        connection = database.getConnection();
        AuthDAO authDAO = new AuthDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        authDAO.clear();
        userDAO.clear();
        //register the user
        registerRequest = new RegisterRequest("STINNKY", "SUGMA", "NOTCOOL@gmail.com");
        RegisterService registerService = new RegisterService();
        registerService.add(registerRequest,connection);

        //Login the user
        request = new LoginRequest("STINNKY","BOFA");
        LoginService service = new LoginService();
        response = service.loginAttempt(request,connection);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getCode(), 401);

    }

}

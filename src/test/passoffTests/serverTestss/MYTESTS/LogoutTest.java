package passoffTests.serverTestss.MYTESTS;

import Request.LoginRequest;
import Request.LogoutRequest;
import Request.RegisterRequest;
import Response.LoginResponse;
import Response.LogoutResponse;
import Response.RegisterResponse;
import Service.LoginService;
import Service.LogoutService;
import Service.RegisterService;
import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class LogoutTest {
    public Connection connection;
    public Database database;
    public LoginRequest request;
    public RegisterRequest registerRequest;
    public LoginResponse response;
    public LogoutRequest logoutRequest;
    public LogoutResponse logoutResponse;
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
    @DisplayName("SUCCESS LOGUOT")
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
        RegisterResponse response = registerService.add(registerRequest,connection);


        //Logout the user

        logoutRequest = new LogoutRequest(response.getAuthToken());
        LogoutService logoutService = new LogoutService();
        logoutResponse  = logoutService.logout(logoutRequest,connection);

        Assertions.assertNotNull(logoutResponse);
        Assertions.assertEquals(logoutResponse.getCode(), 200);
        Assertions.assertNull(authDAO.find(logoutRequest.getAuthToken()));

    }

    @Test
    @DisplayName("Test Logout Fail")
    public void failLogout() throws DataAccessException {
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

        //Log the user out
        logoutRequest = new LogoutRequest();
        LogoutService logoutService = new LogoutService();
        logoutResponse  = logoutService.logout(logoutRequest,connection);
        Assertions.assertNotNull(logoutResponse);
        Assertions.assertEquals(logoutResponse.getCode(), 401);

    }

}

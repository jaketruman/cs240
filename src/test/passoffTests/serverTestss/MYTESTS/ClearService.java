package passoffTests.serverTestss.MYTESTS;

import Request.CreateGameRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.ClearApplicationResponse;
import Response.CreateGameResponse;
import Response.LoginResponse;
import Response.RegisterResponse;
import Service.ClearApplicationService;
import Service.CreateGameService;
import Service.RegisterService;
import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ClearService {

    public Connection connection;
    public Database database;
    public LoginRequest request;
    public RegisterRequest registerRequest;
    public LoginResponse response;
    public CreateGameResponse createGameResponse;
    public CreateGameRequest createGameRequest;
    public Database db;
    public AuthDAO authDAO;
    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        Connection connection = db.getConnection();
        authDAO = new AuthDAO(connection);
        authDAO.clear();
    }


    @Test
    @DisplayName("Clear Succesful")
    public void clearAttempt() throws DataAccessException {
        database = new Database();
        connection = database.getConnection();
        GameDAO gameDAO = new GameDAO(connection);
        AuthDAO authDAO = new AuthDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        gameDAO.clear();
        authDAO.clear();
        userDAO.clear();
        //register the user
        registerRequest = new RegisterRequest("STINNKY", "SUGMA", "NOTCOOL@gmail.com");
        RegisterService registerService = new RegisterService();
        RegisterResponse response = registerService.add(registerRequest,connection);
        //Login the user


        createGameRequest = new CreateGameRequest(response.getAuthToken(),"Blackuser",response.getUsername(),"POOPYGAME");
        CreateGameService createGameService = new CreateGameService();
        createGameResponse = createGameService.create(createGameRequest,connection);

        //Clear the whole DB
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        ClearApplicationResponse clearApplicationResponse = new ClearApplicationResponse();
        clearApplicationResponse = clearApplicationService.clear(connection);

        Assertions.assertNotNull(clearApplicationResponse);
        Assertions.assertEquals(clearApplicationResponse.getCode(),200);
        Assertions.assertEquals(gameDAO.findSize(),0);
        Assertions.assertEquals(userDAO.findSize(),0);
        Assertions.assertEquals(authDAO.findSize(),0);


    }

}

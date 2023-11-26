package passoffTests.serverTestss.MYTESTS;

import Request.CreateGameRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.CreateGameResponse;
import Response.LoginResponse;
import Response.RegisterResponse;
import Service.CreateGameService;
import Service.RegisterService;
import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;


public class CreateGameTest {
    public Connection connection;
    public Database database;
    CreateGameRequest createGameRequest;
    CreateGameResponse createGameResponse;
    RegisterRequest registerRequest;
    LoginRequest request;
    LoginResponse response;
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
    @DisplayName("Creatw Success")
    public void successCreate() throws DataAccessException {
        database = new Database();
        connection = database.getConnection();
        GameDAO gameDAO = new GameDAO(connection);
        AuthDAO authDAO = new AuthDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        gameDAO.clear();
        authDAO.clear();
        userDAO.clear();

        //register the user
       registerRequest = new RegisterRequest("STINNKY","SUGMA","JSDJSD");
        RegisterService registerService = new RegisterService();
        RegisterResponse response = registerService.add(registerRequest,connection);


        createGameRequest = new CreateGameRequest(response.getAuthToken(),"Blackuser",response.getUsername(),"POOPYGAME");
        CreateGameService createGameService = new CreateGameService();
        createGameResponse = createGameService.create(createGameRequest,connection);
        Assertions.assertNotNull(createGameResponse);
        Assertions.assertEquals(createGameResponse.getCode(), 200);
        Assertions.assertEquals(gameDAO.findSize(),1);

    }

    @Test
    @DisplayName("Create Fail")
    public void failCreate() throws DataAccessException {
        database = new Database();
        connection = database.getConnection();
        GameDAO gameDAO = new GameDAO(connection);
        AuthDAO authDAO = new AuthDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        gameDAO.clear();
        authDAO.clear();
        userDAO.clear();

        //register the user
        registerRequest = new RegisterRequest("STINNKY","SUGMA","JSDJSD");
        RegisterService registerService = new RegisterService();
        RegisterResponse response = registerService.add(registerRequest,connection);


        //Logout the user
        createGameRequest = new CreateGameRequest(UUID.randomUUID().toString(),"Blackuser",response.getUsername(),"POOPYGAME");
        CreateGameService createGameService = new CreateGameService();
        createGameResponse = createGameService.create(createGameRequest,connection);
        Assertions.assertNotNull(createGameResponse);
        Assertions.assertEquals(createGameResponse.getCode(), 401);
        Assertions.assertEquals(gameDAO.findSize(),0);

    }

}

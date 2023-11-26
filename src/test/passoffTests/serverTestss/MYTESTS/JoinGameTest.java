package passoffTests.serverTestss.MYTESTS;

import Request.CreateGameRequest;
import Request.JoinGameRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.CreateGameResponse;
import Response.JoinGameResponse;
import Response.LoginResponse;
import Response.RegisterResponse;
import Service.CreateGameService;
import Service.JoinGameService;
import Service.RegisterService;
import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class JoinGameTest {
    public Connection connection;
    public Database database;
    CreateGameRequest createGameRequest;
    CreateGameResponse createGameResponse;
    RegisterRequest registerRequest;
    LoginRequest request;
    LoginResponse response;
    JoinGameResponse joinGameResponse;
    JoinGameRequest joinGameRequest;
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
    @DisplayName("Join Success")
    public void successJoin() throws DataAccessException {
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

        //Join the game
        createGameRequest = new CreateGameRequest(response.getAuthToken(),"Blackuser",null,"POOPYGAME");
        CreateGameService createGameService = new CreateGameService();
        createGameResponse = createGameService.create(createGameRequest,connection);


        String temp = createGameResponse.getGameID();
        joinGameRequest = new JoinGameRequest("WHITE",temp);
        joinGameRequest.setGameID(temp);
        joinGameRequest.setAuthToken(response.getAuthToken());
        JoinGameService joinGameService = new JoinGameService();
        joinGameResponse = joinGameService.Join(joinGameRequest,connection);

        Assertions.assertNotNull(joinGameResponse);
        Assertions.assertEquals(joinGameResponse.getCode(),200);
        Assertions.assertEquals(gameDAO.find(createGameResponse.getGameID()).getGameName(),"POOPYGAME");
    }
    @Test
    @DisplayName("Join Fail")
    public void JoinFail() throws DataAccessException {
        database = new Database();
        connection = database.getConnection();
        GameDAO gameDAO = new GameDAO(connection);
        AuthDAO authDAO = new AuthDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        gameDAO.clear();
        authDAO.clear();
        userDAO.clear();

        //register the user
        registerRequest = new RegisterRequest("STINNKY", "SUGMA", "JSDJSD");
        RegisterService registerService = new RegisterService();
        RegisterResponse response = registerService.add(registerRequest,connection);


        //make second user
        RegisterRequest request1 = new RegisterRequest("notJake", "SUGMA", "JSDJSD");
        registerService.add(registerRequest, connection);
        RegisterResponse response1 = registerService.add(registerRequest,connection);

        //Join the game
        createGameRequest = new CreateGameRequest(response.getAuthToken(), "Blackuser", response.getUsername(), "POOPYGAME");
        CreateGameService createGameService = new CreateGameService();
        createGameResponse = createGameService.create(createGameRequest, connection);
        String temp = createGameResponse.getGameID();

        joinGameRequest = new JoinGameRequest("Black", temp);
        joinGameRequest.setGameID(temp);
        joinGameRequest.setAuthToken(response.getAuthToken());
        JoinGameService joinGameService = new JoinGameService();
        joinGameResponse = joinGameService.Join(joinGameRequest, connection);

        joinGameRequest = new JoinGameRequest("Black", temp);
        joinGameRequest.setGameID(temp);
        joinGameRequest.setAuthToken(response1.getAuthToken());
        joinGameResponse = joinGameService.Join(joinGameRequest, connection);

        Assertions.assertNotNull(joinGameResponse);
        Assertions.assertEquals(joinGameResponse.getCode(), 401);
        Assertions.assertEquals(gameDAO.find(createGameResponse.getGameID()).getGameName(), "POOPYGAME");
    }

}

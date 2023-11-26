package passoffTests.serverTestss.MYTESTS;

import Request.CreateGameRequest;
import Request.ListGamesRequests;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.CreateGameResponse;
import Response.ListGamesResponse;
import Response.LoginResponse;
import Response.RegisterResponse;
import Service.CreateGameService;
import Service.ListGameService;
import Service.RegisterService;
import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ListGamesTest {
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
    @DisplayName("List Success")
    public void successList() throws DataAccessException {
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

        //Create Games
        createGameRequest = new CreateGameRequest(response.getAuthToken(),"Blackuser",response.getUsername(),"POOPYGAME");
        CreateGameService createGameService = new CreateGameService();
        createGameResponse = createGameService.create(createGameRequest,connection);

        createGameRequest = new CreateGameRequest(response.getAuthToken(),"Blackuser",response.getUsername(),"Opps");
        createGameResponse = createGameService.create(createGameRequest,connection);


        ListGamesRequests listGamesRequests = new ListGamesRequests();
        listGamesRequests.setAuthToken(response.getAuthToken());
        ListGamesResponse listGamesResponse = new ListGamesResponse();
        ListGameService listGameService = new ListGameService();
        listGamesResponse = listGameService.findGames(listGamesRequests,connection);

        Assertions.assertNotNull(listGamesResponse);
        Assertions.assertEquals(listGamesResponse.getCode(), 200);
        Assertions.assertEquals(gameDAO.findSize(),2);

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
        createGameRequest = new CreateGameRequest(response.getAuthToken(),"Blackuser",response.getUsername(),"POOPYGAME");
        CreateGameService createGameService = new CreateGameService();
        createGameResponse = createGameService.create(createGameRequest,connection);

        createGameRequest = new CreateGameRequest(response.getAuthToken(),"Blackuser",response.getUsername(),"Opps");
        createGameResponse = createGameService.create(createGameRequest,connection);

        //List games with no authtoken
        ListGamesRequests listGamesRequests = new ListGamesRequests();
        ListGamesResponse listGamesResponse = new ListGamesResponse();
        ListGameService listGameService = new ListGameService();
        listGamesResponse = listGameService.findGames(listGamesRequests,connection);

        Assertions.assertNotNull(listGamesResponse);
        Assertions.assertEquals(listGamesResponse.getCode(), 401);
    }
}

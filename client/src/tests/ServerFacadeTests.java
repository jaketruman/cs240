import Response.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.ServerFacade;

import java.io.IOException;

public class ServerFacadeTests {
    ServerFacade server;
    String serverURl;

    @BeforeEach
    public void setup() throws IOException {
        server= new ServerFacade("http://localhost:8080");
        serverURl =server.serverURl;
        server.clear();
    }

    @Test
    @DisplayName("Clear Success")
    public void clearSuccess() throws IOException {
        //Assert the server sucessfully calls clear
        ClearApplicationResponse  response= server.clear();
        Assertions.assertNotNull(response, "That clear response was null");

    }

    @Test
    @DisplayName("Register Success")
    public void RegisterSuccess() throws IOException {
        //Assert the server sucessfully calls clear
        RegisterResponse response= server.register("LIGMA","BALLZ","trummann@byu.edu");
        Assertions.assertNotNull(response, "That Register response was null");
        Assertions.assertEquals(response.getCode(), 200);
    }
    @Test
    @DisplayName("Register Fail")
    public void RegisterFail() throws IOException {
        //Assert the server sucessfully calls clear
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        RegisterResponse response= server.register("LIGMA","BALLZ","trummann@byu.edu");
        Assertions.assertNotNull(response, "That Register response was null");
        Assertions.assertEquals(response.getCode(),403);
    }
    @Test
    @DisplayName("Login Pass")
    public void LoginPass() throws IOException {
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        server.logout();
        LoginResponse response= server.login("LIGMA", "BALLZ");
        Assertions.assertNotNull(response, "That Login response was null");
        Assertions.assertEquals(response.getCode(),200);
    }
    @Test
    @DisplayName("Login Fail")
    public void LoginFail() throws IOException {
        //login a user that dosen't exist
        LoginResponse response= server.login("LIGMA", "BALLZ");
        Assertions.assertNotNull(response, "That Login response was null");
        Assertions.assertEquals(response.getCode(),401);
    }
    @Test
    @DisplayName("Logout Pass")
    public void LogoutPass() throws IOException {
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        server.login("LIGMA", "BALLZ");
        LogoutResponse response = server.logout();
        Assertions.assertNotNull(response, "That Login response was null");
        Assertions.assertEquals(response.getCode(),200);
    }
    @Test
    @DisplayName("Logout Fail")
    public void LogoutFail() throws IOException {
        LogoutResponse response = server.logout();
        Assertions.assertNotNull(response, "That Login response was null");
        Assertions.assertEquals(response.getCode(),401);
    }
    @Test
    @DisplayName("CreateGame Pass")
    public void CreateGamePass() throws IOException {
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        CreateGameResponse response = server.createGame("HOTDADSONLY");
        Assertions.assertNotNull(response, "That CreateGame response was null");
        Assertions.assertEquals(response.getCode(),200);
    }
    @Test
    @DisplayName("CreateGame Fail")
    public void CreateGameFail() throws IOException {
//    try to create a game when not logged in
        CreateGameResponse response = server.createGame("HOTDADSONLY");
        Assertions.assertNotNull(response, "That Login response was null");
        Assertions.assertEquals(response.getCode(),401);
    }
    @Test
    @DisplayName("JoinGame Observe Pass")
    public void JoingObserervePass() throws IOException {
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        CreateGameResponse createGameResponse = server.createGame("HOTDADSONLY");
        JoinGameResponse response = server.joinGame(createGameResponse.getGameID());
        Assertions.assertNotNull(response, "That JoinGame response was null");
        Assertions.assertEquals(response.getCode(),200);
    }
    @Test
    @DisplayName("JoinGame Observe Pass")
    public void JoinObserverFAil() throws IOException {
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        CreateGameResponse createGameResponse = server.createGame("HOTDADSONLY");
        JoinGameResponse response = server.joinGame(String.valueOf(78));
        Assertions.assertNotNull(response, "That JoinGame response was null");
        Assertions.assertEquals(response.getCode(),400);
    }
    @Test
    @DisplayName("JoinGame Player Pass")
    public void JoinPlayerPass() throws IOException {
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        CreateGameResponse createGameResponse = server.createGame("HOTDADSONLY");
        JoinGameResponse response = server.joinGamePlayer(createGameResponse.getGameID(),"WHITE");
        Assertions.assertNotNull(response, "That JoinGame response was null");
        Assertions.assertEquals(response.getCode(),200);
    }
    @Test
    @DisplayName("JoinGame Player FAil")
    public void JoinPLAYERFail() throws IOException {
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        CreateGameResponse createGameResponse = server.createGame("HOTDADSONLY");
        server.joinGamePlayer(createGameResponse.getGameID(),"WHITE");
        JoinGameResponse response = server.joinGamePlayer(createGameResponse.getGameID(),"WHITE");
        Assertions.assertNotNull(response, "That JoinGame response was null");
        Assertions.assertEquals(response.getCode(),403);
    }
    @Test
    @DisplayName("ListGames Pass")
    public void ListGamesPass() throws IOException {
        server.register("LIGMA","BALLZ","trummann@byu.edu");
        server.createGame("HOTDADSONLY");
        ListGamesResponse response=server.list();
        Assertions.assertNotNull(response, "That JoinGame response was null");
        Assertions.assertEquals(response.getCode(),200);
        Assertions.assertEquals(response.getGames().size() ,1);
    }
    @Test
    @DisplayName("ListGames Fail")
    public void ListGamesFail() throws IOException {
        Assertions.assertThrows(IOException.class,() -> server.list());
        //Assertions.assertEquals(response.getGames().size() ,0);
    }
}

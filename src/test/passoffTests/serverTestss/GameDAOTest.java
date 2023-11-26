package passoffTests.serverTestss;

import Model.GameModel;
import chess.ChessBoardImplmentation;
import chess.ChessGameImplmentation;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.GameDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.HashSet;

public class GameDAOTest {
    Database db;
    GameModel game;
    GameDAO gameDAO;
    ChessGameImplmentation chessGameImplmentation;



    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        ChessBoardImplmentation boardImplmentation = new ChessBoardImplmentation();
        boardImplmentation.resetBoard();
        chessGameImplmentation =new ChessGameImplmentation(boardImplmentation);
        game = new GameModel(1,"JohnCena","RandyOrton","WWE",chessGameImplmentation);
        Connection connection = db.getConnection();
        gameDAO = new GameDAO(connection);
        gameDAO.clear();
    }

    //Insert and find a game model
    @Test
    @DisplayName("Test Insert Pass")
    public void insertPass() throws DataAccessException{
        String ID = String.valueOf(game.getGameID());
        gameDAO.insert(game,ID);
        GameModel result = gameDAO.find(ID);
        Assertions.assertNotNull(result, "UH That isn't supposed to be NULL");
        Assertions.assertEquals(result.getBlackUser(), game.getBlackUser(), "Those blackusernames don't match");
        Assertions.assertEquals(result.getWhiteUsername(), game.getWhiteUsername(), "Those whiteusernames don't match");
        Assertions.assertEquals(result.getGameID(), game.getGameID(), "Those gameIDs don't match");
        Assertions.assertEquals(result.getGameName(), game.getGameName(), "Those game names don't match");
        gameDAO.clear();

    }

    @Test
    @DisplayName("Test Insert FAil")
    public void insertFails() throws DataAccessException{
        gameDAO.insert(game, String.valueOf(game.getGameID()));
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.insert(game,String.valueOf(game.getGameID())));
    }

    @Test
    @DisplayName("Test Clear")
    public void clearPass() throws DataAccessException{
        gameDAO.insert(game,String.valueOf(game.getGameID()));
        gameDAO.clear();
        Assertions.assertEquals(gameDAO.findSize(), 0, "The Database is not empty");
    }

    @Test
    @DisplayName("Test Join")
    public void joinPass() throws DataAccessException {
        //test white join
        GameModel nullWhite = new GameModel(60,null,null,"FEMBOY",chessGameImplmentation);
        gameDAO.insert(nullWhite, String.valueOf(nullWhite.getGameID()));
        gameDAO.joinGame("WHITE",String.valueOf(nullWhite.getGameID()),"JohnCena");
        GameModel result = gameDAO.find(String.valueOf(nullWhite.getGameID()));
        Assertions.assertNotNull(result,"That shouldn't be null");
        Assertions.assertEquals(result.getWhiteUsername(), "JohnCena", "That username doesn't match the one that joined");

        //test black join
        gameDAO.joinGame("BLACK", String.valueOf(nullWhite.getGameID()), "UWUBOY");
        result = gameDAO.find(String.valueOf(nullWhite.getGameID()));
        Assertions.assertNotNull(result,"That shouldn't be null");
        Assertions.assertEquals(result.getBlackUser(), "UWUBOY", "That username doesn't match the one that joined");

    }
    @Test
    @DisplayName("Test Join")
    public void joinFail() throws DataAccessException {
        //test white join invalid game
        GameModel nullWhite = new GameModel(60,null,null,"FEMBOY",chessGameImplmentation);
        gameDAO.insert(nullWhite, String.valueOf(nullWhite.getGameID()));
        gameDAO.joinGame("WHITE", String.valueOf(96),"JohnCena");
        GameModel result = gameDAO.find(String.valueOf(nullWhite.getGameID()));
        Assertions.assertNotNull(result,"That should be null");
        Assertions.assertEquals(result.getWhiteUsername(), null, "That username doesn't match the one that joined");

       //can a user take another users spot in a game?
    }
    @Test
    @DisplayName("Test Find Size Pass")
    public void findSizePass() throws DataAccessException {
        GameModel temp = new GameModel(34,"FEMBOY","WOMENINSTEM","deez");
        gameDAO.insert(temp,String.valueOf(34));
        gameDAO.insert(game,String.valueOf(game.getGameID()));
        int result = gameDAO.findSize();
        Assertions.assertEquals(result,2, "The number of users found doesn't match the actual size");
    }

    @Test
    @DisplayName("Test Find All Pass")
    public void findallPass() throws DataAccessException {
        GameModel temp = new GameModel(34,"FEMBOY","WOMENINSTEM","deez");
        gameDAO.insert(temp,String.valueOf(34));
        gameDAO.insert(game,String.valueOf(game.getGameID()));
        HashSet<GameModel> gameModels = gameDAO.findall();
        Assertions.assertEquals(gameModels.size(),2, "The number of games found doesn't match the actual size");
    }
    @Test
    @DisplayName("Test Find All Fail")
    public void findallFail() throws DataAccessException {
        HashSet<GameModel> gameModels = gameDAO.findall();
        Assertions.assertNotNull(gameModels);
        Assertions.assertEquals(gameModels.size(),0, "The number of games found doesn't match the actual size");
    }



}

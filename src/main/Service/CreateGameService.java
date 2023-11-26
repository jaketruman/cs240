package Service;

import Model.GameModel;
import Request.CreateGameRequest;
import Response.CreateGameResponse;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import chess.ChessBoardImplmentation;
import chess.ChessGameImplmentation;

import java.sql.Connection;
import java.util.Random;

public class CreateGameService {
    public ChessBoardImplmentation boardImplmentation = new ChessBoardImplmentation();

    public ChessGameImplmentation chessGameImplmentation;
    /**
     * This will take a Create game request and attempt to create the game,
     * it should just take in the request and handle the rest inside this class
     */
    public CreateGameResponse create(CreateGameRequest request, Connection connection){
        CreateGameResponse response = new CreateGameResponse();
        //String gameID = UUID.randomUUID().toString();
        Random r = new Random();
        int gameid = r.nextInt(200-1) + 1;
        try{
            GameDAO gameDAO = new GameDAO(connection);
            AuthDAO authDAO = new AuthDAO(connection);
            if (authDAO.find(request.getAuthToken())==null){
                response.setCode(401);
                response.setMessage("Error: Bad Auth");
                return response;
            }
            boardImplmentation.resetBoard();
            chessGameImplmentation = new ChessGameImplmentation(boardImplmentation);
            System.out.println(chessGameImplmentation.getTeamTurn());
            GameModel model = new GameModel(gameid, request.getWhiteUsername(),request.getWhiteUsername(),request.getGameName(),chessGameImplmentation);
            gameDAO.insert(model, String.valueOf(gameid));
            response.setGameID(String.valueOf(gameid));
            System.out.println("GAME CREATE ID"+String.valueOf(gameid) );
            response.setCode(200);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

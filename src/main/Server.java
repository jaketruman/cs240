import Handlers.*;
import WS.WSHANDLER;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Spark;

import java.sql.Connection;

public class Server {

    public static void main(String[] args) throws DataAccessException {
        Integer portNumber = Integer.valueOf(args[0]);
        Database db = new Database();
        Connection connection = db.getConnection();
        WSHANDLER wshandler = new WSHANDLER(connection);
        try {
            Spark.port(portNumber);
            //need websocket here to complete the handshake
            Spark.webSocket("/connect", wshandler);
            Spark.externalStaticFileLocation("src/public");
            Spark.delete("/db", (req, res) -> ClearApplicationHandler.handle(req,res));
            Spark.post("/user", (req, res) -> RegisterHandler.handle(req,res));
            Spark.post("/session", (req, res) -> LoginHandler.handle(req,res));
            Spark.delete("/session", (req, res) -> LogoutHandler.handle(req,res));
            Spark.get("/game", (req, res) -> ListGamesHandler.handle(req,res));
            Spark.post("/game", (req, res) -> CreateGameHandler.handle(req,res));
            Spark.put("/game", (req, res) -> JoinGameHandler.handle(req,res));
        Spark.awaitInitialization();
            System.out.println("Listening on port " + portNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

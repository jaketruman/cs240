package Handlers;

import Request.JoinGameRequest;
import Response.JoinGameResponse;
import Service.JoinGameService;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class JoinGameHandler{
    static public String handle(Request request, Response response) throws IOException {

        try {
            Gson gson = new Gson();
            Database db = new Database();
            Connection connection = db.getConnection();
            JoinGameRequest requested = gson.fromJson(request.body(), JoinGameRequest.class);
            JoinGameService service = new JoinGameService();
            requested.setAuthToken(request.headers("Authorization"));
            JoinGameResponse response1 = service.Join(requested,connection);
            response.status(response1.getCode());
            connection.close();
            return new Gson().toJson(response1);
        } catch (DataAccessException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

package Handlers;

import Request.ListGamesRequests;
import Response.ListGamesResponse;
import Service.ListGameService;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ListGamesHandler{

        static public String handle(Request request, Response response) throws IOException {
            try {
                Gson gson = new Gson();
                Database db = new Database();
                Connection connection = db.getConnection();
                ListGamesRequests requested = new ListGamesRequests();
                requested.setAuthToken(request.headers("Authorization"));
                ListGameService service = new ListGameService();
                ListGamesResponse response1 = service.findGames(requested,connection);
                response.status(response1.getCode());
                connection.close();
                return new Gson().toJson(response1);
            } catch (DataAccessException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


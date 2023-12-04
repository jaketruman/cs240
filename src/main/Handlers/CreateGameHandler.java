package Handlers;

import Request.CreateGameRequest;
import Response.CreateGameResponse;
import Service.CreateGameService;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateGameHandler{
    public static String handle(Request request, Response response) throws IOException {
        try {
            System.out.println("Register Handler");
            Gson gson = new Gson();
            Database db = new Database();
            Connection connection= db.getConnection();
            String temp = request.body();
            CreateGameRequest requested = gson.fromJson(temp, CreateGameRequest.class);
            requested.setAuthToken(request.headers("Authorization"));
            CreateGameService service = new CreateGameService();
            CreateGameResponse responsed = service.create(requested, connection);
            response.status(responsed.getCode());
            connection.close();
            return gson.toJson(responsed);
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }


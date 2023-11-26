package Handlers;

import Request.RegisterRequest;
import Response.RegisterResponse;
import Service.RegisterService;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.Connection;

public class RegisterHandler {
    public static String handle(Request request, Response response) throws IOException {
        try {
            System.out.println("Register Handler");
            Gson gson = new Gson();
            Database db = new Database();
            Connection connection= db.getConnection();
            String temp = request.body();
            RegisterRequest requested = gson.fromJson(temp, RegisterRequest.class);
            RegisterService service = new RegisterService();
            RegisterResponse registerResponse = service.add(requested, connection);
            response.status(registerResponse.getCode());
            return new Gson().toJson(registerResponse);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
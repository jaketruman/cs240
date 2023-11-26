package Handlers;

import Request.LoginRequest;
import Response.LoginResponse;
import Service.LoginService;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.Connection;

public class LoginHandler{

    public static String handle(Request request, Response response) throws IOException {
        try {
            Gson gson = new Gson();
            Database db = new Database();
            Connection connection = db.getConnection();
            LoginRequest requested = gson.fromJson(request.body(),LoginRequest.class);
            LoginService service = new LoginService();
            System.out.println("About to enter service");
            LoginResponse response1 = service.loginAttempt(requested,connection);
            response.status(response1.getCode());
            return new Gson().toJson(response1);
        } catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}

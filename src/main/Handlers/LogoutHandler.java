package Handlers;

import Request.LogoutRequest;
import Response.LogoutResponse;
import Service.LogoutService;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.Connection;

public class LogoutHandler{
    public static String handle(Request request, Response response) throws IOException {

        try {
            Gson gson = new Gson();
            Database db = new Database();
            Connection connection = db.getConnection();
            LogoutRequest requested = new LogoutRequest();
            LogoutService service = new LogoutService();
            requested.setAuthToken(request.headers("Authorization"));
            LogoutResponse response1 = service.logout(requested,connection);
            response.status(response1.getCode());
            if (response1.isSuccess()){
                return "{}";
            }
            else {
            return new Gson().toJson(response1);}
        } catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}

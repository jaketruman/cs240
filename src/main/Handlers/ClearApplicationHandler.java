package Handlers;

import Response.ClearApplicationResponse;
import Service.ClearApplicationService;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.Connection;

public class ClearApplicationHandler {

    static public String handle(Request request, Response response) throws IOException {
        Database db = new Database();
        try{
            Connection connection = db.getConnection();
            ClearApplicationService service = new ClearApplicationService();
            ClearApplicationResponse response1 = service.clear(connection);
            response.status(response1.getCode());
            response.body(response1.getResponse());
        } catch (DataAccessException e) {
           throw new RuntimeException(e);
       }
        return "{}";
    }

}

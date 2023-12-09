package Handlers;

import Response.ClearApplicationResponse;
import Service.ClearApplicationService;
import dataAccess.DataAccessException;
import dataAccess.Database;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Ok beast mode these are annoying but not too bad.
 * Handlers are going to create a connection to the database and then send a request to the service class corresponding
 * to the Handler. The service is going to return a response and that is what is returned
 */
public class ClearApplicationHandler {

    static public String handle(Request request, Response response) throws IOException {
        Database db = new Database();
        try{
            Connection connection = db.getConnection();
            ClearApplicationService service = new ClearApplicationService();
            ClearApplicationResponse response1 = service.clear(connection);
            response.status(response1.getCode());
            response.body(response1.getResponse());
            connection.close();
        } catch (DataAccessException e) {
           throw new RuntimeException(e);
       } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "{}";
    }

}

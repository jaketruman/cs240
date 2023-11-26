package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String filePath = null;
        System.out.println("FILE HANDLER");
        if (url.equals("/")){
            System.out.println("IN URL");
            filePath = "src/web/index.html";
        }
        else if (url.equals("/index.html")){
            filePath = "src/web/index.html";

        }
        else if (url.equals("/favicon.ico")){
            filePath = "src/web/favicon.ico";
        }
        else if (url.equals("/css/index.css")){
            filePath = "src/web/css/index.css";
        }
        else {
            filePath= "src/web/HTML/404.html";

        }
        File file = new File(filePath);

        if (filePath == "web/HTML/404.html"){
            System.out.println("EROROR");
            File file1 = new File("web/HTML/404.html");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND,0);
            OutputStream responseBody = exchange.getResponseBody();
            Files.copy(file1.toPath(), responseBody);
            responseBody.close();
        }
        if (file.exists()){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            OutputStream responseBody = exchange.getResponseBody();
            Files.copy(file.toPath(), responseBody);
            responseBody.close();
        }
    }
}
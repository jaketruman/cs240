package ui;

import Request.*;
import Response.*;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerFacade {
    public String serverURl;
    public String userAuth;

    public ServerFacade(String url){
        this.serverURl=url;
    }

    public ClearApplicationResponse clear() throws IOException {
        //set url
        URL url = new URL(serverURl + "/db");

        //create and connect to server
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.setDoOutput(true);

        ClearApplicationRequest request = new ClearApplicationRequest();
        String jsonRequest = new Gson().toJson(request);

        try(OutputStream requestBody = httpURLConnection.getOutputStream()){
            requestBody.write(jsonRequest.getBytes());
        }
        httpURLConnection.connect();
        //get and set the response
        ClearApplicationResponse response = new ClearApplicationResponse();
        response.setCode(httpURLConnection.getResponseCode());
        return response;
    }
    public RegisterResponse register(String username, String password, String email) throws IOException {
        URL url = new URL(serverURl + "/user");
        RegisterResponse response = new RegisterResponse();


        //create and connect to server
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        //httpURLConnection.addRequestProperty("Accept", "application/json");
        RegisterRequest request = new RegisterRequest(username, password, email);
        String jsonRequest = new Gson().toJson(request);

        try(OutputStream requestBody = httpURLConnection.getOutputStream()){
            requestBody.write(jsonRequest.getBytes());
        }
        int response_code = httpURLConnection.getResponseCode();
        if (response_code == 200){
        httpURLConnection.connect();
        try (InputStream responseBody = httpURLConnection.getInputStream()){
            InputStreamReader reader = new InputStreamReader(responseBody);
            response = new Gson().fromJson((Reader) reader, (Type) RegisterResponse.class);
            userAuth = response.getAuthToken();
            return response;
        }
        }else{
                response.setCode(httpURLConnection.getResponseCode());
                response.setMessage(httpURLConnection.getResponseMessage());
                return response;
            }
    }
    public LoginResponse login(String username, String password) throws IOException {
        URL url = new URL(serverURl + "/session");
        LoginResponse response = new LoginResponse();
        //create and connect to server
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        //httpURLConnection.addRequestProperty("Accept", "application/json");
        LoginRequest request = new LoginRequest(username, password);
        String jsonRequest = new Gson().toJson(request);
        try (OutputStream requestBody = httpURLConnection.getOutputStream()) {
            requestBody.write(jsonRequest.getBytes());
        }
        int response_code = httpURLConnection.getResponseCode();
        if (response_code == 200){
        httpURLConnection.connect();
        try (InputStream responseBody = httpURLConnection.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(responseBody);
            response = new Gson().fromJson((Reader) reader, (Type) LoginResponse.class);
            userAuth = response.getAuthtoken();
            return response;
        }}else {
            response.setCode(httpURLConnection.getResponseCode());
            response.setMessage(httpURLConnection.getResponseMessage());
            return response;
        }
    }
    public LogoutResponse logout() throws IOException {
        URL url = new URL(serverURl + "/session");
        //create and connect to server
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization", userAuth);

        //no request for this just check the responsecode
        int responseCode = httpURLConnection.getResponseCode();
        LogoutResponse response = new LogoutResponse();
        response.setCode(responseCode);
        return response;

    }
    public CreateGameResponse createGame(String gameName) throws IOException {
        URL url = new URL(serverURl + "/game");
        CreateGameResponse response = new CreateGameResponse();
        //create and connect to server
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization", userAuth);

        CreateGameRequest request = new CreateGameRequest(gameName);
        String jsonRequest = new Gson().toJson(request);
        try (OutputStream requestBody = httpURLConnection.getOutputStream()) {
            requestBody.write(jsonRequest.getBytes());
        }
        httpURLConnection.connect();
        int response_code = httpURLConnection.getResponseCode();
        if (response_code == 200){
        try (InputStream responseBody = httpURLConnection.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(responseBody);
            response = new Gson().fromJson(reader, CreateGameResponse.class);
            return response;
        }
        }else {
            response.setCode(httpURLConnection.getResponseCode());
            return response;
        }
    }
    public JoinGameResponse joinGame(String gameID) throws IOException {
        URL url = new URL(serverURl + "/game");

        //create and connect to server
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("PUT");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization", userAuth);

        JoinGameRequest request = new JoinGameRequest(gameID);
        String jsonRequest = new Gson().toJson(request);
        try (OutputStream requestBody = httpURLConnection.getOutputStream()) {
            requestBody.write(jsonRequest.getBytes());
        }
        httpURLConnection.connect();
        int response_code = httpURLConnection.getResponseCode();
        if (response_code == 200){
        try (InputStream responseBody = httpURLConnection.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(responseBody);
            return new Gson().fromJson((Reader) reader, (Type) JoinGameResponse.class);
        }
        }else {
            JoinGameResponse response = new JoinGameResponse();
            response.setCode(httpURLConnection.getResponseCode());
            return response;
        }
    }
    public JoinGameResponse joinGamePlayer(String gameID,String color) throws IOException {
        URL url = new URL(serverURl + "/game");

        //create and connect to server
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("PUT");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization", userAuth);

        JoinGameRequest request = new JoinGameRequest(color, gameID);
        String jsonRequest = new Gson().toJson(request);
        try (OutputStream requestBody = httpURLConnection.getOutputStream()) {
            requestBody.write(jsonRequest.getBytes());
        }
        httpURLConnection.connect();
        if(httpURLConnection.getResponseCode() == 200){
        try (InputStream responseBody = httpURLConnection.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(responseBody);
            //ChessBoardCringe.main(new String[]{"BLACK"});
            return new Gson().fromJson((Reader) reader, (Type) JoinGameResponse.class);
        }}else{
            JoinGameResponse response = new JoinGameResponse();
            response.setCode(httpURLConnection.getResponseCode());
            return response;
        }
    }
    public ListGamesResponse list() throws IOException {
        URL url = new URL(serverURl + "/game");

        //create and connect to server
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.addRequestProperty("Authorization", userAuth);

        httpURLConnection.connect();
        try (InputStream responseBody = httpURLConnection.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(responseBody);
            return new Gson().fromJson(reader, (Type) ListGamesResponse.class);
        }
    }


}


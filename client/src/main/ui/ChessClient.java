package ui;

import Model.GameModel;
import Model.UserModel;
import Response.*;
import chess.ChessGameImplmentation;
import chess.ChessPositionImplmentation;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.MakeMove;

import java.io.IOException;
import java.util.Arrays;

public class ChessClient implements NotificationHandler {
    public ServerFacade server;
    public WebSocketClient webSocket;
    NotificationHandler handler;

    public ChessClient() throws Exception{
        server = new ServerFacade("http://localhost:8080");
        webSocket = new WebSocketClient("ws://localhost:8080/connect", this);
    }
    Boolean logged_in = false;
    UserModel user;
    ChessBoardCringe chessBoardCringe = new ChessBoardCringe();
    String color;


    ChessGameImplmentation game;

    public String userAuth;
    public String username;
    public String gameID;


    public String command(String commandLine) throws Exception {
        String[] length = commandLine.split(" ");
        if (length.length ==0){
            return " ";
        }
        if (!logged_in){
        if (commandLine.toLowerCase().contains("help")){
            return "Help: Lists possible commands you can execute \n" +
                    "Quit: Exits the program \n" +
                    "Register: <Username> , <Password>, and <Email> \n" +
                    "Login: <Username> and <Password> \n"
                    ;
        }
        else if (commandLine.toLowerCase().contains("quit")){
            return "Exiting the Program";
        }
        else if (commandLine.toLowerCase().contains("clear")){
            clear();
        }
        else if (commandLine.toLowerCase().contains("register")){
            String temp = register(length);
            System.out.println(temp);
        }
        else if (commandLine.toLowerCase().contains("login")){
            String temp = login(length);
            System.out.println(temp);
        }
    }else {
            if (commandLine.toLowerCase().contains("help")){
                return  "Help: Lists possible commands you can execute \n" +
                        "Logout: Returns you to Main Menu \n" +
                        "Create: <GameName>\n"+
                        "List: Shows you a list of all the games\n"+
                        "Join: <GameID>, <Team-Color>\n"+
                        "Join: <GameID>\n"
                        ;
            }
            else if (commandLine.toLowerCase().contains("logout")){
                String temp = logOut();
                System.out.println(temp);
            }
            else if (commandLine.toLowerCase().contains("create")){
                String temp = create(length);
                System.out.println(temp);
            }
            else if (commandLine.toLowerCase().contains("join")){
                String temp = join(length);
                System.out.println(temp);
            }
            else if (commandLine.toLowerCase().contains("list")){
                String temp = listGames();
                System.out.println(temp);
            }
            else if (commandLine.toLowerCase().contains("move")){
                String temp = makeMove(Arrays.toString(length));
                System.out.println(temp);
            }


        }
        return "";
    }


    public void clear() throws IOException {
        server.clear();
    }
    public String register(String[] args) throws IOException{
        if (args.length != 4){
            return "Whoops try that again";
        }
        RegisterResponse response = server.register(args[1],args[2],args[3]);
        if(response.getCode() ==200){
            logged_in =true;
            return ("New User Registered!!\n" +
                    "Your AuthToken is "+ response.getAuthToken());
        }
        else {
        return "Failed User Registration";}
    }
    public String login(String[] args) throws IOException{
        if(args.length != 3){
            return "Login take 3 paramters";
        }
        LoginResponse response = server.login(args[1], args[2]);
        if (response.getCode() ==200){
            userAuth = response.getAuthtoken();
            username = args[1];
            logged_in =true;
            return (response.getUsername() + " you have logged in!!");
        }
        return "LOL your login failed";
    }

    public String logOut() throws IOException{
        LogoutResponse response = server.logout();

        if(response.getCode() == 200){
            logged_in = false;
            return ("Congrats you are now not logged in");
        }
        return "Failed Logging Out";
    }
    public String create(String[] args) throws IOException{
        if (args.length !=2){
            return "Create takes 1 argument";
        }
        CreateGameResponse response = server.createGame(args[1]);
        if(response.getCode() ==200){
            return ("Congrats you created a game  "+ response.getGameID());
        }
        return "Failed Creating Game";
    }
    public String join(String[] args) throws Exception {
        if (args.length ==2 ){
            JoinGameResponse response = server.joinGame(args[1]);
            if(response.getCode() ==200){
                //add the ws command
                return ("Congrats you joined  "+ args[1]);
            }
        } else if (args.length ==3) {
            JoinGameResponse response = server.joinGamePlayer(args[1], args[2]);
            if (response.getCode() == 200) {
                gameID = args[1];
                JoinPlayer command = new JoinPlayer(args[1],args[2],username, userAuth);
                webSocket.send(command);
                if (args[2].toLowerCase().equals("white")){
                    color = "white";
                }
                if (args[2].toLowerCase().equals("black")){
                    color = "black";
                }
                return ("\n Congrats you joined  " + args[1] +" as "+ args[2]);
            }
        }
        return "Error Joining Game";
    }
    public String listGames() throws IOException{
            ListGamesResponse response = server.list();
            if (response.getCode() == 200){
                if (response.getGames().size() !=0){
                    StringBuilder output = new StringBuilder();
                    output.append("Game List: \n\n");
                    for(GameModel s : response.getGames()){
                        output.append("GameID: " + s.getGameID() +"\n");
                        output.append("Game Name: " + s.getGameName() +"\n");
                        output.append("Black Username: " +s.getBlackUser()+"\n");
                        output.append("White Username: " +s.getWhiteUsername()+"\n");
                        output.append("\n");
                    }
                    return output.toString();
                }
                return "There are no current games";
            }else {
                return "Error Listing Games";
            }

    }
    public String makeMove(String move) throws Exception {
        ChessPositionImplmentation start = postionFromChar(move.substring(7,9));
        ChessPositionImplmentation end = postionFromChar(move.substring(9,11));
        //ChessMoveImplmentation moveImplmentation = new ChessMoveImplmentation(start,end);
        MakeMove command = new MakeMove(gameID,userAuth,username,start,end);
        webSocket.send(command);
        //chessBoardCringe.updateUIBoard(moveImplmentation);
        //chessBoardCringe.white();
        return  null;
    }
    public ChessGameImplmentation updategame(){
        return null;
    }


    ChessPositionImplmentation postionFromChar(String moves){
        int row = (moves.charAt(0) -'a') + 1;
        int colum =Integer.parseInt(String.valueOf(moves.charAt(1)));
        return new ChessPositionImplmentation(colum ,row);
    }


    @Override
    public void updateBoard(ChessGameImplmentation gameImplmentation) {
        this.game = gameImplmentation;
        chessBoardCringe.updateUIBoard(gameImplmentation);
        if (color.equals("white")){
            ChessBoardCringe.white();
        }else {
            ChessBoardCringe.black();
        }


    }

    @Override
    public void message(String message) {
        if (message != null){
        System.out.println(message);
        }
    }

    @Override
    public void error(String error) {

    }
}

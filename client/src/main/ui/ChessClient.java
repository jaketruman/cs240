package ui;

import Model.GameModel;
import Model.UserModel;
import Response.*;
import chess.ChessGame.TeamColor;
import chess.ChessGameImplmentation;
import chess.ChessMove;
import chess.ChessMoveImplmentation;
import chess.ChessPositionImplmentation;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * This and the WSHANDLER are the most painful classes ever
 *
 * This implements the NotificationHandler class, basically means that when the WebSocketClient receives any
 * message from the server side it will call whatever type of message it is and this class will then execute it.
 * There is some more documentation in the WebSocketClient class about this process.
 *
 * This class is the UI that handles all commandline arguments in a terminal window and allows the user to actually play the game,
 * Not the hardest code but when you have to implement the websockets it sucks.
 *
 * The last lines have the implementation of the Notification Handler, which are essential, they are called when a user makes a move
 * and it will print the updated board and the notification that someone has moved.
 */

public class ChessClient implements NotificationHandler {
    public ServerFacade server;
    public WebSocketClient webSocket;
    NotificationHandler handler;

    public ChessClient() throws Exception{
        server = new ServerFacade("http://localhost:8080");
        webSocket = new WebSocketClient("ws://localhost:8080/connect", this);
    }
    Boolean logged_in = false;
    Boolean in_game = false;
    UserModel user;
    ChessBoardCringe chessBoardCringe = new ChessBoardCringe();
    public String color;


    ChessGameImplmentation game;
    TeamColor teamColor = null;

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
    }else if (logged_in && !in_game){
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
                //in_game = true;
                System.out.println(temp);
            }
            else if (commandLine.toLowerCase().contains("list")){
                String temp = listGames();
                System.out.println(temp);
            }


        }else if (in_game) {
            if (commandLine.toLowerCase().contains("help")){
                return  "Help: Lists possible commands you can execute \n" +
                        "Move: Make a valid move \n" +
                        "High: Highlight valid moves\n"+
                        "Resign: Resign from the game\n"+
                        "Leave: Leave the game\n";
            }
             else if (commandLine.toLowerCase().contains("move")){
                String temp = makeMove(Arrays.toString(length));
                System.out.println(temp);
            } else if (commandLine.toLowerCase().contains("high")) {
                String temp = highlightMoves(color, Arrays.toString(length));
                System.out.println(temp);
            }else if (commandLine.toLowerCase().contains("leave")){
                 leavegame();
                 in_game =false;
            }
             else if (commandLine.toLowerCase().contains("resign")){
                resign();
            }
            else if (commandLine.toLowerCase().contains("redraw")){
                redraw();
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
            userAuth = response.getAuthToken();
            username = args[1];
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
                JoinObserver observer = new JoinObserver(UserGameCommand.CommandType.JOIN_OBSERVER,userAuth,args[1],username);
                webSocket.send(observer);
                return ("Congrats you joined  "+ args[1]);
            }
        } else if (args.length ==3) {
            JoinGameResponse response = server.joinGamePlayer(args[1], args[2]);
            if (response.getCode() == 200) {
                in_game=true;
                gameID = args[1];
                if (args[2].equalsIgnoreCase("white")){
                    color = "white";
                    teamColor = TeamColor.WHITE;
                }
                if (args[2].equalsIgnoreCase("black")){
                    color = "black";
                    teamColor = TeamColor.BLACK;
                }
                JoinPlayer command = new JoinPlayer(args[1],teamColor,username, userAuth);
                webSocket.send(command);
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
    public String highlightMoves(String color, String move){
        if (move.length()!= 10){
            return "That isnt the correct format";
        }
        ChessPositionImplmentation start = postionFromChar(move.substring(7,9));
        Collection<ChessMove> moves =  game.validMoves(start);
        if (color == "white"){
            ChessBoardCringe.Highwhite(moves);

        }
        if (color == "black"){
            ChessBoardCringe.Highblack(moves);
        }
        return null;
    }
    public void leavegame() throws Exception {
        Leave command = new Leave(UserGameCommand.CommandType.LEAVE,userAuth,gameID,username);
        webSocket.send(command);
    }
    public void resign() throws Exception {
        Resign command = new Resign(UserGameCommand.CommandType.RESIGN,userAuth,gameID,username);
        webSocket.send(command);
    }
    public String makeMove(String move) throws Exception {
        if (move.length()!= 12){
            return "That isnt the correct format";
        }
        ChessPositionImplmentation start = postionFromChar(move.substring(7,9));
        ChessPositionImplmentation end = postionFromChar(move.substring(9,11));
        ChessMoveImplmentation moveImplmentation = new ChessMoveImplmentation(start,end);
        if (game.validMoves(start)==null){
            return "You are in checkmate pls leave the game nerd";
        }
        if (!game.validMoves(start).contains(moveImplmentation)){
            return "That move is not valid";
        }
        game.makeMove(moveImplmentation);
        MakeMove command = new MakeMove(gameID,userAuth,username,moveImplmentation);
        webSocket.send(command);
        return  null;
    }
    public void redraw(){
        if (color != null){
            if (color.equals("white")){
                ChessBoardCringe.white();
            }
            if (Objects.equals(color, "black")){
                ChessBoardCringe.black();
            }
        }
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
        if (color ==null){
            ChessBoardCringe.white();
        }else if (color.equals("black")) {
            ChessBoardCringe.black();
        }else{
            ChessBoardCringe.white();
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
//        if (error != null){
//            System.out.println(error);
//        }
    }
}

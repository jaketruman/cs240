package ui;

import chess.ChessBoardImplmentation;
import chess.ChessGame;
import chess.ChessGameImplmentation;
import chess.ChessPositionImplmentation;
import chess.pieces.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * Dealing with websocket Client side is not bad, you just establish a connection with the server side and it stays open
 * until it needs to be closed, and whenever a message comes through it gets handled.
 *
 * Best way to think about websockets in my opinion, The client side can send messages to the server and whatever
 * messges can be sent to the server need to be handled there. Whatever messages can be sent to the client from the server need
 * to be handled here.
 *
 * There is only 3 messages the client can get, Load (just print the board out), Notification(print the message attached to the
 * notification), and Error(print the error)
 *
 * How it works:
 * A player types a command, say Make Move, the Chess Client sends the move to the server,WSHANDLER,
 * in WSHANDLER the move is made on the board, it then sends the updated board to WebsocketClient, WebsocketClient sees
 * the message as a Load Message type. It then calls a Notification Handler function and that function takes a chess game,
 * notificationHandler is an interface that is implemented in Chess Client, so the game is sent to the Chess Client where it is
 * printed out, and now Chess Client is waiting for the next player to type a command.
 *
 * It basically is a giant circle, until a connection is closed
 */

public class WebSocketClient extends Endpoint {


    Session session;
    NotificationHandler notificationHandler;

    public WebSocketClient(String url, NotificationHandler handler) throws Exception {
        this.notificationHandler = handler;
        URI uri = new URI(url);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this , uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                try{
                ServerMessage serverTalk = new Gson().fromJson(message, ServerMessage.class);
                switch (serverTalk.getServerMessageType()) {
                    case LOAD_GAME:
                        notificationHandler.updateBoard(json2game(message));
                    case NOTIFICATION:
                        notificationHandler.message(new Gson().fromJson(message, NotificationMessage.class).message);
                    case ERROR:
                        notificationHandler.error(new Gson().fromJson(message, ErrorMessage.class).toString());
                }
            }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void send(UserGameCommand msg) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(msg));
    }
    @Override
    public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {

    }

    public ChessGameImplmentation json2game(String jsongame) throws JsonProcessingException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessGameImplmentation.class, new GameAdapt());
        Gson gson = builder.create();
        return gson.fromJson(jsongame,ChessGameImplmentation.class);

    }

    public static class GameAdapt extends TypeAdapter<ChessGameImplmentation> {

        @Override
        public void write(JsonWriter jsonWriter, ChessGameImplmentation gameImplmentation) throws IOException {

        }

        @Override
        public ChessGameImplmentation read(JsonReader jsonReader) throws IOException {
            ChessGameImplmentation model = new ChessGameImplmentation();
            ChessBoardImplmentation boardImplmentation = new ChessBoardImplmentation();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String temp = jsonReader.nextName();
                if (temp.equals("game")) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String temp2 = jsonReader.nextName();
                        switch (temp2) {
                            default:
                                jsonReader.skipValue();
                                break;
                            case "teamTurn":
                                JsonToken teamTurn = jsonReader.peek();
                                if (teamTurn == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else {
                                    model.setTeamTurn(ChessGame.TeamColor.valueOf(jsonReader.nextString()));
                                }
                                break;
                            case "whiteUsername":
                                JsonToken whiteUsernameToken = jsonReader.peek();
                                if (whiteUsernameToken == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else {
                                    model.setWhiteUsername(jsonReader.nextString());
                                }
                                break;
                            case "blackUsername":
                                JsonToken blackusernameToken = jsonReader.peek();
                                if (blackusernameToken == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else {
                                    model.setBlackUsername(jsonReader.nextString());
                                }
                                break;
                            case "turnCount":
                                JsonToken turnCount = jsonReader.peek();
                                if (turnCount == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else {
                                    model.setTurnCount(Integer.parseInt(jsonReader.nextString()));
                                }
                                break;
                            case "board":
                                jsonReader.beginObject();
                                jsonReader.nextName();
                                jsonReader.beginArray();
                                int row = 0;
                                while (jsonReader.hasNext()) {
                                    jsonReader.beginArray();
                                    int col = 0;
                                    while (jsonReader.hasNext()) {
                                        ChessPositionImplmentation place = new ChessPositionImplmentation(col, row);
                                        JsonToken token = jsonReader.peek();
                                        if (token == JsonToken.NULL) {
                                            jsonReader.nextNull();
                                            boardImplmentation.addPiece(place, null);
                                        } else {
                                            jsonReader.beginObject();
                                            String poop = jsonReader.nextName();
                                            if (poop.equals("pieceType")) {
                                                String piece = jsonReader.nextString();
                                                jsonReader.nextName();
                                                String color = jsonReader.nextString();
                                                ChessGame.TeamColor teamColor = (color.equals("WHITE")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
                                                switch (piece) {
                                                    case "ROOK":
                                                        RookImplmentation Rookimplmentation = new RookImplmentation(teamColor);
                                                        boardImplmentation.addPiece(place, Rookimplmentation);
                                                        jsonReader.endObject();
                                                        break;
                                                    case "PAWN":
                                                        PawnImplmentation implmentation = new PawnImplmentation(teamColor);
                                                        boardImplmentation.addPiece(place, implmentation);
                                                        jsonReader.endObject();
                                                        break;
                                                    case "KNIGHT":
                                                        KnightImplmentation knightImplmentation = new KnightImplmentation(teamColor);
                                                        boardImplmentation.addPiece(place, knightImplmentation);
                                                        jsonReader.endObject();
                                                        break;
                                                    case "QUEEN":
                                                        QueenImplmentation queenImplmentation = new QueenImplmentation(teamColor);
                                                        boardImplmentation.addPiece(place, queenImplmentation);
                                                        jsonReader.endObject();
                                                        break;
                                                    case "KING":
                                                        KingImplmentation kingImplmentation = new KingImplmentation(teamColor);
                                                        boardImplmentation.addPiece(place, kingImplmentation);
                                                        jsonReader.endObject();
                                                        break;
                                                    case "BISHOP":
                                                        BishopImplmentation bishopImplmentation = new BishopImplmentation(teamColor);
                                                        boardImplmentation.addPiece(place, bishopImplmentation);
                                                        jsonReader.endObject();
                                                        break;
                                                }
                                            }
                                        }

                                        col++;

                                    }

                                    jsonReader.endArray();
                                    row++;
                                }

                                jsonReader.endArray();
                                jsonReader.endObject();
                                model.setGameBoard(boardImplmentation);
                        }
                    }
                    jsonReader.endObject();

            }
                else if (temp.equals("serverMessageType")) {
                    jsonReader.skipValue();}
            }

            jsonReader.endObject();

            return model;
        }

    }



}

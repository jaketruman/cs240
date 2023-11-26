package dataAccess;

import Model.GameModel;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
public class GameDAO {
    private final Connection connection;

    public GameDAO(Connection connection) {
        this.connection = connection;
    }

    public void clear() throws DataAccessException {
        String sql = "DELETE from game";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error while clearing the Game table");
        }
    }

    public void joinGame(String color, String gameID, String username) {
        String team;
        if (color.equals("BLACK")) {
            team = "blackUsername";
        } else {
            team = "whiteUsername";
        }
        String sql = "UPDATE game SET " + team + " = ? WHERE gameID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, gameID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(GameModel game, String gameID) throws DataAccessException {
        String gameString = game2json(game.getGameImplmentation());
        System.out.println(gameString);
        String sql = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, game) VALUES(?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, game.getGameID());
            stmt.setString(2, game.getWhiteUsername());
            stmt.setString(3, game.getBlackUser());
            stmt.setString(4, game.getGameName());
            stmt.setString(5, gameString);
            stmt.executeUpdate();
            System.out.println(game.getGameID());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user to the database");
        }
    }

    public GameModel find(String gameID) {
        GameModel gameModel;
        ResultSet result;
        String sql = "SELECT * FROM game WHERE gameID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, gameID);
            result = stmt.executeQuery();
            if (result.next()) {
                gameModel = new GameModel(result.getInt("gameID"), result.getString("whiteUsername"),
                        result.getString("blackUsername"), result.getString("gameName"),
                        json2game(result.getString("game")));
                return gameModel;
            } else {
                return null;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Finished nul");
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public int findSize() {
        ResultSet result;
        String sql = "SELECT COUNT(*) AS row_count FROM game;";
        int count = 0;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            result = stmt.executeQuery();
            if (result.next()) {
                count = result.getInt("row_count");
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public HashSet<GameModel> findall() throws DataAccessException {
        GameModel model;
        ResultSet result;
        HashSet<GameModel> array = new HashSet<GameModel>();
        String sql = "SELECT * FROM game;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            result = stmt.executeQuery();
            //while
            while (result.next()) {
                if (result.getString("game") != null) {
                    model = new GameModel(result.getInt("gameID"), result.getString("whiteUsername"),
                            result.getString("blackUsername"), result.getString("gameName"),
                            json2game(result.getString("game"))
                    );
                    array.add(model);
                } else {
                    model = new GameModel(result.getInt("gameID"), result.getString("whiteUsername"),
                            result.getString("blackUsername"), result.getString("gameName")
                    );
                    array.add(model);
                }
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
        System.out.println("Print array " + array);
        return array;
    }

    public String game2json(ChessGameImplmentation game) {
       return new Gson().toJson(game);
    }

    public ChessGameImplmentation json2game(String jsongame) throws JsonProcessingException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessGameImplmentation.class, new GameAdapt());
        Gson gson = builder.create();
        return gson.fromJson(jsongame,ChessGameImplmentation.class);



    }

        public static class GameAdapt extends TypeAdapter<ChessGameImplmentation>{

            @Override
            public void write(JsonWriter jsonWriter, ChessGameImplmentation gameImplmentation) throws IOException {

            }

            @Override
            public ChessGameImplmentation read(JsonReader jsonReader) throws IOException, IOException {
                ChessGameImplmentation model = new ChessGameImplmentation();
                ChessBoardImplmentation boardImplmentation = new ChessBoardImplmentation();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String temp = jsonReader.nextName();
                    switch (temp) {
                        case "teamTurn":
                            JsonToken teamTurn = jsonReader.peek();
                            if (teamTurn == JsonToken.NULL) {
                                jsonReader.nextNull();  // Consume the null value
                                // Handle the case when whiteUsername is null, e.g., set a default value or do nothing
                            } else {
                                model.setTeamTurn(ChessGame.TeamColor.valueOf(jsonReader.nextString()));
                            }                            break;
                        case "whiteUsername":
                            JsonToken whiteUsernameToken = jsonReader.peek();
                            if (whiteUsernameToken == JsonToken.NULL) {
                                jsonReader.nextNull();  // Consume the null value
                                // Handle the case when whiteUsername is null, e.g., set a default value or do nothing
                            } else {
                                model.setWhiteUsername(jsonReader.nextString());
                            }
                            break;
                        case "blackUsername":
                            JsonToken blackusernameToken = jsonReader.peek();
                            if (blackusernameToken == JsonToken.NULL) {
                                jsonReader.nextNull();  // Consume the null value
                                // Handle the case when whiteUsername is null, e.g., set a default value or do nothing
                            } else {
                                model.setBlackUsername(jsonReader.nextString());
                            }
                            break;
                        case "turnCount":
                            JsonToken turnCount = jsonReader.peek();
                            if (turnCount == JsonToken.NULL) {
                                jsonReader.nextNull();  // Consume the null value
                                // Handle the case when whiteUsername is null, e.g., set a default value or do nothing
                            } else {
                                model.setTurnCount(Integer.parseInt(jsonReader.nextString()));
                            }
                            break;
                        case "board":
                            jsonReader.beginObject();  // Start reading the outer object
                            jsonReader.nextName();  // Move to the inner "board" field
                            jsonReader.beginArray();                            //jsonReader.nextString();
                            int row = 0;
                            while (jsonReader.hasNext()) {
                                jsonReader.beginArray();
                                int col = 0;
                                while (jsonReader.hasNext()) {
                                    ChessPositionImplmentation place = new ChessPositionImplmentation(col, row);
                                    JsonToken token = jsonReader.peek();
                                    if (token == JsonToken.NULL) {
                                        jsonReader.nextNull();  // Consume the null value
                                        boardImplmentation.addPiece(place, null);
                                    } else {
                                        jsonReader.beginObject();
                                        String poop = jsonReader.nextName();
                                        switch (poop) {
                                            case "pieceType":
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
                return model;
            }

        }
    }

//        }
//    private ChessBoardImplmentation readChessBoard(JsonReader jsonReader) throws IOException {
//        ChessBoardImplmentation chessBoard = new ChessBoardImplmentation();
//        jsonReader.beginObject();
//
//        while (jsonReader.hasNext()) {
//            String name = jsonReader.nextName();
//            switch (name) {
//                case "board":
//                    chessBoard.setBoard(readBoard(jsonReader));
//                    break;
//                default:
//                    jsonReader.skipValue(); // skip values of unknown keys
//                    break;
//            }
//        }
//
//        jsonReader.endObject();
//        return chessBoard;
//    }
//
//    private Piece[][] readBoard(JsonReader jsonReader) throws IOException {
//        jsonReader.beginArray();
//
//        // Assuming the board is a 2D array of Piece objects
//        Piece[][] board = new Piece[9][9];
//
//        int row = 0;
//        while (jsonReader.hasNext()) {
//            jsonReader.beginArray();
//
//            int col = 0;
//            while (jsonReader.hasNext()) {
//                board[row][col] = readPiece(jsonReader);
//                col++;
//            }
//
//            jsonReader.endArray();
//            row++;
//        }
//
//        jsonReader.endArray();
//        return board;
//    }
//
//    private ChessPiece readPiece(JsonReader jsonReader) throws IOException {
//        jsonReader.beginObject();
//
//        while (jsonReader.hasNext()) {
//            String name = jsonReader.nextName();
//
//            switch (name) {
//                case "teamColor":
//                    piece.setTeamColor(jsonReader.nextString());
//                    break;
//                case "pieceType":
//                    piece.setPieceType(jsonReader.nextString());
//                    break;
//                default:
//                    jsonReader.skipValue(); // skip values of unknown keys
//                    break;
//            }
//        }
//
//        jsonReader.endObject();
//        return piece;
//    }







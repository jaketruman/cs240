package ui;

import chess.ChessBoardImplmentation;
import chess.ChessGame;
import chess.ChessGameImplmentation;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoardCringe {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 1;


    private static Random rand = new Random();
    public static String[][] board = {
            {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING, WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK},
            {WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN},
            {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING, BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK}
    };


//    public static void main(String[] args) {
//        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//        if (args.toString() == "BLACK"){
//            black(out);
//        }
//        else {
//            white(out);
//        }
//        white(out);
//        System.out.println();
//        System.out.println();
//        black(out);
//    }
//
//

    public static void black() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("     ");
        for (int i = 0; i < 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + "  ");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(" ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean isBlack = (i + j) % 2 == 1;
                if (j == 0) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(1+i);
                    System.out.print(" ");
                }
                if (isBlack) {
                    System.out.print(SET_BG_COLOR_MAGENTA);
                    if (board[i][j] != EMPTY) {
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[i][j]);
                    } else {
                        System.out.print(EMPTY);
                    }
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if (board[i][j] != EMPTY) {
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[i][j]);
                    } else {
                        System.out.print(EMPTY);
                    }
                }
                System.out.print(SET_TEXT_COLOR_WHITE);
                if (j == 7) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(1+i);
                    System.out.print(" ");
                }

            }
            System.out.print(SET_BG_COLOR_DARK_GREY);
            System.out.println();
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for (int i = 0; i < 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + "  ");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
    }

    public static void white() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for (int i = 0; i < 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + "  ");
        }
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean isBlack = (i + j) % 2 == 1;
                if (j == 0) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(8-i);
                    System.out.print(" ");
                }
                if (isBlack) {
                    System.out.print(SET_BG_COLOR_MAGENTA);
                    if (board[7 - i][7 - j] != EMPTY) {
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[7 - i][7 - j]);
                    } else {
                        System.out.print(EMPTY);
                    }
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if (board[7 - i][7 - j] != EMPTY) {
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[7 - i][7 - j]);
                    } else {
                        System.out.print(EMPTY);
                    }
                }
                System.out.print(SET_TEXT_COLOR_WHITE);
                if (j == 7) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(8-i);
                    System.out.print(" ");
                }

            }
            System.out.print(SET_BG_COLOR_DARK_GREY);
            System.out.println();
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");

        for (int i = 0; i < 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print( x+ "  " );
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
    }
    public void updateUIBoard(ChessGameImplmentation chessGameImplmentation){
//        board[moveImplmentation.getEndPosition().getRow()-1][moveImplmentation.getEndPosition().getColumn()-1] = board[moveImplmentation.getStartPosition().getRow()-1][moveImplmentation.getStartPosition().getColumn()-1];
//        board[moveImplmentation.getStartPosition().getRow()-1][moveImplmentation.getStartPosition().getColumn()-1] = EMPTY;
//        moveImplmentation.getStartPosition();
        ChessBoardImplmentation boardImplmentation = (ChessBoardImplmentation) chessGameImplmentation.getBoard();
        for(int i =1; i<9;i++){
            for(int j =1; j<9; j++){

                if (boardImplmentation.getBoard()[i][j] == null){
                    board[j-1][i-1] = EMPTY;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.PAWN && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_PAWN;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.KNIGHT && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_KNIGHT;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.ROOK && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_ROOK;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.KING && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_KING;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.QUEEN && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_QUEEN;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.BISHOP && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_BISHOP;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.PAWN && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_PAWN;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.KNIGHT && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_KNIGHT;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.ROOK && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_ROOK;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.KING && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_KING;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.QUEEN && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_QUEEN;
                }
                else if (boardImplmentation.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.BISHOP && boardImplmentation.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_BISHOP;
                }

            }
        }
    }

}



package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoardCringe {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 1;


    private static final Random rand = new Random();
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



    public static void black() {
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
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
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
            System.out.print(SET_BG_COLOR_BLACK);
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
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.println();
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
                    if (board[7 - i][j] != EMPTY) {
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[7 - i][ j]);
                    } else {
                        System.out.print(EMPTY);
                    }
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if (board[7 - i][j] != EMPTY) {
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[7 - i][j]);
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
            System.out.print(SET_BG_COLOR_BLACK);
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
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.println();
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
    public static void Highwhite(Collection<ChessMove> moves) {
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
                    //System.out.print(SET_BG_COLOR_MAGENTA);
                    if (board[7 - i][j] != EMPTY) {
                        String background = SET_BG_COLOR_MAGENTA;
                        for (ChessMove x :moves){
                            if (8-x.getEndPosition().getRow() == i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (8-x.getStartPosition().getRow() ==i && x.getStartPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(board[7 - i][ j]);
                    } else {
                        String background = SET_BG_COLOR_MAGENTA;
                        for (ChessMove x :moves){
                            if (8-x.getEndPosition().getRow() ==i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }

                        }
                        System.out.print(background);
                        //System.out.print(EMPTY);
                        System.out.print(EMPTY);
                    }
                } else {
                    //System.out.print(SET_BG_COLOR_DARK_GREY);
                    if (board[7 - i][j] != EMPTY) {
                        String background = SET_BG_COLOR_DARK_GREY;
                        for (ChessMove x :moves){
                            if (8-x.getEndPosition().getRow() == i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (8-x.getStartPosition().getRow() == i && x.getStartPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[7 - i][j]);
                    } else {
                        String background = SET_BG_COLOR_DARK_GREY;
                        for (ChessMove x :moves){
                            if (8-x.getEndPosition().getRow()== i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }

                        }
                        System.out.print(background);
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
        System.out.println();
    }

    public static void Highblack(Collection<ChessMove> moves) {
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
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
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
                        String background = SET_BG_COLOR_MAGENTA;
                        for (ChessMove x :moves){
                            if (x.getEndPosition().getRow()-1 == i && x.getEndPosition().getColumn() -1==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (x.getStartPosition().getRow()-1 == i && x.getStartPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[i][j]);
                    } else {
                        String background = SET_BG_COLOR_MAGENTA;
                        for (ChessMove x :moves){
                            if (x.getEndPosition().getRow()-1 == i && x.getEndPosition().getColumn()-1==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (x.getStartPosition().getRow()-1 == i && x.getStartPosition().getColumn()-1==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(EMPTY);
                    }
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if (board[i][j] != EMPTY) {
                        String background = SET_BG_COLOR_DARK_GREY;
                        for (ChessMove x :moves){
                            if (x.getEndPosition().getRow()-1  == i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (x.getStartPosition().getRow()-1 == i && x.getStartPosition().getColumn()-1==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[i][j]);
                    } else {
                        String background = SET_BG_COLOR_DARK_GREY;
                        for (ChessMove x :moves){
                            if (x.getEndPosition().getRow()-1 == i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (x.getStartPosition().getRow()-1 == i && x.getStartPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
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
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.println();
    }
}



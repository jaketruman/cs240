package chess;

import chess.pieces.*;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ChessBoardImplmentation implements ChessBoard {
    @JsonProperty("board")
    private final ChessPiece[][] board = new ChessPiece[9][9];
    public ChessPiece[][] getBoard(){
        return board;
    }



    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getColumn()][position.getRow()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        if (board[position.getColumn()][position.getRow()] == null){
            return null;
        }
        ChessPiece value = board[position.getColumn()][position.getRow()];
        return value;
    }


    @Override
    public void resetBoard() {
        ///Clear Board
        clearBoard();

        //Create and Set Pieces
        PawnImplmentation whitePawn = new PawnImplmentation(ChessGame.TeamColor.WHITE);
        PawnImplmentation blackPawn = new PawnImplmentation(ChessGame.TeamColor.BLACK);
        for(int i = 1; i<=8; i++){
            board[i][2] = whitePawn;
            board[i][7] = blackPawn;
        }
        KnightImplmentation blackKnight = new KnightImplmentation(ChessGame.TeamColor.BLACK);
        KnightImplmentation whiteKnight = new KnightImplmentation(ChessGame.TeamColor.WHITE);
        BishopImplmentation whiteBishop = new BishopImplmentation(ChessGame.TeamColor.WHITE);
        BishopImplmentation blackBishop = new BishopImplmentation(ChessGame.TeamColor.BLACK);
        RookImplmentation whiteRook = new RookImplmentation(ChessGame.TeamColor.WHITE);
        RookImplmentation blackRook = new RookImplmentation(ChessGame.TeamColor.BLACK);
        QueenImplmentation whiteQueen = new QueenImplmentation(ChessGame.TeamColor.WHITE);
        QueenImplmentation blackQueen = new QueenImplmentation(ChessGame.TeamColor.BLACK);
        KingImplmentation whiteKing = new KingImplmentation(ChessGame.TeamColor.WHITE);
        KingImplmentation blackKing = new KingImplmentation(ChessGame.TeamColor.BLACK);

        board[1][1] =whiteRook;
        board[8][1] =whiteRook;
        board[1][8] =blackRook;
        board[8][8] =blackRook;

        board[2][1] =whiteKnight;
        board[7][1] =whiteKnight;
        board[2][8] =blackKnight;
        board[7][8] =blackKnight;

        board[3][1] =whiteBishop;
        board[6][1] =whiteBishop;
        board[3][8] =blackBishop;
        board[6][8] =blackBishop;

        board[4][1] =whiteQueen;
        board[5][1] =whiteKing;

        board[4][8] =blackQueen;
        board[5][8] =blackKing;

    }
    public void removePiece(ChessPosition position){
        board[position.getColumn()][position.getRow()] =null;
    }
    public void clearBoard(){
        for (int i =1 ; i<8;i++){
            for (int j = 1; j<8;j++){
                board[j][i] = null;
            }
        }
    }
}

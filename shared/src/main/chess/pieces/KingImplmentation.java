package chess.pieces;

import chess.*;
import chess.ChessMoveImplmentation;
import chess.ChessPositionImplmentation;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class KingImplmentation implements ChessPiece {
    @JsonProperty("pieceType")
    private PieceType pieceType = PieceType.KING;
    @JsonProperty("teamColor")
    private ChessGame.TeamColor teamColor;

    public KingImplmentation(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int column = myPosition.getColumn();
        int row = myPosition.getRow();

        Collection<ChessMove> possibleMoves = new HashSet<>();
        if (column+1<9){
            ChessPositionImplmentation left1 = new ChessPositionImplmentation(row,column+1);
            ChessMoveImplmentation leftMove1 = new ChessMoveImplmentation(myPosition, left1);
            if (board.getPiece(left1) != null) {
                if(board.getPiece(left1).getTeamColor() != teamColor){
                possibleMoves.add(leftMove1);
                }
            } else {
            possibleMoves.add(leftMove1);
        }}
        if (column+1<9 && row+1<9){
            ChessPositionImplmentation left2 = new ChessPositionImplmentation(row+1,column+1);
            ChessMoveImplmentation leftMove2 = new ChessMoveImplmentation(myPosition, left2);
            if (board.getPiece(left2) != null) {
                if(board.getPiece(left2).getTeamColor() != teamColor){
                    possibleMoves.add(leftMove2);
                }
            } else {
                possibleMoves.add(leftMove2);
            }
        }if (column+1<9 && row-1>0){
            ChessPositionImplmentation left3 = new ChessPositionImplmentation(row-1,column+1);
            ChessMoveImplmentation leftMove3 = new ChessMoveImplmentation(myPosition, left3);
            if (board.getPiece(left3) != null) {
                if(board.getPiece(left3).getTeamColor() != teamColor){
                    possibleMoves.add(leftMove3);
                }
            } else {
                possibleMoves.add(leftMove3);
            }
        }

        if (column-1>0 ){
            ChessPositionImplmentation left4 = new ChessPositionImplmentation(row,column-1);
            ChessMoveImplmentation leftMove4 = new ChessMoveImplmentation(myPosition, left4);
            if (board.getPiece(left4) != null) {
                if(board.getPiece(left4).getTeamColor() != teamColor){
                    possibleMoves.add(leftMove4);
                }
            } else {
                possibleMoves.add(leftMove4);
            }
        }
        if (column-1>0 && row+1<9){
            ChessPositionImplmentation left5 = new ChessPositionImplmentation(row+1,column-1);
            ChessMoveImplmentation leftMove5 = new ChessMoveImplmentation(myPosition, left5);
            if (board.getPiece(left5) != null) {
                if(board.getPiece(left5).getTeamColor() != teamColor){
                    possibleMoves.add(leftMove5);
                }
            } else {
                possibleMoves.add(leftMove5);
            }
        }
        if (column-1>0 && row-1>0){
            ChessPositionImplmentation left6 = new ChessPositionImplmentation(row-1,column-1);
            ChessMoveImplmentation leftMove6 = new ChessMoveImplmentation(myPosition, left6);
            if (board.getPiece(left6) != null) {
                if(board.getPiece(left6).getTeamColor() != teamColor){
                    possibleMoves.add(leftMove6);
                }
            } else {
                possibleMoves.add(leftMove6);
            }
        }

        if (row+1<9){
            ChessPositionImplmentation left7 = new ChessPositionImplmentation(row+1,column);
            ChessMoveImplmentation leftMove7 = new ChessMoveImplmentation(myPosition, left7);
            if (board.getPiece(left7) != null) {
                if(board.getPiece(left7).getTeamColor() != teamColor){
                    possibleMoves.add(leftMove7);
                }
            } else {
                possibleMoves.add(leftMove7);
            }
        }
        if (row-1>0){
            ChessPositionImplmentation left8 = new ChessPositionImplmentation(row-1,column);
            ChessMoveImplmentation leftMove8 = new ChessMoveImplmentation(myPosition, left8);
            if (board.getPiece(left8) != null) {
                if(board.getPiece(left8).getTeamColor() != teamColor){
                    possibleMoves.add(leftMove8);
                }
            } else {
                possibleMoves.add(leftMove8);
            }
        }
        return possibleMoves;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KingImplmentation that = (KingImplmentation) o;
        return teamColor == that.teamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor);
    }
}

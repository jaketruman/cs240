package chess.pieces;

import chess.*;
import chess.ChessMoveImplmentation;
import chess.ChessPositionImplmentation;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashSet;

public class BishopImplmentation implements ChessPiece {
    @JsonProperty("pieceType")
    private final PieceType pieceType = PieceType.BISHOP;
    @JsonProperty("teamColor")
    private final ChessGame.TeamColor teamColor;

    public BishopImplmentation(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return this.pieceType;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int columm = myPosition.getColumn();
        int row = myPosition.getRow();
        Collection<ChessMove> possiblemoves = new HashSet<>();
        int i =1;
        while (row+i < 9 && columm+i <9){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row+i, columm+i);
            ChessMoveImplmentation vertically = new ChessMoveImplmentation(myPosition, vertical);
            if (board.getPiece(vertical) != null){
                if (board.getPiece(vertical).getTeamColor() != teamColor){
                    possiblemoves.add(vertically);
                    break;
                }
                if (board.getPiece(vertical).getTeamColor() == teamColor){
                    break;
                }
            }
            possiblemoves.add(vertically);
            i++;
        }
        int j =1;
        while (row+j < 9 && columm-j >0){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row+j, columm-j);
            ChessMoveImplmentation vertically = new ChessMoveImplmentation(myPosition, vertical);
            if (board.getPiece(vertical) != null){
                if (board.getPiece(vertical).getTeamColor() != teamColor){
                    possiblemoves.add(vertically);
                    break;
                }
                if (board.getPiece(vertical).getTeamColor() == teamColor){
                    break;
                }
            }
            possiblemoves.add(vertically);
            j++;

        }
        int k =1;
        while (columm-k >0 && row-k >0){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row-k, columm-k);
            ChessMoveImplmentation vertically = new ChessMoveImplmentation(myPosition, vertical);
            if (board.getPiece(vertical) != null){
                if (board.getPiece(vertical).getTeamColor() != teamColor){
                    possiblemoves.add(vertically);
                    break;
                }
                if (board.getPiece(vertical).getTeamColor() == teamColor){
                    break;
                }
            }
            possiblemoves.add(vertically);
            k++;

        }
        int l =1;
        while (columm+ l <9 && row-l >0){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row-l, columm+l);
            ChessMoveImplmentation vertically = new ChessMoveImplmentation(myPosition, vertical);
            if (board.getPiece(vertical) != null){
                if (board.getPiece(vertical).getTeamColor() != teamColor){
                    possiblemoves.add(vertically);
                    break;
                }
                if (board.getPiece(vertical).getTeamColor() == teamColor){
                    break;
                }
            }
            possiblemoves.add(vertically);
            l++;
        }
        //parse the moves that are out of bounds
        for (ChessMove chessMove: possiblemoves){
            ChessPositionImplmentation piece1;
            ChessPositionImplmentation piece2;
            if(board.getPiece(chessMove.getEndPosition()) != null){
                if (board.getPiece(chessMove.getEndPosition()).getTeamColor() != teamColor){

                }
            }

        }
        return possiblemoves;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()){
            return false;
        }
        return super.equals(obj);
    }
}

package chess.pieces;

import chess.*;
import chess.ChessMoveImplmentation;
import chess.ChessPositionImplmentation;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class KnightImplmentation implements ChessPiece {
    @JsonProperty("pieceType")
    private final PieceType pieceType = PieceType.KNIGHT;
    @JsonProperty("teamColor")
    private final ChessGame.TeamColor teamColor;

    public KnightImplmentation(ChessGame.TeamColor teamColor){
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
        if (row+1 <9){
            if (columm+2 <9){
                ChessPositionImplmentation upLeft = new ChessPositionImplmentation(row+1, columm+2);
                ChessMoveImplmentation moveUpLeft = new ChessMoveImplmentation(myPosition, upLeft);
                possiblemoves.add(moveUpLeft);
            }
            if (columm-2 >0){
                ChessPositionImplmentation upRight = new ChessPositionImplmentation(row+1, columm-2);
                ChessMoveImplmentation moveUpRight = new ChessMoveImplmentation(myPosition, upRight);
                possiblemoves.add(moveUpRight);
            }
        }
        if (row+2 < 9){
            if (columm+1 <9){
                ChessPositionImplmentation downLeft = new ChessPositionImplmentation(row+2, columm+1);
                ChessMoveImplmentation moveDownright = new ChessMoveImplmentation(myPosition, downLeft);
                possiblemoves.add(moveDownright);
            }
            if (columm-1 >0){
                ChessPositionImplmentation downRight = new ChessPositionImplmentation(row+2, columm-1);
                ChessMoveImplmentation moveDownLeft = new ChessMoveImplmentation(myPosition, downRight);
                possiblemoves.add(moveDownLeft);
            }
        }
        if (row-1 >0){
            if (columm+2 <9){
                ChessPositionImplmentation upLeft1 = new ChessPositionImplmentation(row-1, columm+2);
                ChessMoveImplmentation moveUpLeft = new ChessMoveImplmentation(myPosition, upLeft1);
                possiblemoves.add(moveUpLeft);
            }
            if (columm-2 >0){
                ChessPositionImplmentation upRight1 = new ChessPositionImplmentation(row-1, columm-2);
                ChessMoveImplmentation moveUpRight = new ChessMoveImplmentation(myPosition, upRight1);
                possiblemoves.add(moveUpRight);
            }
        }
        if (row+-2 > 0){
            if (columm+1 <9){
                ChessPositionImplmentation downLeft1 = new ChessPositionImplmentation(row-2, columm+1);
                ChessMoveImplmentation moveDownright = new ChessMoveImplmentation(myPosition, downLeft1);

                possiblemoves.add(moveDownright);
            }
            if (columm-1 >0){
                ChessPositionImplmentation downRight1 = new ChessPositionImplmentation(row-2, columm-1);
                ChessMoveImplmentation moveDownLeft = new ChessMoveImplmentation(myPosition, downRight1);
                possiblemoves.add(moveDownLeft);
            }
        }
        Iterator<ChessMove> iterator = possiblemoves.iterator();
        while (iterator.hasNext()){
            ChessMove move = iterator.next();
            if (board.getPiece(move.getEndPosition()) != null){
                if (board.getPiece(move.getEndPosition()).getTeamColor() == teamColor){
                    iterator.remove();
                }
            }
        }

        return possiblemoves;
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnightImplmentation that = (KnightImplmentation) o;
        return teamColor == that.teamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor);
    }
}

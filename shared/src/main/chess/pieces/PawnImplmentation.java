package chess.pieces;

import chess.*;
import chess.ChessMoveImplmentation;
import chess.ChessPositionImplmentation;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class PawnImplmentation implements ChessPiece {
    @JsonProperty("pieceType")
    private final PieceType pieceType = PieceType.PAWN;
    @JsonProperty("teamColor")
    private final ChessGame.TeamColor teamColor;

    public PawnImplmentation(ChessGame.TeamColor teamColor){
        this.teamColor =teamColor;
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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition Chessposition) {
        ChessPositionImplmentation position = new ChessPositionImplmentation(Chessposition.getRow(),Chessposition.getColumn());
        Collection<ChessMove> possiblemoves = new HashSet<>();

        //forward move for white
        if (teamColor == ChessGame.TeamColor.WHITE){
            ChessPositionImplmentation straight = new ChessPositionImplmentation(position.getRow()+1, position.getColumn());
            if (position.getRow()+1 == 8){
                if (board.getPiece(straight) ==null){
                ChessMoveImplmentation promotionQ = new ChessMoveImplmentation(position,straight,PieceType.QUEEN);
                ChessMoveImplmentation promotionK = new ChessMoveImplmentation(position,straight,PieceType.KNIGHT);
                ChessMoveImplmentation promotionB = new ChessMoveImplmentation(position,straight,PieceType.BISHOP);
                ChessMoveImplmentation promotionR = new ChessMoveImplmentation(position,straight,PieceType.ROOK);
                possiblemoves.add(promotionB);
                possiblemoves.add(promotionK);
                possiblemoves.add(promotionQ);
                possiblemoves.add(promotionR);}
                if (position.getColumn()+1 <9 && position.getRow()+1 <9){
                ChessPositionImplmentation takeLeft = new ChessPositionImplmentation(position.getRow()+1, position.getColumn()+1);
                if (board.getPiece(takeLeft) != null && board.getPiece(takeLeft).getTeamColor() != teamColor){
                    ChessMoveImplmentation promotionQu = new ChessMoveImplmentation(position,takeLeft,PieceType.QUEEN);
                    ChessMoveImplmentation promotionKn = new ChessMoveImplmentation(position,takeLeft,PieceType.KNIGHT);
                    ChessMoveImplmentation promotionBi = new ChessMoveImplmentation(position,takeLeft,PieceType.BISHOP);
                    ChessMoveImplmentation promotionRo = new ChessMoveImplmentation(position,takeLeft,PieceType.ROOK);
                    possiblemoves.add(promotionBi);
                    possiblemoves.add(promotionKn);
                    possiblemoves.add(promotionQu);
                    possiblemoves.add(promotionRo);}
                }
                if (position.getColumn()-1 >0 && position.getRow()+1<9){
                    ChessPositionImplmentation takeLeft = new ChessPositionImplmentation(position.getRow()+1, position.getColumn()-1);
                    if (board.getPiece(takeLeft) != null && board.getPiece(takeLeft).getTeamColor() != teamColor){
                        ChessMoveImplmentation promotionQu = new ChessMoveImplmentation(position,takeLeft,PieceType.QUEEN);
                        ChessMoveImplmentation promotionKn = new ChessMoveImplmentation(position,takeLeft,PieceType.KNIGHT);
                        ChessMoveImplmentation promotionBi = new ChessMoveImplmentation(position,takeLeft,PieceType.BISHOP);
                        ChessMoveImplmentation promotionRo = new ChessMoveImplmentation(position,takeLeft,PieceType.ROOK);
                        possiblemoves.add(promotionBi);
                        possiblemoves.add(promotionKn);
                        possiblemoves.add(promotionQu);
                        possiblemoves.add(promotionRo);}
                }
            }
            if (position.getRow()+1<8 && board.getPiece(straight) == null ){
            ChessMoveImplmentation forward = new ChessMoveImplmentation(position,straight);
            possiblemoves.add(forward);
            }
            ChessPositionImplmentation upLeft = new ChessPositionImplmentation(position.getRow()+1, position.getColumn()+1);
            ChessPositionImplmentation upRight = new ChessPositionImplmentation(position.getRow()+1, position.getColumn()-1);
            if (position.getRow()+1<8 && position.getColumn() +1 <9 && board.getPiece(upLeft) != null){
            if (board.getPiece(upLeft).getTeamColor() == ChessGame.TeamColor.BLACK){
                ChessMoveImplmentation takeLeft = new ChessMoveImplmentation(position, upLeft);
                possiblemoves.add(takeLeft);
            }}
            if (position.getRow()+1<8 && position.getColumn()-1 >0 &&board.getPiece(upRight) != null ){
            if (board.getPiece(upRight).getTeamColor() == ChessGame.TeamColor.BLACK){
            ChessMoveImplmentation takeRight = new ChessMoveImplmentation(position, upRight);
            possiblemoves.add(takeRight);}}

            if (position.getRow() == 2){
                ChessPositionImplmentation leap = new ChessPositionImplmentation(position.getRow()+2, position.getColumn());
                if (board.getPiece(leap) == null && board.getPiece(straight) ==null){
                    ChessMoveImplmentation leaps = new ChessMoveImplmentation(position,leap );
                        possiblemoves.add(leaps);}
            }

            //possible takes for white pawn
        }
        if (teamColor == ChessGame.TeamColor.BLACK){
            ChessPositionImplmentation straight = new ChessPositionImplmentation(position.getRow()-1, position.getColumn());
            if (position.getRow()-1 == 1){
                if (board.getPiece(straight) ==null){
                    ChessMoveImplmentation promotionQ = new ChessMoveImplmentation(position,straight,PieceType.QUEEN);
                    ChessMoveImplmentation promotionK = new ChessMoveImplmentation(position,straight,PieceType.KNIGHT);
                    ChessMoveImplmentation promotionB = new ChessMoveImplmentation(position,straight,PieceType.BISHOP);
                    ChessMoveImplmentation promotionR = new ChessMoveImplmentation(position,straight,PieceType.ROOK);
                    possiblemoves.add(promotionB);
                    possiblemoves.add(promotionK);
                    possiblemoves.add(promotionQ);
                    possiblemoves.add(promotionR);}
                if (position.getColumn()+1 <9 && position.getRow()-1>0){
                    ChessPositionImplmentation takeLeft = new ChessPositionImplmentation(position.getRow()-1, position.getColumn()+1);
                    if (board.getPiece(takeLeft) != null && board.getPiece(takeLeft).getTeamColor() != teamColor){
                        ChessMoveImplmentation promotionQu = new ChessMoveImplmentation(position,takeLeft,PieceType.QUEEN);
                        ChessMoveImplmentation promotionKn = new ChessMoveImplmentation(position,takeLeft,PieceType.KNIGHT);
                        ChessMoveImplmentation promotionBi = new ChessMoveImplmentation(position,takeLeft,PieceType.BISHOP);
                        ChessMoveImplmentation promotionRo = new ChessMoveImplmentation(position,takeLeft,PieceType.ROOK);
                        possiblemoves.add(promotionBi);
                        possiblemoves.add(promotionKn);
                        possiblemoves.add(promotionQu);
                        possiblemoves.add(promotionRo);}
                }
                if (position.getColumn()-1 >0 && position.getRow()-1>0){
                    ChessPositionImplmentation takeLeft = new ChessPositionImplmentation(position.getRow()-1, position.getColumn()-1);
                    if (board.getPiece(takeLeft) != null && board.getPiece(takeLeft).getTeamColor() != teamColor){
                        ChessMoveImplmentation promotionQu = new ChessMoveImplmentation(position,takeLeft,PieceType.QUEEN);
                        ChessMoveImplmentation promotionKn = new ChessMoveImplmentation(position,takeLeft,PieceType.KNIGHT);
                        ChessMoveImplmentation promotionBi = new ChessMoveImplmentation(position,takeLeft,PieceType.BISHOP);
                        ChessMoveImplmentation promotionRo = new ChessMoveImplmentation(position,takeLeft,PieceType.ROOK);
                        possiblemoves.add(promotionBi);
                        possiblemoves.add(promotionKn);
                        possiblemoves.add(promotionQu);
                        possiblemoves.add(promotionRo);}
                }
            }
            if (position.getRow()-1>1 && board.getPiece(straight) == null){
            ChessMoveImplmentation forward = new ChessMoveImplmentation(position,straight);
            possiblemoves.add(forward);
            }
            ChessPositionImplmentation upLeft = new ChessPositionImplmentation(position.getRow()-1, position.getColumn()+1);
            ChessPositionImplmentation upRight = new ChessPositionImplmentation(position.getRow()-1, position.getColumn()-1);

            //take left
            if (position.getRow()-1>1 && position.getColumn()+1<9 && board.getPiece(upLeft) != null){
                if(board.getPiece(upLeft).getTeamColor() == ChessGame.TeamColor.WHITE){
                ChessMoveImplmentation takeLeft = new ChessMoveImplmentation(position, upLeft);
                possiblemoves.add(takeLeft);
                }
            }

            //take right
            if( position.getColumn()-1>1&& position.getRow()-1>1 &&board.getPiece(upRight) != null){
                if(board.getPiece(upRight).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    ChessMoveImplmentation takeRight = new ChessMoveImplmentation(position, upRight);
                    possiblemoves.add(takeRight);
            }
            }


            if (position.getRow() == 7){
            ChessPositionImplmentation leap = new ChessPositionImplmentation(position.getRow()-2, position.getColumn());
                if (board.getPiece(leap) == null && board.getPiece(straight) == null){
                ChessMoveImplmentation leaps = new ChessMoveImplmentation(position,leap );
                possiblemoves.add(leaps);
                }
            }

            //possible takes for black pawn


        }

        return possiblemoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PawnImplmentation that = (PawnImplmentation) o;
        return ((PawnImplmentation) o).getPieceType().equals(that.teamColor) && ((PawnImplmentation) o).getPieceType() == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }
}

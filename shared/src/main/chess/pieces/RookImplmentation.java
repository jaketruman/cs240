package chess.pieces;

import chess.*;
import chess.ChessMoveImplmentation;
import chess.ChessPositionImplmentation;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashSet;

public class RookImplmentation implements ChessPiece {
    @JsonProperty("pieceType")
    private final PieceType pieceType = PieceType.ROOK;

    @JsonProperty("teamColor")
    private final ChessGame.TeamColor teamColor;
    public RookImplmentation(ChessGame.TeamColor teamColor){
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
            while (row+i < 9){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row+i, columm);
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
        while (row-j> 0){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row-j, columm);
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
        while (columm-k >0){

            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row, columm-k);
            ChessMoveImplmentation vertically = new ChessMoveImplmentation(myPosition, vertical);
            if (board.getPiece(vertical) != null){
                if (board.getPiece(vertical).getTeamColor() != teamColor){
                    System.out.println(board.getPiece(vertical).getPieceType());
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
        while (columm+l < 9){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row, columm+l);
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

        return possiblemoves;
    }

}

package chess.pieces;

import chess.*;
import chess.ChessMoveImplmentation;
import chess.ChessPositionImplmentation;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashSet;

public class QueenImplmentation implements ChessPiece {
    @JsonProperty("pieceType")
    private PieceType pieceType = PieceType.QUEEN;

    @JsonProperty("teamColor")
    private ChessGame.TeamColor teamColor;
    public QueenImplmentation(ChessGame.TeamColor teamColor){
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
        while (columm+ l < 9 && row-l >0){
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
        int q =1;
        while (row+q < 9){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row+q, columm);
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
            q++;
        }
        int w =1;
        while (row-w> 0){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row-w, columm);
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
            w++;

        }
        int e =1;
        while (columm-e >0){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row, columm-e);
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
            e++;

        }
        int r =1;
        while (columm+r <9){
            ChessPositionImplmentation vertical = new ChessPositionImplmentation(row, columm+r);
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
            r++;
        }
        //parse the moves that are out of bounds

        return possiblemoves;
    }
}

package chess;

import java.util.Objects;

public class ChessMoveImplmentation implements ChessMove {
    private ChessPosition start;
    private ChessPosition end;
    private ChessPiece.PieceType promotionPiece;

    public ChessMoveImplmentation(ChessPosition startSquare, ChessPosition endSquare, ChessPiece.PieceType pieceType) {
        this.start = startSquare;
        this.end = endSquare;
        this.promotionPiece = pieceType;
    }
    public ChessMoveImplmentation(ChessPosition startSquare, ChessPosition endSquare) {
        this.start = startSquare;
        this.end = endSquare;
    }


    @Override
    public ChessPosition getStartPosition() {
        return start;
    }

    @Override
    public ChessPosition getEndPosition() {
        return end;
    }




    //Question on this, do we need to prompt for user input for what piece they want to promote too
    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        //Check pieceType is a paw else return null
        //assumes White pieces are on the bottom rows, (1,1) seems like it should be the top left
        if (end.getRow() == 8 || end.getRow() ==1) {
            // Does the promotion piece need to prompt for user input on what
            //piece they want when promoting
            return promotionPiece;
        }


        return null;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMoveImplmentation that = (ChessMoveImplmentation) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end) && promotionPiece == that.promotionPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, promotionPiece);
    }
}
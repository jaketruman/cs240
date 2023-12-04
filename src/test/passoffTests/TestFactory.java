package passoffTests;
import chess.*;
import chess.pieces.*;

/**
 * Used for testing your code
 * Add in code using your classes for each method for each FIXME
 */
public class TestFactory {

    //Chess Functions
    //------------------------------------------------------------------------------------------------------------------
    public static ChessBoard getNewBoard(){
        ChessBoardImplmentation boardImplmentation = new ChessBoardImplmentation();
        boardImplmentation.clearBoard();
        // FIXME
		return boardImplmentation;
    }

    public static ChessGame getNewGame(){
        ChessBoardImplmentation board = (ChessBoardImplmentation) getNewBoard();
        ChessGameImplmentation gameImplmentation =new ChessGameImplmentation(board);
        // FIXME
		return gameImplmentation;
    }

    public static ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        // FIXME
        if (type == ChessPiece.PieceType.BISHOP){
            BishopImplmentation bishop = new BishopImplmentation(pieceColor);
            return bishop;
        }
        if (type == ChessPiece.PieceType.PAWN){
            PawnImplmentation pawnImplmentation = new PawnImplmentation(pieceColor);
            return pawnImplmentation;
        }
        if (type == ChessPiece.PieceType.ROOK){
            RookImplmentation rookImplmentation = new RookImplmentation(pieceColor);
            return rookImplmentation;
        }
        if (type == ChessPiece.PieceType.KING){
           KingImplmentation kingImplmentation = new KingImplmentation(pieceColor);
            return kingImplmentation;
        }
        if (type == ChessPiece.PieceType.QUEEN){
            QueenImplmentation queenImplmentation = new QueenImplmentation(pieceColor);
            return queenImplmentation;
        }
        if (type == ChessPiece.PieceType.KNIGHT){
            KnightImplmentation knightImplmentation = new KnightImplmentation(pieceColor);
            return knightImplmentation;
        }
        return null;
    }

    public static ChessPosition getNewPosition(Integer row, Integer col){
        ChessPosition position = new ChessPositionImplmentation(row,col);
        // FIXME
		return position;
    }

    public static ChessMove getNewMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece){
        if (promotionPiece != null){
            ChessMove move = new ChessMoveImplmentation(startPosition,endPosition,promotionPiece);
            return move;
        }
        ChessMove move1 = new ChessMoveImplmentation(startPosition,endPosition);
        return move1;
        // FIXME
    }
    //------------------------------------------------------------------------------------------------------------------


    //Server API's
    //------------------------------------------------------------------------------------------------------------------
    public static String getServerPort(){
        return "8080";
    }
    //------------------------------------------------------------------------------------------------------------------


    //Websocket Tests
    //------------------------------------------------------------------------------------------------------------------
    public static Long getMessageTime(){
        /*
        Changing this will change how long tests will wait for the server to send messages.
        3000 Milliseconds (3 seconds) will be enough for most computers. Feel free to change as you see fit,
        just know increasing it can make tests take longer to run.
        (On the flip side, if you've got a good computer feel free to decrease it)
         */
        return 2000L;
    }
    //------------------------------------------------------------------------------------------------------------------
}

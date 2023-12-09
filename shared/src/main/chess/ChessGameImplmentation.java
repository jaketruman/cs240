package chess;

import chess.pieces.*;

import java.util.Collection;
import java.util.HashSet;
public class ChessGameImplmentation implements ChessGame {
    public String whiteUsername;
    public String blackUsername;
    public int turnCount = 0;
    private ChessBoard board;

    public ChessGameImplmentation(ChessBoard board){

        this.board = board;
    }
    public ChessGameImplmentation(){
    }
    @Override
    public TeamColor getTeamTurn() {
        if (turnCount%2 ==0){
            return TeamColor.WHITE;
        }
        else return TeamColor.BLACK;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
            if (getTeamTurn() == TeamColor.WHITE){

            }
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        if (board.getPiece(startPosition) == null ){
          return null;
        }
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> moves =  piece.pieceMoves(board, startPosition);
        Collection<ChessMove> finalMoves = new HashSet<>();
        if (!isInCheck(board.getPiece(startPosition).getTeamColor())){
            for (ChessMove temp : moves){
                    if (makeTempMove(temp)){
                        finalMoves.add(temp);
                    }
            }

        }else{
            for (ChessMove temp : moves){
                if (makeTempMove(temp)){
                    finalMoves.add(temp);
                }
            }
        }
        return finalMoves;

    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (board.getPiece(move.getStartPosition())== null){
            throw new InvalidMoveException();
        }
        Collection<ChessMove> valids = validMoves(move.getStartPosition());
        if (valids ==null){
            throw new InvalidMoveException();
        }

        if (board.getPiece(move.getStartPosition()).getTeamColor() != getTeamTurn()){
            throw new InvalidMoveException();
        }
        if (isInCheck(getTeamTurn())){
            if(!valids.contains(move)) throw new InvalidMoveException();
        }

        if (valids.contains(move)){
            //promotion piece
            if (move.getPromotionPiece() != null){
                ChessPiece.PieceType promo = move.getPromotionPiece();
                if (promo == ChessPiece.PieceType.QUEEN){
                    QueenImplmentation queen = new QueenImplmentation(getTeamTurn());
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.addPiece(move.getStartPosition(),queen);
                }
                if (promo == ChessPiece.PieceType.BISHOP){
                    BishopImplmentation bishopImplmentation = new BishopImplmentation(getTeamTurn());
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.addPiece(move.getStartPosition(),bishopImplmentation);
                }
                if (promo == ChessPiece.PieceType.ROOK){
                    RookImplmentation rook = new RookImplmentation(getTeamTurn());
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.addPiece(move.getStartPosition(),rook);
                }
                if (promo == ChessPiece.PieceType.KNIGHT){
                    KnightImplmentation knightImplmentation = new KnightImplmentation(getTeamTurn());
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.addPiece(move.getStartPosition(),knightImplmentation);
                }

            }
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            board.addPiece(move.getStartPosition(),null);
        } else throw new InvalidMoveException();

        turnCount++;
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessMove> possible_moves = new HashSet<>(){};
        ChessPosition king = null;
        ChessPosition position;
        for (int i = 1;i <9; i++){
            for (int j = 1; j<9; j++){
                position = new ChessPositionImplmentation(i,j);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != teamColor){
                    Collection<ChessMove> tempMoves = findMoves(position, board.getPiece(position).getTeamColor());
                    for (ChessMove move: tempMoves){
                        possible_moves.add(move);
                    }
                }
                if (board.getPiece(position) != null && board.getPiece(position).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(position).getTeamColor() == teamColor ){
                    king = position;
                }
            }
        }
        for (ChessMove move: possible_moves){
            if (move.getEndPosition().equals(king)){
                return true;
            }
        }

        return false;
    }
    private Collection<ChessMove> checkLegal(ChessPosition start) throws InvalidMoveException {
        ChessBoard saveBoard = board;
        Collection<ChessMove> possible_moves =findMoves(start,getTeamTurn());
        Collection<ChessMove> legalMoves = new HashSet<>(){};
        for (ChessMove possible : possible_moves){
            makeTempMove(possible);
            if(!isInCheck(getTeamTurn())){
                legalMoves.add(possible);
            }
            setBoard(saveBoard);
        }
        return legalMoves;
    }
    public Boolean makeTempMove(ChessMove move){
        TeamColor color = board.getPiece(move.getStartPosition()).getTeamColor();
        if (move.getEndPosition() != null){
            ChessPiece piece = board.getPiece(move.getEndPosition());
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            board.addPiece(move.getStartPosition(),null);
            if (isInCheck(color)){
                board.addPiece(move.getStartPosition(), board.getPiece(move.getEndPosition()));
                board.addPiece(move.getEndPosition(), piece);
                return false;
            }
            board.addPiece(move.getStartPosition(), board.getPiece(move.getEndPosition()));
            board.addPiece(move.getEndPosition(), piece);
            return true;
        }else
        board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        board.addPiece(move.getStartPosition(),null);
        if (isInCheck(color)){
            board.addPiece(move.getStartPosition(), board.getPiece(move.getEndPosition()));
            board.addPiece(move.getEndPosition(), null);
            return false;
        }
        board.addPiece(move.getStartPosition(), board.getPiece(move.getEndPosition()));
        board.addPiece(move.getEndPosition(), null);
        return true;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessBoard saveBoard = getBoard();
        Collection<ChessMove> possible_moves = new HashSet<>(){};
        ChessPosition position;
        ChessPosition king = null;
        for (int i = 1;i <9; i++){
            for (int j = 1; j<9; j++){
                position = new ChessPositionImplmentation(i,j);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == teamColor){
                    Collection<ChessMove> tempMoves = findMoves(position, board.getPiece(position).getTeamColor());
                    for (ChessMove move: tempMoves){
                        possible_moves.add(move);
                    }
                if (board.getPiece(position) != null && board.getPiece(position).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(position).getTeamColor() == teamColor ){
                    king = position;
                    }
                }
            }
        }
        Collection<ChessMove> chessMoveCollection = new HashSet<ChessMove>();
        for(ChessMove move: possible_moves){
            makeTempMove(move);
            setBoard(saveBoard);
            if (!isInCheck(teamColor)){
                chessMoveCollection.add(move);
            }
        }
        return chessMoveCollection.isEmpty();
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        if (!isInCheck(teamColor)){
            Collection<ChessMove> possible_moves = new HashSet<>(){};
            ChessPosition king = null;
            ChessPosition position;
            for (int i = 1;i <9; i++){
                for (int j = 1; j<9; j++){
                    position = new ChessPositionImplmentation(i,j);
                    if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() != teamColor){
                        Collection<ChessMove> tempMoves = findMoves(position, board.getPiece(position).getTeamColor());
                        for (ChessMove move: tempMoves){
                            possible_moves.add(move);
                        }
                    }
                    if (board.getPiece(position) != null && board.getPiece(position).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(position).getTeamColor() == teamColor ){
                        king = position;
                        System.out.println("King at,"+position.getRow()+", "+position.getColumn());
                    }
                }
            }
            Collection<ChessMove> last = new HashSet<>(){};
            for(ChessMove move: possible_moves){
                if(!makeTempMove(move)){
                    last.add(move);
                }
            }
            return last.isEmpty();
        }
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }

    public Collection<ChessMove> findMoves(ChessPosition startPosition, TeamColor color){
        ChessPiece.PieceType piece = board.getPiece(startPosition).getPieceType();

        if (piece == ChessPiece.PieceType.ROOK){
            RookImplmentation rook = new RookImplmentation(color);
            return rook.pieceMoves(board, startPosition);
        }
        if (piece == ChessPiece.PieceType.BISHOP){
            BishopImplmentation bishopImplmentation = new BishopImplmentation(color);
            return bishopImplmentation.pieceMoves(board,startPosition);
        }
        if (piece == ChessPiece.PieceType.PAWN){
            PawnImplmentation pawnImplmentation = new PawnImplmentation(color);
            return pawnImplmentation.pieceMoves(board,startPosition);
        }
        if (piece == ChessPiece.PieceType.KING){
            KingImplmentation kingImplmentation = new KingImplmentation(color);
            return kingImplmentation.pieceMoves(board,startPosition);
        }
        if (piece == ChessPiece.PieceType.KNIGHT){
            KnightImplmentation knight = new KnightImplmentation(color);
            return knight.pieceMoves(board,startPosition);
        }
        if (piece == ChessPiece.PieceType.QUEEN){
            QueenImplmentation queenImplmentation = new QueenImplmentation(color);
            return queenImplmentation.pieceMoves(board,startPosition);
        }
        return null;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public ChessBoard getGameBoard() {
        return board;
    }

    public void setGameBoard(ChessBoard gameBoard) {
        this.board = gameBoard;
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void findKingLocatesCorrectKing(){
        Board board = new Board(false);
        King whiteKing = new King("K", Color.WHITE, "1");
        King blackKing = new King("k", Color.BLACK, "2");
        board.board[7][4] = whiteKing;
        board.board[0][4] = blackKing;

        assertArrayEquals(new int[]{4, 7}, board.findKing(Color.WHITE, board.board));
        assertArrayEquals(new int[]{4, 0}, board.findKing(Color.BLACK, board.board));
    }

    @Test
    public void isSquareAttackedDetectsRookAlongRow(){
        Board board = new Board(false);
        Rook blackRook = new Rook("r", Color.BLACK, "1");
        board.board[3][0] = blackRook;

        assertTrue(board.isSquareAttacked(7, 3, Color.BLACK, board.board));
    }

    @Test
    public void isSquareAttackedFalseWhenPathBlocked(){
        Board board = new Board(false);
        Rook blackRook = new Rook("r", Color.BLACK, "1");
        Pawn blocker = new Pawn("p", Color.WHITE, "2");
        board.board[3][0] = blackRook;
        board.board[3][4] = blocker;

        assertFalse(board.isSquareAttacked(7, 3, Color.BLACK, board.board));
    }

    @Test
    public void isSquareAttackedByPawnOnEmptySquare(){
        Board board = new Board(false);
        Pawn blackPawn = new Pawn("p", Color.BLACK, "1");
        board.board[3][3] = blackPawn;

        // black pawn at (3,3) attacks the two empty diagonal-forward squares, not the square directly ahead
        assertTrue(board.isSquareAttacked(4, 4, Color.BLACK, board.board));
        assertTrue(board.isSquareAttacked(2, 4, Color.BLACK, board.board));
        assertFalse(board.isSquareAttacked(3, 4, Color.BLACK, board.board));
    }

    @Test
    public void isInCheckTrueWhenKingAttacked(){
        Board board = new Board(false);
        King whiteKing = new King("K", Color.WHITE, "1");
        Queen blackQueen = new Queen("q", Color.BLACK, "2");
        board.board[7][4] = whiteKing;
        board.board[0][4] = blackQueen; // same file as the king, clear path

        assertTrue(board.isInCheck(Color.WHITE, board.board));
    }

    @Test
    public void isInCheckFalseWhenKingSafe(){
        Board board = new Board(false);
        King whiteKing = new King("K", Color.WHITE, "1");
        Queen blackQueen = new Queen("q", Color.BLACK, "2");
        board.board[7][4] = whiteKing;
        board.board[0][3] = blackQueen; // not aligned with the king at all

        assertFalse(board.isInCheck(Color.WHITE, board.board));
    }

    @Test
    public void copyBoardDoesNotMutateOriginal(){
        Board board = new Board(false);
        Pawn pawn = new Pawn("P", Color.WHITE, "1");
        board.board[6][0] = pawn;

        Piece[][] copy = board.copyBoard(board.board);
        copy[5][0] = copy[6][0];
        copy[6][0] = new NullPiece();

        assertSame(pawn, board.board[6][0]);
        assertTrue(copy[6][0] instanceof NullPiece);
    }

    @Test
    public void wouldBeInCheckTrueWhenMovingPinnedPieceAway(){
        Board board = new Board(false);
        King whiteKing = new King("K", Color.WHITE, "1");
        Bishop whiteBishop = new Bishop("B", Color.WHITE, "2");
        Rook blackRook = new Rook("r", Color.BLACK, "3");
        board.board[7][4] = whiteKing;
        board.board[7][2] = whiteBishop; // sits between the king and the rook on the back rank
        board.board[7][0] = blackRook;

        Move move = new Move(2, 7, 2, 6); // moving the bishop off the rank exposes the king

        assertTrue(board.wouldBeInCheck(move, Color.WHITE, board.board));
        // the real board must be untouched by the simulation
        assertSame(whiteBishop, board.board[7][2]);
    }

    @Test
    public void wouldBeInCheckFalseForSafeMove(){
        Board board = new Board(false);
        King whiteKing = new King("K", Color.WHITE, "1");
        Pawn whitePawn = new Pawn("P", Color.WHITE, "2");
        board.board[7][4] = whiteKing;
        board.board[6][0] = whitePawn;

        Move move = new Move(0, 6, 0, 5);

        assertFalse(board.wouldBeInCheck(move, Color.WHITE, board.board));
    }

    @Test
    public void hasLegalMovesTrueOnOpenBoard(){
        Board board = new Board(false);
        King whiteKing = new King("K", Color.WHITE, "1");
        board.board[7][4] = whiteKing;

        assertTrue(board.hasLegalMoves(Color.WHITE, board.board));
        assertFalse(board.isStalemate(Color.WHITE, board.board));
    }

    @Test
    public void isCheckmateWhenKingCorneredAndDefended(){
        Board board = new Board(false);
        King whiteKing = new King("K", Color.WHITE, "1");
        Queen blackQueen = new Queen("q", Color.BLACK, "2");
        King blackKing = new King("k", Color.BLACK, "3");
        board.board[0][0] = whiteKing;
        board.board[1][1] = blackQueen; // checks diagonally, adjacent
        board.board[2][2] = blackKing;  // defends the queen so the king can't capture it

        assertTrue(board.isInCheck(Color.WHITE, board.board));
        assertFalse(board.hasLegalMoves(Color.WHITE, board.board));
        assertTrue(board.isCheckmate(Color.WHITE, board.board));
    }

    @Test
    public void notCheckmateWhenKingCanEscape(){
        Board board = new Board(false);
        King whiteKing = new King("K", Color.WHITE, "1");
        Rook blackRook = new Rook("r", Color.BLACK, "2");
        board.board[0][0] = whiteKing;
        board.board[0][7] = blackRook; // checks along the back rank, but row 1 is free

        assertTrue(board.isInCheck(Color.WHITE, board.board));
        assertTrue(board.hasLegalMoves(Color.WHITE, board.board));
        assertFalse(board.isCheckmate(Color.WHITE, board.board));
    }

    @Test
    public void isStalemateWhenNoLegalMovesAndNotInCheck(){
        Board board = new Board(false);
        King blackKing = new King("k", Color.BLACK, "1");
        Queen whiteQueen = new Queen("Q", Color.WHITE, "2");
        board.board[0][0] = blackKing;   // cornered
        board.board[2][1] = whiteQueen;  // covers every escape square without checking the king

        assertFalse(board.isInCheck(Color.BLACK, board.board));
        assertFalse(board.hasLegalMoves(Color.BLACK, board.board));
        assertTrue(board.isStalemate(Color.BLACK, board.board));
        assertFalse(board.isCheckmate(Color.BLACK, board.board));
    }
}

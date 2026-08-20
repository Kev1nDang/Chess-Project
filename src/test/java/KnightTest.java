import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    @Test
    void testKnightMovement() {
        Knight blackKnight = new Knight("♘", Color.BLACK, "0");
        Board newBoard = new Board(false);
        newBoard.board[0][0] = blackKnight;

        assertTrue(blackKnight.validMove(0, 0, 2, 1, newBoard.board));
        assertTrue(blackKnight.validMove(0, 0, 1, 2, newBoard.board));
        assertFalse(blackKnight.validMove(0, 0, 0, 3, newBoard.board));
        assertFalse(blackKnight.validMove(0, 0, 3, 3, newBoard.board));
    }

    @Test
    void testKnightCaptureDifferentColor() {
        Knight blackKnight = new Knight("♘", Color.BLACK, "0");
        Knight whiteKnight = new Knight("♘", Color.WHITE, "0");
        Board newBoard = new Board(false);
        newBoard.board[0][0] = blackKnight;
        newBoard.board[2][1] = whiteKnight;

        assertTrue(blackKnight.validMove(0, 0, 2, 1, newBoard.board));
    }

    @Test
    void testKnightCaptureSameColor() {
        Knight blackKnight = new Knight("♘", Color.BLACK, "0");
        Knight blackKnight2 = new Knight("♘", Color.BLACK, "1");
        Board newBoard = new Board(false);
        newBoard.board[0][0] = blackKnight;
        newBoard.board[2][1] = blackKnight2;

        assertFalse(blackKnight.validMove(0, 0, 1, 2, newBoard.board));
    }

    @Test
    void testKnightCantMoveToSpot() {
        Knight blackKnight = new Knight("♘", Color.BLACK, "0");
        Board newBoard = new Board(false);
        newBoard.board[0][0] = blackKnight;

        assertFalse(blackKnight.validMove(0, 0, 1, 1, newBoard.board));
    }

    @Test
    public void invalidMoveStraightLine(){
        Board board = new Board(false);
        Piece k = new Knight("N", Color.WHITE, "1");
        board.board[0][0] = k;
        board.display();
        assertFalse(k.validMove(0,0, 0,2, board.board)); // Moving in a straight line
    }

    @Test
    public void obstructedMove(){
        Board board = new Board(false);
        Piece k = new Knight("N", Color.WHITE, "1");
        Piece blocker = new Pawn("P", Color.WHITE, "2");
        board.board[0][0] = k;
        board.board[1][2] = blocker;
        board.display();
        assertFalse(k.validMove(0,0, 2,1, board.board)); // Move obstructed by own piece
    }

}
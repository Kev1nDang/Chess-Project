

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RookTest {
    /**
     *
     */
    @Test
    public void testMoveHorizantal(){
        Board board = new Board(false);
        Piece rook = new Rook("D", Color.WHITE, "1");
        board.board[0][0] = rook;
        board.display();
        Assertions.assertTrue(rook.validMove(0,0, 7,0, board.board));
    }

    @Test
    public void testMoveVertical(){
        Board board = new Board(false);
        Rook rook = new Rook("a", Color.WHITE, "1");
        board.board[0][0] = rook;
        board.display();
        Assertions.assertTrue(rook.validMove(0,0, 0,7, board.board));
    }

    @Test
    public void takePiece(){
        Board board = new Board(false);
        Rook rook = new Rook("a", Color.WHITE, "1");
        Pawn pawn = new Pawn("P", Color.BLACK, "1");
        board.board[0][0] = rook;
        board.board[0][7] = pawn;
        board.display();
        Assertions.assertTrue(rook.validMove(0,0, 7,0, board.board));
    }

    @Test
    public void cannotTake(){
        Board board = new Board(false);
        Rook rook = new Rook("a", Color.WHITE, "1");
        Pawn pawn = new Pawn("P", Color.WHITE, "1");
        board.board[0][0] = rook;
        board.board[0][7] = pawn;
        board.display();
        Assertions.assertFalse(rook.validMove(0,0, 7,0, board.board));
    }
    @Test
    public void invalidMove(){
        Board board = new Board(false);
        Rook rook = new Rook("a", Color.WHITE, "1");
        Pawn pawn = new Pawn("P", Color.WHITE, "1");
        board.board[0][0] = rook;
        board.board[0][2] = pawn;
        board.display();
        Assertions.assertFalse(rook.validMove(0,0, 7,0, board.board));
    }

    @Test
    public void cannotMoveDiagonally(){
        Board board = new Board(false);
        Piece r = new Rook("R", Color.WHITE, "1");
        board.board[0][0] = r;
        board.display();
        Assertions.assertFalse(r.validMove(0,0, 1,1, board.board)); // Moving diagonally
    }

    @Test
    public void obstructedMove(){
        Board board = new Board(false);
        Piece r = new Rook("R", Color.WHITE, "1");
        Piece blocker = new Pawn("P", Color.WHITE, "2");
        board.board[0][0] = r;
        board.board[0][2] = blocker;
        board.display();
        Assertions.assertFalse(r.validMove(0,0, 3,0, board.board)); // Move obstructed by own piece
    }

    @Test
    public void attacksSquareDelegatesToValidMoveForRook(){
        Board board = new Board(false);
        Piece r = new Rook("R", Color.WHITE, "1");
        board.board[0][0] = r;
        board.display();
        Assertions.assertEquals(r.validMove(0,0,0,5,board.board), r.attacksSquare(0,0,0,5,board.board));
    }

}

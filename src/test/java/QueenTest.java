

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class QueenTest {
    /**
     *
     */
    @Test
    public void testMoveHorizantal(){
        Board board = new Board(false);
        Piece rook = new Queen("D", Color.WHITE, "1");
        board.board[0][0] = rook;
        board.display();
        Assertions.assertTrue(rook.validMove(0,0, 7,0, board.board));
    }



    @Test
    public void testMoveVertical(){
        Board board = new Board(false);
        Queen rook = new Queen("a", Color.WHITE, "1");
        board.board[0][0] = rook;
        board.display();
        Assertions.assertTrue(rook.validMove(0,0, 0,7, board.board));
    }

    @Test
    public void takePiece(){
        Board board = new Board(false);
        Queen rook = new Queen("a", Color.WHITE, "1");
        Pawn pawn = new Pawn("P", Color.BLACK, "1");
        board.board[0][0] = rook;
        board.board[0][7] = pawn;
        board.display();
        Assertions.assertTrue(rook.validMove(0,0, 7,0, board.board));
    }

    @Test
    public void cannotTake(){
        Board board = new Board(false);
        Queen rook = new Queen("a", Color.WHITE, "1");
        Pawn pawn = new Pawn("P", Color.WHITE, "1");
        board.board[0][0] = rook;
        board.board[0][7] = pawn;
        board.display();
        Assertions.assertFalse(rook.validMove(0,0, 7,0, board.board));
    }
    @Test
    public void invalidMove(){
        Board board = new Board(false);
        Queen rook = new Queen("a", Color.WHITE, "1");
        Pawn pawn = new Pawn("P", Color.WHITE, "1");
        board.board[0][0] = rook;
        board.board[0][2] = pawn;
        board.display();
        Assertions.assertFalse(rook.validMove(0,0, 7,0, board.board));
    }

    @Test
    public void diagonalDown(){
        Board board = new Board(false);
        Piece b = new Queen("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 7,7, board.board));
    }

    @Test
    public void diagonalUp(){
        Board board = new Board(false);
        Piece b = new Queen("B", Color.WHITE, "1");
        board.board[7][7] = b;
        board.display();
        Assertions.assertTrue(b.validMove(7,7, 0,0, board.board));
    }

    @Test
    public void takePiece2(){
        Board board = new Board(false);
        Piece b = new Queen("B", Color.WHITE, "1");
        Piece b1 = new Pawn("D", Color.BLACK, "d");
        board.board[0][0] = b;
        board.board[7][7] = b1;
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 7,7, board.board));
    }

    @Test
    public void cannotTake2(){
        Board board = new Board(false);
        Piece b = new Queen("B", Color.WHITE, "1");
        Piece b1 = new Bishop("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.board[7][7] = b1;
        board.display();
        Assertions.assertFalse(b.validMove(0,0, 7,7, board.board));
    }
    @Test
    public void invalidMove2(){
        Board board = new Board(false);
        Piece b = new Queen("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 7,7, board.board));
    }

    @Test
    public void cannotMoveInNonStraightLine(){
        Board board = new Board(false);
        Piece q = new Queen("Q", Color.WHITE, "1");
        board.board[0][0] = q;
        board.display();
        Assertions.assertFalse(q.validMove(0,0, 2,1, board.board)); // Moving in a non-straight line
    }

    @Test
    public void obstructedMove(){
        Board board = new Board(false);
        Piece q = new Queen("Q", Color.WHITE, "1");
        Piece blocker = new Pawn("P", Color.WHITE, "2");
        board.board[0][0] = q;
        board.board[1][2] = blocker;
        board.display();
        Assertions.assertFalse(q.validMove(0,0, 2,1, board.board)); // Move obstructed by own piece
    }

    @Test
    public void clearAntiDiagonalMoveWorks(){
        Board board = new Board(false);
        Piece q = new Queen("Q", Color.WHITE, "1");
        board.board[0][7] = q;
        board.display();
        Assertions.assertTrue(q.validMove(7,0, 0,7, board.board)); // up-right/down-left diagonal
    }

    @Test
    public void blockedOnAntiDiagonal(){
        Board board = new Board(false);
        Piece q = new Queen("Q", Color.WHITE, "1");
        Piece blocker = new Pawn("P", Color.BLACK, "1");
        board.board[0][7] = q;
        board.board[3][4] = blocker;
        board.display();
        Assertions.assertFalse(q.validMove(7,0, 0,7, board.board)); // anti-diagonal was never blocking-checked before the fix
    }

    @Test
    public void offDiagonalPieceDoesNotBlock(){
        Board board = new Board(false);
        Piece q = new Queen("Q", Color.WHITE, "1");
        Piece notInThePath = new Pawn("P", Color.BLACK, "1");
        board.board[0][0] = q;
        board.board[2][1] = notInThePath; // inside the old buggy rectangle, but off the true diagonal
        board.display();
        Assertions.assertTrue(q.validMove(0,0, 3,3, board.board));
    }

    @Test
    public void sameSquareMoveDoesNotThrow(){
        Board board = new Board(false);
        Piece q = new Queen("Q", Color.WHITE, "1");
        board.board[0][0] = q;
        Assertions.assertDoesNotThrow(() -> q.validMove(0,0, 0,0, board.board));
    }

}

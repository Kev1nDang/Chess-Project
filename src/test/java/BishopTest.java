import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class BishopTest {
    /**
     *
     */
    @Test
    public void diagonalDown(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 7,7, board.board));
    }

    @Test
    public void diagonalUp(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        board.board[7][7] = b;
        board.display();
        Assertions.assertTrue(b.validMove(7,7, 0,0, board.board));
    }

    @Test
    public void takePiece(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        Piece b1 = new Pawn("D", Color.BLACK, "d");
        board.board[0][0] = b;
        board.board[7][7] = b1;
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 7,7, board.board));
    }

    @Test
    public void cannotTake(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        Piece b1 = new Bishop("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.board[7][7] = b1;
        board.display();
        Assertions.assertFalse(b.validMove(0,0, 7,7, board.board));
    }
    @Test
    public void invalidMove(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 7,7, board.board));
    }

    @Test
    public void cannotMoveHorizontallyOrVertically(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.display();
        Assertions.assertFalse(b.validMove(0,0, 0,3, board.board)); // Horizontal move
        Assertions.assertFalse(b.validMove(0,0, 3,0, board.board)); // Vertical move
    }

    @Test
    public void cannotMoveThroughPieces(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        Piece blocker = new Pawn("P", Color.BLACK, "1");
        board.board[0][0] = b;
        board.board[2][2] = blocker;
        board.display();
        Assertions.assertFalse(b.validMove(0,0, 4,4, board.board)); // Attempting to move through a piece
    }

    @Test
    public void clearAntiDiagonalMoveWorks(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        board.board[0][7] = b;
        board.display();
        Assertions.assertTrue(b.validMove(7,0, 0,7, board.board)); // up-right/down-left diagonal
    }

    @Test
    public void blockedOnAntiDiagonal(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        Piece blocker = new Pawn("P", Color.BLACK, "1");
        board.board[0][7] = b;
        board.board[3][4] = blocker;
        board.display();
        Assertions.assertFalse(b.validMove(7,0, 0,7, board.board)); // anti-diagonal was never blocking-checked before the fix
    }

    @Test
    public void offDiagonalPieceDoesNotBlock(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        Piece notInThePath = new Pawn("P", Color.BLACK, "1");
        board.board[0][0] = b;
        board.board[2][1] = notInThePath; // inside the old buggy rectangle, but off the true diagonal
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 3,3, board.board));
    }

    @Test
    public void sameSquareMoveDoesNotThrow(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        board.board[0][0] = b;
        Assertions.assertDoesNotThrow(() -> b.validMove(0,0, 0,0, board.board));
    }

}


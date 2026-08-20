import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class KingTest {
    /**
     *
     */
    @Test
    public void moveOneUnit(){
        Board board = new Board(false);
        Piece b = new King("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 1,0, board.board));
    }

    @Test
    public void diagonalUpOneUnit(){
        Board board = new Board(false);
        Piece b = new King("B", Color.WHITE, "1");
        board.board[7][7] = b;
        board.display();
        Assertions.assertTrue(b.validMove(7,7, 6,6, board.board));
    }

    @Test
    public void takePiece(){
        Board board = new Board(false);
        Piece b = new Bishop("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.display();
        Assertions.assertTrue(b.validMove(0,0, 7,7, board.board));
    }

    @Test
    public void cannotTake(){
        Board board = new Board(false);
        Piece b = new King("B", Color.WHITE, "1");
        Piece b1 = new Bishop("B", Color.WHITE, "1");
        board.board[0][0] = b;
        board.board[1][1] = b1;
        board.display();
        Assertions.assertFalse(b.validMove(0,0, 1,1, board.board));
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
    public void cannotMoveMoreThanOneStep(){
        Board board = new Board(false);
        Piece k = new King("K", Color.WHITE, "1");
        board.board[0][0] = k;
        board.display();
        Assertions.assertFalse(k.validMove(0,0, 2,0, board.board)); // Moving more than one step
    }




}


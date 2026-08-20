public class NullPiece extends Piece {
    public NullPiece() {
        super("\u001B[30m♙\u001B[0m",Color.WHITE,"");
    }

    @Override
    public boolean validMove(int x, int y, int moveX, int moveY, Piece[][] board) {
        return false;
    }

    @Override
    public boolean isClear(int x, int y, Piece[][] board) {
        return false;
    }
}

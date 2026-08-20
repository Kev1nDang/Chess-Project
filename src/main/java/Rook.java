public class Rook extends Piece{

    public Rook(String piece, Color color, String iD) {
        super(piece, color, iD);
    }

    @Override
    public boolean validMove(int x, int y, int moveX, int moveY, Piece[][] board) {
        if (moveX < 0 || moveX > 7 || moveY > 7 || moveY < 0) {
            return false;
        }
        else if (((isClearY(x, y, moveX, moveY, board) && x == moveX && y != moveY) ||
                (x != moveX && y == moveY && isClearX(x, y, moveX, moveY, board)))
        && (board[moveY][moveX].getColor() != getColor() || board[moveY][moveX] instanceof NullPiece)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isClear(int x, int y, Piece[][] board) {
        return false;
    }

    public boolean isClearY(int x, int y, int moveX, int moveY, Piece[][] board) {
        int increment = (int) Math.signum(moveY - y); // Determine the direction of movement
        for (int r = y + increment; r != moveY; r += increment) {
            if (!(board[r][x] instanceof NullPiece)) {
                return false;
            }
        }
        return true;
    }

    public boolean isClearX(int x, int y, int moveX, int moveY, Piece[][] board) {
        int increment = (int) Math.signum(moveX - x); // Determine the direction of movement
        for (int r = x + increment; r != moveX; r += increment) {
            if (!(board[y][r] instanceof NullPiece)) {
                return false;
            }
        }
        return true;
    }
}

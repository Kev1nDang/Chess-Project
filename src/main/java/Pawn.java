public class Pawn extends Piece {

    public Pawn(String piece, Color color, String iD) {
        super(piece, color, iD);
    }

    @Override
    public boolean validMove(int x, int y, int moveX, int moveY, Piece[][] board) {
        int direction = getColor() == Color.BLACK ? 1 : -1;
        if(moveX < 0 || moveX>7 || moveY >7 || moveY<0){
            return false;
        }
        if(board[y][x] instanceof NullPiece){
            return false;
        }
        else if (isClear(moveX, moveY, board) && x == moveX && y + direction == moveY) {
            return true;
        }
        else if (x == moveX && y + direction * 2 == moveY && (y == 1 || y == 6)
                && isClear(moveX, moveY, board) && isClear(moveX, moveY-direction, board)) {
            return true;
        }
        else if (!(board[moveY][moveX] instanceof NullPiece) &&
                board[moveY][moveX].getColor() != getColor() &&
                (x+1 == moveX || x-1 == moveX) && y+direction==moveY){
            return true;
        }
        return false;
    }

    @Override
    public boolean isClear(int x, int y, Piece[][] board) {
        return board[y][x] instanceof NullPiece;
    }

    @Override
    public boolean attacksSquare(int x, int y, int targetX, int targetY, Piece[][] board) {
        int direction = getColor() == Color.BLACK ? 1 : -1;
        return (targetX == x + 1 || targetX == x - 1) && targetY == y + direction;
    }
}

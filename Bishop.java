public class Bishop extends Piece{
    public Bishop(String piece, Color color, String iD) {
        super(piece, color, iD);
    }


    @Override
    public boolean validMove(int x, int y, int moveX, int moveY, Piece[][] board) {
        int dy = Math.abs(moveY - y);
        int dx = Math.abs(moveX - x);

        if(moveX < 0 || moveX>7 || moveY >7 || moveY<0){
            return false;
        }
        else return dy == dx &&
                isClearDiagonal(x, y, moveX, moveY, board)
                && (board[moveY][moveX] instanceof NullPiece ||
                board[moveY][moveX].getColor() != getColor());
    }


    public boolean isClearDiagonal(int x, int y, int moveX, int moveY, Piece[][] board){
        int directionX = moveX > x ? 1 : -1;
        int directionY = moveY > y ? 1 : -1;
        int distance = Math.abs(moveX - x);
        for(int i = 1; i < distance; i++){
            int r = y + i * directionY;
            int c = x + i * directionX;
            if(!(board[r][c] instanceof NullPiece)){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean isClear(int x, int y, Piece[][] board) {
        return board[y][x].getColor() != getColor();
    }


    public boolean isClearUp(int x, int y, int moveX, int moveY, Piece[][] board){
        for (int r = y; r < moveY; r++){
            for(int c = x; c < moveX; c++){
                if(board[r][c] != null){
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isClearDown(int x, int y, int moveX, int moveY, Piece[][] board){
        for (int r = y; r > moveY; r--){
            for(int c = x; c > moveX; c--){
                if(board[r][c] != null){
                    return false;
                }
            }
        }
        return true;
    }
}

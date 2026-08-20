public class Knight extends Piece{
    public Knight(String piece, Color color, String iD) {
        super(piece, color, iD);
    }

    @Override
    public boolean validMove(int x, int y, int moveX, int moveY, Piece[][] board) {
        int dx = x - moveX;
        int dy = y - moveY;
        if(moveX < 0 || moveX>7 || moveY >7 || moveY<0){
            return false;
        }
        else if(!(board[moveY][moveX] instanceof NullPiece) &&
                board[moveY][moveX].getColor().equals(getColor())){
            return false;
        }
        return(Math.abs(dx) == 1 && Math.abs(dy) == 2 ||Math.abs(dx) == 2 && Math.abs(dy) == 1);
    }

    @Override
    public boolean isClear(int x, int y, Piece[][] board) {
        return false;
    }
}
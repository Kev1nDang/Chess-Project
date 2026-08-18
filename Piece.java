import java.awt.*;

public abstract class Piece {
    private final Color color;
    private String iD;
    private String piece;
    public Piece(String piece, Color color, String iD) {
        this.piece = piece + " ";
        this.color = color;
        this.iD = iD;
    }

    public Color getColor(){
        return color;
    }
    public String getID() {
        return iD;
    }


    public void setID(String iD) {
        this.iD = iD;
    }

    public String getPiece(){
        return piece;
    }

    public void setPiece(String piece){
        this.piece = piece;
    }


    public abstract boolean validMove(int x, int y, int moveX, int moveY, Piece[][] board);

    public boolean validMove(Move move, Piece[][] board) {
        return validMove(move.x, move.y, move.moveX, move.moveY, board);
    }

    public boolean attacksSquare(int x, int y, int targetX, int targetY, Piece[][] board){
        return validMove(x, y, targetX, targetY, board);
    }

    public abstract boolean isClear(int x, int y, Piece[][] board);




}

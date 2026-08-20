public class Board {
    public Piece[][] board;
    private final String spacing = "══";
    public Board(){
        board = new Piece[8][8];
        for(int i =0; i < 8; i++){
            for (int j = 0; j < 8; j ++){
                board[i][j] = new NullPiece();
            }
        }
        initialize();
    }
    public Board(boolean filled){
        board = new Piece[8][8];
        for(int i =0; i < 8; i++){
            for (int j = 0; j < 8; j ++){
                board[i][j] = new NullPiece();
            }
        }
        if (filled){
            initialize();
        }
    }

    public void initialize(){
        Piece blackPawn = new Pawn ("♙", Color.BLACK, "0");
        Piece blackRook = new Rook("♖", Color.BLACK, "0");
        Piece blackKnight = new Knight("♘", Color.BLACK, "0");
        Piece blackKing = new King("♔", Color.BLACK, "0");
        Piece blackBishop = new Bishop("♗", Color.BLACK, "0");
        Piece blackQueen = new Queen("♕", Color.BLACK, "0");

        Piece whitePawn = new Pawn ("♟", Color.WHITE, "0");
        Piece whiteRook = new Rook("♜", Color.WHITE, "0");
        Piece whiteKnight = new Knight("♞", Color.WHITE, "0");
        Piece whiteKing = new King("♚", Color.WHITE, "0");
        Piece whiteBishop = new Bishop("♝", Color.WHITE, "0");
        Piece whiteQueen = new Queen("♛", Color.WHITE, "0");

        board[1][0] = blackPawn;
        board[1][1] = blackPawn;
        board[1][2] = blackPawn;
        board[1][3] = blackPawn;
        board[1][4] = blackPawn;
        board[1][5] = blackPawn;
        board[1][6] = blackPawn;
        board[1][7] = blackPawn;

        board[0][0] = blackRook;
        board[0][1] = blackKnight;
        board[0][2] = blackBishop;
        board[0][3] = blackKing;
        board[0][4] = blackQueen;
        board[0][5] = blackBishop;
        board[0][6] = blackKnight;
        board[0][7] = blackRook;

        board[6][0] = whitePawn;
        board[6][1] = whitePawn;
        board[6][2] = whitePawn;
        board[6][3] = whitePawn;
        board[6][4] = whitePawn;
        board[6][5] = whitePawn;
        board[6][6] = whitePawn;
        board[6][7] = whitePawn;

        board[7][0] = whiteRook;
        board[7][1] = whiteKnight;
        board[7][2] = whiteBishop;
        board[7][3] = whiteKing;
        board[7][4] = whiteQueen;
        board[7][5] = whiteBishop;
        board[7][6] = whiteKnight;
        board[7][7] = whiteRook;
    }

    public void display(){
        System.out.println("    A  B  C  D  E  F  G  H");
        System.out.println("   ╔"+spacing+"╦"+spacing+"╦"+spacing+"╦"+spacing+"╦"+spacing+"╦"+spacing+"╦"+spacing+"╦"+spacing+"╗");
        System.out.println(" 8 " + getRows(board[0]));
        System.out.println(getDivider());
        System.out.println(" 7 " + getRows(board[1]));
        System.out.println(getDivider());
        System.out.println(" 6 " + getRows(board[2]));
        System.out.println(getDivider());
        System.out.println(" 5 " + getRows(board[3]));
        System.out.println(getDivider());
        System.out.println(" 4 " + getRows(board[4]));
        System.out.println(getDivider());
        System.out.println(" 3 " + getRows(board[5]));
        System.out.println(getDivider());
        System.out.println(" 2 " + getRows(board[6]));
        System.out.println(getDivider());
        System.out.println(" 1 " + getRows(board[7]));
        System.out.println("   ╚"+spacing+"╩"+spacing+"╩"+spacing+"╩"+spacing+"╩"+spacing+"╩"+spacing+"╩"+spacing+"╩"+spacing+"╝");
    }

    public String getRows(Piece[] rows){
        return String.format("║%s║%s║%s║%s║%s║%s║%s║%s║", rows[0].getPiece(), rows[1].getPiece(),rows[2].getPiece(), rows[3].getPiece(), rows[4].getPiece(), rows[5].getPiece(), rows[6].getPiece(), rows[7].getPiece());
    }

    public String getDivider(){
        return "   ╠"+spacing+"╬"+spacing+"╬"+spacing+"╬"+spacing+"╬"+spacing+"╬"+spacing+"╬"+spacing+"╬"+spacing+"╣";
    }

    public void applyMove(Move move){
        board[move.moveY][move.moveX] = board[move.y][move.x];
        board[move.y][move.x] = new NullPiece();
    }

    public int[] findKing(Color color, Piece[][] board){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Piece p = board[r][c];
                if(p instanceof King && p.getColor() == color){
                    return new int[]{c, r};
                }
            }
        }
        return null;
    }

    public boolean isSquareAttacked(int x, int y, Color byColor, Piece[][] board){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Piece p = board[r][c];
                if(p instanceof NullPiece || p.getColor() != byColor){
                    continue;
                }
                if(p.attacksSquare(c, r, x, y, board)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInCheck(Color color, Piece[][] board){
        int[] kingPos = findKing(color, board);
        Color opponent = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        return isSquareAttacked(kingPos[0], kingPos[1], opponent, board);
    }

    public Piece[][] copyBoard(Piece[][] board){
        Piece[][] copy = new Piece[8][8];
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                copy[r][c] = board[r][c];
            }
        }
        return copy;
    }

    public boolean wouldBeInCheck(Move move, Color color, Piece[][] board){
        Piece[][] copy = copyBoard(board);
        copy[move.moveY][move.moveX] = copy[move.y][move.x];
        copy[move.y][move.x] = new NullPiece();
        return isInCheck(color, copy);
    }

    public boolean hasLegalMoves(Color color, Piece[][] board){
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                Piece p = board[y][x];
                if(p instanceof NullPiece || p.getColor() != color){
                    continue;
                }
                for(int moveY = 0; moveY < 8; moveY++){
                    for(int moveX = 0; moveX < 8; moveX++){
                        if(x == moveX && y == moveY){
                            continue;
                        }

                        Move move = new Move(x, y, moveX, moveY);
                        if(p.validMove(move, board) && !(wouldBeInCheck(move, color, board))){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(Color color, Piece[][] board){
        return !hasLegalMoves(color, board) && isInCheck(color, board);
    }

    public boolean isStalemate(Color color, Piece[][] board){
        return !isInCheck(color, board) && !hasLegalMoves(color, board);
    }
}

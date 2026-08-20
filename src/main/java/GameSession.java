import java.util.ArrayList;
import java.util.List;

public class GameSession {
    Board board = new Board();
    Color turn = Color.WHITE;
    boolean gameOver = false;
    String result = null;
    Color winner = null;

    public void makeMove(int fromX, int fromY, int toX, int toY) {
        if (gameOver) {
            throw new IllegalMoveException("Game is already over");
        }
        if (fromX < 0 || fromX > 7 || fromY < 0 || fromY > 7 || toX < 0 || toX > 7 || toY < 0 || toY > 7) {
            throw new IllegalMoveException("Move is out of bounds");
        }
        Piece piece = board.board[fromY][fromX];
        if (piece instanceof NullPiece) {
            throw new IllegalMoveException("No piece on that square");
        }
        if (piece.getColor() != turn) {
            throw new IllegalMoveException("It's " + turn + "'s turn");
        }
        Move move = new Move(fromX, fromY, toX, toY);
        if (!piece.validMove(move, board.board)) {
            throw new IllegalMoveException("Illegal move for this piece");
        }
        if (board.wouldBeInCheck(move, turn, board.board)) {
            throw new IllegalMoveException("That move would leave your king in check");
        }

        board.applyMove(move);
        turn = (turn == Color.WHITE) ? Color.BLACK : Color.WHITE;

        if (board.isCheckmate(turn, board.board)) {
            gameOver = true;
            result = "CHECKMATE";
            winner = (turn == Color.WHITE) ? Color.BLACK : Color.WHITE;
        } else if (board.isStalemate(turn, board.board)) {
            gameOver = true;
            result = "STALEMATE";
        }
    }

    public List<int[]> legalMovesFrom(int x, int y) {
        List<int[]> moves = new ArrayList<>();
        if (x < 0 || x > 7 || y < 0 || y > 7 || gameOver) {
            return moves;
        }
        Piece piece = board.board[y][x];
        if (piece instanceof NullPiece || piece.getColor() != turn) {
            return moves;
        }
        for (int moveY = 0; moveY < 8; moveY++) {
            for (int moveX = 0; moveX < 8; moveX++) {
                if (x == moveX && y == moveY) {
                    continue;
                }
                Move move = new Move(x, y, moveX, moveY);
                if (piece.validMove(move, board.board) && !board.wouldBeInCheck(move, turn, board.board)) {
                    moves.add(new int[]{moveX, moveY});
                }
            }
        }
        return moves;
    }

    public GameStateDTO toDTO(String gameId) {
        GameStateDTO dto = new GameStateDTO();
        dto.gameId = gameId;
        dto.board = new PieceDTO[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = board.board[y][x];
                dto.board[y][x] = (p instanceof NullPiece) ? null : new PieceDTO(p.getClass().getSimpleName(), p.getColor().toString());
            }
        }
        dto.turn = turn.toString();
        dto.inCheck = board.isInCheck(turn, board.board);
        dto.gameOver = gameOver;
        dto.result = result;
        dto.winner = (winner == null) ? null : winner.toString();
        return dto;
    }
}

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameState {
    Map <String, Integer> inputMap = Map.of
            ("A", 0,
                    "B", 1,
                    "C", 2,
                    "D", 3,
                    "E",4,
                    "F",5,
                    "G", 6,
                    "H", 7);


    Map <String, Integer> inputMap2 = Map.of
            ("1", 7,
                    "2", 6,
                    "3", 5,
                    "4", 4,
                    "5",3,
                    "6",2,
                    "7", 1,
                    "8", 0);
    public Board board;
    public Color playerColor;
    public GameState() {
        board = new Board();
        playerColor = Color.WHITE;
    }


    public void playGame(){
        while(true){
            board.display();
            if(playerColor ==Color.WHITE){
                System.out.println("White turns: ");
            }else{
                System.out.println("Black turns: ");
            }
            Move moveObj = getMove();
            board.applyMove(moveObj);
            if (playerColor == Color.WHITE){
                playerColor = Color.BLACK;
            }else{
                playerColor = Color.WHITE;
            }
            if (board.isCheckmate(playerColor, board.board)){
                board.display();
                Color winner = (playerColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
                System.out.println("Checkmate! " + winner + " wins.");
                break;
            }
            else if (board.isStalemate(playerColor, board.board)){
                board.display();
                System.out.println("Stalemate! The game is a draw.");
                break;
            }

        }
    }

    public Move getMove(){
        Scanner obj = new Scanner(System.in);
        boolean checker = true;
        while(checker) {
            System.out.println("Enter your move");
            String input = obj.nextLine();
            if(input.length() != 5){
                continue;
            }
            String x = input.substring(0, 1);
            String y = input.substring(1, 2);
            String moveX = input.substring(3, 4);
            String moveY = input.substring(4, 5);
            if(!inputMap.containsKey(x) || !inputMap.containsKey(moveX)){
                System.out.println("Invalid Move");
                continue;
            }if(!inputMap2.containsKey(y) || !inputMap2.containsKey(moveY)){
                System.out.println("Invalid Move");
                continue;
            }
            Move move = new Move(inputMap.get(x), inputMap2.get(y),inputMap.get(moveX), inputMap2.get(moveY));
            Piece piece = board.board[move.y][move.x];
            if (piece instanceof NullPiece || piece.getColor() != playerColor){
                System.out.println("Invalid Move");
                continue;
            }
            if(!piece.validMove(move,board.board)){
                System.out.println("Invalid Move");
                continue;
            }

            if(board.wouldBeInCheck(move, playerColor, board.board)){
                System.out.println("Invalid Move");
                continue;
            }
            return move;
        }

        return null;
    }


}

export type PieceColor = "WHITE" | "BLACK";

export type PieceType = "Pawn" | "Rook" | "Knight" | "Bishop" | "Queen" | "King";

export interface PieceDTO {
  type: PieceType;
  color: PieceColor;
}

export type BoardDTO = (PieceDTO | null)[][];

export interface GameState {
  gameId: string;
  board: BoardDTO;
  turn: PieceColor;
  inCheck: boolean;
  gameOver: boolean;
  result: "CHECKMATE" | "STALEMATE" | null;
  winner: PieceColor | null;
}

export type Square = [number, number];

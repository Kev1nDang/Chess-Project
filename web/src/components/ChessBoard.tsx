import type { BoardDTO, PieceColor, Square as SquareCoord } from "../types";
import Square from "./Square";

const FILES = ["A", "B", "C", "D", "E", "F", "G", "H"];
const RANKS = ["8", "7", "6", "5", "4", "3", "2", "1"];

interface Props {
  board: BoardDTO;
  turn: PieceColor;
  inCheck: boolean;
  selected: SquareCoord | null;
  legalMoves: SquareCoord[];
  lastMove: { from: SquareCoord; to: SquareCoord } | null;
  onSquareClick: (x: number, y: number) => void;
}

export default function ChessBoard({ board, turn, inCheck, selected, legalMoves, lastMove, onSquareClick }: Props) {
  const kingSquare = inCheck ? findKing(board, turn) : null;

  return (
    <div className="inline-block rounded-lg bg-[#12100c] p-3 shadow-2xl shadow-black/60 sm:p-4">
      <div className="grid grid-cols-[auto_1fr] gap-2">
        <div className="flex flex-col justify-around py-1 text-xs text-[#a89f8c] sm:text-sm">
          {RANKS.map((r) => (
            <span key={r} className="flex-1 flex items-center">
              {r}
            </span>
          ))}
        </div>

        <div className="grid w-[min(90vw,560px)] grid-cols-8 overflow-hidden rounded-md border border-black/40">
          {board.map((row, y) =>
            row.map((piece, x) => {
              const isSelected = !!selected && selected[0] === x && selected[1] === y;
              const isLegalMove = legalMoves.some(([mx, my]) => mx === x && my === y);
              const isLastMove =
                !!lastMove &&
                ((lastMove.from[0] === x && lastMove.from[1] === y) || (lastMove.to[0] === x && lastMove.to[1] === y));
              const isChecked = !!kingSquare && kingSquare[0] === x && kingSquare[1] === y;

              return (
                <Square
                  key={`${x}-${y}`}
                  x={x}
                  y={y}
                  piece={piece}
                  isLight={(x + y) % 2 === 0}
                  isSelected={isSelected}
                  isLegalMove={isLegalMove}
                  isLastMove={isLastMove}
                  isChecked={isChecked}
                  onClick={() => onSquareClick(x, y)}
                />
              );
            }),
          )}
        </div>

        <div />
        <div className="grid grid-cols-8 pt-1 text-center text-xs text-[#a89f8c] sm:text-sm">
          {FILES.map((f) => (
            <span key={f}>{f}</span>
          ))}
        </div>
      </div>
    </div>
  );
}

function findKing(board: BoardDTO, color: PieceColor): SquareCoord | null {
  for (let y = 0; y < 8; y++) {
    for (let x = 0; x < 8; x++) {
      const p = board[y][x];
      if (p && p.type === "King" && p.color === color) {
        return [x, y];
      }
    }
  }
  return null;
}

import type { PieceDTO } from "../types";
import PieceGlyph from "./PieceGlyph";

interface Props {
  x: number;
  y: number;
  piece: PieceDTO | null;
  isLight: boolean;
  isSelected: boolean;
  isLegalMove: boolean;
  isLastMove: boolean;
  isChecked: boolean;
  onClick: () => void;
}

export default function Square({ x, y, piece, isLight, isSelected, isLegalMove, isLastMove, isChecked, onClick }: Props) {
  const base = isLight ? "bg-board-light" : "bg-board-dark";

  return (
    <button
      type="button"
      onClick={onClick}
      data-square={`${x},${y}`}
      className={`relative flex aspect-square w-full items-center justify-center ${base} transition-colors duration-150 focus:outline-none`}
    >
      {isLastMove && <span className="absolute inset-0 bg-board-last/50" />}
      {isChecked && <span className="absolute inset-0 bg-board-check/70" />}
      {isSelected && <span className="absolute inset-0 ring-4 ring-inset ring-board-selected/90" />}

      {piece && <PieceGlyph piece={piece} />}

      {isLegalMove && !piece && (
        <span className="absolute h-[28%] w-[28%] rounded-full bg-black/25" />
      )}
      {isLegalMove && piece && (
        <span className="absolute inset-[8%] rounded-full ring-[5px] ring-black/35" />
      )}
    </button>
  );
}

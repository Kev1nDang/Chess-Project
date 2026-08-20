import type { PieceColor } from "../types";

interface Props {
  turn: PieceColor;
  inCheck: boolean;
  error: string | null;
  onNewGame: () => void;
}

export default function StatusBar({ turn, inCheck, error, onNewGame }: Props) {
  return (
    <div className="flex w-full max-w-[560px] flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
      <div className="flex items-center gap-3">
        <span
          className={`h-4 w-4 rounded-full border border-black/30 ${turn === "WHITE" ? "bg-[#fbfbf8]" : "bg-[#23211d]"}`}
        />
        <span className="text-sm text-[#d8d2c4] sm:text-base">
          <span className="font-semibold text-[#f4f1ea]">{turn === "WHITE" ? "White" : "Black"}</span> to move
          {inCheck && <span className="ml-2 font-semibold text-board-check">— in check</span>}
        </span>
      </div>

      <div className="flex items-center gap-3">
        {error && <span className="text-sm text-board-check">{error}</span>}
        <button
          type="button"
          onClick={onNewGame}
          className="rounded-md border border-[#3a362c] bg-[#1c1a15] px-4 py-2 text-sm font-medium text-[#f4f1ea] transition-colors hover:bg-[#28251d]"
        >
          New Game
        </button>
      </div>
    </div>
  );
}

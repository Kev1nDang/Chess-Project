import type { PieceColor } from "../types";

interface Props {
  result: "CHECKMATE" | "STALEMATE";
  winner: PieceColor | null;
  onNewGame: () => void;
}

export default function GameOverModal({ result, winner, onNewGame }: Props) {
  const title = result === "CHECKMATE" ? "Checkmate" : "Stalemate";
  const subtitle =
    result === "CHECKMATE"
      ? `${winner === "WHITE" ? "White" : "Black"} wins`
      : "The game is a draw";

  return (
    <div className="fixed inset-0 z-10 flex items-center justify-center bg-black/70 p-4">
      <div className="w-full max-w-sm rounded-xl border border-[#3a362c] bg-[#171410] p-8 text-center shadow-2xl">
        <h2 className="font-[var(--font-display)] text-3xl text-[#f4f1ea]">{title}</h2>
        <p className="mt-2 text-[#a89f8c]">{subtitle}</p>
        <button
          type="button"
          onClick={onNewGame}
          className="mt-6 w-full rounded-md bg-[#f4f1ea] px-4 py-2 font-medium text-[#171410] transition-opacity hover:opacity-90"
        >
          Play Again
        </button>
      </div>
    </div>
  );
}

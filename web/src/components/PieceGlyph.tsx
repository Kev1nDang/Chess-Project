import type { PieceDTO } from "../types";

const GLYPH: Record<PieceDTO["type"], string> = {
  King: "♚",
  Queen: "♛",
  Rook: "♜",
  Bishop: "♝",
  Knight: "♞",
  Pawn: "♟",
};

export default function PieceGlyph({ piece }: { piece: PieceDTO }) {
  return (
    <span
      className={`piece-glyph select-none ${piece.color === "WHITE" ? "piece-white" : "piece-black"}`}
      style={{ fontSize: "clamp(2rem, 6vw, 3.25rem)" }}
      aria-label={`${piece.color.toLowerCase()} ${piece.type.toLowerCase()}`}
    >
      {GLYPH[piece.type]}
    </span>
  );
}

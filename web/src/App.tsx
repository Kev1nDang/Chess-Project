import { useCallback, useEffect, useState } from "react";
import { ApiError, createGame, getLegalMoves, makeMove } from "./api";
import type { GameState, Square } from "./types";
import ChessBoard from "./components/ChessBoard";
import StatusBar from "./components/StatusBar";
import GameOverModal from "./components/GameOverModal";

export default function App() {
  const [game, setGame] = useState<GameState | null>(null);
  const [selected, setSelected] = useState<Square | null>(null);
  const [legalMoves, setLegalMoves] = useState<Square[]>([]);
  const [lastMove, setLastMove] = useState<{ from: Square; to: Square } | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  const startNewGame = useCallback(() => {
    setLoading(true);
    setError(null);
    setSelected(null);
    setLegalMoves([]);
    setLastMove(null);
    createGame()
      .then(setGame)
      .catch((e) => setError(e instanceof Error ? e.message : "Failed to start game"))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    startNewGame();
  }, [startNewGame]);

  const selectSquare = useCallback(
    (x: number, y: number, currentGame: GameState) => {
      setSelected([x, y]);
      setError(null);
      getLegalMoves(currentGame.gameId, x, y)
        .then(setLegalMoves)
        .catch(() => setLegalMoves([]));
    },
    [],
  );

  const handleSquareClick = (x: number, y: number) => {
    if (!game || game.gameOver) return;
    const piece = game.board[y][x];

    if (selected) {
      const [sx, sy] = selected;
      if (sx === x && sy === y) {
        setSelected(null);
        setLegalMoves([]);
        return;
      }
      const isLegal = legalMoves.some(([mx, my]) => mx === x && my === y);
      if (isLegal) {
        makeMove(game.gameId, selected, [x, y])
          .then((updated) => {
            setGame(updated);
            setLastMove({ from: selected, to: [x, y] });
            setSelected(null);
            setLegalMoves([]);
            setError(null);
          })
          .catch((e) => setError(e instanceof ApiError ? e.message : "Move failed"));
        return;
      }
      if (piece && piece.color === game.turn) {
        selectSquare(x, y, game);
        return;
      }
      setSelected(null);
      setLegalMoves([]);
      return;
    }

    if (piece && piece.color === game.turn) {
      selectSquare(x, y, game);
    }
  };

  return (
    <div className="flex min-h-screen flex-col items-center gap-6 px-4 py-10 sm:py-16">
      <header className="text-center">
        <h1 className="font-[var(--font-display)] text-3xl tracking-wide text-[#f4f1ea] sm:text-4xl">Chess</h1>
        <p className="mt-1 text-sm text-[#a89f8c]">Local two-player · click a piece, then its destination</p>
      </header>

      {loading && !game && <p className="text-[#a89f8c]">Loading board…</p>}

      {game && (
        <>
          <StatusBar turn={game.turn} inCheck={game.inCheck} error={error} onNewGame={startNewGame} />
          <ChessBoard
            board={game.board}
            turn={game.turn}
            inCheck={game.inCheck}
            selected={selected}
            legalMoves={legalMoves}
            lastMove={lastMove}
            onSquareClick={handleSquareClick}
          />
        </>
      )}

      {game?.gameOver && game.result && (
        <GameOverModal result={game.result} winner={game.winner} onNewGame={startNewGame} />
      )}
    </div>
  );
}

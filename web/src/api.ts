import type { GameState, Square } from "./types";

const API_BASE = import.meta.env.VITE_API_BASE ?? "http://localhost:7000";

export class ApiError extends Error {}

async function handle<T>(res: Response): Promise<T> {
  if (!res.ok) {
    const body = await res.json().catch(() => ({ message: res.statusText }));
    throw new ApiError(body.message ?? "Request failed");
  }
  return res.json();
}

export function createGame(): Promise<GameState> {
  return fetch(`${API_BASE}/api/games`, { method: "POST" }).then((r) => handle<GameState>(r));
}

export function getGame(id: string): Promise<GameState> {
  return fetch(`${API_BASE}/api/games/${id}`).then((r) => handle<GameState>(r));
}

export function getLegalMoves(id: string, x: number, y: number): Promise<Square[]> {
  return fetch(`${API_BASE}/api/games/${id}/moves?x=${x}&y=${y}`).then((r) => handle<Square[]>(r));
}

export function makeMove(id: string, from: Square, to: Square): Promise<GameState> {
  return fetch(`${API_BASE}/api/games/${id}/move`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ fromX: from[0], fromY: from[1], toX: to[0], toY: to[1] }),
  }).then((r) => handle<GameState>(r));
}

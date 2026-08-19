# Chess Engine (Java)

A fully object-oriented chess engine written from scratch in Java — no game-logic libraries, no framework, just core data structures and algorithms. Two players take turns entering moves in a terminal; the engine validates every move, tracks check, and detects checkmate and stalemate.

## Highlights

- **Complete rule engine for legal-move validation.** Every piece (pawn, rook, knight, bishop, queen, king) implements its own movement rules through a shared `Piece` abstraction, including path-blocking checks for sliding pieces (rook/bishop/queen) and pawn-specific logic for forward moves, double-step opening moves, and diagonal captures.
- **Check, checkmate, and stalemate detection.** `Board` simulates every legal move for a side (`hasLegalMoves`), attacks each square via each piece's own attack pattern (`isSquareAttacked`), and rejects any move that would leave the mover's own king in check (`wouldBeInCheck`) — the same brute-force approach used in many teaching chess engines.
- **69 unit tests across 7 test classes** (JUnit 5), covering piece movement rules, blocking logic, and board state — written alongside the implementation, not bolted on after.
- **Clean OOP design**: an abstract `Piece` base class with polymorphic `validMove`/`attacksSquare` implementations per piece type, keeping the board and game loop decoupled from any single piece's rules.

## Features

- Standard 8×8 board with full initial setup, rendered as a Unicode-boxed grid in the terminal
- Two-player local play, turn enforcement, and terminal move input (e.g. `E2 E4`)
- Full legal-move validation for all six piece types, including blocked-path detection for rooks, bishops, and queens
- Check detection (`isInCheck`), checkmate detection (`isCheckmate`), and stalemate detection (`isStalemate`)
- Moves that would leave your own king in check are rejected before they're applied

## Tech Stack

- **Java** (no external runtime dependencies)
- **JUnit 5** for unit testing
- IntelliJ IDEA project structure

## Getting Started

### Compile & run

```bash
javac Main.java
java Main
```

Moves are entered as `<from> <to>` using file (A–H) and rank (1–8), e.g.:

```
E2 E4
```

### Run the tests

Add JUnit 5 to your classpath (or open the project in IntelliJ, which resolves it automatically), then run any of the `*Test.java` classes:

```
BishopTest, BoardTest, KingTest, KnightTest, PawnTest, QueenTest, RookTest
```

## Project Structure

| Class | Responsibility |
|---|---|
| `Piece` (abstract) | Shared piece state and the `validMove`/`attacksSquare` contract |
| `Pawn`, `Rook`, `Knight`, `Bishop`, `Queen`, `King` | Per-piece movement and attack rules |
| `Board` | Board state, rendering, move application, check/checkmate/stalemate logic |
| `GameState` | Turn loop, move parsing, and input validation |
| `Move` | Simple move representation (from/to coordinates) |
| `Color` | White/black enum |

## Roadmap

Not yet implemented:

- Castling
- En passant
- Pawn promotion
- Graphical UI (currently console-only)

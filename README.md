# Console Chess (Java)

A simple, console-based chess game written in Java.  
It runs on a single device in the terminal and allows two players to play locally by taking turns entering moves.

> ⚠️ **Note:** This is an early version. There is no UI, and check/checkmate detection is not fully implemented.

---

## ⭐ Features

- ♟️ Standard 8×8 chessboard
- 👥 Two-player local play (same device)
- ⌨️ Text-based move input (e.g., `e2 e4`)
- 🔍 Basic piece movement for:
  - Pawn  
  - Rook  
  - Knight  
  - Bishop  
  - Queen  
  - King

---

## 🚧 Current Limitations

To be transparent about the game state:

- ❌ **Check and checkmate detection are incomplete**
- ❌ Does not reliably enforce check rules
- ❌ Game does not automatically end on checkmate
- ⚠️ Limited validation for special rules:
  - Castling not fully implemented
  - En passant not implemented
  - Pawn promotion may be missing or simple
- 🧱 **No graphical UI**
  - Runs entirely in the console

---

## 📦 Requirements

- Java (JDK 8 or higher)
- Terminal / command prompt

---

## ▶️ How to Compile & Run

From the project directory:

```bash
javac Main.java
java Main

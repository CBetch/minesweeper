# Minesweeper
Caleb Bettcher, Kyndra Nelson

Minesweeper final project for CSCI 4448-5448 (Object Oriented Programming) | Spring 2026

## Mid-Project Review (3/17/2026)

### Design Patterns

**1. Builder - `Grid.Builder`**
The `Grid` class uses the Builder pattern to construct a minesweeper grid. We used the
Builder pattern in order to avoid telescoping constructors with (rows, cols, mines), and
to allow for easy expansion with additional features.

**2. Observer - `TileObserver`**
The `TileObserver` interface is implemented by the `Tile` class allowing Tiles to automatically
update each other when `placeMine()` is called via `notifyObservers()`. This eliminates the 
need for a manual `updateStatuses()` sweep across the whole grid when a change is made.

**3. Factory - `GameFactory`**
The `GameFactory` class creates pre-configured `Game` instances for each standard
difficulty (Beginner 9x9/10 mines, Intermediate 16x16/40 mines, Expert 30x16/99 mines), with 
easy expansion for custom difficulties.

### Object-Oriented Principles

- **Coding to abstractions** - `Game` interacts with `Grid` through its public interfaces
  (`getTile()`, `getRows()`, ...) rather than directly accessing grid's `Tile[][]` array.
- **Polymorphism** - `Tile` implements `TileObserver`, meaning every tile can act as both
  a subject and an observer through the same interface.
- **Dependency injection** - `Game` receives a configured `Grid` rather than constructing
  one internally, allowing for a test grid to be injected directly.

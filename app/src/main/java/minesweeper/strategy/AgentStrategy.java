package minesweeper.strategy;

import minesweeper.MinesweeperGame;

public interface AgentStrategy {
    // Returns an integer array [row, col] of move to play
    int[] selectTile(MinesweeperGame game);
}

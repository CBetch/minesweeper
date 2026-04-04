package minesweeper.strategy;

import minesweeper.Grid;
import minesweeper.MinesweeperGame;
import minesweeper.Tile;

import java.util.ArrayList;
import java.util.List;

public class CheatingAgentStrategy implements AgentStrategy {
    public CheatingAgentStrategy() {}

    @Override
    public int[] selectTile(MinesweeperGame game) {
        Grid grid = game.getGrid();

        // Return first hidden, non-mine tile
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {

                Tile tile = grid.getTile(row, col);
                if (tile.isHidden() && !tile.isMine()) return new int[]{row, col};

            }
        }
        return new int[]{0, 0};
    }
}

package minesweeper.strategy;

import minesweeper.Grid;
import minesweeper.MinesweeperGame;
import minesweeper.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAgentStrategy implements AgentStrategy {
    private final Random random;

    public RandomAgentStrategy() {
        this.random = new Random();
    }

    @Override
    public int[] selectTile(MinesweeperGame game) {
        Grid grid = game.getGrid();

        // Collect every hidden tile position
        List<int[]> hiddenTiles = new ArrayList<>();
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {

                Tile tile = grid.getTile(row, col);
                if (tile.isHidden()) hiddenTiles.add(new int[]{row, col});

            }
        }

        if (hiddenTiles.isEmpty()) {
            return null;
        }

        return hiddenTiles.get(random.nextInt(hiddenTiles.size()));
    }
}

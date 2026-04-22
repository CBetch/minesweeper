package minesweeper;

import minesweeper.strategy.AgentStrategy;

public class MinesweeperDriver {
    private AgentStrategy strategy;

    public MinesweeperDriver(AgentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(AgentStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean takeTurn(MinesweeperGame game) {
        if (game.isGameOver()) return false;

        int[] move = strategy.selectTile(game);
        // clickTile(row, col)
        game.clickTile(move[0], move[1]);
        return true;
    }

    public int playToCompletion(MinesweeperGame game) {
        int turns = 0;
        while (!game.isGameOver() && takeTurn(game)) {
            turns++;
        }
        return turns;
    }

//    public void printBoard(MinesweeperGame game) {
//        Grid grid = game.getGrid();
//        for (int row = 0; row < grid.getRows(); row++) {
//            for (int col = 0; col < grid.getCols(); col++) {
//                Tile tile = grid.getTile(row, col);
//                if (tile.isMine()) {
//                    System.out.print("* ");
//                } else if (tile.isEmpty()) {
//                    System.out.print(". ");
//                } else {
//                    System.out.print(tile.getCount() + " ");
//                }
//            }
//            System.out.println();
//        }
//    }
}
package minesweeper;

public class MinesweeperGame {
    private final Grid grid;
    private boolean gameOver = false;
    private boolean wonGame;

    public MinesweeperGame(Grid grid) {
        this.grid = grid;
    }

    private boolean checkWin() {
        for (int row = 0; row < grid.getRows(); row ++) {
            for (int col = 0; col < grid.getCols(); col++) {
                // We only win if all non-mine tiles are revealed
                Tile tile = grid.getTile(row, col);
                if (tile.isHidden() && !tile.isMine()) return false;
            }
        }
        return true;
    }

    public void clickTile(int row, int col) {
        if (gameOver) return;

        Tile tile = grid.getTile(row, col);
        if (!tile.isHidden()) return; // already revealed, do nothing

        if (tile.click()) {
            gameOver = true;
            wonGame = false;
            // TODO - trigger loss screen
        } else if (checkWin()){
            gameOver = true;
            wonGame = true;
            // TODO - trigger win screen
        }
    }

    // ----- Getters -----

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWonGame() {
        return wonGame;
    }
}
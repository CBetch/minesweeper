package minesweeper;

import org.junit.jupiter.api.Test;

public class MinesweeperGameTest {
    public Grid set3x3Grid() {
        // E-1-1
        // E-2-M
        // E-2-M
        int NUMBER_ROWS = 3;
        int NUMBER_COLS = 3;
        int NUMBER_MINES = 0;
        Grid grid = new Grid.Builder()
                .numberRows(NUMBER_ROWS)
                .numberCols(NUMBER_COLS)
                .numberMines(NUMBER_MINES)
                .build();
        grid.placeSpecificMine(1, 2);
        grid.placeSpecificMine(2, 2);

        return grid;
    }

    @Test
    public void testWinSetGet() {
        MinesweeperGame game = new MinesweeperGame(set3x3Grid());
        game.clickTile(0,0);
        game.clickTile(0,1);
        game.clickTile(0,2);
        game.clickTile(1,1);
        game.clickTile(2,1);

        assert(game.isGameOver());
        assert(game.isWonGame());
    }

    @Test
    public void testLooseSetGet() {
        MinesweeperGame game = new MinesweeperGame(set3x3Grid());
        game.clickTile(0,2);
        game.clickTile(1,2); // clicked a MINE

        assert(game.isGameOver());
        assert(!game.isWonGame());
    }
}

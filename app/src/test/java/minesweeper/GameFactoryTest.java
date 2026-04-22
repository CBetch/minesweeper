package minesweeper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameFactoryTest {

    private int getNumberMines(Grid grid) {
        int countedMines = 0;
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                if (grid.getTile(row, col).isMine()) countedMines++;
            }
        }
        return countedMines;
    }

    @Test
    public void testBeginner() {
        MinesweeperGame game = GameFactory.beginner();
        // first click in initializes mines
        game.clickTile(0, 0);
        assertEquals(9, game.getGrid().getRows());
        assertEquals(9, game.getGrid().getCols());
        assertEquals(10, getNumberMines(game.getGrid()));
    }

    @Test
    public void testIntermediate() {
        MinesweeperGame game = GameFactory.intermediate();
        // first click in initializes mines
        game.clickTile(0, 0);
        assertEquals(16, game.getGrid().getRows());
        assertEquals(16, game.getGrid().getCols());
        assertEquals(40, getNumberMines(game.getGrid()));
    }

    @Test
    public void testExpert() {
        MinesweeperGame game = GameFactory.expert();
        // first click in initializes mines
        game.clickTile(0, 0);
        assertEquals(30, game.getGrid().getRows());
        assertEquals(16, game.getGrid().getCols());
        assertEquals(99, getNumberMines(game.getGrid()));
    }

    @Test
    public void testCustom() {
        MinesweeperGame game = GameFactory.custom(5, 10, 8);
        // first click in initializes mines
        game.clickTile(0, 0);
        assertEquals(5, game.getGrid().getRows());
        assertEquals(10, game.getGrid().getCols());
        assertEquals(8, getNumberMines(game.getGrid()));
    }
}
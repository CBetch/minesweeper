package minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    @Test
    void testNumberMineAssignments() {
        Tile empty = new Tile();
        Tile one = new Tile();
        Tile mine = new Tile();
        mine.placeMine();

        empty.connect(one);
        one.connect(mine);
        empty.updateStatus();
        one.updateStatus();
        mine.updateStatus();

        assert (empty.isEmpty());
        assert (one.getCount() == 1);
        assert (mine.isMine());
        assert (mine.isHidden());
    }

    @Test
    void testCascadingEmpty() {
        // E-1-M
        // E-2-2
        // E-1-M
        int NUMBER_ROWS = 3;
        int NUMBER_COLS = 3;
        int NUMBER_MINES = 0;
        Grid grid = new Grid(NUMBER_ROWS, NUMBER_COLS, NUMBER_MINES);
        grid.placeSpecificMine(0, 2);
        grid.placeSpecificMine(2, 2);

        // Game shouldn't end since we click empty row
        assertFalse(grid.getTile(1,0).click());

        // make sure first two columns were revealed completely
        assertFalse(grid.getTile(0,0).isHidden());
        assertFalse(grid.getTile(1,0).isHidden());
        assertFalse(grid.getTile(2,0).isHidden());
        assertFalse(grid.getTile(0,1).isHidden());
        assertFalse(grid.getTile(1,1).isHidden());
        assertFalse(grid.getTile(2,1).isHidden());
        assertTrue(grid.getTile(0,2).isHidden());
        assertTrue(grid.getTile(1,2).isHidden());
        assertTrue(grid.getTile(2,2).isHidden());
    }
}

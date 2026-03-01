package minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {
    @Test
    public void testGridConstructionPlacesMines() {
        int NUMBER_ROWS = 2;
        int NUMBER_COLS = 2;
        int NUMBER_MINES = 2;
        Grid grid = new Grid(NUMBER_ROWS, NUMBER_COLS, NUMBER_MINES);

        int countedMines = 0;
        for (int row = 0; row < NUMBER_ROWS; row++) {
            for (int col = 0; col < NUMBER_COLS; col++) {
                if (grid.getTile(row, col).isMine()) countedMines++;
            }
        }
        assert(countedMines == 2);
    }

    @Test
    public void testGridUpdatesStatuses() {
        // E-1-1
        // E-2-M
        // E-2-M
        int NUMBER_ROWS = 3;
        int NUMBER_COLS = 3;
        int NUMBER_MINES = 0;
        Grid grid = new Grid(NUMBER_ROWS, NUMBER_COLS, NUMBER_MINES);
        grid.placeSpecificMine(1, 2);
        grid.placeSpecificMine(2, 2);

        assertEquals(3, grid.getTile(0,0).numNeighbors());
        assertEquals(5, grid.getTile(0,1).numNeighbors());
        assertEquals(3, grid.getTile(0,2).numNeighbors());
        assertEquals(5, grid.getTile(1,0).numNeighbors());
        assertEquals(8, grid.getTile(1,1).numNeighbors());
        assertEquals(5, grid.getTile(1,2).numNeighbors());
        assertEquals(3, grid.getTile(2,0).numNeighbors());
        assertEquals(5, grid.getTile(2,1).numNeighbors());
        assertEquals(3, grid.getTile(2,2).numNeighbors());



        assertTrue(grid.getTile(0,0).isEmpty());
        assertTrue(grid.getTile(1,0).isEmpty());
        assertTrue(grid.getTile(2,0).isEmpty());
        assertTrue(grid.getTile(1,2).isMine());
        assertTrue(grid.getTile(2,2).isMine());
        assertEquals(1, grid.getTile(0,1).getCount());
        assertEquals(1, grid.getTile(0,2).getCount());
        assertEquals(2, grid.getTile(1,1).getCount());
        assertEquals(2, grid.getTile(2,1).getCount());
    }
}

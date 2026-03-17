package minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {
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
    public void testGridConstructionPlacesMines() {
        int NUMBER_ROWS = 2;
        int NUMBER_COLS = 2;
        int NUMBER_MINES = 2;
        Grid grid = new Grid.Builder()
                .numberRows(NUMBER_ROWS)
                .numberCols(NUMBER_COLS)
                .numberMines(NUMBER_MINES)
                .build();

        assert(getNumberMines(grid) == 2);
    }

    @Test
    public void testRandomGridCreation() {
        int NUMBER_ROWS = 20;
        int NUMBER_COLS = 20;
        int NUMBER_MINES = 40;
        Grid grid = new Grid.Builder()
                .numberRows(NUMBER_ROWS)
                .numberCols(NUMBER_COLS)
                .numberMines(NUMBER_MINES)
                .build();

        assert(getNumberMines(grid) == NUMBER_MINES);
    }

    @Test
    public void testTileObserversUpdateTileStatuses() {
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

        // Check all tiles have correct number of neighbors
        assertEquals(3, grid.getTile(0,0).numNeighbors());
        assertEquals(5, grid.getTile(0,1).numNeighbors());
        assertEquals(3, grid.getTile(0,2).numNeighbors());
        assertEquals(5, grid.getTile(1,0).numNeighbors());
        assertEquals(8, grid.getTile(1,1).numNeighbors());
        assertEquals(5, grid.getTile(1,2).numNeighbors());
        assertEquals(3, grid.getTile(2,0).numNeighbors());
        assertEquals(5, grid.getTile(2,1).numNeighbors());
        assertEquals(3, grid.getTile(2,2).numNeighbors());

        // Check tiles values are as expected according to diagram
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

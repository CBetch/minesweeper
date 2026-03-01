package minesweeper;

import java.util.Random;

public class Grid {
    public static Random rand = new Random();
    private final Tile[][] tiles;
    private int rows;
    private int cols;

    public Grid(int rows, int cols, int numberMines) {
        this.tiles = initializeGrid(rows, cols);
        this.rows = rows;
        this.cols = cols;

        placeMines(numberMines);
        connectNeighbors();
        updateStatuses();
    }

    private Tile[][] initializeGrid(int rows, int cols) {
        Tile[][] tiles = new Tile[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                tiles[row][col] = new Tile();
            }
        }
        return tiles;
    }

    private void placeMines(int numberMines) {
        int placedMines = 0;
        while (placedMines < numberMines) {
            int row = rand.nextInt(this.rows);
            int col = rand.nextInt(this.cols);

            if (!tiles[row][col].isMine()) {
                tiles[row][col].placeMine();
                placedMines++;
            }
        }
    }

    private void connectNeighbors() {
        // Row wise
        for (int row = 0; row < this.rows-1; row++) {
            for (int col = 0; col < this.cols; col++) {
                tiles[row][col].connect(tiles[row+1][col]);
            }
        }
        // Column wise
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols-1; col++) {
                tiles[row][col].connect(tiles[row][col+1]);
            }
        }
        // "\" Diagonals
        for (int row = 0; row < this.rows-1; row++) {
            for (int col = 0; col < this.cols-1; col++) {
                tiles[row][col].connect(tiles[row+1][col+1]);
            }
        }
        // "/" Diagonals
        for (int row = 0; row < this.rows-1; row++) {
            for (int col = 1; col < this.cols; col++) {
                tiles[row][col].connect(tiles[row+1][col-1]);
            }
        }
    }

    private void updateStatuses() {
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                tiles[row][col].updateStatus();
            }
        }
    }

    void placeSpecificMine(int row, int col) {
        tiles[row][col].placeMine();
        updateStatuses();
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }
}

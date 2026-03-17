package minesweeper;

import java.util.Random;

public class Grid {
    public static Random rand = new Random();
    private final Tile[][] tiles;
    private int rows;
    private int cols;
    private int numberMines;

    private Grid(Builder builder) {
        this.rows = builder.rows;
        this.cols = builder.cols;
        this.numberMines = builder.numberMines;
        this.tiles = initializeGrid(builder.rows, builder.cols);

        placeMines(builder.numberMines);
        connectNeighbors();
    }

    public static class Builder {
        private int rows = 9;
        private int cols = 9;
        private int numberMines = 10;

        public Builder() {}

        public Builder numberRows (int rows) {
            this.rows = rows;
            return this;
        }

        public Builder numberCols (int cols) {
            this.cols = cols;
            return this;
        }

        public Builder numberMines(int numberMines) {
            this.numberMines = numberMines;
            return this;
        }

        public Grid build() {
            return new Grid(this);
        }
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

    void placeSpecificMine(int row, int col) {
        tiles[row][col].placeMine();
    }

    // ----- Getters -----
    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return cols;
    }

    public int getNumberMines() {
        return numberMines;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }
}

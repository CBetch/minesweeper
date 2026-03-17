package minesweeper;

public class GameFactory {
    public static MinesweeperGame beginner() {
        Grid grid = new Grid.Builder()
                .numberRows(9)
                .numberCols(9)
                .numberMines(10)
                .build();
        return new MinesweeperGame(grid);
    }

    public static MinesweeperGame intermediate() {
        Grid grid = new Grid.Builder()
                .numberRows(16)
                .numberCols(16)
                .numberMines(40)
                .build();
        return new MinesweeperGame(grid);
    }

    public static MinesweeperGame expert() {
        Grid grid = new Grid.Builder()
                .numberRows(30)
                .numberCols(16)
                .numberMines(99)
                .build();
        return new MinesweeperGame(grid);
    }

    public static MinesweeperGame custom(int rows, int cols, int mines) {
        Grid grid = new Grid.Builder()
                .numberRows(rows)
                .numberCols(cols)
                .numberMines(mines)
                .build();
        return new MinesweeperGame(grid);
    }
}
package minesweeper;

import javax.swing.SwingUtilities;

public class MinesweeperLauncher {
    // ---- Human Gameplay ----
    public static void main(String[] args){
        MinesweeperGame game = GameFactory.beginner();

        //SwingUtilities.invokeLater(() -> {
        new MinesweeperGUI();
        //});
    }

}

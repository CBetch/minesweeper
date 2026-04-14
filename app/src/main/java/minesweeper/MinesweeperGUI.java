package minesweeper;

import javax.swing.*;
import java.awt.*;


public class MinesweeperGUI extends JFrame{
    private MinesweeperGame game;
    private JPanel boardPanel;
    private JPanel gameStartPanel;
    private JButton[][] buttons;

    public MinesweeperGUI(){
        this.setTitle("Minesweeper");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());
        this.setSize(600,600);
        //creating menu to show the user for them to choose what game they want to play
        this.createMenu();

        this.setVisible(true);

        //starting the game with default beginner if no input from the user
        //this.startNewGame(GameFactory.beginner());
    }

    // ----- Menu -----
    private void createMenu(){
        gameStartPanel = new JPanel();
        gameStartPanel.setLayout(new GridLayout(4,1));

        JButton beginner = new JButton("Beginner");
        JButton intermediate = new JButton("Intermediate");
        JButton expert = new JButton("Expert");
        JButton custom = new JButton("Custom");

        beginner.addActionListener(e -> startNewGame(GameFactory.beginner()));
        intermediate.addActionListener(e -> startNewGame(GameFactory.intermediate()));
        expert.addActionListener(e -> startNewGame(GameFactory.expert()));
        custom.addActionListener(e -> createCustomGame());

        gameStartPanel.add(beginner);
        gameStartPanel.add(intermediate);
        gameStartPanel.add(expert);
        gameStartPanel.add(custom);

        showPanel(gameStartPanel);
    }
    // ---- One Screen At A Time
    private void showPanel(JPanel panel){
        getContentPane().removeAll();
        add(panel);

        revalidate();
        repaint();
    }

    // ----- Custom Game Menu -----
    private void createCustomGame(){
        String rowsInput = JOptionPane.showInputDialog(this, "Enter # of rows: ");
        String colsInput = JOptionPane.showInputDialog(this, "Enter # of cols: ");
        String minesInput = JOptionPane.showInputDialog(this, "Enter # of mines: ");

        int rows = Integer.parseInt(rowsInput);
        int cols = Integer.parseInt(colsInput);
        int mines = Integer.parseInt(minesInput);

        MinesweeperGame game = GameFactory.custom(rows, cols, mines);
        startNewGame(game);
    }

    // ----- New Game -----
    private void startNewGame(MinesweeperGame newGame){
        this.game = newGame;

        //look at checking for old game boards
        if(boardPanel != null){
            remove(boardPanel);
        }

        int rows = game.getGrid().getRows();
        int cols = game.getGrid().getCols();

        boardPanel = new JPanel(new GridLayout(rows, cols));
        buttons = new JButton[rows][cols];

        for(int r = 0; r < rows ; r++){
            for(int c = 0; c < cols ; c++){
                JButton button = new JButton();
                buttons[r][c] = button;

                int row = r;
                int col = c;

                button.addActionListener(e -> clickBoard(row, col));
                boardPanel.add(button);
            }
        }

        showPanel(boardPanel);
        updateBoard();
    }

    // ----- Clicks -----
    private void clickBoard(int row, int col){
        if(game.isGameOver()){
            return;
        }

        game.clickTile(row, col);
        updateBoard();

        if(game.isGameOver()){
            if(game.isWonGame()){
                JOptionPane.showMessageDialog(this,"you win! ");
            }else{
                JOptionPane.showMessageDialog(this,"you lose :( ");
            }
            createMenu();
        }
    }

    // ----- Update -----
    public void updateBoard(){
        Grid grid = game.getGrid();
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                Tile tile = grid.getTile(row, col);
                JButton button = buttons[row][col];

                if(tile.isHidden()){
                    button.setText(" ");
                } else if (tile.isMine()) {
                    button.setText("* ");
                } else if (tile.isEmpty()) {
                    button.setText("0 ");
                } else {
                    button.setText(tile.getCount() + " ");
                }
            }
        }
    }
}
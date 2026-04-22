package minesweeper;

import javax.swing.*;
import java.awt.*;


public class MinesweeperGUI extends JFrame{
    private MinesweeperGame game;
    private JPanel boardPanel;
    private JPanel gameMenuPanel;
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
    }

    // ----- Background For Menu -----
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(new Color(0, 0, 0, 20));

            for(int i = 0; i < 30; i++){
                int x = (int)(Math.random() * getWidth());
                int y = (int)(Math.random() * getHeight());
                g.fillOval(x, y, 5, 5);
            }
        }
    }

    // ----- Menu -----
    private void createMenu(){
        gameMenuPanel = new BackgroundPanel();
        gameMenuPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("MINESWEEPER");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 42));
        title.setBorder(BorderFactory.createEmptyBorder(200,0,10,0));
        gameMenuPanel.add(title, BorderLayout.PAGE_START);

        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(200, 70));
        startButton.setFocusPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        startButton.addActionListener(e -> this.createSecondMenu());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(startButton);
        centerPanel.setOpaque(false);
        gameMenuPanel.add(centerPanel, BorderLayout.CENTER);

        showPanel(gameMenuPanel);
    }

    // ----- Second Page Menu -----
    private void createSecondMenu(){
        gameStartPanel = new JPanel();
        gameStartPanel.setLayout(new GridLayout(4,1));

        JButton beginner = new JButton("Beginner");
        JButton intermediate = new JButton("Intermediate");
        JButton expert = new JButton("Expert");
        JButton custom = new JButton("Custom");

        Font font = new Font("Monospaced", Font.PLAIN, 25);
        beginner.addActionListener(e -> startNewGame(GameFactory.beginner()));
        beginner.setFont(font);
        intermediate.addActionListener(e -> startNewGame(GameFactory.intermediate()));
        intermediate.setFont(font);
        expert.addActionListener(e -> startNewGame(GameFactory.expert()));
        expert.setFont(font);
        custom.addActionListener(e -> createCustomGame());
        custom.setFont(font);

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
        JTextField rowsInput = new JTextField();
        JTextField colsInput = new JTextField();
        JTextField minesInput = new JTextField();

        Object[] message = {
                "# of rows: ", rowsInput,
                "# of cols: ", colsInput,
                "# of mines; ", minesInput
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Custom Game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if(option == JOptionPane.OK_OPTION){
            try{
                int rows = Integer.parseInt(rowsInput.getText());
                int cols = Integer.parseInt(colsInput.getText());
                int mines = Integer.parseInt(minesInput.getText());

                MinesweeperGame game = GameFactory.custom(rows, cols, mines);
                startNewGame(game);
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "# # # required usage, try again", "Custom Game", JOptionPane.PLAIN_MESSAGE, null);
            }
        }
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

        int tileSize = 600/Math.max(rows, cols);
        Font font = new Font("Monospaced", Font.PLAIN, tileSize/2);

        boardPanel = new JPanel(new GridLayout(rows, cols));
        buttons = new JButton[rows][cols];

        for(int r = 0; r < rows ; r++){
            for(int c = 0; c < cols ; c++){
                JButton button = new JButton();
                button.setFont(font);

                buttons[r][c] = button;

                int row = r;
                int col = c;

                button.addMouseListener(new java.awt.event.MouseAdapter(){
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e){
                        Grid grid = game.getGrid();
                        //right click allows the user to flag a certain block
                        if(SwingUtilities.isRightMouseButton(e)){
                            grid.getTile(row, col).toggleFlag();
                            updateBoard();
                            repaint();
                        } else if(SwingUtilities.isLeftMouseButton(e)){ //still has left click capabilities
                            clickBoard(row, col);
                        }

                    }
                });
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
                JOptionPane.showMessageDialog(this,"All empty tiles clicked! You win", "Game Over", JOptionPane.PLAIN_MESSAGE, null);
            }else{
                JOptionPane.showMessageDialog(this,"Mine Clicked! You lose", "Game Over", JOptionPane.PLAIN_MESSAGE, null);
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
                button.setOpaque(true);
                button.setContentAreaFilled(true);

                if(tile.isFlagged()){
                    button.setText("F");
                    button.setForeground(Color.PINK);
                } else if(tile.isHidden()){
                    button.setText(" ");
                } else if (tile.isMine()) {
                    button.setText("* ");
                    button.setForeground(Color.RED);
                } else if (tile.isEmpty()) {
                    button.setText("0 ");
                    button.setBackground(Color.LIGHT_GRAY);
                } else {
                    button.setText(tile.getCount() + " ");

                    switch(tile.getCount()){
                        case 1 -> button.setForeground(Color.BLUE);
                        case 2 -> button.setForeground(new Color(245, 227, 66));
                        case 3 -> button.setForeground(new Color(255, 151, 23));
                        default -> button.setForeground(Color.BLACK);
                    }
                }
            }
        }
    }
}
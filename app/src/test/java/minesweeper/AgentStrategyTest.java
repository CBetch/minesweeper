package minesweeper;

import minesweeper.strategy.AgentStrategy;
import minesweeper.strategy.CheatingAgentStrategy;
import minesweeper.strategy.LosingAgentStrategy;
import minesweeper.strategy.RandomAgentStrategy;
import org.junit.jupiter.api.Test;

public class AgentStrategyTest {
    @Test
    public void testRandomAgentTilesSelected() {
        // Build Grid & Game
        int NUMBER_ROWS = 2;
        int NUMBER_COLS = 2;
        int NUMBER_MINES = 0;
        MinesweeperGame game = GameFactory.custom(NUMBER_ROWS, NUMBER_COLS, NUMBER_MINES);
        AgentStrategy randStrategy = new RandomAgentStrategy();

        // "play"
        int[] firstMove = randStrategy.selectTile(game);
        // set a tile that is NOT the selected tile to be a mine, so we can assert
            // the next move clicks the mine (gets around cascading reveal of empty tiles)
        if (firstMove[0] == 0 && firstMove[1] == 0) {
            game.getGrid().getTile(1, 1).placeMine();
        } else {
            game.getGrid().getTile(0, 0).placeMine();
        }

        game.clickTile(firstMove[0], firstMove[1]);
        int[] secondMove = randStrategy.selectTile(game);
        assert(firstMove[0] != secondMove[0] || firstMove[1] != secondMove[1]);
        game.clickTile(secondMove[0], secondMove[1]);
    }

    @Test
    public void testRandomAgentWillClickMines() {
        MinesweeperGame game = GameFactory.beginner();
        AgentStrategy randStrategy = new RandomAgentStrategy();

        // force agent to select mine tile (eventually)
        int[] move = randStrategy.selectTile(game);
        while(!game.getGrid().getTile(move[0], move[1]).isMine()) {
            move = randStrategy.selectTile(game);
        }
        game.clickTile(move[0], move[1]);
        assert(game.isGameOver());
        assert(!game.isWonGame());
    }

    @Test
    public void testCheatingStrategyAlwaysWins() {
        // play 100 expert games
        AgentStrategy winStrategy = new CheatingAgentStrategy();
        for (int i = 0; i < 100; i++) {
            MinesweeperGame game = GameFactory.expert();
            while(!game.isGameOver()) {
                int[] move = winStrategy.selectTile(game);
                game.clickTile(move[0], move[1]);
            }
            assert(game.isWonGame());
        }
    }

    @Test
    public void testLoosingStrategyAlwaysLooses() {
        // play 100 expert games
        AgentStrategy looseStrategy = new LosingAgentStrategy();
        for (int i = 0; i < 100; i++) {
            MinesweeperGame game = GameFactory.expert();
            while(!game.isGameOver()) {
                int[] move = looseStrategy.selectTile(game);
                game.clickTile(move[0], move[1]);
            }
            assert(!game.isWonGame());
        }
    }
}
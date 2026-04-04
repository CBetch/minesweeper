package minesweeper;

import minesweeper.strategy.AgentStrategy;
import minesweeper.strategy.CheatingAgentStrategy;
import minesweeper.strategy.LosingAgentStrategy;
import org.junit.jupiter.api.Test;

public class MinesweeperDriverTest {
    @Test
    public void testPlayToCompletion() {
        AgentStrategy winStrategy = new CheatingAgentStrategy();
        MinesweeperDriver driver = new MinesweeperDriver(winStrategy);
        MinesweeperGame game = GameFactory.expert();
        driver.playToCompletion(game);
        assert (game.isWonGame());
        assert (game.isGameOver());
    }

    @Test
    public void testSwitchingStrategy() {
        AgentStrategy winStrategy = new CheatingAgentStrategy();
        AgentStrategy loseStrategy = new LosingAgentStrategy();

        MinesweeperDriver driver = new MinesweeperDriver(winStrategy);
        MinesweeperGame game = GameFactory.expert();
        driver.takeTurn(game);

        // Now lose the game
        driver.setStrategy(loseStrategy);
        driver.takeTurn(game);

        assert(!game.isWonGame());
        assert(game.isGameOver());
    }
}


package lordsoftheants.ants.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Adrian Scripca
 */
@Component
public class Game extends AbstractGame {
    private final GameState state = new GameState();

    @Autowired
    private PlayerStore playerStore;
    @Autowired
    private AntBrains antBrains;

    private AntGame antGame;

    @PostConstruct
    public void initialize() {
        GameBoard board = GameBoardBuilder.boardFromData(16, 16, new int[]{
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, GameBoard.CellType.SPAWNING_POINT_1.getValue(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
        });

        state.setBoard(board);
        state.setMaxAntsPerPlayer(1);
        antGame = new AntGame(state, playerStore, antBrains);
    }

    public AntGame getAntGame() {
        return antGame;
    }

    public void setAntGame(AntGame antGame) {
        this.antGame = antGame;
    }

    public synchronized void start() {
        antGame.start();
        startFrameRunner();
    }

    public synchronized void stop() {
        stopFrameRunner();
        antGame.stop();
    }

    @Override
    protected void doFrame() {
        antGame.doFrame();
        if (state.isFinished()) {
            stop();
        }
    }

    public boolean isStarted() {
        return state.isPlaying();
    }
}

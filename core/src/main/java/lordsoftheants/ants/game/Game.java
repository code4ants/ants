package lordsoftheants.ants.game;

import lordsoftheants.ants.api.rest.GameStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    private ReadWriteLock statusUpdaterLock = new ReentrantReadWriteLock();
    private GameStatusResponse gameStatusResponse;

    @PostConstruct
    public void initialize() {
        GameBoard board = GameBoardBuilder.boardFromData(16, 16, new int[]{
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                1, GameBoard.CellType.SPAWNING_POINT_1.getValue(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, GameBoard.CellType.SPAWNING_POINT_2.getValue(), 1,
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
        refreshGameStateResponse();
    }

    public AntGame getAntGame() {
        return antGame;
    }

    public void setAntGame(AntGame antGame) {
        this.antGame = antGame;
    }

    public synchronized void start() {
        for (Player player : playerStore.getAll()) {
            if (antBrains.newBrainForPlayer(player) == null) {

            }
        }

        antGame.start();
        startFrameRunner();
        refreshGameStateResponse();
    }

    public synchronized void stop() {
        stopFrameRunner();
        antGame.stop();
        refreshGameStateResponse();
    }

    public void playerJoined(Player player) {
        refreshGameStateResponse();
    }

    @Override
    protected void doFrame() {
        antGame.doFrame();
        if (state.isFinished()) {
            stop();
        }

        refreshGameStateResponse();
    }

    private void refreshGameStateResponse() {
        try {
            statusUpdaterLock.writeLock().lock();
            gameStatusResponse = ModelAdapter.coreToApi(getAntGame());
            gameStatusResponse.reportSuccess("Ok");
        } finally {
            statusUpdaterLock.writeLock().unlock();
        }
    }

    public boolean isStarted() {
        return state.isPlaying();
    }

    public GameStatusResponse getGameStatusResponse() {
        try {
            statusUpdaterLock.readLock().lock();
            return gameStatusResponse;
        } finally {
            statusUpdaterLock.readLock().unlock();
        }
    }
}

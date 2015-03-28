package lordsoftheants.ants.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public synchronized void start() {
        if (state.isPlaying())
            throw new GameException("Game already started");

        System.out.println("Starting the game");
        state.startPlaying();
        startFrameRunner();
    }

    public synchronized void stop() {
        if (!state.isPlaying())
            throw new GameException("Game is not started");

        System.out.println("Stopping the game");
        state.stopPlaying();
        stopFrameRunner();
    }

    @Override
    protected void doFrame() {
        synchronized (state) {
            updateLivingAnts();
            spawnNewAnts();
            state.nextFrame();

            if (state.isFinished()) {
                stop();
            }
        }
    }

    private void spawnNewAnts() {
        System.out.println("Frame: " + state.getFrameNumber() + " - spawning new ants");
        for (Player player : playerStore.getAll()) {
            Ant ant = new Ant();
            ant.setId(state.nextAntId());
            ant.setBrain(antBrains.newBrainForPlayer(player));
            ant.setOwner(player);
            state.ants.add(ant);
        }
    }

    private void updateLivingAnts() {
        System.out.println("Frame: " + state.getFrameNumber() + " - updating living ants");
        for (Ant a : state.ants) {
            a.think();
        }
    }

    public boolean isStarted() {
        return state.isPlaying();
    }

}

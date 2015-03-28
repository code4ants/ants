package lordsoftheants.ants.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Adrian Scripca
 */
public abstract class AbstractGame {
    @Autowired
    protected TaskScheduler taskScheduler;
    protected GameExecutor gameFrameRunner = new GameExecutor(this);
    protected ScheduledFuture<?> scheduledFuture;

    protected void startFrameRunner() {
        scheduledFuture = taskScheduler.scheduleAtFixedRate(gameFrameRunner, 1000L);
    }

    protected void stopFrameRunner() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
    }

    protected abstract void doFrame();

    private class GameExecutor implements Runnable {
        private final AbstractGame game;

        public GameExecutor(AbstractGame game) {
            this.game = game;
        }

        @Override
        public void run() {
            System.out.println("Executing a frame");
            game.doFrame();
        }
    }
}

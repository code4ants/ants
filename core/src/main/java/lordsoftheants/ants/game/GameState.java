package lordsoftheants.ants.game;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Adrian Scripca
 */
public class GameState {
    public List<Ant> ants = new LinkedList<>();
    private boolean playing;
    private int lastAntId = 0;
    private int frameNumber = 0;

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getLastAntId() {
        return lastAntId;
    }

    public void setLastAntId(int lastAntId) {
        this.lastAntId = lastAntId;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public void reset() {
        lastAntId = 0;
        playing = false;
        frameNumber = 0;
    }

    public void nextFrame() {
        frameNumber++;
    }

    public boolean isFinished() {
        return frameNumber > 2;
    }

    public int nextAntId() {
        return lastAntId++;
    }

    public void startPlaying() {
        reset();
        setPlaying(true);
    }

    public void stopPlaying() {
        setPlaying(false);
    }
}

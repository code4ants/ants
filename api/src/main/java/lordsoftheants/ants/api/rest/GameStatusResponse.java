package lordsoftheants.ants.api.rest;

import lordsoftheants.ants.api.Player;

import java.util.List;

/**
 * @author Adrian Scripca
 */
public class GameStatusResponse extends Response {
    //    private Board board;
    private List<Player> players;
    private boolean playing;
    private int currentFrame;

//    public Board getBoard() {
//        return board;
//    }

//    public void setBoard(Board board) {
//        this.board = board;
//    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
}

package lordsoftheants.ants.api;

import java.util.List;

/**
 * @author Adrian Scripca
 */
public class GameStatus {
    private Board board;
    private List<Player> players;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}

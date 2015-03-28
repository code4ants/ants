package lordsoftheants.ants.api.rest;

import java.util.List;

/**
 * @author Adrian Scripca
 */
public class GameStatusResponse extends Response {
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

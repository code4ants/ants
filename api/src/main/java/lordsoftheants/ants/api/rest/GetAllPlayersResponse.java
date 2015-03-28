package lordsoftheants.ants.api.rest;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Adrian Scripca
 */
public class GetAllPlayersResponse extends Response {
    private List<String> players = new LinkedList<>();

    public void addPlayer(String name) {
        players.add(name);
    }

    public List<String> getPlayers() {
        return players;
    }
}


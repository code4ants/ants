package lordsoftheants.ants.game;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adrian Scripca
 */
@Component
public class PlayerStore {

    Map<String, Player> playerMap = new LinkedHashMap<>();

    public Player getByToken(String token) {
        return playerMap.get(token);
    }

    public Player getByName(String name) {
        for (Map.Entry<String, Player> stringPlayerEntry : playerMap.entrySet()) {
            Player player = stringPlayerEntry.getValue();
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public List<Player> getAll() {
        List<Player> result = new LinkedList<>();
        for (Map.Entry<String, Player> stringPlayerEntry : playerMap.entrySet()) {
            result.add(stringPlayerEntry.getValue());
        }
        return result;
    }

    public Player addNew(String name) {
        Player player = new Player();
        player.setName(name);
        player.setSlot(playerMap.size());
        do {
            player.setToken(UUID.randomUUID().toString());
        } while (getByToken(player.getToken()) != null);
        playerMap.put(player.getToken(), player);
        return player;
    }
}

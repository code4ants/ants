package lordsoftheants.ants.game;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adrian Scripca
 */
@Component
public class PlayerStore {

    Map<String, Player> playerMap = new ConcurrentHashMap<>();

    public Player getByToken(String token) {
        return playerMap.get(token);
    }

    public Player getByName(String name) {
        for (Map.Entry<String, Player> stringPlayerEntry : playerMap.entrySet()) {
            Player player = stringPlayerEntry.getValue();
            if (player.name.equalsIgnoreCase(name)) {
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
        player.name = name;
        do {
            player.token = UUID.randomUUID().toString();
        } while (getByToken(player.token) != null);
        playerMap.put(player.token, player);
        return player;
    }
}

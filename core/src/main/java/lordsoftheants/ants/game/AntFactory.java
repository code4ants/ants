package lordsoftheants.ants.game;

import lordsoftheants.ants.api.Ant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Adrian Scripca
 */
@Component
public class AntFactory {

    @Autowired
    private AntStore antStore;

    private Map<Player, AntLoader> antLoaders = new LinkedHashMap<>();

    public Ant newAntForPlayer(Player player) {
        if (antChangedForPlayer(player)) {
            antLoaders.put(player, new AntLoader(antStore.getLastEntryForPlayer(player), getClass().getClassLoader()));
        }

        AntLoader loader = antLoaders.get(player);
        if (loader == null) {
            System.out.println("No loader configured for player " + player.getName());
            return null;
        }

        try {
            return (Ant) loader.loadClass(loader.getEntry().classFqn).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ClassFormatError e) {
            throw new AntLoaderException(e.getMessage(), e);
        }
    }

    private boolean antChangedForPlayer(Player player) {
        AntStore.AntStoreEntry lastEntry = antStore.getLastEntryForPlayer(player);
        AntStore.AntStoreEntry currentEntry = antLoaders.get(player) == null ? null : antLoaders.get(player).getEntry();
        return !Objects.equals(lastEntry, currentEntry);
    }
}

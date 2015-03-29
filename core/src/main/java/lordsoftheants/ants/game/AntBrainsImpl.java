package lordsoftheants.ants.game;

import lordsoftheants.ants.api.AntBrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Adrian Scripca
 */
@Component
public class AntBrainsImpl implements AntBrains {

    @Autowired
    private BrainStore brainStore;

    private Map<Player, BrainLoader> brainLoaders = new LinkedHashMap<>();

    public AntBrain newBrainForPlayer(Player player) {
        if (brainChangedForPlayer(player)) {
            brainLoaders.put(player, new BrainLoader(brainStore.getLastEntryForPlayer(player), getClass().getClassLoader()));
        }

        BrainLoader loader = brainLoaders.get(player);
        if (loader == null) {
            System.out.println("No logger configured for player " + player.getName());
            return null;
        }

        try {
            return (AntBrain) loader.loadClass(loader.getEntry().classFqn).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ClassFormatError e) {
            throw new BrainLoaderException(e.getMessage(), e);
        }
    }

    private boolean brainChangedForPlayer(Player player) {
        BrainStore.BrainStoreEntry lastEntry = brainStore.getLastEntryForPlayer(player);
        BrainStore.BrainStoreEntry currentEntry = brainLoaders.get(player) == null ?
                null :
                brainLoaders.get(player).getEntry();
        return !Objects.equals(lastEntry, currentEntry);
    }
}

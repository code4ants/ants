package lordsoftheants.ants.game;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Adrian Scripca
 */
@Component
public class BrainStore {

    private Map<Player, LinkedList<BrainStoreEntry>> store = new LinkedHashMap<>();

    public static BrainStoreEntry newEntry(byte[] byteCode, String classFqn) {
        return new BrainStoreEntry(byteCode, classFqn);
    }

    public void storeForPlayer(BrainStoreEntry entry, Player player) {
        LinkedList<BrainStoreEntry> entries = store.get(player);
        if (entries == null) {
            entries = new LinkedList<>();
            store.put(player, entries);
        }
        entries.add(entry);
    }

    public BrainStoreEntry getLastEntryForPlayer(Player player) {
        LinkedList<BrainStoreEntry> entries = store.get(player);
        return entries == null || entries.isEmpty() ?
                null :
                entries.getLast();
    }

    static class BrainStoreEntry {
        public byte[] byteCode;
        public Long timestamp;
        public String classFqn;

        public BrainStoreEntry(byte[] byteCode, String classFqn) {
            this.byteCode = byteCode;
            this.classFqn = classFqn;
            this.timestamp = System.currentTimeMillis();
        }
    }
}

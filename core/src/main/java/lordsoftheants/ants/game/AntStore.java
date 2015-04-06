package lordsoftheants.ants.game;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Adrian Scripca
 */
@Component
public class AntStore {

    private Map<Player, LinkedList<AntStoreEntry>> store = new LinkedHashMap<>();

    public static AntStoreEntry newEntry(byte[] byteCode, String classFqn) {
        return new AntStoreEntry(byteCode, classFqn);
    }

    public void storeForPlayer(AntStoreEntry entry, Player player) {
        LinkedList<AntStoreEntry> entries = store.get(player);
        if (entries == null) {
            entries = new LinkedList<>();
            store.put(player, entries);
        }
        entries.add(entry);
    }

    public AntStoreEntry getLastEntryForPlayer(Player player) {
        LinkedList<AntStoreEntry> entries = store.get(player);
        return entries == null || entries.isEmpty() ?
                null :
                entries.getLast();
    }

    public void removeLastEntryForPlayer(Player player) {
        LinkedList<AntStoreEntry> entries = store.get(player);
        if (entries != null && !entries.isEmpty())
            entries.removeLast();
    }

    static class AntStoreEntry {
        public byte[] byteCode;
        public Long timestamp;
        public String classFqn;

        public AntStoreEntry(byte[] byteCode, String classFqn) {
            this.byteCode = byteCode;
            this.classFqn = classFqn;
            this.timestamp = System.currentTimeMillis();
        }
    }
}

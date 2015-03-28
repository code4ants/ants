package lordsoftheants.ants.game;

/**
 * @author Adrian Scripca
 */
public class BrainLoader extends ClassLoader {

    private BrainStore.BrainStoreEntry entry;

    public BrainLoader(BrainStore.BrainStoreEntry entry, ClassLoader parent) {
        super(parent);
        this.entry = entry;
    }

    public BrainStore.BrainStoreEntry getEntry() {
        return entry;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.equals(entry.classFqn)) {
            byte[] b = loadClassData(name);
            return defineClass(name, b, 0, b.length);
        } else {
            return super.findClass(name);
        }
    }

    private byte[] loadClassData(String name) {
        return entry.byteCode;
    }
}

package lordsoftheants.ants.game;

/**
 * @author Adrian Scripca
 */
public class AntLoader extends ClassLoader {

    private AntStore.AntStoreEntry entry;

    public AntLoader(AntStore.AntStoreEntry entry, ClassLoader parent) {
        super(parent);
        this.entry = entry;
    }

    public AntStore.AntStoreEntry getEntry() {
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

    @SuppressWarnings("unused")
    private byte[] loadClassData(String name) {
        return entry.byteCode;
    }
}

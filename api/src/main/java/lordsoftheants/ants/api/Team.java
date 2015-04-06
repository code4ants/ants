package lordsoftheants.ants.api;

/**
 * @author Bogdan Mocanu
 */
public interface Team {

    boolean isSharedString(String key);

    String getSharedString(String key);

    void setSharedString(String key, String value);

    boolean isSharedAction(String key);

    AntAction getSharedAction(String key);

    void setSharedAction(String key, AntAction action);
}

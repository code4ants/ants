package lordsoftheants.ants.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adrian Scripca
 */
public class Player {
    public int id;
    public String name;
    public String token;
    private Map<String, String> parameters = new ConcurrentHashMap<>();

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }
}

package lordsoftheants.ants.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adrian Scripca
 */
public class Player {
    private String name;
    private String token;
    private Map<String, String> parameters = new ConcurrentHashMap<>();
    private int slot;
    private int score;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addToScore(int amount) {
        score += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return slot == player.slot;

    }

    @Override
    public int hashCode() {
        return slot;
    }
}

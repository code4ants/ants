package lordsoftheants.ants.api;

import java.util.List;

/**
 * @author Adrian Scripca
 */
public class Player {
    private String name;
    private int slot;
    private int score;
    private List<Ant> ants;

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

    public List<Ant> getAnts() {
        return ants;
    }

    public void setAnts(List<Ant> ants) {
        this.ants = ants;
    }
}

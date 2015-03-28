package lordsoftheants.ants.game;

import lordsoftheants.ants.api.AntBrain;

/**
 * @author Adrian Scripca
 */
public class Ant {
    private int id;
    private AntBrain brain;
    private Player owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AntBrain getBrain() {
        return brain;
    }

    public void setBrain(AntBrain brain) {
        this.brain = brain;
    }

    public void think() {
        if (brain == null) {
            System.out.println("No brain for the poor ant to think");
        } else {
            System.out.println("Ant " + id + " thinks: " + brain.think());
        }
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}

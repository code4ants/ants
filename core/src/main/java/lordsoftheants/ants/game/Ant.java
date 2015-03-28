package lordsoftheants.ants.game;

import lordsoftheants.ants.api.AntBrain;
import lordsoftheants.ants.api.Decision;

/**
 * @author Adrian Scripca
 */
public class Ant {
    private int id;
    private AntBrain brain;
    private Player owner;
    private int x;
    private int y;
    private int nextX;
    private int nextY;

    public int getNextX() {
        return nextX;
    }

    public void setNextX(int nextX) {
        this.nextX = nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public void setNextY(int nextY) {
        this.nextY = nextY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

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

    public Decision think() {
        if (brain == null)
            throw new IllegalStateException("The ant with id " + id + " does not have a brain!");

        return brain.think();
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}

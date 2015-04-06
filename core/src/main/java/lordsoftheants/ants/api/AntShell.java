package lordsoftheants.ants.api;

import lordsoftheants.ants.api.Ant;
import lordsoftheants.ants.api.AntAction;
import lordsoftheants.ants.game.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adrian Scripca
 */
public class AntShell {
    private int id;
    public Ant ant;
    private lordsoftheants.ants.game.Player owner;
    public int x;
    public int y;
    public int hp;
    public int nextX;
    public int nextY;

    private Map<String, Object> parameters = new HashMap<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void think() {
        if (ant == null) {
            throw new IllegalStateException("The ant shell with id " + id + " does not have an ant inside!");
        }
        ant.x = x;
        ant.y = y;
        ant.hp = hp;
        ant.think();
    }

    public AntAction act() {
        if (ant == null) {
            throw new IllegalStateException("The ant shell with id " + id + " does not have an ant inside!");
        }
        ant.x = x;
        ant.y = y;
        ant.hp = hp;
        return ant.act();
    }

    public lordsoftheants.ants.game.Player getOwner() {
        return owner;
    }

    public void setOwner(lordsoftheants.ants.game.Player owner) {
        this.owner = owner;
    }
}

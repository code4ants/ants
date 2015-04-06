package lordsoftheants.ants.api;

import lordsoftheants.ants.api.observables.ObservablePlayer;

import java.util.Collection;

/**
 * @author Adrian Scripca
 * @author Bogdan Mocanu
 */
public abstract class Ant {

    protected final World world;

    protected final Team team;

    protected final Player master;

    protected final Collection<ObservablePlayer> otherMasters;

    protected int x;

    protected int y;

    protected int hp;

    protected Ant(AntGeneticMaterial geneticMaterial) {
        this.world = geneticMaterial.world;
        this.team = geneticMaterial.team;
        this.master = geneticMaterial.master;
        this.otherMasters = geneticMaterial.otherMasters;
    }

    protected abstract void think();

    protected abstract AntAction act();

}

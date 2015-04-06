package lordsoftheants.ants.api;

import lordsoftheants.ants.api.observables.ObservablePlayer;

import java.util.Collection;

/**
 * @author Bogdan Mocanu
 */
public class AntGeneticMaterial {

    protected final World world;
    protected final Team team;
    protected final Player master;
    protected final Collection<ObservablePlayer> otherMasters;

    public AntGeneticMaterial(World world, Team team, Player master, Collection<ObservablePlayer> otherMasters) {
        this.world = world;
        this.team = team;
        this.master = master;
        this.otherMasters = otherMasters;
    }
}

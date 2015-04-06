package lordsoftheants.ants.api.observables;

/**
 * @author Bogdan Mocanu
 */
public interface Observable {

    ObservableType getType();

    boolean isEmpty();

    boolean isAnt();

    boolean isWall();

}

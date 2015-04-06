package lordsoftheants.ants.api;

import lordsoftheants.ants.api.observables.Observable;

import java.util.List;

/**
 * @author Bogdan Mocanu
 */
public interface World {

    int getWidth();

    int getHeight();

    List<List<Observable>> getMap();

    Observable getAt(int x, int y);

}

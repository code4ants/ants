import lordsoftheants.ants.api.Ant;
import lordsoftheants.ants.api.AntAction;
import lordsoftheants.ants.api.AntGeneticMaterial;

/**
 * @author Adrian Scripca
 * @author Bogdan Mocanu
 */
public class AntToLeft extends Ant {

    public AntToLeft(AntGeneticMaterial geneticMaterial) {
        super(geneticMaterial);
    }

    @Override
    public void think() {
        // hulk not thinks!
    }

    @Override
    public AntAction act() {
        return AntAction.GO_LEFT;
    }

}

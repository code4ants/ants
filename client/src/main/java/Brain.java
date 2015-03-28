import lordsoftheants.ants.api.AntBrain;
import lordsoftheants.ants.api.Decision;

/**
 * @author Adrian Scripca
 */
public class Brain implements AntBrain {

    @Override
    public Decision think() {
        return Decision.GO_RIGHT;
    }
}

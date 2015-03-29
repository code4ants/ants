import lordsoftheants.ants.api.AntBrain;
import lordsoftheants.ants.api.Decision;

/**
 * @author Adrian Scripca
 */
public class Brain2 implements AntBrain {

    @Override
    public Decision think() {
        return Decision.GO_LEFT;
    }
}

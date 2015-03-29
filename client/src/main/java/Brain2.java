import lordsoftheants.ants.api.Ant;
import lordsoftheants.ants.api.AntBrain;
import lordsoftheants.ants.api.Decision;
import lordsoftheants.ants.api.GameStatus;

import java.util.Map;

/**
 * @author Adrian Scripca
 */
public class Brain2 implements AntBrain {

    @Override
    public Decision think(int currentAntX, int currentAntY, GameStatus gameStatus, int ownerSlot, Map<String, String> ownerSettings, Map<String, Object> antSettings) {
        return Decision.GO_LEFT;
    }
}

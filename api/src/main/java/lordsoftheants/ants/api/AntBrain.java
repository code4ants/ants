package lordsoftheants.ants.api;

import java.util.Map;

/**
 * @author Adrian Scripca
 */
public interface AntBrain {
    Decision think(int currentAntX, int currentAntY, GameStatus gameStatus, int ownerSlot, Map<String, String> ownerSettings, Map<String, Object> antSettings);
}

import lordsoftheants.ants.api.AntBrain;
import lordsoftheants.ants.api.Decision;
import lordsoftheants.ants.api.GameStatus;

import java.util.Map;
import java.util.Random;

/**
 * @author Adrian Scripca
 */
public class Brain implements AntBrain {

    @Override
    public Decision think(int currentAntX, int currentAntY, GameStatus gameStatus, int ownerSlot, Map<String, String> ownerSettings, Map<String, Object> antSettings) {
        Decision decision = null;
        if (antSettings.containsKey("lastDecision")) {
            decision = (Decision) antSettings.get("lastDecision");
        }

        if (decision == null || !possibleToMoveFromTowards(currentAntX, currentAntY, decision, gameStatus)) {
            do {
                decision = randomDecision();
            } while (!possibleToMoveFromTowards(currentAntX, currentAntY, decision, gameStatus));
            antSettings.put("lastDecision", decision);
        }

        return decision;
    }

    private boolean possibleToMoveFromTowards(int x, int y, Decision decision, GameStatus gameStatus) {
        int newX = x;
        int newY = y;
        if (decision == Decision.GO_LEFT)
            newX -= 1;
        else if (decision == Decision.GO_RIGHT)
            newX += 1;
        else if (decision == Decision.GO_UP)
            newY -= 1;
        else if (decision == Decision.GO_DOWN)
            newY += 1;

        return gameStatus.getBoard().getAt(newX, newY) == 0;
    }

    private Decision randomDecision() {
        return Decision.fromValue(new Random().nextInt(4));
    }
}

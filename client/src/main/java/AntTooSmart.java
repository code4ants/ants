import lordsoftheants.ants.api.Ant;
import lordsoftheants.ants.api.AntAction;
import lordsoftheants.ants.api.AntGeneticMaterial;

import java.util.Random;

/**
 * @author Adrian Scripca
 */
public class AntTooSmart extends Ant {

    public AntTooSmart(AntGeneticMaterial geneticMaterial) {
        super(geneticMaterial);
    }

    @Override
    public void think() {
        // I am smarter than Hulk... but still no thinks!
    }

    @Override
    public AntAction act() {
        if ("commitSuicide".equals(team.getSharedString("order"))) {
            return AntAction.GO_RIGHT;
        }

        AntAction antAction = null;
        if (team.isSharedAction("lastDecision")) {
            antAction = team.getSharedAction("lastDecision");
        }

        if (antAction == null || !possibleToMoveFromTowards(antAction)) {
            do {
                antAction = randomDecision();
            } while (!possibleToMoveFromTowards(antAction));
            team.setSharedAction("lastDecision", antAction);
        }

        return antAction;
    }

    private boolean possibleToMoveFromTowards(AntAction previousAction) {
        int newX = x;
        int newY = y;
        switch (previousAction) {
            case GO_LEFT:
                newX -= 1;
                break;
            case GO_RIGHT:
                newX += 1;
                break;
            case GO_UP:
                newY -= 1;
                break;
            case GO_DOWN:
                newY += 1;
                break;
        }
        return world.getAt(newX, newY).isEmpty();
    }

    private AntAction randomDecision() {
        return AntAction.values()[new Random().nextInt(4)];
    }
}

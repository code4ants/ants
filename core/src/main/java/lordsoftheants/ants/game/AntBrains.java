package lordsoftheants.ants.game;

import lordsoftheants.ants.api.AntBrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Adrian Scripca
 */
@Component
public interface AntBrains {

    AntBrain newBrainForPlayer(Player player);
}

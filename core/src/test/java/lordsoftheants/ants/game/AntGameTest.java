package lordsoftheants.ants.game;

import lordsoftheants.ants.api.AntBrain;
import lordsoftheants.ants.api.Decision;
import lordsoftheants.ants.api.GameStatus;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Adrian Scripca
 */
public class AntGameTest {

    @Test
    public void testAntIsSpawnedWhereNeeded() throws Exception {
        GameBoard board = GameBoardBuilder.boardFromData(3, 3, new int[] {
            0, 0, 1,
            GameBoard.CellType.SPAWNING_POINT_1.getValue(), 0, 1,
            0, 0, 1
        });

        GameState state = new GameState();
        state.setBoard(board);
        state.setMaxAntsPerPlayer(1);

        PlayerStore playerStore = new PlayerStore();
        Player player = playerStore.addNew("benishor");

        AntBrains antBrains = new AntBrains() {
            @Override
            public AntBrain newBrainForPlayer(Player player) {
                return new AntBrain() {
                    @Override
                    public Decision think(int currentAntX, int currentAntY, GameStatus gameStatus, int ownerSlot, Map<String, String> ownerSettings, Map<String, Object> antSettings) {
                        return Decision.GO_RIGHT;
                    }
                };
            }
        };

        AntGame game = new AntGame(state, playerStore, antBrains);
        game.start();

        game.doFrame();
        assertThat(board.get(0, 1).getAnts().size(), is(0));
        assertThat(board.get(1, 1).getAnts().size(), is(1));
        assertThat(board.get(2, 1).getAnts().size(), is(0));

        game.doFrame();
        assertThat(board.get(0, 1).getAnts().size(), is(0));
        assertThat(board.get(1, 1).getAnts().size(), is(0));
        assertThat(board.get(2, 1).getAnts().size(), is(1));

        game.doFrame();
        assertThat(board.get(0, 1).getAnts().size(), is(0));
        assertThat(board.get(1, 1).getAnts().size(), is(1));
        assertThat(board.get(2, 1).getAnts().size(), is(0));

        assertThat(player.getScore(), is(-1));
    }
}
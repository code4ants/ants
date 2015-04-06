package lordsoftheants.ants.game;

import lordsoftheants.ants.api.AntShell;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Adrian Scripca
 */
public class GameBoardTest {

    @Test
    public void testRemoveAfterAdd() {
        AntShell ant = new AntShell();

        GameBoard board = new GameBoard(3, 3);
        board.get(1, 1).add(ant);
        assertThat(board.get(1, 1).getAnts().size(), is(1));

        board.get(1, 1).remove(ant);
        assertThat(board.get(1, 1).getAnts().size(), is(0));
    }

}
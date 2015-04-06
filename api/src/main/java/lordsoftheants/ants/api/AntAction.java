package lordsoftheants.ants.api;

/**
 * All the actions that an ant can choose from, in the ACT step of a game round. Besides moving up, down, left, right, an ant can
 * also choose to commit suicide. See <a href="https://github.com/lordsoftheants/ants/wiki/Game-concept">Game concept</a> for more details.
 *
 * @author Adrian Scripca
 * @author Bogdan Mocanu
 */
public enum AntAction {
    GO_LEFT,
    GO_RIGHT,
    GO_UP,
    GO_DOWN,
    SUICIDE;
}

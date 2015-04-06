package lordsoftheants.ants.game;

import lordsoftheants.ants.api.AntAction;
import lordsoftheants.ants.api.AntShell;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Adrian Scripca
 */
public class AntGame {
    private GameState state;

    private PlayerStore playerStore;

    private AntFactory antFactory;

    public AntGame(GameState state, PlayerStore playerStore, AntFactory antFactory) {
        this.state = state;
        this.playerStore = playerStore;
        this.antFactory = antFactory;
    }

    public GameState getState() {
        return state;
    }

    public PlayerStore getPlayerStore() {
        return playerStore;
    }

    public void start() {
        if (state.isPlaying())
            throw new GameException("Game already started");

        state.startPlaying();
    }

    public void stop() {
        if (state.isPlaying())
            state.stopPlaying();
    }

    protected void doFrame() {
        evaluateBoard();
        spawnNewAnts();
        makeAntsThink();
        moveAntsToTheirDesiredTarget();
        state.nextFrame();
    }

    private void moveAntsToTheirDesiredTarget() {
        for (AntShell antShell : state.antShells) {
            state.getBoard().get(antShell.x, antShell.y).remove(antShell);
            state.getBoard().get(antShell.nextX, antShell.nextY).add(antShell);
            antShell.x = antShell.nextX;
            antShell.y = antShell.nextY;
        }
    }

    private void evaluateBoard() {
        for (int y = 0; y < state.getBoard().getHeight(); y++) {
            for (int x = 0; x < state.getBoard().getWidth(); x++) {
                GameBoard.Cell cell = state.getBoard().get(x, y);
                if (!cell.getAnts().isEmpty()) {
                    List<AntShell> ants = new LinkedList<>(cell.getAnts());
                    switch (cell.getType()) {
                        case BORDER:
                            for (AntShell ant : ants) {
                                ant.getOwner().addToScore(-1);
                                killAnt(ant);
                            }
                            break;
                        case WALL:
                            for (AntShell ant : ants) {
                                ant.getOwner().addToScore(-1);
                                killAnt(ant);
                                cell.setType(GameBoard.CellType.EMPTY);
                            }
                            break;
                        case EMPTY:
                            Set<Player> antMastersInThisCell = new HashSet<>();
                            for (AntShell ant : ants) {
                                antMastersInThisCell.add(ant.getOwner());
                            }
                            if (antMastersInThisCell.size() > 1) {
                                for (AntShell ant : ants) {
                                    ant.getOwner().addToScore(-1);
                                    killAnt(ant);
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    private void killAnt(AntShell antShell) {
        state.getBoard().get(antShell.x, antShell.y).remove(antShell);
        state.antShells.remove(antShell);
    }

    private void spawnNewAnts() {
        for (Player player : playerStore.getAll()) {
            if (state.getAntsForPlayer(player).size() < state.getMaxAntsPerPlayer()) {
                AntShell antShell = newAntForPlayer(player);
                state.getBoard().get(antShell.x, antShell.y).add(antShell);
                state.antShells.add(antShell);
            }
        }
    }

    private AntShell newAntForPlayer(Player player) {
        GameBoard.Cell spawningPoint = state.getBoard().getSpawingPointForPlayerSlot(player.getSlot());
        if (spawningPoint == null) {
            throw new GameException("Failed to retrieve spawning point for player with slot " + player.getSlot());
        }

        AntShell antShell = new AntShell();
        antShell.setId(state.nextAntId());
        try {
            antShell.ant = antFactory.newAntForPlayer(player);
        } catch (AntLoaderException e) {
            antShell.ant = null;
        }
        antShell.setOwner(player);
        antShell.x = spawningPoint.getX();
        antShell.y = spawningPoint.getY();

        return antShell;
    }

    private void makeAntsThink() {
        // Ants think!
        for (AntShell antShell : state.antShells) {
            antShell.think();
        }

        // Ants act!!!
        for (AntShell antShell : state.antShells) {
            AntAction antAction = antShell.act();

            antShell.nextX = antShell.x;
            antShell.nextY = antShell.y;
            switch (antAction) {
                case GO_LEFT:
                    antShell.nextX = antShell.x - 1;
                    break;
                case GO_RIGHT:
                    antShell.nextX = antShell.x + 1;
                    break;
                case GO_UP:
                    antShell.nextY = antShell.y - 1;
                    break;
                case GO_DOWN:
                    antShell.nextY = antShell.y + 1;
                    break;
                case SUICIDE:
                    // no code here
                    break;
                default:
                    throw new IllegalArgumentException("Unknown ant action: " + antAction);
            }

        }
    }
}

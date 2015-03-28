package lordsoftheants.ants.game;
import lordsoftheants.ants.api.Decision;

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

    private AntBrains antBrains;

    public AntGame(GameState state, PlayerStore playerStore, AntBrains antBrains) {
        this.state = state;
        this.playerStore = playerStore;
        this.antBrains = antBrains;
    }

    public GameState getState() {
        return state;
    }

    public PlayerStore getPlayerStore() {
        return playerStore;
    }

    public AntBrains getAntBrains() {
        return antBrains;
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
        for (Ant ant : state.ants) {
            state.getBoard().get(ant.getX(), ant.getY()).remove(ant);
            state.getBoard().get(ant.getNextX(), ant.getNextY()).add(ant);
            ant.setX(ant.getNextX());
            ant.setY(ant.getNextY());

        }
    }

    private void evaluateBoard() {
        for (int y = 0; y < state.getBoard().getHeight(); y++) {
            for (int x = 0; x < state.getBoard().getWidth(); x++) {
                GameBoard.Cell cell = state.getBoard().get(x, y);
                if (!cell.getAnts().isEmpty()) {
                    List<Ant> ants = new LinkedList<>(cell.getAnts());
                    switch (cell.getType()) {
                        case BORDER:
                            for (Ant ant : ants) {
                                ant.getOwner().addToScore(-1);
                                killAnt(ant);
                            }
                            break;
                        case WALL:
                            for (Ant ant : ants) {
                                ant.getOwner().addToScore(-1);
                                killAnt(ant);
                                cell.setType(GameBoard.CellType.EMPTY);
                            }
                            break;
                        case EMPTY:
                            Set<Player> antMastersInThisCell = new HashSet<>();
                            for (Ant ant : ants) {
                                antMastersInThisCell.add(ant.getOwner());
                            }
                            if (antMastersInThisCell.size() > 1) {
                                for (Ant ant : ants) {
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

    private void killAnt(Ant ant) {
        state.getBoard().get(ant.getX(), ant.getY()).remove(ant);
        state.ants.remove(ant);
    }

    private void spawnNewAnts() {
        System.out.println("Frame: " + state.getFrameNumber() + " - spawning new ants");
        for (Player player : playerStore.getAll()) {
            if (state.getAntsForPlayer(player).size() < state.getMaxAntsPerPlayer()) {
                Ant ant = newAntForPlayer(player);
                state.getBoard().get(ant.getX(), ant.getY()).add(ant);
                state.ants.add(ant);
            }
        }
    }

    private Ant newAntForPlayer(Player player) {
        GameBoard.Cell spawningPoint = state.getBoard().getSpawingPointForPlayerSlot(player.getSlot());
        if (spawningPoint == null) {
            throw new GameException("Failed to retrieve spawning point for player with slot " + player.getSlot());
        }

        Ant ant = new Ant();
        ant.setId(state.nextAntId());
        ant.setBrain(antBrains.newBrainForPlayer(player));
        ant.setOwner(player);
        ant.setX(spawningPoint.getX());
        ant.setY(spawningPoint.getY());

        System.out.println("Created new ant " + ant.getId());

        return ant;
    }

    private void makeAntsThink() {
        System.out.println("Frame: " + state.getFrameNumber() + " - updating living ants");
        for (Ant ant : state.ants) {
            Decision decision = ant.think();
            System.out.println("Ant " + ant.getId() + " opted to " + decision);
            ant.setNextX(ant.getX());
            ant.setNextY(ant.getY());
            switch (decision) {
                case GO_LEFT:
                    ant.setNextX(ant.getX() - 1);
                    break;
                case GO_RIGHT:
                    ant.setNextX(ant.getX() + 1);
                    break;
                case GO_UP:
                    ant.setNextY(ant.getY() - 1);
                    break;
                case GO_DOWN:
                    ant.setNextY(ant.getY() + 1);
                    break;
            }
        }
    }
}

package lordsoftheants.ants.game;

import lordsoftheants.ants.api.Ant;
import lordsoftheants.ants.api.Board;
import lordsoftheants.ants.api.GameStatus;
import lordsoftheants.ants.api.rest.GameStatusResponse;
import lordsoftheants.ants.api.Player;
import lordsoftheants.ants.game.AntGame;

import java.util.LinkedList;

/**
 * @author Adrian Scripca
 */
public class ModelAdapter {

    public static GameStatusResponse coreToApi(AntGame game) {
        GameStatusResponse response = new GameStatusResponse();
        response.setPlayers(new LinkedList<Player>());
        for (lordsoftheants.ants.game.Player p : game.getPlayerStore().getAll()) {
            Player player = new Player();
            player.setSlot(p.getSlot());
            player.setName(p.getName());
            player.setScore(p.getScore());
            player.setAnts(new LinkedList<Ant>());

            response.getPlayers().add(player);
            for (lordsoftheants.ants.game.Ant a : game.getState().ants) {
                if (a.getOwner() == p) {
                    Ant ant = new Ant();
                    ant.setX(a.getX());
                    ant.setY(a.getY());
                    player.getAnts().add(ant);
                }
            }
        }

        lordsoftheants.ants.game.GameBoard board = game.getState().getBoard();

        response.setBoard(new Board());
        response.getBoard().setWidth(board.getWidth());
        response.getBoard().setHeight(board.getHeight());
        response.getBoard().setData(new LinkedList<Integer>());

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                response.getBoard().getData().add(board.get(x, y).getType().getValue());
            }
        }

        return response;
    }

    public static GameStatus coreToApiStatus(AntGame game) {
        GameStatus result = new GameStatus();
        result.setPlayers(new LinkedList<Player>());
        for (lordsoftheants.ants.game.Player p : game.getPlayerStore().getAll()) {
            Player player = new Player();
            player.setSlot(p.getSlot());
            player.setName(p.getName());
            player.setScore(p.getScore());
            player.setAnts(new LinkedList<Ant>());

            result.getPlayers().add(player);
            for (lordsoftheants.ants.game.Ant a : game.getState().ants) {
                if (a.getOwner() == p) {
                    Ant ant = new Ant();
                    ant.setX(a.getX());
                    ant.setY(a.getY());
                    player.getAnts().add(ant);
                }
            }
        }

        lordsoftheants.ants.game.GameBoard board = game.getState().getBoard();

        result.setBoard(new Board());
        result.getBoard().setWidth(board.getWidth());
        result.getBoard().setHeight(board.getHeight());
        result.getBoard().setData(new LinkedList<Integer>());

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                result.getBoard().getData().add(board.get(x, y).getType().getValue());
            }
        }

        return result;
    }
}

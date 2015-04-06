package lordsoftheants.ants.web;

import lordsoftheants.ants.api.rest.*;
import lordsoftheants.ants.game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adrian Scripca
 */
@Controller
public class GameController {

    @Autowired
    private AntStore antStore;

    @Autowired
    private PlayerStore playerStore;

    @Autowired
    private AntBrains antBrains;

    @Autowired
    private Game game;

    @ExceptionHandler
    @ResponseBody
    public Response errorHandler(Exception e) {
        Response response = new Response();
        response.reportFailure(e.getMessage());
        return response;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "status";
    }

    @RequestMapping(value = "/players", method = RequestMethod.POST)
    @ResponseBody
    public AddPlayerResponse addPlayer(@RequestBody AddPlayerRequest request) {
        AddPlayerResponse response = new AddPlayerResponse();
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            if (playerStore.getByName(request.getName().trim()) != null) {
                response.reportFailure("User already registered");
            } else {
                if (game.isStarted()) {
                    response.reportFailure("Cannot add users while the game is running");
                } else {
                    Player player = playerStore.addNew(request.getName().trim());
                    response.reportSuccess("User created.");
                    response.setToken(player.getToken());
                    response.setSlot(player.getSlot());
                    game.playerJoined(player);
                }
            }
        } else {
            response.reportFailure("Invalid username");
        }

        return response;
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    @ResponseBody
    public GetAllPlayersResponse getAllPlayers() {
        GetAllPlayersResponse response = new GetAllPlayersResponse();
        for (Player player : playerStore.getAll()) {
            response.addPlayer(player.getName());
        }
        response.reportSuccess("Got all players");
        return response;
    }

    @RequestMapping(value = "/brain", method = RequestMethod.PUT)
    @ResponseBody
    public UploadBrainResponse uploadBrain(@RequestBody UploadBrainRequest request) {
        UploadBrainResponse response = new UploadBrainResponse();
        Player player = playerStore.getByToken(request.getToken());
        if (player == null) {
            response.reportFailure("Unknown token");
        } else {
            System.out.println("Received brain bytecode with size: " + request.getBrainCode().length);
            antStore.storeForPlayer(AntStore.newEntry(request.getBrainCode(), request.getClassFqn()), player);
            try {
                antBrains.newBrainForPlayer(player);
                response.reportSuccess("Class uploaded ok");
            } catch (AntLoaderException e) {
                antStore.removeLastEntryForPlayer(player);
                response.reportFailure("Failed to load class. Reason: " + e.getMessage());
            }
        }
        return response;
    }

    @RequestMapping(value = "/parameter", method = RequestMethod.POST)
    @ResponseBody
    public SetParameterResponse sendMessage(@RequestBody SetParameterRequest request) {
        SetParameterResponse response = new SetParameterResponse();
        Player player = playerStore.getByToken(request.getToken());
        if (player == null) {
            response.reportFailure("Unknown token");
        } else {
            player.setParameter(request.getKey(), request.getValue());
            response.reportSuccess("Parameter set.");
        }
        return response;
    }

    @RequestMapping(value = "/game/start", method = RequestMethod.POST)
    @ResponseBody
    public Response startGame() {
        game.start();
        Response response = new Response();
        response.reportSuccess("Started game");
        return response;
    }

    @RequestMapping(value = "/game/stop", method = RequestMethod.POST)
    @ResponseBody
    public Response stopGame() {
        game.stop();
        Response response = new Response();
        response.reportSuccess("Stopped the game");
        return response;
    }

    @RequestMapping(value = "/game/status", method = RequestMethod.GET)
    @ResponseBody
    public GameStatusResponse gameStatus() {
        return game.getGameStatusResponse();
    }
}

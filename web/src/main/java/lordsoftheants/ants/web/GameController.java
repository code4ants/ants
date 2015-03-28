package lordsoftheants.ants.web;

import lordsoftheants.ants.api.rest.*;
import lordsoftheants.ants.game.BrainStore;
import lordsoftheants.ants.game.Game;
import lordsoftheants.ants.game.Player;
import lordsoftheants.ants.game.PlayerStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adrian Scripca
 */
@Controller
public class GameController {

    @Autowired
    private BrainStore brainStore;

    @Autowired
    private PlayerStore playerStore;

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
    @ResponseBody
    public String index() {
        return "Welcome to the Lords of the Ants!";
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
                    response.setToken(player.token);
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
            response.addPlayer(player.name);
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
            brainStore.storeForPlayer(BrainStore.newEntry(request.getBrainCode(), request.getClassFqn()), player);
            response.reportSuccess("Class uploaded ok");
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
}

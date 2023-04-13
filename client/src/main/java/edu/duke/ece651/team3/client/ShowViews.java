package edu.duke.ece651.team3.client;

//import edu.duke.ece651.team3.client.controller.*;
import edu.duke.ece651.team3.client.controller.*;
import edu.duke.ece651.team3.client.model.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ShowViews {
    public static void showGameView(Stage stage, String xmlPath, Game game) throws IOException {
        URL xmlResource = ShowViews.class.getResource(xmlPath);
        FXMLLoader loader = new FXMLLoader(xmlResource);

        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(ChooseActionController.class, new ChooseActionController(game.getPlayerId(), stage, game));
        controllers.put(MapController.class, new MapController(game));
        controllers.put(MovePageController.class, new MovePageController(game.getPlayerId(), stage, game));
        controllers.put(AttackPageController.class, new AttackPageController(game.getPlayerId(), stage, game));
        controllers.put(UpgradePageController.class, new UpgradePageController(game.getPlayerId(), stage, game));
        controllers.put(DonePageController.class, new DonePageController(game.getPlayerId(), stage, game));
        controllers.put(ResultPageController.class, new ResultPageController(game.getPlayerId(), stage, game));
        //TODO: add new controller
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });

        Scene scene = new Scene(loader.load(), 924, 600);

        stage.setTitle("RISC Game");
        stage.setScene(scene);
        stage.show();
    }
}

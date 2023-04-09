package edu.duke.ece651.team3.client;

//import edu.duke.ece651.team3.client.controller.*;
import edu.duke.ece651.team3.client.controller.CheckBoxController;
import edu.duke.ece651.team3.client.controller.MapController;
import edu.duke.ece651.team3.client.controller.SliderController;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ShowViews {
    public static void showGameView(Stage stage, String xmlPath, Game game) throws IOException {
        URL xmlResource = ShowViews.class.getResource(xmlPath);
        FXMLLoader loader = new FXMLLoader(xmlResource);

        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(CheckBoxController.class, new CheckBoxController(game.getPlayerId()));
        controllers.put(MapController.class, new MapController(game));
        controllers.put(SliderController.class, new SliderController());
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });

        Scene scene = new Scene(loader.load(), 924, 600);

        stage.setTitle("RISC Game");
        stage.setScene(scene);
        stage.show();
    }
}

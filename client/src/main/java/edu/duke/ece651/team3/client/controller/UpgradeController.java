package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.model.Game;
import javafx.stage.Stage;

public class UpgradeController {
    Game gameEntity;
    Stage stage;

    public UpgradeController(Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
    }
}

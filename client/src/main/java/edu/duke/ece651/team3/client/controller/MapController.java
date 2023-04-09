package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.model.Game;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class MapController {
    @FXML
    private Group DarkBay;
    @FXML
    private Group Drone;
    @FXML
    private Group GoldenFields;
    @FXML
    private Group MistyHollow;
    @FXML
    private Group Pyke;
    @FXML
    private Group RiverRun;
    @FXML
    private Group SouthHeaven;
    @FXML
    private Group Stormlands;
    @FXML
    private Group TheEyrie;
    @FXML
    private Group TheIronIslands;
    @FXML
    private Group TheNorth;
    @FXML
    private Group TheSouth;

    Game gameEntity;

    @FXML
    void getNorth(MouseEvent event) {
        String text = TheNorth.getAccessibleText();

    }

    public MapController(Game _gameEntity) {
        this.gameEntity = _gameEntity;
    }

}

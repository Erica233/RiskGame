package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MoveChoiceController {
    @FXML
    private RadioButton move;
    @FXML
    private ToggleGroup action;

    Game gameEntity;
    Stage stage;

    public MoveChoiceController(Stage _stage, Game _gameEntity, ToggleGroup _actoin) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
        this.action = _actoin;
    }


    public void initialize() {
        move.setToggleGroup(action);
    }
}

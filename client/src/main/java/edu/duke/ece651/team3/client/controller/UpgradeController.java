package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpgradeController {
    @FXML
    private ChoiceBox<?> LV0_choice;
    @FXML
    private ChoiceBox<?> LV1_choice;
    @FXML
    private ChoiceBox<?> LV2_choice;
    @FXML
    private ChoiceBox<?> LV3_choice;
    @FXML
    private ChoiceBox<?> LV4_choice;
    @FXML
    private ChoiceBox<?> LV5_choice;
    @FXML
    private ChoiceBox<?> LV6_choice;
    @FXML
    private ChoiceBox<?> choice_source;
    @FXML
    private ChoiceBox<?> choice_Dest;
    @FXML
    private Label playerColor;
    @FXML
    private Button nextButton;

    Game gameEntity;
    Stage stage;

    public UpgradeController(Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;

    }


}

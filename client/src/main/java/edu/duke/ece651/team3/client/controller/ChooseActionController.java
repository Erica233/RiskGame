package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ChooseActionController {
    @FXML
    private Button next;
    @FXML
    private RadioButton move;
    @FXML
    private ToggleGroup Action;
    @FXML
    private RadioButton upgrade;
    @FXML
    private RadioButton attack;
    @FXML
    private RadioButton done;
    @FXML
    private Label playerColor;

    private String color;
    private int playerId;

    private Stage stage;

    private Game gameEntity;

    public ChooseActionController(int id, Stage _stage, Game _gameEntity) {
        this.playerId = id;
        this.stage = _stage;
        this.gameEntity = _gameEntity;
    }

    @FXML
    void goToActionPage(MouseEvent event) throws IOException {
        if(move.isSelected()){
            ShowViews.showGameView(stage, "/ui/movePage.fxml", gameEntity);
        }
        else if(upgrade.isSelected()){
            ShowViews.showGameView(stage, "/ui/upgradePage.fxml", gameEntity);
        }
        else if(attack.isSelected()){
            ShowViews.showGameView(stage, "/ui/attackPage.fxml", gameEntity);
        }
        else if(done.isSelected()){
            ShowViews.showGameView(stage, "/ui/donePage.fxml", gameEntity);
        }
    }

    //    @FXML
    public void initialize() {
        if (playerId == 0) {
            playerColor.setText("You are the Orange Player. What would you like to do?");
        } else {
            playerColor.setText("You are the Blue Player. What would you like to do?");
        }
    }
}


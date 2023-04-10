package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class UpgradeChoiceController {
    @FXML
    private RadioButton upgrade;
    @FXML
    private ToggleGroup action;

    Game gameEntity;
    Stage stage;

    public UpgradeChoiceController(Stage _stage, Game _gameEntity, ToggleGroup _actoin) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
        this.action = _actoin;
    }

    @FXML
    void goToActionPage(MouseEvent event) throws IOException {
        if(upgrade.isSelected()){
            Parent nextPageParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/slider.fxml")));
            Scene nextPageScene = new Scene(nextPageParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(nextPageScene);
            window.show();
        }
    }

    public void initialize() {
        upgrade.setToggleGroup(action);
    }
}

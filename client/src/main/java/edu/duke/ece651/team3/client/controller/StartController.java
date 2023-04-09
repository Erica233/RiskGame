package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.App;
import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.ClientCommunicator;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML
    private Button startButton;
    @FXML
    private Button quitButton;

    Game gameEntity;

    public StartController(Game _gameEntity) {
        this.gameEntity = _gameEntity;
    }


    @FXML
    public void onQuitButton(ActionEvent ae) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onStartButton(ActionEvent ae) throws Exception {
        gameEntity.storePlayerId();
        System.out.println("playerId=" + gameEntity.getPlayerId());
        if (gameEntity.getPlayerId() != 0 && gameEntity.getPlayerId() != 1) {
            throw new Exception("Failed to receive valid playerId!");
        }
        ShowViews.showGameView(App.stage, "/ui/whole.fxml", gameEntity);

    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}

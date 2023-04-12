package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.App;
import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.ClientCommunicator;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML
    private Button startButton;
    @FXML
    private Button quitButton;
    @FXML
    private Label waitInfo;

    Game gameEntity;
    Stage stage;

    public StartController(Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
    }

    @FXML
    public void onQuitButton(ActionEvent ae) {
        Stage currStage = (Stage) quitButton.getScene().getWindow();
        currStage.close();
    }

    @FXML
    public void onStartButton(ActionEvent ae) throws Exception {
        //waitInfo.setVisible(true);
//        waitInfo.setText("Connecting......");
        gameEntity = new Game();
        //waitInfo.setVisible(true);
        gameEntity.storePlayerId();
        int playerID = gameEntity.getPlayerId();
        System.out.println("playerId=" + playerID);
        if (playerID != 0 && playerID != 1) {
            throw new Exception("Failed to receive valid playerId!");
        }
        gameEntity.storeNewBoard();
        //waitInfo.setText("Connecting......");
        System.out.println("A new turn: updated new board as below!");
        System.out.println(gameEntity.getRiskGameBoard().displayBoard());
        ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);

    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}

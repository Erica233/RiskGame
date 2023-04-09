package edu.duke.ece651.team3.client.controller;

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

//    RiskGameBoard riskGameBoard;
//    int playerId = -1;
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
    public void onStartButton(ActionEvent ae) throws IOException {
//        Object source = ae.getSource();
//        if (source instanceof Button) {
//            Button btn = (Button) source;
//            currentNumber.setText(currentNumber.getText() + btn.getText());
//        } else {
//            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
//        }
//        if (playerId == 0 || playerId == 1) {
//            System.out.println("playerId=" + playerId);
//
//        }
        gameEntity.storePlayerId();
        System.out.println("playerId=" + gameEntity.getPlayerId());


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

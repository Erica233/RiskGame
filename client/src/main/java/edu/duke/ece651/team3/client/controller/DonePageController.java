package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DonePageController {
    @FXML
    private Label playerColor;
    @FXML
    private Label turnResults;

    private int playerId;
    private Game gameEntity;
    private Stage stage;

    @FXML
    private Label gameResultText;

    @FXML
    private Button quitButton;

    int gameResult = -1;

    public DonePageController(int id, Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
        this.playerId = id;
    }

    @FXML
    public void onGameResults(ActionEvent ae) throws Exception {
        gameResult = gameEntity.recvGameResult();
        if (gameResult == 0 || gameResult == 1) {
            ShowViews.showGameView(stage, "/ui/resultPage.fxml", gameEntity);
        } else {
            gameEntity.storeNewBoard();
            gameEntity.clearActionList();
            ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
        }

    }

    public void initialize() throws IOException, ClassNotFoundException {
        if (playerId == 0) {
            playerColor.setText("You are the Orange Player. Your last turn results are:");
        } else {
            playerColor.setText("You are the Blue Player. Your last turn results are:");
        }
        HashMap<String, Integer> turnResultsMap = gameEntity.recvTurnResults();
        String occupyResults = "\nYou occupy: \n";
        String loseResults = "\nYou lose: \n";
        if (turnResultsMap.size() == 0) {
            turnResults.setText("Your ownership of territories did not change.");
        } else {
            for (String territoryName: turnResultsMap.keySet()) {
                System.out.println("playerId=" + playerId);
                System.out.println("territory.getwinnerId=" + turnResultsMap.get(territoryName));
                if (turnResultsMap.get(territoryName) == playerId) {
                    occupyResults += territoryName;
                    occupyResults += "\n";
                } else {
                    loseResults += territoryName;
                    loseResults += "\n";
                }
            }
            turnResults.setText(occupyResults + loseResults);
        }
    }
}

package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultPageController {
    private Stage stage;
    private Game gameEntity;
    private  int playerID;
    private  int gameResult;

    @FXML
    private Label gameResultText;
    @FXML
    private Button startButton;
    @FXML
    private Button quitButton;

    ResultPageController(int _gameResult, Stage _stage, Game _gameEntity){
        this.stage = _stage;
        this.gameEntity = _gameEntity;
        this.gameResult = _gameResult;
    }

    @FXML
    void onReturnStartPage(ActionEvent event) {
        Stage currStage = (Stage) quitButton.getScene().getWindow();
        currStage.close();
    }

    @FXML
    public void initialize() {
        getResult();
    }

    @FXML
    public void getResult() {
        if (gameResult == 0) {
            if(playerID == 0) {
                gameResultText.setText("You are Orange Player You Are the Winner!");
            }
            else {
                gameResultText.setText("You are Blue Player You Are the Loser!");
            }

        }
        else if (gameResult == 1){
            if(playerID == 0) {
                gameResultText.setText("You are Orange Player You Are the Loser!");
            }
            else {
                gameResultText.setText("You are Blue Player You Are the Winner!");
            }
        }
    }

}
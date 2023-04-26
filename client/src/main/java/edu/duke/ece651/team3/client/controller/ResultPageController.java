package edu.duke.ece651.team3.client.controller;
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

    @FXML
    private Label gameResultText;
    @FXML
    private Button startButton;
    @FXML
    private Button quitButton;

    public ResultPageController(int _id, Stage _stage, Game _gameEntity){
        this.stage = _stage;
        this.gameEntity = _gameEntity;
        playerID = _id;
    }

    /**
     * quit the game then close the window and close the pipe
     * @param event
     * @throws IOException
     */
    @FXML
    void onQuitButton(ActionEvent event) throws IOException {
        Stage currStage = (Stage) quitButton.getScene().getWindow();
        currStage.close();
        gameEntity.closePipes();
    }

    /**
     * initialize the ResultPageController
     * get the result of the game
     */
    @FXML
    public void initialize() {
        getResult();
    }

    /**
     * set the text about the result of the game, show to the player
     */
    @FXML
    public void getResult() {
        int gameResult = gameEntity.getGameResult();
        System.out.println("The current playerId is: " + playerID + " The game result is: " + gameResult);
        if (gameResult == 0) {
            if(playerID == 0) {
                gameResultText.setText("You are BROWN Player  You Are the Winner!");
            }
            else {
                gameResultText.setText("You are WHITE Player You Are the Loser!");
            }

        }
        else if (gameResult == 1){
            if(playerID == 0) {
                gameResultText.setText("You are BROWN Player You Are the Loser!");
            }
            else {
                gameResultText.setText("You are WHITE Player You Are the Winner!");
            }
        }
    }

}

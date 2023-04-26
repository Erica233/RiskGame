package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    /**
     * if the player click the quit button, close the window
     * @param ae
     * @throws IOException
     */
    @FXML
    public void onQuitButton(ActionEvent ae) throws IOException {
        Stage currStage = (Stage) quitButton.getScene().getWindow();
        currStage.close();
    }

    /**
     * if user click the start button, connect to the server and start the game
     * @param ae
     */
    @FXML
    public void onStartButton(ActionEvent ae) throws IOException {
//        gameEntity = new Game();
        ShowViews.showStartView(stage, "/ui/loginPage.fxml", gameEntity);

//        String ssound = "bgm.mp3";
//        Media sound = new Media(ssound);
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();

    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}

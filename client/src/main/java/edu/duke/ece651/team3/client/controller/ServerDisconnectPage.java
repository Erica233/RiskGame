package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerDisconnectPage {
    private Stage stage;
    private Game gameEntity;
    private  int playerID;

    @FXML
    private Label gameResultText;
    @FXML
    private Button startButton;
    @FXML
    private Button okButton;

    public ServerDisconnectPage(int _id, Stage _stage, Game _gameEntity){
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
    void onOkButton(MouseEvent event) throws IOException {
        ShowViews.showGameView(stage, "/ui/start.fxml", gameEntity);
        gameEntity.closePipes();
    }

    /**
     * initialize the ResultPageController
     * get the result of the game
     */
    @FXML
    public void initialize() {
    }

}

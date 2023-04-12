package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.App;
import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.ClientCommunicator;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.Player;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    public void onQuitButton(ActionEvent ae) throws IOException {
        Stage currStage = (Stage) quitButton.getScene().getWindow();
        currStage.close();
        //gameEntity.closePipes();
    }

    @FXML
    public void onStartButton(ActionEvent ae){
        waitInfo.setVisible(true);
        Thread th = new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                try {
//                    Thread.sleep(5000);
                    gameEntity = new Game();
                    gameEntity.storePlayerId();
                    int playerID = gameEntity.getPlayerId();
                    System.out.println("playerId=" + playerID);
                    if (playerID != 0 && playerID != 1) {
                        throw new Exception("Failed to receive valid playerId!");
                    }
                    gameEntity.storeNewBoard();
                    System.out.println("A new turn: updated new board as below!");
                    System.out.println(gameEntity.getRiskGameBoard().displayBoard());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }

        });
        th.setDaemon(true);
        th.start();
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}

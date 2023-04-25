package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
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
    private Label eventResult;

    @FXML
    private Button quitButton;

    int gameResult = -1;

    /**
     * constructor of DonePageController
     * @param id player id
     * @param _stage stage
     * @param _gameEntity gameEntity
     */
    public DonePageController(int id, Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
        this.playerId = id;
    }

    /**
     * show game result, if not end show the whole map and choice, if end show the result page
     * @param ae Action event
     * @throws Exception
     */
    @FXML
    public void onGameResults(ActionEvent ae) throws Exception {
//        if(!gameEntity.isServerConnect()){
//            System.out.println("The server is disconnected!");
//            ShowViews.showGameView(stage, "/ui/serverDisconnectPage.fxml", gameEntity);
//        }
//
//        Thread th = new Thread(new Task() {
//            @Override
//            protected Object call() throws Exception {
//                gameResult = gameEntity.recvGameResult();
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            if (gameResult == 0 || gameResult == 1) {
//                                ShowViews.showGameView(stage, "/ui/resultPage.fxml", gameEntity);
//                            } else {
//                                gameEntity.storeNewBoard();
//                                gameEntity.clearActionList();
//                                ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
//                            }
//
//                        } catch (IOException | ClassNotFoundException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                });
//                return null;
//            }
//        });
//        th.setDaemon(true);
//        th.start();

        gameResult = gameEntity.recvGameResult();

        if (gameResult == 0 || gameResult == 1) {
            ShowViews.showGameView(stage, "/ui/resultPage.fxml", gameEntity);
        } else {
            gameEntity.storeNewBoard();
            gameEntity.clearActionList();
            ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);
        }
    }

    /**
     * initialize the DonePageController, show the last turn's game result
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void initialize() throws IOException, ClassNotFoundException {
        if (playerId == 0) {
            playerColor.setText("You are the Orange Player. Your last turn results are:");
        } else {
            playerColor.setText("You are the Blue Player. Your last turn results are:");
        }
        HashMap<String, Integer> turnResultsMap = (HashMap<String, Integer>) gameEntity.recvTurnResults();
        System.out.println("Has received turnResultMap?" + turnResultsMap);
        HashMap<Integer, String> eventResultsMap = (HashMap<Integer, String>) gameEntity.recvEventResults();
        System.out.println("Has received eventResultMap?" + eventResultsMap);
        eventResult.setText(eventResultsMap.get(playerId));

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

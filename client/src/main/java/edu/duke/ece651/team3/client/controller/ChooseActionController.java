package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class ChooseActionController {
    @FXML
    private Button next;
    @FXML
    private RadioButton move;
    @FXML
    private ToggleGroup Action;
    @FXML
    private RadioButton upgrade;
    @FXML
    private RadioButton attack;
    @FXML
    private RadioButton done;
    @FXML
    private Label playerColor;
    @FXML
    private Label waitInfo;

    @FXML
    private RadioButton eventButton;

    private String color;
    private int playerId;
    private Stage stage;
    private Game gameEntity;

    public ChooseActionController(int id, Stage _stage, Game _gameEntity) {
        this.playerId = id;
        this.stage = _stage;
        this.gameEntity = _gameEntity;
    }


    /**
     * show the game view according to the selected choice
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    void goToActionPage(MouseEvent event) throws IOException, ClassNotFoundException {
        if(move.isSelected()){
            ShowViews.showGameView(stage, "/ui/movePage.fxml", gameEntity);
        }
        else if(upgrade.isSelected()){
            ShowViews.showGameView(stage, "/ui/upgradePage.fxml", gameEntity);
        }
        else if(attack.isSelected()){
            ShowViews.showGameView(stage, "/ui/attackPage.fxml", gameEntity);
        }
        else if(eventButton.isSelected()){
            ShowViews.showGameView(stage, "/ui/eventPage.fxml", gameEntity);
        }
        else if(done.isSelected()){
            waitInfo.setVisible(true);
            if(!gameEntity.isServerConnect()){
                System.out.println("The server is disconnected!");
                ShowViews.showGameView(stage, "/ui/serverDisconnectPage.fxml", gameEntity);
            }
            Thread th = new Thread(new Task() {
                @Override
                protected Object call() throws Exception {
                    try {
                        gameEntity.sendAllActions();
                        gameEntity.printActionsLists();
                        gameEntity.storeNewBoard();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ShowViews.showGameView(stage, "/ui/donePage.fxml", gameEntity);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                    } catch (ClassNotFoundException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }

            });
            th.setDaemon(true);
            th.start();
        }
    }

    /**
     * initialize the ChooseActionController
     * show the text of label playerColor
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void initialize() throws IOException, ClassNotFoundException {
        if (playerId == 0) {
            playerColor.setText("You are the Orange Player. What would you like to do?");
        } else {
            playerColor.setText("You are the Blue Player. What would you like to do?");
        }
        //gameEntity.storeNewBoard();
        System.out.println("A new turn: updated new board as below!");
        System.out.println(gameEntity.getRiskGameBoard().displayBoard());
    }
}


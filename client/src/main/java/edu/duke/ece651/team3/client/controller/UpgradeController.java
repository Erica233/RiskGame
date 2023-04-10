package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpgradeController {
    @FXML
    private ChoiceBox<?> LV0_choice;
    @FXML
    private ChoiceBox<?> LV1_choice;
    @FXML
    private ChoiceBox<?> LV2_choice;
    @FXML
    private ChoiceBox<?> LV3_choice;
    @FXML
    private ChoiceBox<?> LV4_choice;
    @FXML
    private ChoiceBox<?> LV5_choice;
    @FXML
    private ChoiceBox<?> LV6_choice;
    @FXML
    private ChoiceBox<?> choice_source;
    @FXML
    private ChoiceBox<?> choice_Dest;
    @FXML
    private Label playerColor;
    @FXML
    private Button nextButton;

    Game gameEntity;
    Stage stage;

    public UpgradeController(Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;

    }

    @FXML
    public void onUpgradePage(ActionEvent ae) throws Exception {
        gameEntity.storePlayerId();
        int playerID = gameEntity.getPlayerId();
        System.out.println("playerId=" + playerID);
        if (playerID != 0 && playerID != 1) {
            throw new Exception("Failed to receive valid playerId!");
        }
        gameEntity.storeNewBoard();
        System.out.println("A new turn: updated new board as below!");
        System.out.println(gameEntity.getRiskGameBoard().displayBoard());
        ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);



    }


}

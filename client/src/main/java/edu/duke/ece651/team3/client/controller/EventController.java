package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EventController {
    @FXML
    private ChoiceBox<String> choice_source;
    @FXML
    private Label playerColor;
    @FXML
    private Button nextButton;

    private int playerID;
    private HashMap<Unit, ChoiceBox<Integer>> eachLevelUnitNum; //The Unit and its choices
    private ArrayList<ChoiceBox<Integer>> allChoiceBoxes; //All choice boxes
    private Game gameEntity;
    private Stage stage;

    public EventController(int id, Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
        this.playerID = id;
    }

    /**
     * get user's upgrade action and
     * check whether the user's upgrade is valid or not
     * @param ae
     * @throws Exception
     */
    @FXML
    public void onCheckValidEvent(ActionEvent ae) throws Exception {
        ArrayList<Unit> units = RiskGameBoard.initBasicUnits(0);
//        for (int level = 0; level < units.size(); level++) {
//            if (allChoiceBoxes.get(level).getValue() != null) {
//                units.get(level).setNumUnits(allChoiceBoxes.get(level).getValue());
//            }
//        }
        UpgradeAction upgradeAction = new UpgradeAction(choice_source.getValue(), choice_source.getValue(), units);
        gameEntity.checkValidAction(upgradeAction);
        gameEntity.storeActionToList(upgradeAction);
        gameEntity.executeAction(upgradeAction);
        ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);

    }


    /**
     * add the owned territory into choice_source
     */
    private void initializeSourceChoice() {
        Player currPlayer = gameEntity.getRiskGameBoard().getAllPlayers().get(playerID);
        ArrayList<Territory> selfTerr = currPlayer.getOwnedTerritories();
        choice_source.getItems().clear();
        for (Territory Terr : selfTerr) {
            choice_source.getItems().add(Terr.getTerritoryName());
        }
    }

    /**
     * This method initializes all the choice boxes
     *
     */
    @FXML
    public void initialize() {
        initializeId();
        initializeSourceChoice();
    }

    /**
     * This method initialize the player ID and its corresponding text field
     */
    @FXML
    public void initializeId() {
        if (playerID == 0) {
            playerColor.setText("You are the Orange Player. You Chose EVENT");
        }
        else {
            playerColor.setText("You are the Blue Player. You Chose EVENT");
        }
        System.out.println("set id" + playerColor);
    }

}

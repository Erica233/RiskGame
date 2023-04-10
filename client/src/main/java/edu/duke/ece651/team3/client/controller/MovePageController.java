package edu.duke.ece651.team3.client.controller;
import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.A;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MovePageController {
    @FXML
    private ChoiceBox<Integer> LV0_choice;
    @FXML
    private ChoiceBox<Integer> LV1_choice;
    @FXML
    private ChoiceBox<Integer> LV2_choice;
    @FXML
    private ChoiceBox<Integer> LV3_choice;
    @FXML
    private ChoiceBox<Integer> LV4_choice;
    @FXML
    private ChoiceBox<Integer> LV5_choice;
    @FXML
    private ChoiceBox<Integer> LV6_choice;
    @FXML
    private ChoiceBox<String> choice_source;
    @FXML
    private ChoiceBox<String> choice_Dest;
    @FXML
    private Label playerColor;
    @FXML
    private Button nextButton;

    private int playerID;
    private HashMap<Unit, ChoiceBox<Integer>> eachLevelUnitNum; //The Unit and its choices
    private ArrayList<ChoiceBox<Integer>> allChoiceBoxes; //All choice boxes
    private Game gameEntity;
    Stage stage;

    public MovePageController(int id, Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.playerID = id;
        this.stage = _stage;
    }

    @FXML
    public void onCheckValidMove(ActionEvent ae) throws Exception {
        ArrayList<Unit> units = RiskGameBoard.initBasicUnits(0);
        units.get(0).setNumUnits(LV0_choice.getValue());
        units.get(1).setNumUnits(LV1_choice.getValue());
        units.get(2).setNumUnits(LV2_choice.getValue());
        units.get(3).setNumUnits(LV3_choice.getValue());
        units.get(4).setNumUnits(LV4_choice.getValue());
        units.get(5).setNumUnits(LV5_choice.getValue());
        units.get(6).setNumUnits(LV6_choice.getValue());
        MoveAction moveAction = new MoveAction(choice_source.getValue(), choice_source.getValue(), units);
        gameEntity.checkValidAction(moveAction);
        gameEntity.storeActionToList(moveAction);
        gameEntity.executeAction(moveAction);
        ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);

    }

    /**
     * This method wraps all the choice boxes into allChoiceBoxes
     */
    public void wrapUpChoices() {
        LV0_choice = new ChoiceBox<Integer>();
        LV1_choice = new ChoiceBox<Integer>();
        LV2_choice = new ChoiceBox<Integer>();
        LV3_choice = new ChoiceBox<Integer>();
        LV4_choice = new ChoiceBox<Integer>();
        LV5_choice = new ChoiceBox<Integer>();
        LV6_choice = new ChoiceBox<Integer>();
        allChoiceBoxes = new ArrayList<>();
        allChoiceBoxes.add(LV0_choice);
        allChoiceBoxes.add(LV1_choice);
        allChoiceBoxes.add(LV2_choice);
        allChoiceBoxes.add(LV3_choice);
        allChoiceBoxes.add(LV4_choice);
        allChoiceBoxes.add(LV5_choice);
        allChoiceBoxes.add(LV6_choice);
    }

    public void wrapUpUnitChoices() {
        this.eachLevelUnitNum = new HashMap<>();
        RiskGameBoard r = gameEntity.getRiskGameBoard();

        Player currPlayer = r.getAllPlayers().get(playerID);
        ArrayList<Territory> allTerrs = currPlayer.getOwnedTerritories();

        for (Territory Terr : allTerrs) {
            ArrayList<Unit> currUnits = Terr.getUnits();
            for (int i = 0; i < currUnits.size(); i++) {
                eachLevelUnitNum.put(currUnits.get(i), allChoiceBoxes.get(i));
            }
        }
    }

    /**
     * This method initialize all units choices
     */
    private void initializeUnitChoice() {
        for (Unit currUnit : eachLevelUnitNum.keySet()) {
            for (int i = 0; i < currUnit.getNumUnits(); i++) {
                eachLevelUnitNum.get(currUnit).getItems().add(i);
            }
        }
    }
    private void initializeSourceChoice() {
        Player currPlayer = gameEntity.getRiskGameBoard().getAllPlayers().get(playerID);

        ArrayList<Territory> selfTerr = currPlayer.getOwnedTerritories();
        for (Territory Terr : selfTerr) {
//            System.out.println("territory:" + );
            choice_source.getItems().add(Terr.getTerritoryName());
        }
    }

    private void initializeDestChoice() {
        Player currPlayer = gameEntity.getRiskGameBoard().getAllPlayers().get(1 - playerID);

        ArrayList<Territory> selfTerr = currPlayer.getOwnedTerritories();
        for (Territory Terr : selfTerr) {
            choice_Dest.getItems().add(Terr.getTerritoryName());
        }
    }


    /**
     * This method initializes all the choice boxes
     *
     */
    @FXML
    public void initialize() {
        initializeId();
        wrapUpChoices();
        wrapUpUnitChoices();
        initializeUnitChoice();
        initializeSourceChoice();
        initializeDestChoice();

        //System.out.println("choice_source: " + choice_source.getValue());

    }



    /**
     * This method initialize the player ID and its corresponding text field
     */
    @FXML
    public void initializeId() {
        if (playerID == 0) {
            playerColor.setText("You are the Orange Player. What would you like to do?");
        } else {
            playerColor.setText("You are the Blue Player. What would you like to do?");
        }
    }

}


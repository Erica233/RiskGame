package edu.duke.ece651.team3.client.controller;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.Player;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import edu.duke.ece651.team3.shared.Unit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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




    public MovePageController(Game _gameEntity, int id) {
        this.gameEntity = _gameEntity;
        playerID = id;
    }
    public MovePageController(){

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

    }

    private void initializeDestChoice() {
    }


    /**
     * This method initializes all the choice boxes
     *
     */
    @FXML
    public void initialize() {
        wrapUpChoices();
        wrapUpUnitChoices();
        initializeUnitChoice();
        initializeSourceChoice();
        initializeDestChoice();

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


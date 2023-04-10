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
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.A;

import java.net.URL;
import java.util.*;

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
    private Stage stage;




    public MovePageController(int id, Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
        this.playerID = id;
    }

    /**
     * This method wraps all the choice boxes into allChoiceBoxes
     */
    public void wrapUpChoices() {
//        LV0_choice = new ChoiceBox<Integer>();
//        LV1_choice = new ChoiceBox<Integer>();
//        LV2_choice = new ChoiceBox<Integer>();
//        LV3_choice = new ChoiceBox<Integer>();
//        LV4_choice = new ChoiceBox<Integer>();
//        LV5_choice = new ChoiceBox<Integer>();
//        LV6_choice = new ChoiceBox<Integer>();
        allChoiceBoxes = new ArrayList<>();
        allChoiceBoxes.add(LV0_choice);
        allChoiceBoxes.add(LV1_choice);
        allChoiceBoxes.add(LV2_choice);
        allChoiceBoxes.add(LV3_choice);
        allChoiceBoxes.add(LV4_choice);
        allChoiceBoxes.add(LV5_choice);
        allChoiceBoxes.add(LV6_choice);
    }

    public void wrapUpUnitChoices(Territory currTerr) {
        this.eachLevelUnitNum = new HashMap<>();
        RiskGameBoard r = gameEntity.getRiskGameBoard();

        Player currPlayer = r.getAllPlayers().get(playerID);

        ArrayList<Unit> currUnits = currTerr.getUnits();
        for (int i = 0; i < currUnits.size(); i++) {
            eachLevelUnitNum.put(currUnits.get(i), allChoiceBoxes.get(i));//Unit, CheckBox(LV?)
//            System.out.println("curr unit is: " + currUnits.get(i).getUnitName() + "currentBox: " + allChoiceBoxes.get(i));
        }
    }

    /**
     * This method initialize all units choices
     */
    private void initializeUnitChoice(Territory currTerr) {
        Set<Integer> items = new HashSet<>();
        System.out.println("curr Territory's name is: " + currTerr.getTerritoryName());
        ArrayList<Unit> currUnits = currTerr.getUnits();
        for (Unit unit : currUnits) {
            System.out.println("curr Unit is: " + unit.getUnitName() + "currBox is: " + eachLevelUnitNum.get(unit));
            System.out.println("curr unit's number is: " + unit.getNumUnits());
            if(unit.getNumUnits() == 0){
                items.add(0);
            }
            for (int i = 0; i < unit.getNumUnits() + 1; i++) {
                items.add(i);

            }
            eachLevelUnitNum.get(unit).getItems().addAll(new ArrayList<>(items));
            items.clear();
            System.out.println("currBox is: " + eachLevelUnitNum.get(unit) + "curr items:" + eachLevelUnitNum.get(unit).getItems());
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

    public void setTerrUnit(){
        choice_source.setOnAction(e -> {
            // Retrieve the selected value from the choice box
            String currTerrName = choice_source.getValue();

            // Do something with the selected value
            System.out.println("Selected option: " + currTerrName);

            Player currPlayer = gameEntity.getRiskGameBoard().getAllPlayers().get(playerID);
            Territory currTerr = currPlayer.findOwnedTerritoryByName(currTerrName);
            wrapUpUnitChoices(currTerr);
            initializeUnitChoice(currTerr);
            eachLevelUnitNum.clear();
        });
    }


    /**
     * This method initializes all the choice boxes
     *
     */
    @FXML
    public void initialize() {
        System.out.println("initialize all entities");
        initializeId();
        initializeSourceChoice();
        initializeDestChoice();

        wrapUpChoices();
        setTerrUnit();

//        LV0_choice.getItems().addAll(1, 2, 3, 4, 5);



//        for(Unit u : eachLevelUnitNum.keySet()){
//            System.out.println("Current unit is: " + u + "choicebox is: " + eachLevelUnitNum.get(u));
//        }

//        //Test
//        this.eachLevelUnitNum = new HashMap<>();
//        RiskGameBoard r = gameEntity.getRiskGameBoard();
//
//        Player currPlayer = r.getAllPlayers().get(playerID);
//        ArrayList<Territory> allTerrs = currPlayer.getOwnedTerritories();
//
//        for (Territory Terr : allTerrs) {
//            ArrayList<Unit> currUnits = Terr.getUnits();
//            for (int i = 0; i < currUnits.size(); i++) {
//                System.out.println("Level " + i + "is: " + allChoiceBoxes.get(i));
//                System.out.println("Current number is: " + currUnits.get(i).getNumUnits());
//            }
//        }
    }



    /**
     * This method initialize the player ID and its corresponding text field
     */
    @FXML
    public void initializeId() {

        if (playerID == 0) {
            playerColor.setText("You are the Orange Player. You Chose MOVE");

        } else {
            playerColor.setText("You are the Blue Player. You Chose MOVE");
        }
        System.out.println("set id" + playerColor);
    }


}


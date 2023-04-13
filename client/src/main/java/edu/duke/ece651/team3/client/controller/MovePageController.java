package edu.duke.ece651.team3.client.controller;
import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    @FXML
    private AnchorPane contextBox;

    private int playerID;

    private HashMap<Unit, ChoiceBox<Integer>> eachLevelUnitNum; //The Unit and its choices

    private ArrayList<ChoiceBox<Integer>> allChoiceBoxes; //All choice boxes

    private Game gameEntity;
    Stage stage;

    /**
     * constructor of MovePageController
     * @param id player id
     * @param _stage stage
     * @param _gameEntity gameEntity
     */
    public MovePageController(int id, Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.playerID = id;
        this.stage = _stage;
    }

    /**
     *
     * @param ae ActionEvent
     * @throws Exception
     */
    @FXML
    public void onCheckValidMove(ActionEvent ae) throws Exception {
        ArrayList<Unit> units = RiskGameBoard.initBasicUnits(0);
        for (int level = 0; level < units.size(); level++) {
            if (allChoiceBoxes.get(level).getValue() != null) {
                units.get(level).setNumUnits(allChoiceBoxes.get(level).getValue());
            }
        }
        MoveAction moveAction = new MoveAction(choice_source.getValue(), choice_Dest.getValue(), units);

        gameEntity.checkValidAction(moveAction);
        gameEntity.storeActionToList(moveAction);
        gameEntity.executeAction(moveAction);
        //System.out.println("in onCheckValidMove");
        //System.out.println(gameEntity.getRiskGameBoard().displayBoard());

        ShowViews.showGameView(stage, "/ui/whole.fxml", gameEntity);

    }

    /**
     * This method wraps all the choice boxes into allChoiceBoxes
     */
    public void wrapUpChoices() {
        allChoiceBoxes = new ArrayList<>();
        allChoiceBoxes.add(LV0_choice);
        allChoiceBoxes.add(LV1_choice);
        allChoiceBoxes.add(LV2_choice);
        allChoiceBoxes.add(LV3_choice);
        allChoiceBoxes.add(LV4_choice);
        allChoiceBoxes.add(LV5_choice);
        allChoiceBoxes.add(LV6_choice);
    }

    /**
     * This method wraps all the Unit choices into allChoiceBoxes
     * @param currTerr current territory
     */
    public void wrapUpUnitChoices(Territory currTerr) {
        this.eachLevelUnitNum = new HashMap<>();

        ArrayList<Unit> currUnits = currTerr.getUnits();
        for (int i = 0; i < currUnits.size(); i++) {
            eachLevelUnitNum.put(currUnits.get(i), allChoiceBoxes.get(i));//Unit, CheckBox(LV?)
        }
    }

    /**
     * This method initialize all units choices
     */
    private void initializeUnitChoice(Territory currTerr) {
        Set<Integer> items = new HashSet<>();
//        System.out.println("curr Territory's name is: " + currTerr.getTerritoryName());
        ArrayList<Unit> currUnits = currTerr.getUnits();
        for (Unit unit : currUnits) {
//            System.out.println("curr Unit is: " + unit.getUnitName() + "currBox is: " + eachLevelUnitNum.get(unit));
//            System.out.println("curr unit's number is: " + unit.getNumUnits());
            if(unit.getNumUnits() == 0){
                items.add(0);
            }
            for (int i = 0; i < unit.getNumUnits() + 1; i++) {
                items.add(i);

            }
            eachLevelUnitNum.get(unit).getItems().clear();
            eachLevelUnitNum.get(unit).getItems().addAll(new ArrayList<>(items));
            items.clear();
//            System.out.println("currBox is: " + eachLevelUnitNum.get(unit) + "curr items:" + eachLevelUnitNum.get(unit).getItems());
        }

    }

    /**
     * This method initialize all source choices
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
     * This method initialize all destination choices
     * @param currTerr current territory
     */
    private void initializeDestChoice(Territory currTerr) {
        Player currPlayer = gameEntity.getRiskGameBoard().getAllPlayers().get(playerID);
        choice_Dest.getItems().clear();
        ArrayList<Territory> selfTerr = currPlayer.getOwnedTerritories();
        for (Territory Terr : selfTerr) {
            if(Terr.equals(currTerr)){
                continue;
            }
            choice_Dest.getItems().add(Terr.getTerritoryName());

        }
    }

    /**
     * initialize the destination choices and unit choices according to src territory chosen
     */
    public void onSelectSrcCheckBox(){
        choice_source.setOnAction(e -> {
            // Retrieve the selected value from the choice box
            String currTerrName = choice_source.getValue();

            // Do something with the selected value
            //System.out.println("Selected option: " + currTerrName);

            //Getting the current territory
            Player currPlayer = gameEntity.getRiskGameBoard().getAllPlayers().get(playerID);
            Territory currTerr = currPlayer.findOwnedTerritoryByName(currTerrName);
            wrapUpUnitChoices(currTerr);
            initializeDestChoice(currTerr);
            initializeUnitChoice(currTerr);
        });
    }


    /**
     * This method initializes all the choice boxes
     *
     */
    @FXML
    public void initialize() {
        wrapUpChoices();
        initializeId();

        initializeSourceChoice();
        onSelectSrcCheckBox();
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
    }

}


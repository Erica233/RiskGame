package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.Player;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import edu.duke.ece651.team3.shared.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MapController {
    @FXML
    private Group DarkBay;
    @FXML
    private Group Drone;
    @FXML
    private Group GoldenFields;
    @FXML
    private Group MistyHollow;
    @FXML
    private Group Pyke;
    @FXML
    private Group RiverRun;
    @FXML
    private Group SouthHeaven;
    @FXML
    private Group Stormlands;
    @FXML
    private Group TheEyrie;
    @FXML
    private Group TheIronIslands;
    @FXML
    private Group TheNorth;
    @FXML
    private Group TheSouth;
    @FXML
    private Button hideInfoButton;

    Game gameEntity;
    HashMap<String, String> hashName;


    @FXML
    private Text terrInfo;

    @FXML
    private Text terrName;

    @FXML
    private AnchorPane wholeInfoBox;

    @FXML
    void showTerrInfo(MouseEvent event){
        Object source = event.getSource();
        if (source instanceof Group) {
            Group clickedGroup = (Group) source;
            String clickedTerrName = clickedGroup.getId();
            for(String name : hashName.keySet()){
                if (clickedTerrName.equals(name)) {
                    char c = hashName.get(name).charAt(hashName.get(name).length()-2);
                    RiskGameBoard b = gameEntity.getRiskGameBoard();
                    Territory t = b.getAllPlayers().get(gameEntity.getPlayerId()).findOwnedTerritoryByName(String.valueOf(c));
                    String terrInfoText = getTerrInfo(t);
                    terrName.setText(hashName.get(name));
                    terrInfo.setText(terrInfoText);
                    wholeInfoBox.setVisible(true);
                }
            }
        }
    }

    @FXML
    void hideTerrInfo(ActionEvent event) {
        wholeInfoBox.setVisible(false);
    }


    String getTerrInfo(Territory t){

        String output = "Number of Units: " + t.getNumUnits() + "\n";
        ArrayList<Unit> units = t.getUnits();
        for(int i = 0; i < units.size(); i++){
            output += String.valueOf(i)+". " + units.get(i).getUnitName() + units.get(i).getNumUnits() +"\n";
        }
        output += "\n" + "Distances: ";
//        for () {
//
//        }

        return output;
    }

    public MapController(Game _gameEntity) {
        this.gameEntity = _gameEntity;
        fxidHash();
    }



    /**
     * create the fxid hashmap, key is the fxid, value is the whole name
     */
    public void fxidHash(){
        this.hashName = new HashMap<>();
        this.hashName.put("DarkBay", "Dark Bay(k)");
        this.hashName.put("Drone", "Drone(c)");
        this.hashName.put("GoldenFields","Golden Fields(l)");
        this.hashName.put("MistyHollow","Misty Hollow(f)");
        this.hashName.put("Pyke","Pyke(b)");
        this.hashName.put("RiverRun","Riverrun(i)");
        this.hashName.put("SouthHeaven","Sun Heaven(h)");
        this.hashName.put("Stormlands","Stormlands(g)");
        this.hashName.put("TheEyrie","The Eyrie(a)");
        this.hashName.put("TheIronIslands","The Iron Islands(e)");
        this.hashName.put("TheNorth","The North(j)");
        this.hashName.put("TheSouth","The South(d)");
    }


}

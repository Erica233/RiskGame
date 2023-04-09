package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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
            if (clickedTerrName.equals("TheNorth")) {
                String terrInfoText = gameEntity.getTerritoryInfo(clickedTerrName);
                String terrNameText = gameEntity.getTerritoryName(clickedTerrName);
                terrInfo.setText(terrInfoText);
                terrName.setText(terrNameText);
                wholeInfoBox.setVisible(true);
            }
        }
    }

    @FXML
    void hideTerrInfo(ActionEvent event) {
        wholeInfoBox.setVisible(false);
    }

    public MapController(Game _gameEntity) {
        this.gameEntity = _gameEntity;
    }

}

package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.ShowViews;
import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DonePageController {
    private int playerID;
    private Game gameEntity;
    private Stage stage;

    public DonePageController(int id, Stage _stage, Game _gameEntity) {
        this.gameEntity = _gameEntity;
        this.stage = _stage;
        this.playerID = id;
    }

    @FXML
    public void onTurnResults(ActionEvent ae) throws Exception {


        ShowViews.showGameView(stage, "/ui/donePage.fxml", gameEntity);

    }

}

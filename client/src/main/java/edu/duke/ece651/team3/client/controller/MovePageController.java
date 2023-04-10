package edu.duke.ece651.team3.client.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MovePageController implements Initializable {
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


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        LV0_choice.getItems().addAll("1", "2", "3");
    }



}

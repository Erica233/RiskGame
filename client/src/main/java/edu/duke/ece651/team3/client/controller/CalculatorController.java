package edu.duke.ece651.team3.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {
    @FXML
    TextField currentNumber;
    @FXML
    NumButtonController numButtonController;
    public void initialize(URL location, ResourceBundle resources) {
        numButtonController.currentNumber= currentNumber;
    }
}
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team3.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application{
//  public String getMessage() {
//    return "Hello from the client for "+ MyName.getName();
//  }
//  public static void main(String[] args) {
//    App a = new App();
//    System.out.println(a.getMessage());
////    for (int i = 0; i < args.length; i++) {
////      System.out.println("args["+i+"]="+ args[i]);
////    }
//  }

  @Override
  public void start(Stage stage) throws IOException {
    URL xmlResource = getClass().getResource("/ui/calc-split.xml");
    GridPane gp = FXMLLoader.load(xmlResource);

    Scene scene = new Scene(gp, 640, 480);

    URL cssResource = getClass().getResource("/ui/calcbuttons.css");
    scene.getStylesheets().add(cssResource.toString());

    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) {
    launch();
  }

}

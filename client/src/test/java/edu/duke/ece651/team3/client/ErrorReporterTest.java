//package edu.duke.ece651.team3.client;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import javafx.application.Platform;
//import javafx.scene.Node;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.DialogPane;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.api.FxRobot;
//import org.testfx.framework.junit5.ApplicationExtension;
//
//@ExtendWith(ApplicationExtension.class)
//public class ErrorReporterTest {
//  @Test
//  public void test_alert(FxRobot robot) {
//    ErrorReporter er = new ErrorReporter();
//    Platform.runLater(()->er.uncaughtException(Thread.currentThread(),
//            new IllegalStateException("Test exception")));
//    DialogPane errorDialog = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
//    assertEquals("java.lang.IllegalStateException", errorDialog.getHeaderText());
//    assertEquals("Test exception", errorDialog.getContentText());
//    Node ok = errorDialog.lookupButton(ButtonType.OK);
//    assertNotNull(ok);
//    robot.clickOn(ok);
//  }
//}
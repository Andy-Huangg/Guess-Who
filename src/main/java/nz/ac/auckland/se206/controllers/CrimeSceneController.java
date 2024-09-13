package nz.ac.auckland.se206.controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CrimeSceneController {

  @FXML private Rectangle rectSafe, rectGuestList, rectGlass, rectNewsPaper;
  @FXML private Pane newsPaperPane;
  private boolean newsPaperClicked = false;
  private static boolean[] clueArray = new boolean[3]; // [guestList,glass,newspaper]

  public void initialize() {}

  @FXML
  private void handleRectangleClick(MouseEvent event) {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    switch (clickedRectangle.getId()) {
      case "safe":
        handleSafeInteraction();
        break;
      case "guestList":
        handleGuestListInteraction();
        break;
      case "glass":
        handleGlassInteraction();
        break;
      default:
        handleNewsInteraction();
        break;
    }
  }

  private void handleSafeInteraction() {
    // Handle interaction with the safe
  }

  private void handleGuestListInteraction() {
    // Handle interaction with the guest list
    if (!clueArray[0]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[0] = true;
    }
  }

  private void handleGlassInteraction() {
    // Handle interaction with the broken glass
    if (!clueArray[1]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[1] = true;
    }
  }

  private void handleNewsInteraction() {
    // Handle interaction with the broken glass
    if (!clueArray[2]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[2] = true;
    }
    if (newsPaperClicked == false) {
      newsPaperPane.setVisible(true);
      newsPaperClicked = true;
      TranslateTransition translate = new TranslateTransition();
      translate.setNode(newsPaperPane);
      translate.setDuration(Duration.millis(500));
      translate.setByY(-500);
      translate.play();
    }
  }

  @FXML
  private void closeNewsPaper() {

    if (newsPaperClicked == true) {
      newsPaperClicked = false;
      TranslateTransition translate = new TranslateTransition();
      translate.setNode(newsPaperPane);
      translate.setDuration(Duration.millis(500));
      translate.setByY(500);
      translate.play();
    }
    newsPaperPane.setVisible(false);
  }
}

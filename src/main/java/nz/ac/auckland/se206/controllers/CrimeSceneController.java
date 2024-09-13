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

  public void initialize() {}

  @FXML
  private void handleRectangleClick(MouseEvent event) {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    if (clickedRectangle == rectSafe) {
      handleClue1Interaction();
    } else if (clickedRectangle == rectGuestList) {
      handleClue2Interaction();
    } else if (clickedRectangle == rectGlass) {
      handleClue3Interaction();
    } else if (clickedRectangle == rectNewsPaper) {
      handleClue4Interaction();
    }
  }

  private void handleClue1Interaction() {
    // Handle interaction with the safe
  }

  private void handleClue2Interaction() {
    // Handle interaction with the guest list
  }

  private void handleClue3Interaction() {
    // Handle interaction with the broken glass
  }

  private void handleClue4Interaction() {
    // Handle interaction with the broken glass
    newsPaperPane.setVisible(true);
    if (newsPaperClicked == false) {
      TranslateTransition translate = new TranslateTransition();
      translate.setNode(newsPaperPane);
      translate.setDuration(Duration.millis(750));
      translate.setByY(-500);
      translate.play();
    }
  }
}

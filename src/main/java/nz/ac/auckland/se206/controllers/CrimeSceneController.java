package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class CrimeSceneController {

  @FXML private Rectangle rectSafe, rectGuestList, rectGlass;

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
}

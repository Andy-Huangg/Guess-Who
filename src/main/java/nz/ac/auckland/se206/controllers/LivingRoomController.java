package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class LivingRoomController {
  @FXML private Rectangle rectBruce;

  public void initialize() {}

  @FXML
  private void handleRectangleClick(MouseEvent event) {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    if (clickedRectangle == rectBruce) {
      handleAlfredInteraction();
    }
  }

  private void handleAlfredInteraction() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handleBruceInteraction'");
  }
}

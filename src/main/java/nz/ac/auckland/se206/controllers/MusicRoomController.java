package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class MusicRoomController {
  @FXML private Rectangle rectBruce;

  public void initialize() {}

  @FXML
  private void handleRectangleClick(MouseEvent event) {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    if (clickedRectangle == rectBruce) {
      handleSaulInteraction();
    }
  }

  private void handleSaulInteraction() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handleBruceInteraction'");
  }
}

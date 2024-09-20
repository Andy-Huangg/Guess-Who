package nz.ac.auckland.se206.controllers;

import javafx.scene.Node;
import javafx.scene.robot.Robot;

public class DraggableMaker {

  private double mouseAnchorX;
  private double mouseAnchorY;

  public void makeDraggable(Node node) {
    // Gets current mouse position
    node.setOnMousePressed(
        mouseEvent -> {
          mouseAnchorX = mouseEvent.getX();
          mouseAnchorY = mouseEvent.getY();
        });

    // Set the new position of where the node should be
    node.setOnMouseDragged(
        mouseEvent -> {
          node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
          node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
          // Node resets back to the middle if it has exceeded the borders.
          if (mouseEvent.getSceneX() > 680
              || mouseEvent.getSceneX() < -10
              || mouseEvent.getSceneY() < -10
              || mouseEvent.getSceneY() > 550) {
            new Robot().mouseMove(850, 400);
          }
        });
  }
}

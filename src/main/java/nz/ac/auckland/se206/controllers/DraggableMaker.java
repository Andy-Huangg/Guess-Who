package nz.ac.auckland.se206.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;

/** Make a node draggable within its parent container. */
public class DraggableMaker {

  private double mouseAnchorX;
  private double mouseAnchorY;

  /**
   * Controls the dragging of a node when the mouse is pressed and dragged.
   *
   * @param node the node to make draggable
   */
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
          double newX = mouseEvent.getSceneX() - mouseAnchorX;
          double newY = mouseEvent.getSceneY() - mouseAnchorY;

          // Get the parent container dimensions
          Parent parent = node.getParent();
          double parentWidth = parent.getLayoutBounds().getWidth();
          double parentHeight = parent.getLayoutBounds().getHeight();

          // Get the node dimensions
          double nodeWidth = node.getBoundsInParent().getWidth();
          double nodeHeight = node.getBoundsInParent().getHeight();

          // Ensure the node stays within the bounds of the parent container
          if (newX >= -200 && newX + nodeWidth <= parentWidth + 200) {
            node.setLayoutX(newX);
          }
          if (newY >= -50 && newY + nodeHeight <= parentHeight + 50) {
            node.setLayoutY(newY);
          }
        });
  }
}

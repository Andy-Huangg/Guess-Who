package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class CrimeSceneController {

  @FXML private Rectangle rectSafe, rectGuestList, rectGlass, rectNewsPaper;
  @FXML private Pane newsPaperPane;
  @FXML private Pane wineCluePane;
  @FXML private Pane notepadPane;
  @FXML private Rectangle rectFingerPrint1;
  @FXML private ImageView fingerprint1, fingerprint2;
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
      case "fingerprint1":
        handleFingerPrintInteraction(1);
        break;
      case "fingerprint2":
        handleFingerPrintInteraction(2);
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

  private static boolean hasClickedFingerPrint = false;

  private void handleGlassInteraction() {
    // Handle interaction with the broken glass
    if (!clueArray[1]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[1] = true;
    }
    wineCluePane.setVisible(true);
    if (hasClickedFingerPrint == true) {
      notepadPane.setVisible(true);
    }
  }

  private void handleNewsInteraction() {
    // Handle interaction with the broken glass
    if (!clueArray[2]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[2] = true;
    }

    newsPaperPane.setVisible(true);
  }

  @FXML
  private void closeNewsPaper() {

    newsPaperPane.setVisible(false);
  }

  @FXML
  private void closeWineClue() {

    wineCluePane.setVisible(false);
  }

  private boolean hasClickedFingerPrint1 = false;
  private boolean hasClickedFingerPrint2 = false;

  private void handleFingerPrintInteraction(int number) {
    hasClickedFingerPrint = true;
    notepadPane.setVisible(true);

    if (number == 1) {
      hasClickedFingerPrint1 = true;
      fingerprint1.setVisible(true);
    }
    if (number == 2) {
      fingerprint2.setVisible(true);
    }
  }

  private void updateFingerPrint(int number) {}
}

package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class CrimeSceneController {

  private static boolean hasClickedFingerPrint1 = false;
  private static boolean hasClickedFingerPrint2 = false;
  private static boolean foundBruceFingerPrint = false;
  private static boolean foundSaulFingerPrint = false;
  private static boolean foundAlfredFingerPrint = false;
  @FXML private Rectangle rectSafe, rectGuestList, rectGlass, rectNewsPaper;
  @FXML private Pane newsPaperPane, wineCluePane, notepadPane;
  @FXML private Rectangle rectFingerPrint1, rectFingerPrint2;
  @FXML
  private ImageView fingerprint1,
      fingerprint2,
      brucefingerprint,
      saulfingerprint,
      alfredfingerprint;
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
      updateFingerPrints();
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

  // Deals with clicking on the wine glass clue.
  private void handleFingerPrintInteraction(int number) {
    hasClickedFingerPrint = true;
    notepadPane.setVisible(true);

    if (number == 1) {
      hasClickedFingerPrint1 = true;
    }
    if (number == 2) {
      hasClickedFingerPrint2 = true;
    }
    updateFingerPrints();
  }

  // shows the fingerprints that have been interacted with.
  private void updateFingerPrints() {
    if (hasClickedFingerPrint1) {
      fingerprint1.setVisible(true);
    }
    if (hasClickedFingerPrint2) {
      fingerprint2.setVisible(true);
    }
    if (foundBruceFingerPrint) {
      brucefingerprint.setVisible(true);
    }
    if (foundSaulFingerPrint) {
      saulfingerprint.setVisible(true);
    }
    if (foundAlfredFingerPrint) {
      alfredfingerprint.setVisible(true);
    }
  }

  private void setFingerPrintsFound(String name) {
    switch (name) {
      case "Bruce":
        foundBruceFingerPrint = true;
        break;
      case "Saul":
        foundSaulFingerPrint = true;
        break;
      case "Alfred":
        foundAlfredFingerPrint = true;
        break;
      default:
        break;
    }
  }
}

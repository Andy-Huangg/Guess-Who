package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class CrimeSceneController {

  @FXML private Rectangle rectSafe, rectDocuments, rectWallet, rectNewsPaper;
  @FXML private Pane newsPaperPane, documentsPane;
  @FXML private Pane newsPaperPiece1, newsPaperPiece2, newsPaperPiece3, newsPaperPiece4;
  @FXML private Pane documentsGuestList, documentsInvoice, documentsLetter;
  private static boolean[] clueArray = new boolean[3]; // [guestList,glass,newspaper]

  DraggableMaker draggableMaker = new DraggableMaker();

  public void initialize() {
    draggableMaker.makeDraggable(newsPaperPiece1);
    draggableMaker.makeDraggable(newsPaperPiece2);
    draggableMaker.makeDraggable(newsPaperPiece3);
    draggableMaker.makeDraggable(newsPaperPiece4);
    draggableMaker.makeDraggable(documentsGuestList);
    draggableMaker.makeDraggable(documentsInvoice);
    draggableMaker.makeDraggable(documentsLetter);
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    switch (clickedRectangle.getId()) {
      case "documents":
        handleDocumentsInteraction();
        break;
      case "wallet":
        handleWalletInteraction();
        break;
      default:
        handleNewsInteraction();
        break;
    }
  }

  private void handleDocumentsInteraction() {
    // Handle interaction with the guest list
    if (!clueArray[0]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[0] = true;
    }
    System.out.println("document clicked");
    documentsPane.setVisible(true);
  }

  private void handleWalletInteraction() {
    // Handle interaction with the broken glass
    if (!clueArray[1]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[1] = true;
    }
    System.out.println("wallet clicked");
  }

  private void handleNewsInteraction() {
    // Handle interaction with the broken glass
    if (!clueArray[2]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[2] = true;
    }
    System.out.println("news clicked");
    newsPaperPane.setVisible(true);
  }

  @FXML
  private void closeNewsPaper() {

    newsPaperPane.setVisible(false);
  }

  @FXML
  private void closeDocuments() {
    documentsPane.setVisible(false);
  }
}

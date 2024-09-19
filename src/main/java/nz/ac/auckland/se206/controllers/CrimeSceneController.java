package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;

public class CrimeSceneController {

  @FXML private Rectangle rectSafe;
  @FXML private Rectangle rectDocuments;
  @FXML private Rectangle rectWallet;
  @FXML private Rectangle rectNewsPaper;
  @FXML private Pane newsPaperPane;
  @FXML private Pane documentsPane;
  @FXML private Pane newsPaperPiece1;
  @FXML private Pane newsPaperPiece2;
  @FXML private Pane newsPaperPiece3;
  @FXML private Pane newsPaperPiece4;
  @FXML private Pane documentsGuestList;
  @FXML private Pane documentsInvoice;
  @FXML private Pane documentsLetter;
  private static boolean[] clueArray = new boolean[3]; // [guestList,glass,newspaper]

  private DraggableMaker draggableMaker = new DraggableMaker();

  public void initialize() {
    // creating draggable nodes to make the clue interactive
    draggableMaker.makeDraggable(newsPaperPiece1);
    draggableMaker.makeDraggable(newsPaperPiece2);
    draggableMaker.makeDraggable(newsPaperPiece3);
    draggableMaker.makeDraggable(newsPaperPiece4);
    draggableMaker.makeDraggable(documentsGuestList);
    draggableMaker.makeDraggable(documentsInvoice);
    draggableMaker.makeDraggable(documentsLetter);
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) { // re-direct based on the rectangle clicked
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    App.setClueInteracted(true);
    // id is setted up in fxml
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
    System.out.println("document clicked");
    documentsPane.setVisible(true);
  }

  private void handleWalletInteraction() {
    // Handle interaction with the broken glass
    System.out.println("wallet clicked");
  }

  private void handleNewsInteraction() {
    System.out.println("news clicked");
    newsPaperPane.setVisible(true);
  }

  @FXML
  private void onCloseNewsPaper() {

    newsPaperPane.setVisible(false);
  }

  @FXML
  private void onCloseDocuments() {
    documentsPane.setVisible(false);
  }
}

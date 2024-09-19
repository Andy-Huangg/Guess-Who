package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CrimeSceneController {

  @FXML private Rectangle rectSafe, rectDocuments, rectWallet, rectNewsPaper;
  @FXML private Pane newsPaperPane, documentsPane, walletOpenPane, walletClosedPane, walletCluePane;
  @FXML private Pane newsPaperPiece1, newsPaperPiece2, newsPaperPiece3, newsPaperPiece4;
  @FXML private Pane documentsGuestList, documentsInvoice, documentsLetter;
  @FXML private ImageView ImageDriversLicense, ImageCreditCard, ImageLoyaltyCard;
  private static boolean[] clueArray = new boolean[3]; // [guestList,glass,newspaper]
  private Map<ImageView, Boolean> walletClueMap = new HashMap<>();
  private TranslateTransition cardTranslate = new TranslateTransition();
  private boolean cardTranslating = false;

  DraggableMaker draggableMaker = new DraggableMaker();

  public void initialize() {
    draggableMaker.makeDraggable(newsPaperPiece1);
    draggableMaker.makeDraggable(newsPaperPiece2);
    draggableMaker.makeDraggable(newsPaperPiece3);
    draggableMaker.makeDraggable(newsPaperPiece4);
    draggableMaker.makeDraggable(documentsGuestList);
    draggableMaker.makeDraggable(documentsInvoice);
    draggableMaker.makeDraggable(documentsLetter);
    walletClueMap.put(ImageDriversLicense, false);
    walletClueMap.put(ImageCreditCard, false);
    walletClueMap.put(ImageLoyaltyCard, false);
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
    documentsPane.setVisible(true);
  }

  private void handleWalletInteraction() {
    // Handle interaction with the broken glass
    if (!clueArray[1]) { // if first time clicked
      MainLayoutController.incrementClueCount();
      clueArray[1] = true;
    }
    walletCluePane.setVisible(true);
  }

  @FXML
  private void handleWalletClueClick(MouseEvent event) {

    if (cardTranslating == true) {
      return;
    }
    ImageView currentImage = (ImageView) event.getTarget();
    String currentID = currentImage.getId();
    ImageView imageToMove = null;
    switch (currentID) {
      case "ImageDriversLicense":
        imageToMove = ImageDriversLicense;
        break;
      case "ImageCreditCard":
        imageToMove = ImageCreditCard;
        break;
      case "ImageLoyaltyCard":
        imageToMove = ImageLoyaltyCard;
      default:
        break;
    }
    if (walletClueMap.get(imageToMove) == true) {
      cardTransition(imageToMove, "down");
      walletClueMap.put(imageToMove, false);
    } else {
      cardTransition(imageToMove, "up");
    }
  }

  private void cardTransition(ImageView image, String direction) {
    cardTranslate.setNode(image);
    image.setVisible(true);
    cardTranslate.setDuration(Duration.millis(200));
    if (direction.equals("up")) {
      walletClueMap.put(image, true);
      cardTranslate.setByY(-60);
    } else {
      cardTranslate.setByY(60);
    }
    cardTranslate.play();
    cardTranslating = true;
    cardTranslate.setOnFinished(
        event -> {
          cardTranslating = false;
        });
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

  @FXML
  private void closeWallet() {
    walletCluePane.setVisible(false);
  }

  @FXML
  private void openWallet() {
    walletOpenPane.setVisible(true);
    walletClosedPane.setVisible(false);
  }
}

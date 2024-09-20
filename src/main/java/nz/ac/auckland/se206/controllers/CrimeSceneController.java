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
  @FXML private Pane walletOpenPane;
  @FXML private Pane walletClosedPane;
  @FXML private Pane walletCluePane;
  @FXML private ImageView ImageDriversLicense;
  @FXML private ImageView ImageCreditCard;
  @FXML private ImageView ImageLoyaltyCard;
  @FXML private ImageView newsStroke;
  @FXML private ImageView walletStroke;
  @FXML private ImageView documentStroke;

  private Map<ImageView, Boolean> walletClueMap = new HashMap<>();
  private TranslateTransition cardTranslate = new TranslateTransition();
  private boolean cardTranslating = false;
  private ImageView currentHover;

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
    walletClueMap.put(ImageDriversLicense, false);
    walletClueMap.put(ImageCreditCard, false);
    walletClueMap.put(ImageLoyaltyCard, false);
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
    documentsPane.setVisible(true);
  }

  private void handleWalletInteraction() {
    // Handle interaction with the broken glass
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
        break;
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

  @FXML
  private void closeWallet() {
    walletCluePane.setVisible(false);
  }

  @FXML
  private void openWallet() {
    walletOpenPane.setVisible(true);
    walletClosedPane.setVisible(false);
  }

  @FXML
  private void handleRectangleHover(MouseEvent event) {
    switch (((Rectangle) event.getSource()).getId()) {
      case "documents":
        currentHover = documentStroke;
        break;
      case "wallet":
        currentHover = walletStroke;
        break;
      default:
        currentHover = newsStroke;
        break;
    }
    currentHover.setVisible(true);
  }

  @FXML
  private void handleRectangleUnhover() {
    currentHover.setVisible(false);
  }
}

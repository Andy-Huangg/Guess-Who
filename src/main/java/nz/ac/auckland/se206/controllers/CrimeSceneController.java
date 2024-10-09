package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;

public class CrimeSceneController {

  @FXML private Rectangle rectSafe;
  @FXML private Rectangle rectDocuments;
  @FXML private Rectangle rectShelf;
  @FXML private Rectangle rectNewsPaper;
  @FXML private Rectangle keypadUnderline1;
  @FXML private Rectangle keypadUnderline2;
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
  @FXML private Pane metalPanelPane;
  @FXML private ImageView imageDriversLicense;
  @FXML private ImageView imageCreditCard;
  @FXML private ImageView imageLoyaltyCard;
  @FXML private ImageView newsStroke;
  @FXML private ImageView shelfStroke;
  @FXML private ImageView documentStroke;
  @FXML private Circle panelTopLeftCircle;
  @FXML private Circle panelBotLeftCircle;
  @FXML private Circle panelTopRightCircle;
  @FXML private Circle panelBotRightCircle;

  private Map<ImageView, Boolean> walletClueMap = new HashMap<>();
  private TranslateTransition cardTranslate = new TranslateTransition();
  private boolean cardTranslating = false;
  private ImageView currentHover;
  private Thread closeClueThread = new Thread();
  private Map<String, Boolean> circlesClicked;
  private Timeline keypadFlash;

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
    walletClueMap.put(imageDriversLicense, false);
    walletClueMap.put(imageCreditCard, false);
    walletClueMap.put(imageLoyaltyCard, false);
    circlesClicked = new HashMap<>();
    startKeypadFlash(1);
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
      case "shelf":
        handleShelfInteraction();
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

  private void handleShelfInteraction() {
    // Handle interaction with the broken glass
    walletCluePane.setVisible(true);
  }

  @FXML
  private void handleCircleClicked(MouseEvent event) {
    Circle circleClicked = (Circle) event.getTarget();
    String currentCircle = circleClicked.getId();
    circlesClicked.put(currentCircle, true);
    circleClicked.setOpacity(1);

    if (circlesClicked.size() >= 4) {
      System.out.println("SHOULD SLIDE OFF!");
      paneTransition(metalPanelPane);
    }
  }

  @FXML
  private void startKeypadFlash(int number) {
    keypadFlash =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0.75),
                e -> {
                  if (number == 1) {
                    keypadUnderline1.setOpacity(0);
                  } else if (number == 2) {
                    keypadUnderline2.setOpacity(0);
                  }
                }),
            new KeyFrame(
                Duration.seconds(1.5),
                e -> {
                  if (number == 1) {
                    keypadUnderline1.setOpacity(1);
                  } else if (number == 2) {
                    keypadUnderline2.setOpacity(1);
                  }
                }));
    keypadFlash.setCycleCount(Animation.INDEFINITE);
    keypadFlash.play();
  }

  private void stopKeypadFlash() {
    keypadFlash.stop();
  }

  @FXML
  private void handleWalletClueClick(MouseEvent event) {
    // Return if the card is already moving.
    if (cardTranslating == true) {
      return;
    }
    // Find which card to move
    ImageView currentImage = (ImageView) event.getTarget();
    String currentIdentification = currentImage.getId();
    ImageView imageToMove = null;
    switch (currentIdentification) {
      case "imageDriversLicense":
        imageToMove = imageDriversLicense;
        break;
      case "imageCreditCard":
        imageToMove = imageCreditCard;
        break;
      case "imageLoyaltyCard":
        imageToMove = imageLoyaltyCard;
        break;
      default:
        break;
    }
    // Find the direction the card should move in
    if (walletClueMap.get(imageToMove) == true) {
      cardTransition(imageToMove, "down");
      walletClueMap.put(imageToMove, false);
    } else {
      cardTransition(imageToMove, "up");
    }
  }

  private void paneTransition(Pane pane) {
    cardTranslate.setNode(pane);
    cardTranslate.setDuration(Duration.millis(1000));
    cardTranslate.setByY(-750);
    cardTranslate.play();
    cardTranslate.setOnFinished(
        event -> {
          pane.setVisible(false);
          System.out.println("Finished");
        });
  }

  private void cardTransition(ImageView image, String direction) {
    // Sets the card to be translated
    cardTranslate.setNode(image);
    image.setVisible(true);
    cardTranslate.setDuration(Duration.millis(200));
    // Find direction for card to move
    if (direction.equals("up")) {
      walletClueMap.put(image, true);
      cardTranslate.setByY(-60);
    } else {
      cardTranslate.setByY(60);
    }
    // Start translating and set variables for if a card is already moving
    cardTranslate.play();
    cardTranslating = true;
    cardTranslate.setOnFinished(
        event -> {
          cardTranslating = false;
        });
  }

  private void handleNewsInteraction() {
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
  private void onCloseWallet() {
    walletCluePane.setVisible(false);
  }

  @FXML
  private void onOpenWallet() {
    walletOpenPane.setVisible(true);
    walletClosedPane.setVisible(false);
  }

  @FXML
  private void handleRectangleHover(MouseEvent event) {
    // Highlights the clue when mouse hovers over it
    switch (((Rectangle) event.getSource()).getId()) {
      case "documents":
        currentHover = documentStroke;
        break;
      case "shelf":
        currentHover = shelfStroke;
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

  @FXML
  private void onMouseLeave() {
    try {
      closeClueThread.interrupt();
      closeClueThread =
          new Thread(
              () -> {
                try {
                  // sleep until 5 sec
                  Thread.sleep(5000);
                  if (closeClueThread.isInterrupted()) { // quit if interrupt
                    return;
                  }
                  // close all clue pane
                  Platform.runLater(
                      () -> {
                        onCloseDocuments();
                        onCloseNewsPaper();
                        onCloseWallet();
                      });
                } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              });
      closeClueThread.start();
    } catch (Exception e) { // in case interrupt casue error
      // TODO: handle exception
    }
  }

  @FXML
  private void onMouseEnter() {
    try {
      // "refresh" the clue pane
      closeClueThread.interrupt();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}

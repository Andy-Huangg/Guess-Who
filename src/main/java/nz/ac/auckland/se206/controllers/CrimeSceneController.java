package nz.ac.auckland.se206.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.speech.TextToSpeech;

public class CrimeSceneController {

  @FXML private Rectangle rectSafe;
  @FXML private Rectangle rectDocuments;
  @FXML private Rectangle rectShelf;
  @FXML private Rectangle rectNewsPaper;
  @FXML private Rectangle keypadUnderline1;
  @FXML private Rectangle keypadUnderline2;
  @FXML private Rectangle unlockRectangle;
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
  @FXML private Pane keypadPane;
  @FXML private Pane keypadLogPane;
  @FXML private ImageView newsStroke;
  @FXML private ImageView shelfStroke;
  @FXML private ImageView documentStroke;
  @FXML private Text keypadNumberDisplay1;
  @FXML private Text keypadNumberDisplay2;
  @FXML private Text keypadNumberDisplayText;

  private TranslateTransition cardTranslate = new TranslateTransition();
  private ImageView currentHover;
  private Thread closeClueThread = new Thread();
  private Timeline keypadFlash;
  private int keypadNumber1;
  private int keypadNumber2;
  private int successfulKeypadNumber = 45;

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
    startKeypadFlash(1);
    keypadNumber1 = -1;
    keypadNumber2 = -1;
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
  private void handleMetalPanel(MouseEvent event) {
    paneTransition(metalPanelPane);
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
  private void handleKeypadClick(MouseEvent event) {
    Rectangle currentRectangle = (Rectangle) event.getTarget();
    String rectangleID = currentRectangle.getId();
    if (rectangleID.equals("keypadEnter")) {
      if (keypadNumber1 > -1 && keypadNumber2 > -1) {
        StringBuilder sb = new StringBuilder();
        sb.append(keypadNumber1);
        sb.append(keypadNumber2);
        int keypadNumber = Integer.parseInt(sb.toString());
        validateKeypadNumber(keypadNumber);
      }
      return;
    }
    TextToSpeech.speakLocally("click");
    char lastLetter = rectangleID.charAt(rectangleID.length() - 1);
    int input = Character.getNumericValue(lastLetter);

    if (keypadNumber1 < 0) {
      keypadNumber1 = input;
      keypadNumberDisplay1.setText(String.valueOf(input));
      stopKeypadFlash();
      startKeypadFlash(2);
      keypadUnderline1.setOpacity(1);
    } else if (keypadNumber2 < 0) {
      keypadNumber2 = input;
      keypadNumberDisplay2.setText(String.valueOf(input));
      stopKeypadFlash();
      keypadUnderline2.setOpacity(1);
    }
  }

  @FXML
  private void validateKeypadNumber(int keypadNumber) {
    if (keypadNumber < successfulKeypadNumber) {
      setKeypadOutcome("ERR: KEY TOO LOW", false);
      TextToSpeech.speakLocally("error");
    } else if (keypadNumber > successfulKeypadNumber) {
      setKeypadOutcome("ERR: KEY TOO HIGH", false);
      TextToSpeech.speakLocally("error");
    } else {
      keypadNumberDisplayText.getStyleClass().add("success");
      setKeypadOutcome("SUCCESS", true);
      TextToSpeech.speakLocally("success");
    }
  }

  @FXML
  private void setKeypadOutcome(String text, boolean correctGuess) {
    keypadUnderline1.setOpacity(0);
    keypadUnderline2.setOpacity(0);
    keypadNumberDisplay1.setText("");
    keypadNumberDisplay2.setText("");
    stopKeypadFlash();
    keypadNumberDisplayText.setText(text);
    if (correctGuess) {
      unlockRectangle.setOpacity(1);
    }
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(
        event -> {
          if (correctGuess) {
            keypadPane.setVisible(false);
            keypadLogPane.setVisible(true);
            return;
          }
          keypadUnderline1.setOpacity(1);
          keypadUnderline2.setOpacity(1);
          startKeypadFlash(1);
          keypadNumber1 = -1;
          keypadNumber2 = -1;
          keypadNumberDisplayText.setText("");
        });
    pause.play();
  }

  private void paneTransition(Pane pane) {
    cardTranslate.setNode(pane);
    cardTranslate.setDuration(Duration.millis(1000));
    cardTranslate.setByY(-750);
    cardTranslate.play();
    cardTranslate.setOnFinished(
        event -> {
          pane.setVisible(false);
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

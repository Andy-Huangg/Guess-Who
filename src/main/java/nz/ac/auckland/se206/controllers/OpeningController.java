package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;

/**
 * Controller class for the opening scene view. Handles user interactions within the opening scene
 * where the user can see the opening animation.
 */
public class OpeningController {

  @FXML private ImageView block1;
  @FXML private Label block1Dialog1;
  @FXML private Label block1Dialog2;

  @FXML private ImageView block2;
  @FXML private Label block2Dialog1;
  @FXML private Label block2Dialog2;

  @FXML private ImageView block3;
  @FXML private Label block3Dialog1;
  @FXML private Label block3Dialog2;

  @FXML private ImageView block4;
  @FXML private Label block4Dialog1;
  @FXML private Label block4Dialog2;

  @FXML private ImageView block5;
  @FXML private Label block5Dialog1;
  @FXML private Label block5Dialog2;

  @FXML private ImageView block6;
  @FXML private Label block6Dialog1;
  @FXML private Label block6Dialog2;

  @FXML private ImageView block7;
  @FXML private Label block7Dialog1;
  @FXML private Label block7Dialog2;

  @FXML private Rectangle coverRect;
  @FXML private ImageView startImage;
  @FXML private Pane startPane;

  private Thread openingThread;

  private TranslateTransition pullFromLeft = new TranslateTransition();
  private TranslateTransition pullFromRight = new TranslateTransition();

  private TranslateTransition textGoRight = new TranslateTransition();
  private TranslateTransition textGoLeft = new TranslateTransition();

  /** Initializes the opening scene view. It will display the opening animation. */
  public void initialize() {
    openingThread =
        new Thread(
            () -> {
              try {
                // animation of the opening scene
                // block1
                Thread.sleep(500);
                startPane.setVisible(false);
                textFadeIn(block1Dialog1);
                imageFadeIn(block1);
                imagePull(true, block1, 1500);
                textGo(false, block1Dialog1, 1300);
                Thread.sleep(500);
                textFadeIn(block1Dialog2);
                textGo(true, block1Dialog2, 800);
                Thread.sleep(1000);
                textFadeOut(block1Dialog1);
                textFadeOut(block1Dialog2);
                imageFadeOut(block1);
                // end of block 1
                Thread.sleep(10);

                // block 2
                imageFadeIn(block2);
                textFadeIn(block2Dialog1);
                imagePull(false, block2, 2000);
                textGo(false, block2Dialog1, 2000);
                Thread.sleep(1000);
                textFadeIn(block2Dialog2);
                textGo(true, block2Dialog2, 1000);
                Thread.sleep(1000);
                textFadeOut(block2Dialog1);
                textFadeOut(block2Dialog2);
                imageFadeOut(block2);
                // end of block 2
                Thread.sleep(10);

                // block 3
                imageFadeIn(block3);
                textFadeIn(block3Dialog1);
                imagePull(true, block3, 1500);
                textGo(false, block3Dialog1, 800);
                Thread.sleep(350);
                textFadeIn(block3Dialog2);
                textGo(true, block3Dialog2, 600);
                Thread.sleep(950);
                textFadeOut(block3Dialog1);
                textFadeOut(block3Dialog2);
                imageFadeOut(block3);
                // end of block 3
                Thread.sleep(10);

                // block 4
                imageFadeIn(block4);
                textFadeIn(block4Dialog1);
                textGo(false, block4Dialog1, 1500);
                imageDown(block4);
                Thread.sleep(1000);
                textFadeIn(block4Dialog2);
                textGo(true, block4Dialog2, 1000);
                Thread.sleep(1550);
                textFadeOut(block4Dialog1);
                textFadeOut(block4Dialog2);
                imageFadeOut(block4);
                // end of block 4
                Thread.sleep(10);

                // block 5
                imageFadeIn(block5);
                textFadeIn(block5Dialog1);
                textGo(false, block5Dialog1, 1500);
                imageUp(block5);
                Thread.sleep(1000);
                textFadeIn(block5Dialog2);
                textGo(true, block5Dialog2, 1000);
                Thread.sleep(1550);
                textFadeOut(block5Dialog1);
                textFadeOut(block5Dialog2);
                imageFadeOut(block5);
                // end of block 5
                Thread.sleep(10);

                // block 6
                imageFadeIn(block6);
                textFadeIn(block6Dialog1);
                textGo(false, block6Dialog1, 1500);
                imageDown(block6);
                Thread.sleep(1000);
                textFadeIn(block6Dialog2);
                textGo(true, block6Dialog2, 1000);
                Thread.sleep(1550);
                textFadeOut(block6Dialog1);
                textFadeOut(block6Dialog2);
                imageFadeOut(block6);
                // end of block 6 block
                Thread.sleep(10);

                // block 7
                imageFadeIn(block7);
                textFadeIn(block7Dialog1);
                textGo(false, block7Dialog1, 1500);
                Thread.sleep(1000);
                textFadeIn(block7Dialog2);
                textGo(true, block7Dialog2, 1000);
                Thread.sleep(1200);
                // transit to black screen
                handleSkipClick(null);
              } catch (Exception e) {
                // do nothing
              }
            });
  }

  /**
   * This method is used to fade in the image.
   *
   * @param temp the image to be faded in
   */
  public void imageFadeIn(ImageView temp) {
    // fade in the image
    Platform.runLater(
        () -> {
          FadeTransition fadeIn = new FadeTransition();
          fadeIn.setToValue(0.60);
          fadeIn.setDuration(Duration.millis(750));
          fadeIn.setNode(temp);
          fadeIn.play();
        });
  }

  /**
   * This method is used to fade out the image.
   *
   * @param temp the image to be faded out
   */
  public void imageFadeOut(ImageView temp) {
    //  fade out the image
    Platform.runLater(
        () -> {
          FadeTransition fadeOut = new FadeTransition();
          fadeOut.setToValue(0);
          fadeOut.setDuration(Duration.millis(500));
          fadeOut.setNode(temp);
          fadeOut.play();
        });
  }

  /**
   * This method is used to pull the image to the left or right.
   *
   * @param isLeft true if the image is pulled to the left, false if the image is pulled to the
   *     right
   * @param temp the image to be pulled
   * @param time the time taken to pull the image
   */
  public void imagePull(boolean isLeft, ImageView temp, int time) {
    // move the image to the left or right
    if (isLeft) {
      Platform.runLater(
          () -> {
            pullFromLeft.setDuration(Duration.millis(time));
            pullFromLeft.setByX(-1 * time / 160);
            pullFromLeft.setNode(temp);
            pullFromLeft.play();
          });
    } else {
      Platform.runLater(
          () -> {
            pullFromRight.setDuration(Duration.millis(time));
            pullFromRight.setByX(time / 160);
            pullFromRight.setNode(temp);
            pullFromRight.play();
          });
    }
  }

  /**
   * This method is used to fade in the text.
   *
   * @param temp the text to be faded in
   */
  public void textFadeIn(Label temp) {
    // fade in the text
    Platform.runLater(
        () -> {
          FadeTransition textFadeIn = new FadeTransition();
          textFadeIn.setToValue(1);
          textFadeIn.setDuration(Duration.millis(500));
          textFadeIn.setNode(temp);
          textFadeIn.play();
        });
  }

  /**
   * This method is used to fade out the text.
   *
   * @param temp the text to be faded out
   */
  public void textFadeOut(Label temp) {
    // fade out the text
    Platform.runLater(
        () -> {
          FadeTransition textFadeOut = new FadeTransition();
          textFadeOut.setToValue(0);
          textFadeOut.setDuration(Duration.millis(500));
          textFadeOut.setNode(temp);
          textFadeOut.play();
        });
  }

  /**
   * This method is used to move the text to the left or right.
   *
   * @param isLeft true if the text is moved to the left, false if the text is moved to the right
   * @param temp the text to be moved
   * @param time the time taken to move the text
   */
  public void textGo(boolean isLeft, Label temp, int time) {
    // move the text to the left or right
    if (isLeft) {
      Platform.runLater(
          () -> {
            textGoLeft.setDuration(Duration.millis(time));
            textGoLeft.setByX(-1 * time / 160);
            textGoLeft.setNode(temp);
            textGoLeft.play();
          });
    } else {
      Platform.runLater(
          () -> {
            textGoRight.setDuration(Duration.millis(time));
            textGoRight.setByX(time / 160);
            textGoRight.setNode(temp);
            textGoRight.play();
          });
    }
  }

  /**
   * This method is used to move the image up.
   *
   * @param temp the image to be moved up
   */
  public void imageUp(ImageView temp) {
    // move the image up
    TranslateTransition goUp = new TranslateTransition();
    goUp.setDuration(Duration.millis(3000));
    goUp.setByY(2800 / 160);
    goUp.setNode(temp);
    goUp.play();
  }

  /**
   * This method is used to move the image down.
   *
   * @param temp the image to be moved down
   */
  public void imageDown(ImageView temp) {
    //  move the image down
    TranslateTransition goDown = new TranslateTransition();
    goDown.setDuration(Duration.millis(2800));
    goDown.setByY(-1 * 2800 / 160);
    goDown.setNode(temp);
    goDown.play();
  }

  /** This method is used to end the opening scene. */
  public void endOpening() {
    // fade in the black screen
    FadeTransition temp = new FadeTransition();
    temp.setToValue(1);
    temp.setDuration(Duration.millis(1300));
    temp.setNode(coverRect);
    coverRect.setVisible(true);
    temp.play();
  }

  /**
   * This method is used to handle the skip button click event.
   *
   * @param event the event of the skip button click
   * @throws IOException if the "src/main/resources/fxml/mainLayout.fxml" file is not found
   */
  @FXML
  public void handleSkipClick(ActionEvent event) throws IOException {
    openingThread.interrupt(); // stop the animation thread
    // use timeline to schecule the transition
    KeyFrame f1 = new KeyFrame(Duration.millis(0), e -> endOpening());
    KeyFrame f3 = new KeyFrame(Duration.millis(250), e -> App.preloadMainLayout(coverRect));
    KeyFrame f2 =
        new KeyFrame(
            Duration.millis(1650),
            e -> {
              try {
                App.switchMainGame(coverRect);
              } catch (IOException e1) {
                e1.printStackTrace();
              }
            });
    Timeline tl = new Timeline(f1, f3, f2);
    if (event != null) { // disable button if access by click skip button
      Button temp = (Button) event.getSource();
      temp.setDisable(true);
    }
    Platform.runLater(
        () -> {
          tl.play();
        });
  }

  /**
   * This method is used to handle the start button click event.
   *
   * @param event the event of the start button click
   */
  @FXML
  public void handleStartClick(ActionEvent event) {
    // sliding the start button and image to the left
    TranslateTransition temp1 = new TranslateTransition();
    temp1.setByX(-1000);
    temp1.setDuration(Duration.millis(500));
    temp1.setNode((Button) event.getSource());
    temp1.play();

    TranslateTransition temp2 = new TranslateTransition();
    temp2.setByX(-1000);
    temp2.setDuration(Duration.millis(500));
    temp2.setNode(startImage);
    temp2.play();

    openingThread.start();
  }
}

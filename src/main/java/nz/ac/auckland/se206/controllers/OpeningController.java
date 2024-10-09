package nz.ac.auckland.se206.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class OpeningController {

  @FXML ImageView block1;
  @FXML Label block1Dialog1;
  @FXML Label block1Dialog2;

  @FXML ImageView block2;
  @FXML Label block2Dialog1;
  @FXML Label block2Dialog2;

  @FXML ImageView block3;
  @FXML Label block3Dialog1;
  @FXML Label block3Dialog2;

  @FXML ImageView block4;
  @FXML Label block4Dialog1;
  @FXML Label block4Dialog2;

  @FXML ImageView block5;
  @FXML Label block5Dialog1;
  @FXML Label block5Dialog2;

  @FXML ImageView block6;
  @FXML Label block6Dialog1;
  @FXML Label block6Dialog2;

  @FXML ImageView block7;
  @FXML Label block7Dialog1;
  @FXML Label block7Dialog2;

  @FXML Rectangle coverRect;

  Thread openingThread;

  TranslateTransition pullFromLeft = new TranslateTransition();
  TranslateTransition pullFromRight = new TranslateTransition();

  TranslateTransition textGoRight = new TranslateTransition();
  TranslateTransition textGoLeft = new TranslateTransition();

  public void initialize() {

    openingThread =
        new Thread(
            () -> {
              try {
                // block1
                textFadeIn(block1Dialog1);
                imageFadeIn(block1);
                imagePull(true, block1, 1500);
                textGo(false, block1Dialog1, 1300);
                Thread.currentThread().sleep(500);
                textFadeIn(block1Dialog2);
                textGo(true, block1Dialog2, 800);
                Thread.currentThread().sleep(1000);
                textFadeOut(block1Dialog1);
                textFadeOut(block1Dialog2);
                imageFadeOut(block1);
                // end of block 1
                Thread.currentThread().sleep(10);

                // block 2
                imageFadeIn(block2);
                textFadeIn(block2Dialog1);
                imagePull(false, block2, 2000);
                textGo(false, block2Dialog1, 2000);
                Thread.currentThread().sleep(1000);
                textFadeIn(block2Dialog2);
                textGo(true, block2Dialog2, 1000);
                Thread.currentThread().sleep(1100);
                textFadeOut(block2Dialog1);
                textFadeOut(block2Dialog2);
                imageFadeOut(block2);
                Thread.currentThread().sleep(10);

                imageFadeIn(block3);
                textFadeIn(block3Dialog1);
                imagePull(true, block3, 1500);
                textGo(false, block3Dialog1, 800);
                Thread.currentThread().sleep(350);
                textFadeIn(block3Dialog2);
                textGo(true, block3Dialog2, 600);
                Thread.currentThread().sleep(950);
                textFadeOut(block3Dialog1);
                textFadeOut(block3Dialog2);
                imageFadeOut(block3);
                Thread.currentThread().sleep(10);

                imageFadeIn(block4);
                textFadeIn(block4Dialog1);
                textGo(false, block4Dialog1, 1500);
                imageDown(block4);
                Thread.currentThread().sleep(1000);
                textFadeIn(block4Dialog2);
                textGo(true, block4Dialog2, 1000);
                Thread.currentThread().sleep(1750);
                textFadeOut(block4Dialog1);
                textFadeOut(block4Dialog2);
                imageFadeOut(block4);
                Thread.currentThread().sleep(10);

                imageFadeIn(block5);
                textFadeIn(block5Dialog1);
                textGo(false, block5Dialog1, 1500);
                imageUp(block5);
                Thread.currentThread().sleep(1000);
                textFadeIn(block5Dialog2);
                textGo(true, block5Dialog2, 1000);
                Thread.currentThread().sleep(1750);
                textFadeOut(block5Dialog1);
                textFadeOut(block5Dialog2);
                imageFadeOut(block5);
                Thread.currentThread().sleep(10);

                imageFadeIn(block6);
                textFadeIn(block6Dialog1);
                textGo(false, block6Dialog1, 1500);
                imageDown(block6);
                Thread.currentThread().sleep(1000);
                textFadeIn(block6Dialog2);
                textGo(true, block6Dialog2, 1000);
                Thread.currentThread().sleep(1750);
                textFadeOut(block6Dialog1);
                textFadeOut(block6Dialog2);
                imageFadeOut(block6);
                Thread.currentThread().sleep(10);

                imageFadeIn(block7);
                textFadeIn(block7Dialog1);
                textGo(false, block7Dialog1, 1500);
                Thread.currentThread().sleep(1000);
                textFadeIn(block7Dialog2);
                textGo(true, block7Dialog2, 1000);
                Thread.currentThread().sleep(1750);
                // transit to black screen
                endOpening();
              } catch (Exception e) {
              }
            });
    openingThread.start();
  }

  public void imageFadeIn(ImageView temp) {
    Platform.runLater(
        () -> {
          FadeTransition fadeIn = new FadeTransition();
          fadeIn.setToValue(0.60);
          fadeIn.setDuration(Duration.millis(750));
          fadeIn.setNode(temp);
          fadeIn.play();
        });
  }

  public void imageFadeOut(ImageView temp) {
    Platform.runLater(
        () -> {
          FadeTransition fadeOut = new FadeTransition();
          fadeOut.setToValue(0);
          fadeOut.setDuration(Duration.millis(500));
          fadeOut.setNode(temp);
          fadeOut.play();
        });
  }

  public void imagePull(boolean isLeft, ImageView temp, int time) {
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

  public void textFadeIn(Label temp) {
    Platform.runLater(
        () -> {
          FadeTransition textFadeIn = new FadeTransition();
          textFadeIn.setToValue(1);
          textFadeIn.setDuration(Duration.millis(500));
          textFadeIn.setNode(temp);
          textFadeIn.play();
        });
  }

  public void textFadeOut(Label temp) {
    Platform.runLater(
        () -> {
          FadeTransition textFadeOut = new FadeTransition();
          textFadeOut.setToValue(0);
          textFadeOut.setDuration(Duration.millis(500));
          textFadeOut.setNode(temp);
          textFadeOut.play();
        });
  }

  public void textGo(boolean isLeft, Label temp, int time) {
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

  public void imageUp(ImageView temp) {
    TranslateTransition goUp = new TranslateTransition();
    goUp.setDuration(Duration.millis(3000));
    goUp.setByY(2800 / 160);
    goUp.setNode(temp);
    goUp.play();
  }

  public void imageDown(ImageView temp) {
    TranslateTransition goDown = new TranslateTransition();
    goDown.setDuration(Duration.millis(2800));
    goDown.setByY(-1 * 2800 / 160);
    goDown.setNode(temp);
    goDown.play();
  }

  public void endOpening() {
    FadeTransition temp = new FadeTransition();
    temp.setToValue(1);
    temp.setDuration(Duration.millis(2000));
    temp.setNode(coverRect);
    coverRect.setVisible(true);
    temp.play();
  }

  @FXML
  public void handleSkipClick(ActionEvent event) {
    openingThread.interrupt();
    Button temp = (Button) event.getSource();
    temp.setDisable(true);
    endOpening();
  }
}

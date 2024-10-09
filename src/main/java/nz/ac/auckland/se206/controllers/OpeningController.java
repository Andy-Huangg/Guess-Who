package nz.ac.auckland.se206.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

  Thread openingThread;

  TranslateTransition blockAnimation = new TranslateTransition();
  int clickCount = 0;
  double opacity = 0;

  FadeTransition fadeIn = new FadeTransition();
  FadeTransition fadeOut = new FadeTransition();

  FadeTransition textFadeIn = new FadeTransition();
  FadeTransition textFadeOut = new FadeTransition();

  TranslateTransition pullFromLeft = new TranslateTransition();
  TranslateTransition pullFromRight = new TranslateTransition();

  TranslateTransition textGoRight = new TranslateTransition();
  TranslateTransition textGoLeft = new TranslateTransition();

  public void initialize() {

    fadeIn.setFromValue(0);
    fadeIn.setToValue(0.55);
    fadeIn.setDuration(Duration.millis(1000));

    fadeOut.setFromValue(0.55);
    fadeOut.setToValue(0);
    fadeOut.setDuration(Duration.millis(500));

    textFadeIn.setToValue(1);
    textFadeIn.setDuration(Duration.millis(500));

    textFadeOut.setFromValue(1);
    textFadeOut.setToValue(0);
    textFadeOut.setDuration(Duration.millis(300));

    pullFromLeft.setByX(-50);
    pullFromLeft.setDuration(Duration.millis(8000));
    pullFromRight.setByX(50);
    pullFromRight.setDuration(Duration.millis(8000));

    openingThread =
        new Thread(
            () -> {
              try {
                textFadeIn(block1Dialog1);
                imageFadeIn(block1);
                imagePull(true, block1);
                textGo(true, block1Dialog1);
                Thread.currentThread().sleep(500);

                textFadeIn(block1Dialog2);
                textGo(true, block1Dialog2);
              } catch (Exception e) {
              }
            });
    openingThread.start();
  }

  public void imageFadeIn(ImageView temp) {
    Platform.runLater(
        () -> {
          fadeIn.setNode(temp);
          fadeIn.play();
        });
  }

  public void imageFadeOut(ImageView temp) {
    Platform.runLater(
        () -> {
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
          textFadeIn.setNode(temp);
          textFadeIn.play();
        });
  }

  public void textFadeOut(Label temp) {
    Platform.runLater(
        () -> {
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
}

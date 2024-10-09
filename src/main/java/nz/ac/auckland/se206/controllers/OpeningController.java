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

    pullFromLeft.setByX(-10);
    pullFromLeft.setDuration(Duration.millis(1600));
    pullFromRight.setByX(50);
    pullFromRight.setDuration(Duration.millis(1600));

    textGoLeft.setByX(50);
    textGoLeft.setDuration(Duration.millis(8000));

    textGoRight.setByY(50);
    textGoRight.setDuration(Duration.millis(8000));

    openingThread =
        new Thread(
            () -> {
              Platform.runLater(
                  () -> {
                    textFadeIn(block1Dialog1);
                    imageFadeIn(block1);
                    imagePull(true, block1);
                    textGo(true, block1Dialog1);
                    textFadeIn(block1Dialog2);
                    textGo(true, block1Dialog2);
                  });
            });
    openingThread.start();
  }

  public void imageFadeIn(ImageView temp) {
    fadeIn.setNode(temp);
    fadeIn.play();
  }

  public void imageFadeOut(ImageView temp) {
    fadeOut.setNode(temp);
    fadeOut.play();
  }

  public void imagePull(boolean isLeft, ImageView temp) {
    if (isLeft) {
      pullFromLeft.setNode(temp);
      pullFromLeft.play();
    } else {
      pullFromRight.setNode(temp);
      pullFromRight.play();
    }
  }

  public void textFadeIn(Label temp) {
    textFadeIn.setNode(temp);
    textFadeIn.play();
  }

  public void textFadeOut(Label temp) {
    textFadeOut.setNode(temp);
    textFadeOut.play();
  }

  public void textGo(boolean isLeft, Label temp) {
    if (isLeft) {
      textGoLeft.setNode(temp);
      textGoLeft.play();
    } else {
      textGoRight.setNode(temp);
      textGoRight.play();
    }
  }
}

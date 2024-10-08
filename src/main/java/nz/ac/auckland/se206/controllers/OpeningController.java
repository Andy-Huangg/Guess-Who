package nz.ac.auckland.se206.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class OpeningController {

  @FXML ImageView block1;
  @FXML ImageView block2;
  @FXML ImageView block3;
  @FXML ImageView block4;
  @FXML ImageView mouseImage;
  @FXML Label nameLabel;
  @FXML Label dialogLabel;
  @FXML Rectangle nameRectangle;
  @FXML Rectangle dialogRectangle;
  @FXML Rectangle coverRectangle;

  ImageView currentBlock;

  TranslateTransition blockAnimation = new TranslateTransition();
  int clickCount = 0;
  double opacity = 0;

  FadeTransition coverAnimation = new FadeTransition();

  String[] dialogArray = {
    "",
    "You must be the detective from PI Master. I’m Hawthorne, the owner of this house.",
    " Come on in, I’ll tell you what happened.",
    "",
    " You see, I have got a gala hosting tonight, and 3 of my guests are here already.",
    " I left at around 2:20pm for some party stuff. 30 mins later when I got back, I saw my safe"
        + " opened and the jewellery was missing.",
    "",
    "That \"Sapphire of Versailles\", was supposed to be present tonight. It’s a priceless family"
        + " heirloom of mine.",
    "Shame to say that I forgot to lock the safe last night, but luckily I got you here.",
    "",
    "There we are, the study room, where I kept my jewellery. the thief must have left some clues"
        + " here.",
    "Good luck detective, found the thief among them three. I’ll see you at 4pm. Don’t let me down."
  };

  public void initialize() {

    // setting animation properties
    coverAnimation.setFromValue(0);
    coverAnimation.setToValue(1);
    coverAnimation.setByValue(0.05);
    coverAnimation.setNode(coverRectangle);
    coverAnimation.setDuration(Duration.millis(2000));

    currentBlock = block1;
    blockAnimation.setByX(8);
    blockAnimation.setAutoReverse(true);
    blockAnimation.setDuration(Duration.millis(150));
    blockAnimation.setCycleCount(2);
    blockAnimation.setNode(currentBlock);
  }

  @FXML
  public void progress() {
    mouseImage.setVisible(false);
    if (clickCount == 1) { // enableing the chatbox related nodes
      nameLabel.setVisible(true);
      nameRectangle.setVisible(true);
      dialogLabel.setVisible(true);
      dialogRectangle.setVisible(true);
    }
    // prevent further click when all dialog appeared
    if (clickCount > 12) {
      return;
    }
    System.out.println(clickCount);
    updateDialog();
    showBlock();
    clickCount++;
  }

  public void updateDialog() {
    if (clickCount == 12) {
      transitionToGame();
      return;
    }
    if (clickCount == 2) {
      nameLabel.setText("Hawthorne");
    }
    dialogLabel.setText(dialogArray[clickCount]);
  }

  public void showBlock() {
    // play the correspond animation accroding to clue count
    if (clickCount == 0) {
      blockAnimation.play();
      currentBlock.setVisible(true);
      currentBlock = block2;
      blockAnimation.setNode(currentBlock);
    }
    if (clickCount == 3) {
      blockAnimation.play();
      currentBlock.setVisible(true);
      currentBlock = block3;
      blockAnimation.setNode(currentBlock);
    }
    if (clickCount == 6) {
      blockAnimation.play();
      currentBlock.setVisible(true);
      currentBlock = block4;
      blockAnimation.setNode(currentBlock);
    }
    if (clickCount == 9) {
      blockAnimation.play();
      currentBlock.setVisible(true);
    }
  }

  public void transitionToGame() {
    coverAnimation.play();
    // to mainlayour controller
  }
}

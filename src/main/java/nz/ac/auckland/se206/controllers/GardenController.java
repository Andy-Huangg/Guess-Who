package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;

public class GardenController extends ChatSceneController {

  @FXML private Rectangle rectBruce;

  public void initialize() {
    super.initialize("Bruce");
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws InterruptedException {
    enableChat();
    rectBruce.setDisable(true);
    if (!App.isBruceInteracted()) { // if bruce is talked for the first time
      playIntroAudio("Bruce_intro.mp3");
      // Make the text animate in
      TextAnimator intro =
          new TextAnimator(
              "Ah Detective, I’m Bruce. I was just looking around when all this happened.",
              txtChat);
      intro.startAnimation();
    } else {
      // if have talked to bruce before
      playIntroAudio("Bruce_return.mp3");
      TextAnimator text =
          new TextAnimator("Detective, back already? How’s it going on your end?", txtChat);
      text.run();
    }
    chatHandler.setCharacter("bruce");
  }

  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setBruceInteracted(interacted);
  }
}

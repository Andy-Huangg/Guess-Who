package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;

public class LivingRoomController extends ChatSceneController {

  @FXML private Rectangle rectSaul;

  public void initialize() {
    super.initialize("Saul");
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws InterruptedException {
    enableChat();
    rectSaul.setDisable(true);
    if (!App.isSaulInteracted()) { // if talked to saul for the first time
      playIntroAudio("Saul_intro.mp3");
      TextAnimator text =
          new TextAnimator(
              "Hi Detective, Saul here. Strange to be involved in something like this.", txtChat);
      text.startAnimation();
    } else { // if talked to saul before
      playIntroAudio("Saul_return.mp3");
      TextAnimator text =
          new TextAnimator("Detective, what can I help you with this time?", txtChat);
      text.startAnimation();
    }
    chatHandler.setCharacter("saul");
  }

  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setSaulInteracted(interacted);
  }
}

package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;

public class GardenController extends ChatSceneController {

  public void initialize() {
    super.initialize("Bruce");
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws InterruptedException {
    enableChat();
    if (!App.isBruceInteracted()) {
      playIntroAudio("Bruce_intro.mp3");
      txtChat.appendText(
          "Ah Detective, I’m Bruce. I was just looking around when all this happened.");
    } else {
      playIntroAudio("Bruce_return.mp3");
      txtChat.appendText("Detective, back already? How’s it going on your end?");
    }
    chatHandler.setCharacter("bruce");
  }

  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setBruceInteracted(interacted);
  }
}

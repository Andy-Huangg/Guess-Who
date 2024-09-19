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
      playIntroAudio("Bruce.mp3");
      txtChat.appendText(
          "Ah Detective, I’m Bruce. I was just looking around when all this happened.");
    }
    chatHandler.setCharacter("bruce");
  }

  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setBruceInteracted(interacted);
  }
}

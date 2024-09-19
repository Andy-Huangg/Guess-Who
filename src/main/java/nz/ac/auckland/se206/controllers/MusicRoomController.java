package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;

public class MusicRoomController extends ChatSceneController {

  public void initialize() {
    super.initialize("Alfred");
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws InterruptedException {
    enableChat();
    playIntroAudio("Alfred.mp3");
    txtChat.appendText(
        "Hello, Detective, I’m Alfred. I hope I can be of some help to your investigation.");
    chatHandler.setCharacter("alfred");
  }

  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setAlfredInteracted(interacted);
  }
}

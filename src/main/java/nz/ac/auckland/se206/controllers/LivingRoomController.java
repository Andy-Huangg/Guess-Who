package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;

public class LivingRoomController extends ChatSceneController {

  public void initialize() {
    super.initialize("Saul");
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws InterruptedException {
    enableChat();
    playIntroAudio("Saul.mp3");
    txtChat.appendText("Hi Detective, Saul here. Strange to be involved in something like this.");
    chatHandler.setCharacter("saul");
  }

  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setSaulInteracted(interacted);
  }
}

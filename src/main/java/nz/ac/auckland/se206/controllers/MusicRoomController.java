package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;

/**
 * Controller class for the music room view. Handles user interactions within the music room and
 * Alfred.
 */
public class MusicRoomController extends ChatSceneController {

  @FXML private Rectangle rectAlfred;

  public void initialize() throws InterruptedException {
    super.initialize("Alfred");
    startChat();
  }

  /**
   * Start the chat with Alfred.
   *
   * @throws InterruptedException when the thread is interrupted
   */
  @FXML
  private void startChat() throws InterruptedException {
    if (!App.isAlfredInteracted()) { // if talk to alfred for the first time
      playIntroAudio("Alfred_intro.mp3");
      TextAnimator text =
          new TextAnimator(
              "Hello, Detective, I’m Alfred. I hope I can be of some help to your investigation.",
              txtChat);
      text.startAnimation();
    } else { // if talked to alfred before
      playIntroAudio("Alfred_return.mp3");
      TextAnimator text =
          new TextAnimator("Hello again, Detective. Anything else you need from me?", txtChat);
      text.startAnimation();
    }
    chatHandler.setCharacter("alfred");
  }

  /**
   * Set the interacted flag.
   *
   * @param interacted the interacted flag
   */
  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setAlfredInteracted(interacted);
  }
}

package nz.ac.auckland.se206.controllers;

import nz.ac.auckland.se206.App;

/** Controller for the living room scene with Saul. */
public class LivingRoomController extends ChatSceneController {

  public void initialize() throws InterruptedException {
    super.initialize("Saul");
    startChat();
  }

  /**
   * Start the chat with Saul.
   *
   * @throws InterruptedException when the thread is interrupted
   */
  private void startChat() throws InterruptedException {
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

  /**
   * Set the interacted flag.
   *
   * @param interacted the interacted flag
   */
  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setSaulInteracted(interacted);
  }
}

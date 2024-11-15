package nz.ac.auckland.se206.controllers;

import nz.ac.auckland.se206.App;

/** Controller for the garden scene with Bruce. */
public class GardenController extends ChatSceneController {

  public void initialize() throws InterruptedException {
    super.initialize("Bruce");
    startChat();
  }

  /**
   * Start the chat with Bruce.
   *
   * @throws InterruptedException when the thread is interrupted
   */
  private void startChat() throws InterruptedException {
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

  /**
   * Set the interacted flag.
   *
   * @param interacted the interacted flag
   */
  @Override
  protected void setInteractedFlag(boolean interacted) {
    App.setBruceInteracted(interacted);
  }
}

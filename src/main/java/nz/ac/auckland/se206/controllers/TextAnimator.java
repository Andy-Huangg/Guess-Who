package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/** Class to animate text in a TextArea. */
public class TextAnimator implements Runnable {

  private static boolean isRunning = false;
  private static int runningCount = 0;

  @FXML private TextArea textOutput;

  private int animationTime = 30;
  private String text;

  /**
   * Constructor for TextAnimator.
   *
   * @param text the text to animate
   * @param textField the TextArea to output the text
   */
  public TextAnimator(String text, TextArea textField) {
    this.text = text;
    this.textOutput = textField;
  }

  public static boolean getIsRunning() {
    return isRunning;
  }

  public static void setRunningCount(int value) {
    runningCount = value;
  }

  /** Start the text animation at given speed. */
  public void startAnimation() {
    while (isRunning) {
      try {
        Thread.sleep(5); // pause a bit
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    Thread thread = new Thread(this);
    thread.start();
  }

  /** Get the running count. */
  @Override
  public void run() {
    // increment running count
    runningCount += 1;
    Task<Void> task =
        new Task<Void>() {
          protected Void call() throws Exception {
            // animate text
            isRunning = true;
            for (int i = 1; i <= text.length(); i++) {
              int j = i;
              String character = text.substring(i - 1, i);
              Platform.runLater(
                  () -> {
                    // append character by character
                    textOutput.appendText(character);
                    if (j == text.length() && runningCount == 2) {
                      ChatSceneController.readyToSendMessage = true;
                    }
                  });
              // pause 30miliseconds for every character
              Thread.sleep(animationTime);
            }
            isRunning = false;
            return null;
          }
        };

    // start the thread
    Thread backgroundThread = new Thread(task);
    backgroundThread.setDaemon(true);
    backgroundThread.start();
  }
}

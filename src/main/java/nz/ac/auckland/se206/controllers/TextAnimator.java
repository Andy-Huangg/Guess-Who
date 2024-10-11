package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TextAnimator implements Runnable {
  @FXML private TextArea textOutput;
  private static boolean isRunning = false;
  private static int runningCount = 0;
  private int animationTime = 30;
  private String text;

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

  @Override
  public void run() {
    runningCount += 1;
    Task<Void> task =
        new Task<Void>() {
          protected Void call() throws Exception {
            isRunning = true;
            for (int i = 1; i <= text.length(); i++) {
              int j = i;
              String character = text.substring(i - 1, i);
              Platform.runLater(
                  () -> {
                    textOutput.appendText(character);
                    if (j == text.length() && runningCount == 2) {
                      ChatSceneController.readyToSendMessage = true;
                    }
                  });
              Thread.sleep(animationTime);
            }
            isRunning = false;
            return null;
          }
        };

    Thread backgroundThread = new Thread(task);
    backgroundThread.setDaemon(true);
    backgroundThread.start();
  }

  public void startAnimation() {
    while (isRunning) {
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    Thread thread = new Thread(this);
    thread.start();
  }
}

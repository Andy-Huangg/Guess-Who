package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

public class TextAnimator implements Runnable {
  private static boolean isRunning = false;
  private String text;
  private int animationTime = 30;
  private TextArea textOutput;

  public TextAnimator(String text, TextArea textField) {
    this.text = text;
    this.textOutput = textField;
  }

  @Override
  public void run() {
    Task<Void> task =
        new Task<Void>() {
          protected Void call() throws Exception {
            isRunning = true;
            for (int i = 1; i <= text.length(); i++) {
              String character = text.substring(i - 1, i);
              Platform.runLater(() -> textOutput.appendText(character));
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
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    Thread thread = new Thread(this);
    thread.start();
  }
}

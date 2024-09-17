package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.ChatHandler;

public class GuessController {

  @FXML private Label timeLabel, resultLabel, explainLabel, suspectSelectedLabel;
  @FXML private TextArea answerText;
  @FXML private Button SubmitBtn;
  @FXML private Pane suspectSelectedPane;

  private String suspectSelected;
  private int timeCount = 60;

  public void initialize() {
    // load the prompt at the start;
    Thread loadingPrompt =
        new Thread(
            () -> {
              ChatHandler.setCharacter("owner");
            });
    loadingPrompt.start();
    Thread timer = // very ugly looking but will work as a timer
        new Thread(
            () -> {
              while (timeCount >= 0) {
                Platform.runLater(
                    () -> {
                      timeLabel.setText("You have " + timeCount + " seconds remaining");
                    });
                timeCount--;
                try {
                  Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              }
              onSumbit();
            });
    timer.start();
  }

  @FXML
  private void getSuspect(MouseEvent event) {
    ImageView selected = (ImageView) event.getSource();
    enableSuspectSelectedPane(selected.getId());
  }

  private void enableSuspectSelectedPane(String id) {
    suspectSelectedPane.setVisible(true);
    suspectSelected = id;
    suspectSelectedLabel.setText("Well done... Why do you think " + id + " is the theif?");
  }

  @FXML
  private void onBack() {
    suspectSelectedPane.setVisible(false);
  }

  @FXML
  private void onSumbit() {}

  @FXML
  private void onRestart() {}
}

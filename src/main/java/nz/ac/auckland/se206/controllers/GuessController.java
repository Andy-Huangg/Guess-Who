package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.ChatHandler;

public class GuessController {

  @FXML Label timeLabel, resultLabel, explainLabel, suspectSelectedLabel;
  @FXML TextArea answerText;
  @FXML Button SubmitBtn;
  @FXML Pane suspectSelectedPane;

  String suspectSelected;

  public void initialize() {
    // load the prompt at the start;
    Thread loadingPrompt =
        new Thread(
            () -> {
              ChatHandler.setCharacter("owner");
            });
    loadingPrompt.start();
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

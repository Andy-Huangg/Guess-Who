package nz.ac.auckland.se206.controllers;

import java.util.Random;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.ChatHandler;

public class GuessController {

  @FXML private Label timeLabel, resultLabel, explainLabel, suspectSelectedLabel, ownerLabel;
  @FXML private TextArea answerText;
  @FXML private Button SubmitBtn;
  @FXML private Pane suspectSelectedPane;

  private String suspectSelected;
  private int timeCount = 61;
  private String[] responseList = {
    "Are you being serious right now? Me? Really?",
    "Yea yea you're right, I set up the whole thing just for fun.",
    "Don't look at me, there is no answer on my face!",
    "I think I have given you too much time to muck around...",
  };
  private ChatHandler chatHandler;

  public void initialize() {
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
              onSubmit(null);
            });
    timer.start();

    // load the prompt at the start;
    Thread loadingPrompt =
        new Thread(
            () -> {
              try {
                chatHandler.setCharacter("owner");
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
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
  private void onSubmit(ActionEvent event) {}

  @FXML
  private void onRestart(ActionEvent event) {}

  @FXML
  private void handleOwnerClick() {
    int rnd = new Random().nextInt(4);
    ownerLabel.setText(responseList[rnd]);
  }
}

package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.ChatHandler;

public class GuessController extends ChatSceneController {

  @FXML private Label timeLabel, suspectSelectedLabel, ownerLabel;
  @FXML private TextArea answerText;
  @FXML private Pane suspectSelectedPane;
  @FXML private Pane resultPane;

  private String suspectSelected;
  private ChatHandler chatHandler = new ChatHandler("owner");
  private int timeCount = 61;
  private String[] responseList = {
    "Are you being serious right now? Me? Really?",
    "Yea yea you're right, I set up the whole thing just for fun.",
    "Don't look at me, there is no answer on my face!",
    "I think I have given you too much time to muck around...",
  };

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
                  e.printStackTrace();
                }
              }

              try {
                onSubmit(null);
              } catch (ApiProxyException | IOException e) {
                e.printStackTrace();
              }
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
  private void setSuspect(MouseEvent event) {
    ImageView selected = (ImageView) event.getSource();
    suspectSelected = selected.getId();
  }

  @FXML
  private void getSuspect(MouseEvent event) throws IOException {
    if (suspectSelected == null) {
      return;
    }
    if (suspectSelected.equals("Bruce")) {
      App.setWinner(true);
      enableReasoningPane("Bruce");
    } else {
      App.setWinner(false);
      App.openEndGameWindow(timeLabel);
    }
  }

  private void enableReasoningPane(String id) {
    suspectSelectedPane.setVisible(true);
    suspectSelected = id;
    suspectSelectedLabel.setText("Well done... Why do you think " + id + " is the thief?");
  }

  @FXML
  private void onSubmit(ActionEvent event) throws ApiProxyException, IOException {

    String userInput = answerText.getText().strip();
    if (!userInput.equals("")) {
      App.setGuessReason(userInput);
    } else {
      App.setGuessReason("No reason given");
    }
    App.openEndGameWindow(timeLabel);
  }

  @FXML
  private void handleOwnerClick() {
    int rnd = new Random().nextInt(4);
    ownerLabel.setText(responseList[rnd]);
  }

  @Override
  public void appendChatMessage(ChatMessage msg) {
    Platform.runLater(() -> txtChat.appendText(msg.getContent() + "\n"));
  }

  @Override
  protected void setInteractedFlag(boolean interacted) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setInteractedFlag'");
  }
}
